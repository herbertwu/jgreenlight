package com.jgreenlight.core.script.def;

import java.util.Map;

public interface ParameterValuesResolver {
	Map<String, Object> resolve(Map<String, ReferenceValue> referenceParameters);
}
