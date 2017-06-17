package com.jgreenlight.core.script;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jgreenlight.core.script.def.RunnableScript;

@Component
public class RunnableScriptRepo {
	private static final int MAX_REPO_ENTRIES=500;
	private Map<String, RunnableScript> executableRepo = new LinkedHashMap<String, RunnableScript>(MAX_REPO_ENTRIES, 0.7f,false) {
		private static final long serialVersionUID = 6803673484107913643L;
		protected boolean removeEldestEntry(Map.Entry<String, RunnableScript> eldest) {
        return size() > MAX_REPO_ENTRIES;
     }
	};

	public void addExecutable(RunnableScript runnable) {
		executableRepo.put(runnable.getId(), runnable);
	}
	
	public RunnableScript getRunnableScript(String id) {
		return executableRepo.get(id);
	}
}
