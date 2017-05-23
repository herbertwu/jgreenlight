package com.jgreenlight.core.script.def;

public class ScriptRunInfo {
	private final String id;
	private final String name;
	private final ScriptRunStatus status;
	private final String message;
	private final boolean hasOutput;
	private final Object output;
	private Long startTime;
	private Long endTime;
	
	public ScriptRunInfo(String id, String name, Long startTime, ScriptRunStatus status, String message, boolean hasOutput, Object output) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.message = message;
		this.hasOutput = hasOutput;
		this.output = output;
		this.startTime = startTime;
	}

	public ScriptRunInfo(String id, String name, ScriptRunStatus status, String message, boolean hasOutput, Object output, Long startTime,Long endTime) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.message = message;
		this.output = output;
		this.hasOutput = hasOutput;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public ScriptRunStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getOutput() {
		return output;
	}

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}
	
	public boolean hasOutput() {
		return this.hasOutput;
	}
}
