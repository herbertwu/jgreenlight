package com.jgreenlight.core.script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgreenlight.core.script.def.RunnableScript;
import com.jgreenlight.core.script.def.StepActionRunner;
import com.jgreenlight.core.script.source.ScriptSource;

@Component
public class RunnableScriptBuilder {
	@Autowired
	private StepActionRunner stepAction;

	public RunnableScript build(ScriptSource scriptSource) {
		return new JsonScriptSourceParser(stepAction).parse(scriptSource);
	}
}
