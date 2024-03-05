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

import static com.ubiqube.etsi.mano.Constants.ensureDisabled;
import static com.ubiqube.etsi.mano.Constants.ensureFailedTemp;
import static com.ubiqube.etsi.mano.Constants.ensureInstantiated;
import static com.ubiqube.etsi.mano.Constants.ensureIsEnabled;
import static com.ubiqube.etsi.mano.Constants.ensureIsOnboarded;
import static com.ubiqube.etsi.mano.Constants.ensureLockedByMyself;
import static com.ubiqube.etsi.mano.Constants.ensureNotInUse;
import static com.ubiqube.etsi.mano.Constants.ensureNotInstantiated;
import static com.ubiqube.etsi.mano.Constants.ensureNotLocked;
import static com.ubiqube.etsi.mano.Constants.ensureNotOnboarded;
import static com.ubiqube.etsi.mano.Constants.getSafeUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.exception.GenericException;

import jakarta.validation.constraints.NotNull;

@SuppressWarnings("static-method")
class ConstantTest {

	@Test
	void testName() throws Exception {
		Set<String> res;
		res = Constants.VNF_SEARCH_MANDATORY_FIELDS;
		assertNotNull(res);
		res = Constants.VNFPMJOB_SEARCH_MANDATORY_FIELDS;
		assertNotNull(res);
		res = Constants.VNFTHR_SEARCH_MANDATORY_FIELDS;
		assertNotNull(res);
		res = Constants.ALARM_SEARCH_MANDATORY_FIELDS;
		assertNotNull(res);
		res = Constants.VNFLCMOPOCC_SEARCH_MANDATORY_FIELDS;
		assertNotNull(res);
		res = Constants.VNFLCM_SEARCH_MANDATORY_FIELDS;
		assertNotNull(res);
	}

	@Test
	void testGetSingleField000() {
		final String res = Constants.getSingleField(null, null);
		assertNull(res);
	}

	@Test
	void testGetSingleField001() {
		final String param = "";
		final String res = Constants.getSingleField(null, param);
		assertNull(res);
	}

	@Test
	void testGetSingleField002() {
		final String param = "";
		final MultiValueMap<String, String> bag = new LinkedMultiValueMap<>();
		final String res = Constants.getSingleField(bag, param);
		assertNull(res);
	}

	@Test
	void testGetSingleField003() {
		final String param = "";
		final MultiValueMap<String, String> bag = new LinkedMultiValueMap<>();
		bag.put("key", List.of());
		final String res = Constants.getSingleField(bag, param);
		assertNull(res);
	}

	@Test
	void testGetSingleField004() {
		final String param = "key";
		final MultiValueMap<String, String> bag = new LinkedMultiValueMap<>();
		bag.put("key", List.of());
		final String res = Constants.getSingleField(bag, param);
		assertNull(res);
	}

	@Test
	void testGetSingleField005() {
		final String param = "key";
		final MultiValueMap<String, String> bag = new LinkedMultiValueMap<>();
		bag.put("key", List.of("v1"));
		final String res = Constants.getSingleField(bag, param);
		assertEquals("v1", res);
	}

	@Test
	void testGetSingleField006() {
		final String param = "key";
		final MultiValueMap<String, String> bag = new LinkedMultiValueMap<>();
		bag.put("key", List.of("v1", "v2"));
		assertThrows(GenericException.class, () -> Constants.getSingleField(bag, param));
	}

	/**
	 *
	 */
	@Test
	void testNsdEnsureDisabledTrue() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOperationalState(PackageOperationalState.DISABLED);
		ensureDisabled(pack);
		assertTrue(true);
	}

	@Test
	void testNsdEnsureDisabledFalse() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOperationalState(PackageOperationalState.ENABLED);
		assertThrows(ConflictException.class, () -> ensureDisabled(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdEnsureIsEnabledTrue() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOperationalState(PackageOperationalState.ENABLED);
		ensureIsEnabled(pack);
		assertTrue(true);
	}

	@Test
	void testNsdEnsureIsEnabledFalse() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOperationalState(PackageOperationalState.DISABLED);
		assertThrows(ConflictException.class, () -> ensureIsEnabled(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdNotInUseTrue() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdUsageState(UsageStateEnum.NOT_IN_USE);
		ensureNotInUse(pack);
		assertTrue(true);
	}

	@Test
	void testNsdEnsureNotInUseFalse() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdUsageState(UsageStateEnum.IN_USE);
		assertThrows(ConflictException.class, () -> ensureNotInUse(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdEnsureOnboardedTrue() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		ensureIsOnboarded(pack);
		assertTrue(true);
	}

	@Test
	void testNsdEnsureOnboardedFalse() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOnboardingState(OnboardingStateType.PROCESSING);
		assertThrows(ConflictException.class, () -> ensureIsOnboarded(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdEnsureNotOnboardedTrue() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOnboardingState(OnboardingStateType.UPLOADING);
		ensureNotOnboarded(pack);
		assertTrue(true);
	}

	@Test
	void testNsdEnsureNotOnboardedFalse() {
		final NsdPackage pack = new NsdPackage();
		pack.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		assertThrows(ConflictException.class, () -> ensureNotOnboarded(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdNotInstantiatedTrue() {
		final NsdInstance pack = new NsdInstance();
		pack.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		ensureNotInstantiated(pack);
		assertTrue(true);
	}

	@Test
	void testNsdNotInstantiatedFalse() {
		final NsdInstance pack = new NsdInstance();
		pack.setInstantiationState(InstantiationState.INSTANTIATED);
		assertThrows(ConflictException.class, () -> ensureNotInstantiated(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdInstantiatedTrue() {
		final NsdInstance pack = new NsdInstance();
		pack.setInstantiationState(InstantiationState.INSTANTIATED);
		ensureInstantiated(pack);
		assertTrue(true);
	}

	@Test
	void testNsdInstantiatedFalse() {
		final NsdInstance pack = new NsdInstance();
		pack.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		assertThrows(ConflictException.class, () -> ensureInstantiated(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdNotLockedTrue() {
		final NsdInstance pack = new NsdInstance();
		pack.setInstantiationState(InstantiationState.INSTANTIATED);
		ensureNotLocked(pack);
		assertTrue(true);
	}

	@Test
	void testNsdNotLockedFalse() {
		final NsdInstance pack = new NsdInstance();
		pack.setLockedBy(UUID.randomUUID());
		assertThrows(ConflictException.class, () -> ensureNotLocked(pack));
	}

	/**
	 *
	 */
	@Test
	void testNsdLockedByMySelfTrue() {
		final NsdInstance pack = new NsdInstance();
		final UUID id = UUID.randomUUID();
		pack.setLockedBy(id);
		pack.setId(id);
		assertThrows(ConflictException.class, () -> ensureLockedByMyself(pack, id));
	}

	@Test
	void testNsdLockedByMySelfFalse() {
		final NsdInstance pack = new NsdInstance();
		pack.setLockedBy(UUID.randomUUID());
		final UUID randomId = UUID.randomUUID();
		ensureLockedByMyself(pack, randomId);
		assertTrue(true);
	}

	@Test
	void testNsdLockedByMySelfNullFalse() {
		final NsdInstance pack = new NsdInstance();
		pack.setLockedBy(null);
		final UUID randomId = UUID.randomUUID();
		ensureLockedByMyself(pack, randomId);
		assertTrue(true);
	}

	/**
	 *
	 */
	@Test
	void testEnsureFailTempTrue() {
		final VnfBlueprint pack = new VnfBlueprint();
		pack.setOperationStatus(OperationStatusType.FAILED_TEMP);
		ensureFailedTemp(pack);
		assertTrue(true);
	}

	@Test
	void testEnsureFailTempFalse() {
		final VnfBlueprint pack = new VnfBlueprint();
		pack.setOperationStatus(OperationStatusType.FAILED);
		assertThrows(ConflictException.class, () -> ensureFailedTemp(pack));
	}

	/**
	 *
	 */
	@Test
	void testEnsureDisabledTrue() {
		final TestPackage pack = new TestPackage();
		pack.setOperationalState(PackageOperationalState.DISABLED);
		ensureDisabled(pack);
		assertTrue(true);
	}

	@Test
	void testEnsureDisabledFalse() {
		final TestPackage pack = new TestPackage();
		pack.setOperationalState(PackageOperationalState.ENABLED);
		assertThrows(ConflictException.class, () -> ensureDisabled(pack));
	}

	/**
	 *
	 */
	@Test
	void testEnsureEnableTrue() {
		final TestPackage pack = new TestPackage();
		pack.setOperationalState(PackageOperationalState.ENABLED);
		ensureIsEnabled(pack);
		assertTrue(true);
	}

	@Test
	void testEnsureEnableFalse() {
		final TestPackage pack = new TestPackage();
		pack.setOperationalState(PackageOperationalState.DISABLED);
		assertThrows(ConflictException.class, () -> ensureIsEnabled(pack));
	}

	/**
	 *
	 */
	@Test
	void testEnsureNotInUseTrue() {
		final TestPackage pack = new TestPackage();
		pack.setUsageState(UsageStateEnum.NOT_IN_USE);
		ensureNotInUse(pack);
		assertTrue(true);
	}

	@Test
	void testEnsureNotInUseFalse() {
		final TestPackage pack = new TestPackage();
		pack.setUsageState(UsageStateEnum.IN_USE);
		assertThrows(ConflictException.class, () -> ensureNotInUse(pack));
	}

	/**
	 *
	 */
	@Test
	void testEnsureIsOnboardedTrue() {
		final TestPackage pack = new TestPackage();
		pack.setOnboardingState(OnboardingStateType.ONBOARDED);
		ensureIsOnboarded(pack);
		assertTrue(true);
	}

	@Test
	void testEnsureIsOnboardedFalse() {
		final TestPackage pack = new TestPackage();
		pack.setOnboardingState(OnboardingStateType.PROCESSING);
		assertThrows(ConflictException.class, () -> ensureIsOnboarded(pack));
	}

	/**
	 *
	 */
	@Test
	void testEnsureNotOnboardedTrue() {
		final TestPackage pack = new TestPackage();
		pack.setOnboardingState(OnboardingStateType.PROCESSING);
		ensureNotOnboarded(pack);
		assertTrue(true);
	}

	@Test
	void testEnsureNotOnboardedFalse() {
		final TestPackage pack = new TestPackage();
		pack.setOnboardingState(OnboardingStateType.ONBOARDED);
		assertThrows(ConflictException.class, () -> ensureNotOnboarded(pack));
	}

	/**
	 *
	 */
	@Test
	void testEnsureInstantiatedTrue() {
		final Instance vnfInstance = new VnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		ensureInstantiated(vnfInstance);
		assertTrue(true);
	}

	@Test
	void testEnsureInstantiatedFalse() {
		final Instance vnfInstance = new VnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		assertThrows(ConflictException.class, () -> ensureInstantiated(vnfInstance));
	}

	/**
	 *
	 */
	@Test
	void testEnsureNotInstantiatedTrue() {
		final Instance vnfInstance = new VnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		ensureNotInstantiated(vnfInstance);
		assertTrue(true);
	}

	@Test
	void testEnsureNotInstantiatedFalse() {
		final Instance vnfInstance = new VnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		assertThrows(ConflictException.class, () -> ensureNotInstantiated(vnfInstance));
	}

	@Test
	void testSageGetUUID() {
		@NotNull
		final UUID res = getSafeUUID(UUID.randomUUID().toString());
		assertNotNull(res);
	}
}
