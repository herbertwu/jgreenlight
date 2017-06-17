package com.jgreenlight.core.script.def;

import java.util.Map;

public interface StepAction {
	StepActionRunInfo run(String name, Map<String, Object> params);
}
