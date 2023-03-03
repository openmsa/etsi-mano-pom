/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.dao;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ModelTest {

	private static final Logger LOG = LoggerFactory.getLogger(ModelTest.class);

	private final Reflections reflections;

	public ModelTest() {
		reflections = new Reflections("com.ubiqube.etsi.mano", new SubTypesScanner(false));
	}

	@Test
	void test001() {
		final Set<Class<? extends Object>> set = reflections.getSubTypesOf(Object.class);
		final Map<String, Set<String>> subtype = reflections.getStore().get("SubTypes");
		subtype.forEach((x, y) -> {
			handle(x);
			y.forEach(z -> handle(z));
		});
	}

	private static void handle(final String x) {
		if (x.startsWith("java.lang") || x.startsWith("com.ubiqube.etsi.mano.repository")
				|| x.startsWith("com.ubiqube.etsi.mano.service.rest.")) {
			return;
		}
		try {
			realHandle(x);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void realHandle(final String x) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		if (x.startsWith("com.ubiqube.etsi.mano.utils") || x.endsWith("Builder")) {
			return;
		}
		final Class<?> clazz = Class.forName(x);
		if (clazz.isInterface()
				|| clazz.isEnum()
				|| Modifier.isAbstract(clazz.getModifiers())
				|| Exception.class.isAssignableFrom(clazz)) {
			return;
		}
		final Object obj = clazz.getConstructor().newInstance();
		final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		final PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		testObject(obj, props);
	}

	private static void testObject(final Object obj, final PropertyDescriptor[] props) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException {
		for (final PropertyDescriptor propertyDescriptor : props) {
			final Method mr = propertyDescriptor.getReadMethod();
			if (null != mr) {
				System.out.println("" + mr);
				mr.invoke(obj);
			}
			final Method mw = propertyDescriptor.getWriteMethod();
			if ((null != mw) && (null != mr)) {
				final Class<?> ret = mr.getReturnType();
				if (Modifier.isAbstract(ret.getModifiers())) {
					// continue;
				}
				mw.invoke(obj, createType(ret));
			}
		}
		obj.hashCode();
		obj.toString();
		obj.equals(null);
		obj.equals(props);
		obj.equals(obj);
	}

	private static Object createType(final Class<?> ret) throws NoSuchMethodException, SecurityException, IllegalArgumentException {
		if ("boolean".equals(ret.getCanonicalName()) || ret.isAssignableFrom(Boolean.class)) {
			return Boolean.TRUE;
		}
		if ("int".equals(ret.getCanonicalName()) || ret.isAssignableFrom(Boolean.class)) {
			return 123;
		}
		if ("long".equals(ret.getCanonicalName()) || ret.isAssignableFrom(Boolean.class)) {
			return 123L;
		}
		if ("double".equals(ret.getCanonicalName()) || ret.isAssignableFrom(Boolean.class)) {
			return 123d;
		}
		if (ret.isAssignableFrom(Set.class)) {
			return Set.of();
		}
		if (ret.isAssignableFrom(List.class)) {
			return List.of();
		}
		if (ret.isAssignableFrom(Map.class)) {
			return Map.of();
		}
		if (ret.isAssignableFrom(UUID.class)) {
			return UUID.randomUUID();
		}
		if (ret.isAssignableFrom(LocalTime.class)) {
			return LocalTime.now();
		}
		if (ret.isAssignableFrom(LocalDateTime.class)) {
			return LocalDateTime.now();
		}
		if (ret.isAssignableFrom(OffsetDateTime.class)) {
			return OffsetDateTime.now();
		}
		if (ret.isAssignableFrom(ZonedDateTime.class)) {
			return ZonedDateTime.now();
		}
		if (ret.isAssignableFrom(Long.class)) {
			return Long.valueOf(123);
		}
		if (ret.isAssignableFrom(Integer.class)) {
			return Integer.valueOf(123);
		}
		if (ret.isAssignableFrom(Double.class)) {
			return Double.valueOf(123D);
		}
		if (ret.isAssignableFrom(BigDecimal.class)) {
			return BigDecimal.ONE;
		}
		if (ret.isAssignableFrom(URI.class)) {
			return URI.create("http://localhost/");
		}
		return createComplex(ret);
	}

	private static Object createComplex(final Class<?> ret) throws NoSuchMethodException, SecurityException, IllegalArgumentException {
		if (ret.isEnum()) {
			final Object[] cst = ret.getEnumConstants();
			return cst[0];
		}
		try {
			final Constructor<?> ctor = ret.getConstructor();
			return ctor.newInstance();
		} catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
			LOG.trace("", e);
			return null;
		}
	}

}
