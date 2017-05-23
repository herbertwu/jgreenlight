package com.jgreenlight.core.script.def;

public class StepResult {
	private StepStatus status;
	private String message;
	private boolean hasOutput;
	private Object output;
	
	public StepResult(StepStatus status, String message, boolean hasOutput, Object output) {
		this.status = status;
		this.message = message;
		this.hasOutput = hasOutput;
		this.output = output;
	}

	public StepStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public boolean hasOutput() {
		return hasOutput;
	}

	public Object getOutput() {
		return output;
	}
	
	public boolean isEnd() {
		return status == StepStatus.SKIP || status == StepStatus.COMPLETED || status == StepStatus.FAILED;
	}
	
	public boolean isContinue() {
		return status == StepStatus.CONTINUE;
	}
}
