package com.jgreenlight.core.script.def;

import java.time.Instant;
import java.util.Map;

public class RunnableStep {
	private ScriptRunContext runnableContext;
	private String name;
	private Map<String, ReferenceValue> stepParams;
	private ParameterValuesResolver resolver;
	private StepAction command;
	private StepRunHandler preRunHandler;
	private StepRunHandler postRunHandler;
	
	private boolean hasOutput;
	private Object output;
	private String outputVarName;
	
	private StepRuntime stepRuntime; //Maybe a collection if step is repeatable
	
	public RunnableStep(ScriptRunContext executableContext, String name, boolean hasOutput, String outputVarName, Map<String, ReferenceValue> stepParams,ParameterValuesResolver resolver, StepAction command) {
		this.runnableContext = executableContext;
		this.name = name;
		this.stepParams = stepParams;
		this.resolver = resolver;
		this.command = command;
		
		this.hasOutput = hasOutput;
		this.outputVarName = outputVarName;
	}
	
	public StepResult run() {
		try {
			start();
			//TODO run preRunhandler if any, such as skip
			Map<String, Object> resolvedParamValues = resolveStepParameters();
			StepActionRunInfo commRunInfo = command.run(name, resolvedParamValues);
			saveOutputIfAny(commRunInfo);
			//TODO run postRunHandler if any, such as step repeat
			StepResult result = createStepRunInfo(commRunInfo);
			end(result);
			return result;
		} catch (Exception e) {
			return fail(e);
		}
	}

	private StepResult fail(Exception e) {
		return new StepResult(StepStatus.FAILED, e.getLocalizedMessage(),this.hasOutput,null);
	}

	private void start() {
		Instant startTime = Instant.now();
		this.stepRuntime = new StepRuntime(this.name + "-" + startTime);
		this.stepRuntime.setStartTime(startTime);
		this.stepRuntime.setStatus(StepStatus.RUNNING);
	}
	
	private Map<String, Object> resolveStepParameters() {
		Map<String, Object> resolvedParamValues = resolver.resolve(stepParams);
		//log parameters
		this.stepRuntime.setInputParameters(resolvedParamValues);
		return resolvedParamValues;
	}

	private void saveOutputIfAny(StepActionRunInfo commRunInfo) {
		if (hasOutput) {
			this.output = commRunInfo.getOutput();
			if (outputVarName != null ) {
				runnableContext.addVar(outputVarName, this.output);
			}
			//log output
			this.stepRuntime.setOutput(this.output);
		}
		
	}
	

	private void end(StepResult result) {
		this.stepRuntime.setStatus(result.getStatus());
		this.stepRuntime.setEndTime(Instant.now());
		this.stepRuntime.setHasOutput(result.hasOutput());
		this.stepRuntime.setOutput(result.getOutput());
		if (result.getStatus() == StepStatus.FAILED) 
		   this.stepRuntime.setErrMessage(result.getMessage());
	
	}


	private StepResult createStepRunInfo(StepActionRunInfo commRunInfo) {
		if (commRunInfo.getCode() == 0) {
			return new StepResult(StepStatus.COMPLETED, commRunInfo.getMessage(),commRunInfo.isHasOutput(),commRunInfo.getOutput());
		} else {
			return new StepResult(StepStatus.FAILED, commRunInfo.getMessage(),commRunInfo.isHasOutput(),commRunInfo.getOutput());
		}
	}

	public ScriptRunContext getExecutableContext() {
		return runnableContext;
	}

	public Object getOutput() {
		return output;
	}
	
	public StepRuntime getStepRuntime() {
		return this.stepRuntime;
	}
}
