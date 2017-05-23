package com.jgreenlight.core.script.def;

public class ReferenceValue {
	private ReferenceType type;
	private Object value;
	
	public ReferenceValue(ReferenceType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public ReferenceType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
}
