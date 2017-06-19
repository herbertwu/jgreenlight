package com.jgreenlight.core.script;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jgreenlight.core.script.def.RunnableScript;
import com.jgreenlight.core.script.def.ScriptRun;
import com.jgreenlight.core.script.def.ScriptRunInfo;
import com.jgreenlight.core.script.def.ScriptRunResult;
import com.jgreenlight.core.script.def.ScriptRunStatus;
import com.jgreenlight.core.script.source.ScriptSource;
@Component
public class ScriptExecutor {
	private static final Logger log =  LoggerFactory.getLogger(ScriptExecutor.class);
	
	private RunnableScriptBuilder executableBuilder;
	private RunnableScriptRepo runnableScriptRepo;
	private ScriptRunRepo scriptRunRepo;
    private String idFileLocation;
	private ExecutorService executor;
	private Map<String,String> outputs;
	
	public ScriptExecutor(RunnableScriptBuilder executableBuilder, RunnableScriptRepo executableRepo, ScriptRunRepo scriptRunRepo,
			@Value("${taskrunidfile.location}") String idFileLocation,  @Value("${taskexecutorpool.size}") int executorPoolSize) {
		this.executableBuilder = executableBuilder;
		this.runnableScriptRepo = executableRepo;
		this.scriptRunRepo = scriptRunRepo;
		this.idFileLocation = idFileLocation;
		this.executor = createExecutor(executorPoolSize);
	}
	
	private ExecutorService createExecutor(int poolSize) {
		ExecutorService executor=Executors.newFixedThreadPool(poolSize);
		log.info("Task Executor created - poolSize="+poolSize);
		return executor;
	}

	public ScriptRunResult execute(ScriptSource request){
		RunnableScript executable = createAndSaveExecutable(request);
		ScriptRunInfo execInfo = executable.start(); 
		ScriptRunResult taskRunResult = createTaskRunResult(execInfo);
		postRunProcess(request, executable,taskRunResult); 
		return taskRunResult;
	}
	
	public ScriptRunResult asyncExecute(ScriptSource request){
		RunnableScript runnable = createAndSaveExecutable(request);
		ScriptRunResult res = createScriptRun(runnable) ;
		return res;
	}
	
	public ScriptRunInfo getRootRunnableInfoByScriptRunId(Long scriptRunId) {
		ScriptRun scriptRun = scriptRunRepo.getTaskRun(scriptRunId);
		if (scriptRun == null) {
			return new ScriptRunInfo(null, null, null, ScriptRunStatus.FAILED, "TaskRun not found", false, null);
		}
		return scriptRun.getScriptRunInfo();
	}
	
	public ScriptRunInfo getExecutableInfoById(String executableId) {
		RunnableScript runnable = runnableScriptRepo.getRunnableScript(executableId);
		if (runnable == null) {
			return new ScriptRunInfo(null, null, null, ScriptRunStatus.FAILED, "Executable not found", false, null);
		}
		return runnable.getInfo();
	}


	private ScriptRunResult createScriptRun(RunnableScript executable) {
		Long taskRunId = new IDGenerator(idFileLocation).getNewID();
		scriptRunRepo.addScriptRun(taskRunId, executable);
		run(executable);
		log.info(String.format("Taskrun started: takrunId=%d, execId=%s",taskRunId,executable.getId()));
		return new ScriptRunResult(taskRunId, ScriptRunStatus.START.toString(),executable.getId());
	}


	private void run(RunnableScript runnableScript) {
		Runnable taskRunnable = new ScriptRunnable(runnableScript);
		executor.execute(taskRunnable);
	}

	private void postRunProcess(ScriptSource scriptSource,RunnableScript runnableScript, ScriptRunResult taskRunResult) {
		// TODO generate test reports, email notifications etc
	}


	private ScriptRunResult createTaskRunResult(ScriptRunInfo execInfo) {
		return new ScriptRunResult(execInfo.getStatus().name(),execInfo.getMessage(),(execInfo.getOutput()==null)?null:execInfo.getOutput().toString(), execInfo.getId());
	}


	private RunnableScript createAndSaveExecutable(ScriptSource request) {
		RunnableScript executableScript = executableBuilder.build(request);
		runnableScriptRepo.addExecutable(executableScript);
		log.info("Executable created: execId="+executableScript.getId());
		return executableScript;
	}
	
	
	private static class ScriptRunnable implements Runnable {
		private RunnableScript runnableScript;
		
		public ScriptRunnable(RunnableScript runnableScript) {
			this.runnableScript = runnableScript;
		}

		@Override
		public void run() {
			runnableScript.start();
		}
	}
}
