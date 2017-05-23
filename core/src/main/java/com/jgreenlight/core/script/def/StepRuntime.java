package com.jgreenlight.core.script.def;

public class StepRuntime extends BaseRuntime {
	private StepStatus status;
	
	public StepRuntime(String id) {
		super(id);
	}
    

	public StepStatus getStatus() {
		return status;
	}

	public void setStatus(StepStatus status) {
		this.status = status;
	}


	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder("__stepRuntime__"+LINE_RETURN);
		strB.append("Id: "+getId()+LINE_RETURN);
		strB.append("Start time: "+getStartTime() +LINE_RETURN);
		strB.append("Status: " + this.status +LINE_RETURN);
		if (!getInputParameters().isEmpty()) {
			strB.append(strParams());
		}
		if (hasOutput()) {
			strB.append("Output: [" + getOutput() +"]"+LINE_RETURN );
		}
		strB.append("End time: "+getEndTime()+LINE_RETURN);
		
		if (getErrMessage() != null) {
			strB.append("Error: " + getErrMessage());
		}
		return strB.toString();
	}

}
