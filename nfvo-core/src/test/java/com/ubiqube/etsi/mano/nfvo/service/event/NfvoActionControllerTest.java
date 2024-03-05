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
package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageOnboardingImpl;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;

@ExtendWith(MockitoExtension.class)
class NfvoActionControllerTest {
	@Mock
	private NfvoActions nfvoAction;
	@Mock
	private NsPackageOnboardingImpl nsPack;
	@Mock
	private VnfPackageOnboardingImpl vnfPkgOnbarding;
	@Mock
	private NsUpadteManager nsUpdateManager;

	@Test
	void testUnknown() {
		final NfvoActionController nac = new NfvoActionController(nfvoAction, nsPack, vnfPkgOnbarding, nsUpdateManager);
		final UUID id = UUID.randomUUID();
		nac.dispatch(ActionType.MEO_ONBOARDING, id, Map.of());
		assertTrue(true);
	}

	@SuppressWarnings("null")
	private static Stream<Arguments> provider3Class() {
		return Stream.of(
				Arguments.of(ActionType.VNF_PKG_ONBOARD_FROM_URI),
				Arguments.of(ActionType.VNF_PKG_ONBOARD_FROM_BYTES),
				Arguments.of(ActionType.NSD_PKG_ONBOARD_FROM_BYTES),
				Arguments.of(ActionType.NS_HEAL),
				Arguments.of(ActionType.NS_UPDATE),
				Arguments.of(ActionType.NS_UPDATE_ACTION));
	}

	@ParameterizedTest
	@MethodSource("provider3Class")
	void testAll(final ActionType arg) {
		final NfvoActionController nac = new NfvoActionController(nfvoAction, nsPack, vnfPkgOnbarding, nsUpdateManager);
		final UUID id = UUID.randomUUID();
		nac.dispatch(arg, id, Map.of());
		assertTrue(true);
	}

	@Test
	void testInstantiate() {
		final NfvoActionController nac = new NfvoActionController(nfvoAction, nsPack, vnfPkgOnbarding, nsUpdateManager);
		final UUID id = UUID.randomUUID();
		doNothing().when(nfvoAction).instantiate(id);
		nac.dispatch(ActionType.NS_INSTANTIATE, id, Map.of());
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final NfvoActionController nac = new NfvoActionController(nfvoAction, nsPack, vnfPkgOnbarding, nsUpdateManager);
		final UUID id = UUID.randomUUID();
		doNothing().when(nfvoAction).terminate(id);
		nac.dispatch(ActionType.NS_TERMINATE, id, Map.of());
		assertTrue(true);
	}

	@Test
	void testScale() {
		final NfvoActionController nac = new NfvoActionController(nfvoAction, nsPack, vnfPkgOnbarding, nsUpdateManager);
		final UUID id = UUID.randomUUID();
		doNothing().when(nfvoAction).scale(id);
		nac.dispatch(ActionType.NS_SCALE, id, Map.of());
		assertTrue(true);
	}
}
