package com.jgreenlight.core.script.def;

import java.util.Map;

public interface Command {
	CommandRunInfo run(String name, Map<String, Object> params);
}
