package com.jgreenlight.core.script;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jgreenlight.core.script.def.StepAction;
import com.jgreenlight.core.script.def.StepActionRunInfo;
import com.jgreenlight.core.script.def.RunnableStep;
import com.jgreenlight.core.script.def.ParameterValuesResolver;
import com.jgreenlight.core.script.def.ReferenceType;
import com.jgreenlight.core.script.def.ReferenceValue;
import com.jgreenlight.core.script.def.ScriptRunContext;
import com.jgreenlight.core.script.def.ScriptRunInfo;
import com.jgreenlight.core.script.def.ScriptRunStatus;

public class RunnableScriptImplTest {
	String echoValue="Hello";
	
	ScriptRunContext execContext = new ScriptRunContext();
	ParameterValuesResolver paramValuesResolver = new DefaultParameterValuesResolver(execContext);
	
	String id = "uuid-adsjkfhaskdjfaslfkasflh";
	String scriptName = "testScript";
	String inputParamName = "greeting";
	String inputParamValue = "Hello";
	
	String echoParamName="message";
	String actionName="Echo";
	StepAction echoCommand = new EchoCommand();
	
	StepAction errCommand = new ErrorCommand();
	
	String step1OutVarName="out1";
	Map<String, ReferenceValue> step1InputParams = stubStep1InputParams();
	
	String step2OutVarName="out2";
	Map<String, ReferenceValue> step2InputParams = stubStep2InputParams();
	
	String step3OutVarName="out3";
	String step3ErrMsgs = "404 not found";
	Map<String, ReferenceValue> step3InputParams = new HashMap<String, ReferenceValue>();
	
	ReferenceValue scriptOutputReference = new ReferenceValue(ReferenceType.VAR, "out2");
	List<RunnableStep> executableSteps = Arrays.asList(step1(),step2());
	List<RunnableStep> executableStepWithFailure = Arrays.asList(step1(),step3());
	Map<String, ReferenceValue> inputParams = stubScriptInputParams();
	
	
	RunnableScriptImpl twoEchosExecutable = new RunnableScriptImpl(id, scriptName, execContext, true, scriptOutputReference, executableSteps, inputParams, paramValuesResolver);
	
	@Test
	public void runExecutableOfTwoEchoesWithOutputToScriptOutput_ok() {
		ScriptRunInfo execInfo = twoEchosExecutable.start();
		assertThat(ScriptRunStatus.COMPLETED, is(execInfo.getStatus()));
		assertThat(id, is(execInfo.getId()));
		assertThat(scriptName, is(execInfo.getName()));
		assertThat("Hello", is(execInfo.getOutput()));
		assertNotNull(execInfo.getStartTime());
	}
	
	@Test
	public void runExecutable_actionFailed() {
		RunnableScriptImpl errResultExecutable = new RunnableScriptImpl(id, scriptName, execContext, true, scriptOutputReference, executableStepWithFailure, inputParams, paramValuesResolver);
		ScriptRunInfo execInfo = errResultExecutable.start();
		assertThat(ScriptRunStatus.FAILED, is(execInfo.getStatus()));
		assertThat(id, is(execInfo.getId()));
		assertThat(scriptName, is(execInfo.getName()));
		assertThat(step3ErrMsgs, is(execInfo.getMessage()));
		assertNull(execInfo.getOutput());
		assertNotNull(execInfo.getStartTime());
	}
	
	
	
	private RunnableStep step1() {
		return new RunnableStep(execContext, actionName, true,  step1OutVarName, step1InputParams, paramValuesResolver, echoCommand);
	}
	
	private RunnableStep step2() {
		return new RunnableStep(execContext, actionName, true,  step2OutVarName, step2InputParams, paramValuesResolver, echoCommand);
		
	}
	
	private RunnableStep step3() {
		return new RunnableStep(execContext, "errAction", true,  step3OutVarName, step3InputParams, paramValuesResolver, errCommand);
		
	}
	private Map<String, ReferenceValue> stubScriptInputParams() {
		return new HashMap<String, ReferenceValue>() {
			{put (inputParamName, new ReferenceValue(ReferenceType.LITERAL, inputParamValue));}
		};
	}
	
	private Map<String, ReferenceValue> stubStep1InputParams() {
		return new HashMap<String, ReferenceValue>() {
			{put (echoParamName, new ReferenceValue(ReferenceType.VAR, inputParamName));}
		};
	}
	
	private Map<String, ReferenceValue> stubStep2InputParams() {
		return new HashMap<String, ReferenceValue>() {
			{put (echoParamName, new ReferenceValue(ReferenceType.VAR, step1OutVarName));}
		};
	}
	
	private static class EchoCommand implements StepAction {
		private static final String ECHO_PARAM = "message";
		@Override
		public StepActionRunInfo run(String name, Map<String, Object> params) {
			return new StepActionRunInfo(0, "ok", true, params.get(ECHO_PARAM));
		}
	}
	
	private static class ErrorCommand implements StepAction {
		private static final String Err_Msgs = "404 not found";
		@Override
		public StepActionRunInfo run(String name, Map<String, Object> params) {
			return new StepActionRunInfo(1, Err_Msgs, true, null);
		}
	}

}
