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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.mapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.annotation.Nullable;

public class CollectHashMapListener extends AbstractCollectListener {
	private final LinkedList<ClassTypeHolder> obj = new LinkedList<>();

	public CollectHashMapListener(final Class<?> obj) {
		this.obj.push(new ClassTypeHolder(obj, null));
	}

	@Override
	public void complexStart(final String name) {
		final ClassTypeHolder curr = obj.getFirst();
		if (curr.clazz.isAssignableFrom(Map.class)) {
			getStack().push(new AttrMapEntryNode(name));
			obj.push(new ClassTypeHolder(curr.param, null));
			return;
		}
		if (curr.clazz.isAssignableFrom(List.class)) {
			getStack().push(new NamedAttrNode(name));
			obj.push(new ClassTypeHolder(curr.param, null));
			return;
		}
		final BeanInfo beanInfo = getIntrospector(curr.clazz);
		final PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();
		final PropertyDescriptor props = find(name, propDescs);
		if (props == null) {
			throw new BeanWalkerException("Unable to find property [" + name + "] on " + curr.clazz);
		}
		final Class<?> ret = Objects.requireNonNull(props.getPropertyType());
		ClassTypeHolder newVal = new ClassTypeHolder(ret, null);
		if (ret.isAssignableFrom(Map.class) || ret.isAssignableFrom(List.class)) {
			final Class<?> subClass = extractInnerListType(props);
			newVal = new ClassTypeHolder(ret, subClass);
		}
		obj.push(newVal);
		getStack().push(new NamedAttrNode(name));
	}

	private static Class<?> extractInnerListType(final PropertyDescriptor propertyDescriptor) {
		final Method method = propertyDescriptor.getReadMethod();
		final ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();
		final Type[] type = returnType.getActualTypeArguments();
		return (Class<?>) type[0];
	}

	private static @Nullable PropertyDescriptor find(final String name, final @Nullable PropertyDescriptor[] propDescs) {
		if (null == propDescs) {
			return null;
		}
		for (final PropertyDescriptor propertyDescriptor : propDescs) {
			if (propertyDescriptor.getName().equals(name)) {
				return propertyDescriptor;
			}
		}
		return null;
	}

	/**
	 * getBeanInfo cannot be null.
	 *
	 * @param clazz The class to introspect.
	 * @return Non null BeanInfo
	 */
	@SuppressWarnings("null")
	private static BeanInfo getIntrospector(final Class<?> clazz) {
		try {
			return Introspector.getBeanInfo(clazz);
		} catch (final IntrospectionException e) {
			throw new BeanWalkerException(e);
		}
	}

	@Override
	public void complexEnd() {
		getStack().pop();
		obj.pop();
	}

	record ClassTypeHolder(Class<?> clazz, @Nullable Class<?> param) {
		/**/}
}
