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
package com.ubiqube.etsi.mano.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.annotation.Nullable;

public class ReflectionUtils {

	private ReflectionUtils() {
		//
	}

	public static Set<Class<?>> getGenericTypes(final ParameterizedType parameterizedType) {
		final Set<Class<?>> genericTypes = new LinkedHashSet<>();
		for (final Type genericType : parameterizedType.getActualTypeArguments()) {
			if (genericType instanceof Class<?>) {
				genericTypes.add((Class<?>) genericType);
			}
		}
		return genericTypes;
	}

	public static Method getMethodOrNull(final Object target, final String methodName, final Class<?>... parameterTypes) {
		try {
			return getMethod(target.getClass(), methodName, parameterTypes);
		} catch (final RuntimeException e) {
			return null;
		}
	}

	public static Method getMethod(final Class<?> targetClass, final String methodName, final Class<?>... parameterTypes) {
		try {
			return targetClass.getDeclaredMethod(methodName, parameterTypes);
		} catch (final NoSuchMethodException e) {
			try {
				return targetClass.getMethod(methodName, parameterTypes);
			} catch (final NoSuchMethodException ignore) {
				//
			}

			if (!targetClass.getSuperclass().equals(Object.class)) {
				return getMethod(targetClass.getSuperclass(), methodName, parameterTypes);
			}
			throw new IllegalArgumentException(e);
		}
	}

	public static Method getDeclaredMethodOrNull(final Class<?> targetClass, final String methodName, final Class<?>... parameterTypes) {
		try {
			return targetClass.getDeclaredMethod(methodName, parameterTypes);
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}

	@Nullable
	public static <U> U invoke(final Method meth, final Object obj, final Object... args) {
		try {
			return (U) meth.invoke(obj, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}

	}

}
