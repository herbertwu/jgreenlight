package com.jgreenlight.core.script.def;

public interface ScriptRun {
	String getId();
	ScriptRunInfo start() throws ScriptRunException;
    ScriptRunInfo getInfo();
    void stop() throws ScriptRunException;
}
