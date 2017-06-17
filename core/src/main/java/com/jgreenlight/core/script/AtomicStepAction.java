package com.jgreenlight.core.script;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgreenlight.commands.Command;
import com.jgreenlight.commands.CommandExecutor;
import com.jgreenlight.commands.CommandRunResult;
import com.jgreenlight.core.script.def.StepAction;
import com.jgreenlight.core.script.def.StepActionRunInfo;


@Component
public class AtomicStepAction implements StepAction {
	private static final int COMMAND_EXEC_ERR = 1;
	
	@Autowired
	private CommandExecutor commandExecutor;

	@Override
	public StepActionRunInfo run(String commandName, Map<String, Object> params) {
		try {
			Command request = createCommand(commandName, params);
			CommandRunResult res = commandExecutor.execute(request);
			return new StepActionRunInfo(res.getCode(), res.getMessage(), res.isHasOutput(), res.getResult());
		} catch (Exception e) {
			return new StepActionRunInfo(COMMAND_EXEC_ERR, e.getLocalizedMessage(), false, null);
		}
	}

	private Command createCommand(String commandName, Map<String, Object> params) {
		Map<String, String> strParamsMap = params.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
		//TODO
		return null;
	}
}
