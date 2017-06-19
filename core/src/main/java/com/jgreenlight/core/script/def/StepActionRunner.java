package com.jgreenlight.core.script.def;

import java.util.Map;

public interface StepActionRunner {
	StepActionRunInfo run(String name, Map<String, Object> params);
}
