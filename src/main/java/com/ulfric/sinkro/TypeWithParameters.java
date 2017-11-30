package com.ulfric.sinkro;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class TypeWithParameters implements ParameterizedType {

	private final Type raw;
	private final Type[] parameters;

	public TypeWithParameters(Type raw, Type... parameters) {
		this.raw = raw;
		this.parameters = parameters.clone();
	}

	@Override
	public Type[] getActualTypeArguments() {
		return parameters.clone();
	}

	@Override
	public Type getRawType() {
		return raw;
	}

	@Override
	public Type getOwnerType() {
		return null;
	}

}
