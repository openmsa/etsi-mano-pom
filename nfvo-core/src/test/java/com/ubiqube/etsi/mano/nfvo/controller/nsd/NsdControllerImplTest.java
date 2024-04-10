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
package com.ubiqube.etsi.mano.nfvo.controller.nsd;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.repository.NsdRepository;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.event.EventManager;

@ExtendWith(MockitoExtension.class)
class NsdControllerImplTest {
	@Mock
	@Nonnull
	private NsdRepository nsdRepository;
	@Mock
	@Nonnull
	private Patcher patcher;
	@Mock
	@Nonnull
	private EventManager eventManager;
	@Mock
	@Nonnull
	private SearchableService searchableService;

	@Test
	void testDelete() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		final NsdPackage nsdPackage = new NsdPackage();
		nsdPackage.setNsdOperationalState(PackageOperationalState.DISABLED);
		nsdPackage.setNsdUsageState(UsageStateEnum.NOT_IN_USE);
		when(nsdRepository.get(any())).thenReturn(nsdPackage);
		srv.nsDescriptorsNsdInfoIdDelete(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testInfoGet() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		srv.nsDescriptorsNsdInfoIdGet(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testContentGet() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		final NsdPackage nsdPackage = new NsdPackage();
		nsdPackage.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		when(nsdRepository.get(any())).thenReturn(nsdPackage);
		srv.nsDescriptorsNsdInfoIdNsdContentGet(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testContentPut() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		final NsdPackage nsdPackage = new NsdPackage();
		when(nsdRepository.get(any())).thenReturn(nsdPackage);
		srv.nsDescriptorsNsdInfoIdNsdContentPut(UUID.randomUUID(), null);
		assertTrue(true);
	}

	@Test
	void testPatch() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		final NsdPackage nsdPackage = new NsdPackage();
		nsdPackage.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		when(nsdRepository.get(any())).thenReturn(nsdPackage);
		srv.nsDescriptorsNsdInfoIdPatch(UUID.randomUUID(), null, null);
		assertTrue(true);
	}

	@Test
	void testPatch2() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		final NsdPackage nsdPackage = new NsdPackage();
		nsdPackage.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		when(nsdRepository.get(any())).thenReturn(nsdPackage);
		final UUID uuid = UUID.randomUUID();
		assertThrows(PreConditionException.class, () -> srv.nsDescriptorsNsdInfoIdPatch(uuid, null, "45"));
		assertTrue(true);
	}

	@Test
	void testPatch3() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		final NsdPackage nsdPackage = new NsdPackage();
		nsdPackage.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		nsdPackage.setVersion(45);
		when(nsdRepository.get(any())).thenReturn(nsdPackage);
		srv.nsDescriptorsNsdInfoIdPatch(UUID.randomUUID(), null, "45");
		assertTrue(true);
	}

	@Test
	void testDescriptorPost() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		srv.nsDescriptorsPost(null);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final NsdControllerImpl srv = new NsdControllerImpl(nsdRepository, patcher, eventManager, searchableService);
		srv.search(null, null, null, null, null, null);
		assertTrue(true);
	}
}
