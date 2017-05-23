package com.jgreenlight.core.script.def;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BaseRuntime {
	static final String LINE_RETURN="\n";
	private final String id;
	private Instant startTime;
	private Instant endTime;
	private Map<String, Object> inputParameters = new HashMap<String, Object>();;
	private boolean hasOutput = true;
	private Object output;
	private String errMessage;
	
	public BaseRuntime(String id) {
		this.id = id;
	}
    
	public String getId() {
		return this.id;
	}
	
	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	public Map<String, Object> getInputParameters() {
		return inputParameters;
	}

	public void setInputParameters(Map<String, Object> stepParameters) {
		this.inputParameters = stepParameters;
	}

	public boolean hasOutput() {
		return hasOutput;
	}

	public void setHasOutput(boolean hasOutput) {
		this.hasOutput = hasOutput;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	String strParams() {
		StringBuilder paramsB = new StringBuilder("Parameters: "+LINE_RETURN);
		for (Entry<String, Object> param: inputParameters.entrySet()) {
			paramsB.append("  "+param.getKey()+"=["+param.getValue()+"]"+LINE_RETURN);
		}
		return paramsB.toString();
	}
}
