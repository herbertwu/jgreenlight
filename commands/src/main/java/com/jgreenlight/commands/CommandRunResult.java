package com.jgreenlight.commands;

public class CommandRunResult {
	private  String status;
	private  int code;
	private  String message;
	private  String result;
	private  boolean hasOutput;
	
	public String getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getResult() {
		return result;
	}
	
	public boolean isHasOutput() {
		return hasOutput;
	}

	public void setHasOutput(boolean hasOutput) {
		this.hasOutput = hasOutput;
	}

	

	@Override
	public String toString() {
		return "ActionRunResult [status=" + status + ", code=" + code + ", message=" + message + ", result=" + result
				+ ", hasOutput=" + hasOutput + "]";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}