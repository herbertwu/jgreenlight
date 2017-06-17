package com.jgreenlight.core.script;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgreenlight.core.script.def.RunnableStep;
import com.jgreenlight.core.script.def.LiteralAndVariableValueResolver;
import com.jgreenlight.core.script.def.ParameterValuesResolver;
import com.jgreenlight.core.script.def.ReferenceValue;
import com.jgreenlight.core.script.def.RunnableScript;
import com.jgreenlight.core.script.def.ScriptRunContext;
import com.jgreenlight.core.script.def.ScriptRunException;
import com.jgreenlight.core.script.def.ScriptRunInfo;
import com.jgreenlight.core.script.def.ScriptRunStatus;
import com.jgreenlight.core.script.def.ScriptRuntime;
import com.jgreenlight.core.script.def.StepResult;
import com.jgreenlight.core.script.def.StepRuntime;
import com.jgreenlight.core.script.def.StepStatus;

/**
 * A script run consists of multiple executable steps and each step execution details 
 * are implemented by command interface to allow sub-script command implementation.
 * Executable step flow-control(if-else, loop) is implemented by pre-run and post-run handler.
 * 
 */
public class RunnableScriptImpl implements RunnableScript {
	private final Logger log =  LoggerFactory.getLogger(RunnableScriptImpl.class);
	
	private String id;
	private String name;
	private ScriptRunStatus scriptRunStatus = ScriptRunStatus.START;
	private String message;
	private ScriptRunContext scriptRunContext;
	private boolean hasOutput;
	private Object output;
	private ReferenceValue outputReference;
	private List<RunnableStep> executableSteps;
	private Map<String, ReferenceValue> inputParams;
	private ParameterValuesResolver resolver;
	private int currentStepNum = 0;
	private long startTime = System.currentTimeMillis();
	
	private ScriptRuntime execRuntime; //Maybe a collection if script is repeatable

	public RunnableScriptImpl(
			String id, 
			String name, 
			ScriptRunContext executableContext, 
			boolean hasOutput, 
			ReferenceValue outputReference,
			List<RunnableStep> executableSteps, 
			Map<String, ReferenceValue> inputParams,
			ParameterValuesResolver resolver) {
		this.id = id;
		this.name = name;
		this.scriptRunContext = executableContext;
		this.hasOutput = hasOutput;
		this.outputReference = outputReference;
		this.executableSteps = executableSteps;
		this.inputParams = inputParams;
		this.resolver = resolver;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ScriptRunInfo start() throws ScriptRunException {
		try {
			begin();
			preRunCheckup();
			//Execute all steps
			while (currentStepNum < executableSteps.size()) {
				RunnableStep currentStep = executableSteps.get(currentStepNum);
				StepResult stepRunInfo = currentStep.run();
				printStepRuntime(currentStep.getStepRuntime());
				if (stepRunInfo.getStatus()==StepStatus.COMPLETED) {
					currentStepNum++;
				} else if (stepRunInfo.getStatus()==StepStatus.FAILED) {
					return createFailedScriptRunInfo(stepRunInfo);
				} else if (stepRunInfo.getStatus()==StepStatus.CONTINUE) {
					continue;
				}
			}
			postRunProcess();
			return success();
		} catch (Exception e ) {
			return fail(e);
		}
	}
	
	private ScriptRunInfo fail(Exception e) {
		String errMsgs = String.format("Script execution(id=%s, name=%s) unexpectaly failed: %s", this.id, this.name,e.getLocalizedMessage());
		return new ScriptRunInfo(getId(),this.name, this.startTime,ScriptRunStatus.FAILED, errMsgs, this.hasOutput, null);

	}
	
	private ScriptRunInfo success() {
		ScriptRunInfo execInfo = new ScriptRunInfo(getId(),this.name, this.startTime,ScriptRunStatus.COMPLETED, null, this.hasOutput, this.output);
		end(execInfo);
		printExecutableRuntime();
		return execInfo;
	}

	private ScriptRunInfo createFailedScriptRunInfo(StepResult stepRunInfo) {
		ScriptRunInfo execInfo = new ScriptRunInfo(getId(),this.name, this.startTime,ScriptRunStatus.FAILED, stepRunInfo.getMessage(), this.hasOutput, null);
		end(execInfo);
		log.error("Step "+ (currentStepNum+1) +" failed:");
		printExecutableRuntime();
		return execInfo;
	}

	private void preRunCheckup() {
		resolveTaskInputParameters();
		runnableCheck();
		this.scriptRunStatus= ScriptRunStatus.RUNNING;
	}
	
	private void runnableCheck() {
		// TODO more later
	}
	
	private void postRunProcess() {
		this.scriptRunStatus = ScriptRunStatus.COMPLETED;
		addOutputIfAny();
	}
	
	private void addOutputIfAny() {
		if (this.hasOutput && this.outputReference != null) {
			this.output = resolveOutputValue();
		}
	}
	
	private Object resolveOutputValue() {
		return new LiteralAndVariableValueResolver(scriptRunContext).resolve(this.outputReference);
	}

	private void resolveTaskInputParameters() {
		Map<String, Object> resolvedInputParamsValues = resolver.resolve(inputParams);
		for (Entry<String, Object> ent : resolvedInputParamsValues.entrySet()) {
			scriptRunContext.addVar(ent.getKey(), ent.getValue());
		}
	}

	@Override
	public ScriptRunInfo getInfo() {
		return new ScriptRunInfo(getId(),this.name, this.startTime,this.scriptRunStatus, this.message, this.hasOutput, this.output); 
	}

	@Override
	public void stop() throws ScriptRunException {
		// TODO impl later
	}
	

	private void printStepRuntime(StepRuntime stepRuntime) {
		System.out.println(stepRuntime);
	}
	
	private void printExecutableRuntime() {
		log.info("\n\n"+execRuntime);
	}
	
	private void begin() {
		Instant startTime = Instant.now();
		this.execRuntime = new ScriptRuntime(this.name + "-" + startTime);
		this.execRuntime.setStartTime(startTime);
		this.execRuntime.setStatus(ScriptRunStatus.START);
	}

	private void end(ScriptRunInfo result) {
		this.execRuntime.setStatus(result.getStatus());
		this.execRuntime.setEndTime(Instant.now());
		this.execRuntime.setHasOutput(result.hasOutput());
		this.execRuntime.setOutput(result.getOutput());
		if (result.getStatus() == ScriptRunStatus.FAILED) 
			this.execRuntime.setErrMessage(result.getMessage());
	
	}
}
