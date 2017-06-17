package com.jgreenlight.commands;


public interface CommandExecutor {
	public CommandRunResult execute(Command command);
}