package com.jgreenlight.core.script.def;

public interface RunnableScript {
	String getId();
	ScriptRunInfo start() throws ScriptRunException;
    ScriptRunInfo getInfo();
    void stop() throws ScriptRunException;
}
