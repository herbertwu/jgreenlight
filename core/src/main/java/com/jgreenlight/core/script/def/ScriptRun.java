package com.jgreenlight.core.script.def;

public class ScriptRun {
	private Long id;
	private RunnableScript rootRunnable;
	private long createTime;
	
	public ScriptRun(Long id, RunnableScript rootRunnable) {
		this.id = id;
		this.rootRunnable = rootRunnable;
		this.createTime=System.currentTimeMillis();
	}

	public Long getId() {
		return id;
	}

	public long getCreateTime() {
		return createTime;
	}
	
	public ScriptRunInfo getScriptRunInfo() {
		return rootRunnable.getInfo();
	}

}
