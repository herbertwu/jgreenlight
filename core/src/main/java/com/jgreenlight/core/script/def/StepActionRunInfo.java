package com.jgreenlight.core.script.def;

public class StepActionRunInfo {
	private int code;
	private String message;
	private boolean hasOutput;
	private Object output;
	
	public StepActionRunInfo(int code, String message, boolean hasOutput, Object output) {
		this.code = code;
		this.message = message;
		this.hasOutput = hasOutput;
		this.output = output;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public boolean isHasOutput() {
		return hasOutput;
	}

	public Object getOutput() {
		return output;
	}
}
