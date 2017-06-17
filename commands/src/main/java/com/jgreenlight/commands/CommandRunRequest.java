package com.jgreenlight.commands;

import java.util.Map;

public class CommandRunRequest {
	private String actionType; //Null for now
	private String actionName; 
	private Map<String, String> params;
	
	public CommandRunRequest(String actionType, String actionName, Map<String, String> params) {
		this.actionType = actionType;
		this.actionName = actionName;
		this.params = params;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String credential) {
		this.actionName = credential;
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}