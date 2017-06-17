package com.jgreenlight.core.script.def;

public class ScriptRunResult {
	private  Long scriptRunId;
	private  String status;
	private  String message;
	private  String result;
	private  String scriptName;
	private  String startTime;
	private  String endTime;
	private  String rootRunnableId;
	
	public ScriptRunResult(String status, String message, String result) {
		this.status = status;
		this.message = message;
		this.result = result;
	}
	
	public ScriptRunResult(String status, String message, String result,String rootExecutableId) {
		this.status = status;
		this.message = message;
		this.result = result;
		this.rootRunnableId=rootExecutableId;
	}
	
	public ScriptRunResult(Long scriptRunId, String status ) {
		this.scriptRunId = scriptRunId;
		this.status = status;
	}
	
	public ScriptRunResult(Long scriptRunId, String status, String rootRunnableId) {
		this.scriptRunId = scriptRunId;
		this.status = status;
		this.rootRunnableId=rootRunnableId;
	}
	
	
	public ScriptRunResult(Long scriptRunId, String rootExecutableId, String status, String taskName, String startTime, String endTime) {
		this.scriptRunId = scriptRunId;
		this.status = status;
		this.scriptName = taskName;
		this.startTime = startTime;
		this.endTime=endTime;
		this.rootRunnableId=rootExecutableId;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public Long getScriptRunId() {
		return scriptRunId;
	}

	public String getTaskName() {
		return scriptName;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	
	public String getRootRunnableId() {
		return this.rootRunnableId;
	}
}
