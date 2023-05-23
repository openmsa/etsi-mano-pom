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
package com.ubiqube.etsi.mano.alarm.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.alarm.ModelTest;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class ModelEntitiesTest {

	@Test
	void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		ModelTest.realHandle(AuthentificationInformations.class.getName());
		ModelTest.realHandle(AuthParamBasic.class.getName());
		ModelTest.realHandle(AuthParamOauth2.class.getName());
		ModelTest.realHandle(AlarmSubscription.class.getName());
		assertTrue(true);
	}

}
