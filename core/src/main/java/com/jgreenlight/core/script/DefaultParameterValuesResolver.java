package com.jgreenlight.core.script;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jgreenlight.core.script.def.LiteralAndVariableValueResolver;
import com.jgreenlight.core.script.def.ParameterValuesResolver;
import com.jgreenlight.core.script.def.ReferenceValue;
import com.jgreenlight.core.script.def.ScriptRunContext;


public class DefaultParameterValuesResolver implements ParameterValuesResolver {
	private LiteralAndVariableValueResolver literalAndVariableValueResolver;

	public DefaultParameterValuesResolver(ScriptRunContext executableContext) {
		this.literalAndVariableValueResolver = new LiteralAndVariableValueResolver(executableContext);
	}

	@Override
	public Map<String, Object> resolve(Map<String, ReferenceValue> referenceParameters) {
		Map<String, Object> resolvedParams = new HashMap<String, Object>();
		for (Entry<String,ReferenceValue> refParam : referenceParameters.entrySet()) {
			resolvedParams.put(refParam.getKey(), literalAndVariableValueResolver.resolve(refParam.getValue()));
		}
		return resolvedParams;
	}
}
