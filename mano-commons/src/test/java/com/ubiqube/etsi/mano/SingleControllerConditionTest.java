/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.bind.annotation.RestController;

@ExtendWith(MockitoExtension.class)
class SingleControllerConditionTest {

	SingleControllerCondition single = new SingleControllerCondition();
	@Mock
	private AnnotatedTypeMetadata metadata;
	@Mock
	private ConditionContext context;
	@Mock
	MergedAnnotations annotations;
	@Mock
	MergedAnnotation<RestController> anSrc;
	@Mock
	ConfigurableListableBeanFactory beanFactory;

	@Test
	void testBase() {
		when(metadata.getAnnotations()).thenReturn(annotations);
		when(annotations.get(RestController.class)).thenReturn(anSrc);
		when(anSrc.getSource()).thenReturn(ControllerA.class.getCanonicalName());
		single.getMatchOutcome(context, metadata);
		single.getConfigurationPhase();
		assertTrue(true);
	}

	@Test
	void testMatch() throws Exception {
		when(metadata.getAnnotations()).thenReturn(annotations);
		when(annotations.get(RestController.class)).thenReturn(anSrc);
		when(anSrc.getSource()).thenReturn(ControllerA.class.getCanonicalName());
		when(context.getBeanFactory()).thenReturn(beanFactory);
		final String[] value = {
				ControllerB.class.getCanonicalName()
		};
		when(beanFactory.getBeanNamesForAnnotation(RestController.class)).thenReturn(value);
		when(beanFactory.getType(value[0])).thenReturn((Class) ControllerB.class);
		final ConditionOutcome res = single.getMatchOutcome(context, metadata);
		assertTrue(res.isMatch());
	}

	@Test
	void testNotMatch() throws Exception {
		when(metadata.getAnnotations()).thenReturn(annotations);
		when(annotations.get(RestController.class)).thenReturn(anSrc);
		when(anSrc.getSource()).thenReturn(ControllerA.class.getCanonicalName());
		when(context.getBeanFactory()).thenReturn(beanFactory);
		final String[] value = {
				ControllerA.class.getCanonicalName(),
				ControllerA.class.getCanonicalName()
		};
		when(beanFactory.getBeanNamesForAnnotation(RestController.class)).thenReturn(value);
		when(beanFactory.getType(value[0])).thenReturn((Class) ControllerA.class);
		when(beanFactory.getType(value[1])).thenReturn((Class) ControllerA.class);
		final ConditionOutcome res = single.getMatchOutcome(context, metadata);
		assertFalse(res.isMatch());
	}
}
