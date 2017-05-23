package com.jgreenlight.core.script.def;

import java.util.HashMap;
import java.util.Map;

public class ScriptRunContext {
	private static final String VAR_TYPE="var_";
	private Map<Object, Object> context = new HashMap<Object, Object>();
    
	public Object get(Object key) {
		return context.get(key);
	}
	
	public void add(Object key, Object value) {
		 context.put(key, value);
	}
	
	public void removebyKey(Object key) {
		context.remove(key);
	}
	
	public void addVar(String varName, Object value) {
		add(createVarKey(varName), value);
	}
	
	public Object getVar(String varName) {
		return get(createVarKey(varName));
	}
	
	private String createVarKey(String varName) {
		return VAR_TYPE+varName;
	}
}
