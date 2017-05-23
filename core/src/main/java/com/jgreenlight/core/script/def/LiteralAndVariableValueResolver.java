package com.jgreenlight.core.script.def;

public class LiteralAndVariableValueResolver {
	private ScriptRunContext executableContext;
	
	public LiteralAndVariableValueResolver(ScriptRunContext executableContext) {
		this.executableContext = executableContext;
	}

	public Object resolve(ReferenceValue refVal) {
		if (refVal.getType() == ReferenceType.LITERAL) {
			return refVal.getValue();
		} else if (refVal.getType() == ReferenceType.VAR) {
			return executableContext.getVar(refVal.getValue().toString());
		} else {
			throw new ScriptRunException("Only LITERAL and Var type of value can be resolved here, but this value has type: " + refVal.getType());
		}
	}
}
