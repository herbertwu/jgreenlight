package com.jgreenlight.core.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;

import com.jgreenlight.core.script.def.ParameterValuesResolver;
import com.jgreenlight.core.script.def.ReferenceType;
import com.jgreenlight.core.script.def.ReferenceValue;
import com.jgreenlight.core.script.def.RunnableScript;
import com.jgreenlight.core.script.def.RunnableStep;
import com.jgreenlight.core.script.def.ScriptRunContext;
import com.jgreenlight.core.script.def.StepActionRunner;
import com.jgreenlight.core.script.source.ScriptSource;
import com.jgreenlight.core.script.source.ScriptSource.ScriptStep;
 
public class JsonScriptSourceParser {

	private StepActionRunner stepAction;

	public JsonScriptSourceParser(StepActionRunner stepAction) {
		this.stepAction = stepAction;
	}

	public RunnableScript parse(ScriptSource scriptSource) {
		String id = generateExecutableId();
		String scriptName = scriptSource.getName();
		ScriptRunContext runContext = new ScriptRunContext();
		ParameterValuesResolver paramValuesResolver = new DefaultParameterValuesResolver(runContext);
		ReferenceValue scriptOutputReference = null; //unused
		List<RunnableStep> runnableSteps = buildSteps(scriptSource,runContext, paramValuesResolver);
		Map<String, ReferenceValue> inputParams = parseInputParams(scriptSource);
		return new RunnableScriptImpl(id, scriptName, runContext, true, scriptOutputReference, runnableSteps, inputParams, paramValuesResolver);
	}

	private Map<String, ReferenceValue> parseInputParams(ScriptSource source) {
		Map<String, ReferenceValue> inputParams = new HashMap<String, ReferenceValue>();
		if (source.getParams() == null) 
			return inputParams;
		for (Entry<String,String> scriptInputParam : source.getParams().entrySet()) {
			ReferenceValue referenceVal = parseParam(scriptInputParam.getValue());
			inputParams.put(scriptInputParam.getKey(), referenceVal);
		}
		return inputParams;
	}

	private List<RunnableStep> buildSteps(ScriptSource scriptSource,ScriptRunContext runContext,ParameterValuesResolver paramValuesResolver) {
		List<RunnableStep> execSteps = new ArrayList<RunnableStep>();
		for (ScriptStep scriptStep : scriptSource.getSteps()) {
			String stepName = scriptStep.getName().toString();
			boolean hasOutput = hasOutput(stepName);
			String stepOutputVarName = (scriptStep.getOutputVar()==null)? null:scriptStep.getOutputVar().toString();
			Map<String, ReferenceValue> stepParams = createStepParams(scriptStep);
			RunnableStep execStep = new RunnableStep(runContext, stepName, hasOutput, stepOutputVarName, stepParams, paramValuesResolver, this.stepAction);
			execSteps.add(execStep);
		};
		return execSteps;
	}

	private boolean hasOutput(String stepName) {
		// TODO read from step definition
		return true;
	}

	private Map<String, ReferenceValue> createStepParams(ScriptStep scriptStep) {
		Map<String, ReferenceValue> stepParams = new HashMap<String, ReferenceValue>();
		if (scriptStep.getParams()==null) 
			return stepParams;
		for (Entry<String,String> stepParam : scriptStep.getParams().entrySet()) {
			ReferenceValue referenceVal = parseParam(stepParam.getValue());
			stepParams.put(stepParam.getKey(), referenceVal);
		}
		return stepParams;
	}
	
	private ReferenceValue parseParam(String paramVal) {
		if(StringUtils.contains(paramVal, "${")){
			String match = StringUtils.substringBetween(paramVal, "${", "}");
			return new ReferenceValue(ReferenceType.VAR, match);
		}else{
			//its a literal value
			return new ReferenceValue(ReferenceType.LITERAL, paramVal);
		}
	}
	
	private String generateExecutableId() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 1000);
		return String.format("exec-%d-%d", randomNum, System.currentTimeMillis());
	}
}
