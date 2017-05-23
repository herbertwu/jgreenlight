package com.jgreenlight.core.script.def;

public class CommandRunInfo {
	private int code;
	private String message;
	private boolean hasOutput;
	private Object output;
	
	public CommandRunInfo(int code, String message, boolean hasOutput, Object output) {
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
