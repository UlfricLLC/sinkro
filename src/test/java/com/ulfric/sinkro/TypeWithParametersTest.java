package com.ulfric.sinkro;

import java.lang.reflect.Type;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

class TypeWithParametersTest {

	@Test
	void testGetOwnerTypeReturnsNull() {
		Truth.assertThat(new TypeWithParameters(Object.class).getOwnerType()).isNull();
	}

	@Test
	void testGetRawTypeReturnsExpected() {
		Truth.assertThat(new TypeWithParameters(Object.class).getRawType()).isSameAs(Object.class);
	}

	@Test
	void testGetActualTypeArgumentsReturnsExpected() {
		Type[] parameters = new TypeWithParameters(Object.class, Object.class).getActualTypeArguments();
		Truth.assertThat(parameters).asList().containsExactly(Object.class);
	}

	@Test
	void testGetActualTypeArgumentsReturnsCloneOfExpected() {
		Type[] passed = { Object.class };
		Type[] parameters = new TypeWithParameters(Object.class, passed).getActualTypeArguments();
		Truth.assertThat(parameters).isNotSameAs(passed);
	}

}
