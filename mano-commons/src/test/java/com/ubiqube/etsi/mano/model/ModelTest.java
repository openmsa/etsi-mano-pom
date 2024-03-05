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
package com.ubiqube.etsi.mano.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleByStepData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleToLevelData;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author olivier
 *
 */
class ModelTest {

	private final PodamFactoryImpl podam;

	public ModelTest() {
		podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testGeneric(final Class<?> clazz) {
		final Object avc = podam.manufacturePojo(clazz);
		assertNotNull(avc);
		final String res = avc.toString();
		assertNotNull(res);
		avc.hashCode();
		avc.toString();
		avc.equals(null);
		avc.equals(res);
		avc.equals(avc);
		final EqualsVerifierReport rep = EqualsVerifier
				.simple()
				.forClass(avc.getClass())
				.suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT, Warning.SURROGATE_KEY)
				.report();
		System.out.println("" + rep.getMessage());
	}

	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(EventMessage.class),
				Arguments.of(ProblemDetails.class),
				Arguments.of(VnfScaleToLevelRequest.class),
				Arguments.of(VnfOperateRequest.class),
				Arguments.of(ApiVersionInformation.class),
				Arguments.of(VnfScaleRequest.class),
				Arguments.of(VnfHealRequest.class),
				Arguments.of(ExternalManagedVirtualLink.class));
	}

	@Test
	void testVnfScaleRequest() {
		final ScaleByStepData scaleData = podam.manufacturePojo(ScaleByStepData.class);
		final VnfScaleRequest res = VnfScaleRequest.of(ScaleTypeEnum.fromValue("IN"), scaleData, Set.of());

		assertNotNull(res);
	}

	@Test
	void testVnfScaleToLevelRequest() {
		final ScaleToLevelData scaleData = podam.manufacturePojo(ScaleToLevelData.class);
		final VnfScaleToLevelRequest res = VnfScaleToLevelRequest.of(scaleData);
		assertNotNull(res);
	}

	@Test
	void testEventMessage() {
		final EventMessage res = new EventMessage(NotificationEvent.APP_INSTANCE_CREATE, UUID.randomUUID(), Map.of());
		assertNotNull(res);
	}
}
