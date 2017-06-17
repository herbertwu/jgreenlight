package com.jgreenlight.core.script.source;

import java.util.List;
import java.util.Map;

/**
 * Directly mapped to json string
 */
public class ScriptSource {
	
	public static class ScriptStep {
		private String name;
		private String outputVar;
		private Map<String,String> params;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOutputVar() {
			return outputVar;
		}
		public void setOutputVar(String outputVar) {
			this.outputVar = outputVar;
		}
		public Map<String, String> getParams() {
			return params;
		}
		public void setParams(Map<String, String> params) {
			this.params = params;
		}
		
		
	}

	private String name;
	
	private String description;
	
	private List<ScriptStep> steps;
	
	private Map<String,String> params;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ScriptStep> getSteps() {
		return steps;
	}

	public void setSteps(List<ScriptStep> steps) {
		this.steps = steps;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	
}
