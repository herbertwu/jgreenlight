package com.jgreenlight.core.script.def;

public class ScriptRunException extends RuntimeException {
	private static final long serialVersionUID = -5403220475295839680L;

	public ScriptRunException() {
		super();
	}

	public ScriptRunException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScriptRunException(String message) {
		super(message);
	}

	public ScriptRunException(Throwable cause) {
		super(cause);
	}

}
