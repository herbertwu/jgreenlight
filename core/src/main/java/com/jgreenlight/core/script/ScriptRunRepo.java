package com.jgreenlight.core.script;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.jgreenlight.core.script.def.RunnableScript;
import com.jgreenlight.core.script.def.ScriptRun;

@Component
public class ScriptRunRepo {
	private static final int MAX_REPO_ENTRIES=100;
	private Map<Long, ScriptRun> scriptRunRepo = new LinkedHashMap<Long, ScriptRun>(MAX_REPO_ENTRIES, 0.7f,false) {
		private static final long serialVersionUID = -7304408796331645950L;
		protected boolean removeEldestEntry(Map.Entry<Long, ScriptRun> eldest) {
        return size() > MAX_REPO_ENTRIES;
     }
	};

	public void addScriptRun(Long taskRunId,RunnableScript runnable) {
		scriptRunRepo.put(taskRunId, new ScriptRun(taskRunId,runnable));
	}
	
	public ScriptRun getTaskRun(Long scriptRunId) {
		return scriptRunRepo.get(scriptRunId);
	}
}
