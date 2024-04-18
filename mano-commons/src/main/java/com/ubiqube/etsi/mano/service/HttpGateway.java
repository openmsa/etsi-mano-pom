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
package com.ubiqube.etsi.mano.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;

import com.ubiqube.etsi.mano.controller.subscription.ApiAndType;
import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.GrantInterface;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.utils.Version;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public interface HttpGateway {

	Class<?> getVnfPackageClass();

	Object createVnfPackageRequest(Map<String, String> userDefinedData);

	ParameterizedTypeReference<List<Class<?>>> getVnfPackageClassList();

	Class<?> getVnfPackageSubscriptionClass();

	Object getPkgmSubscriptionRequest(Subscription req);

	Class<?> getVnfIndicatorValueChangeSubscriptionClass();

	Object getVnfIndicatorValueChangeSubscriptionRequest(Subscription req);

	Object mapGrantRequest(GrantInterface o);

	Class<?> getGrantResponse();

	Object createGrantRequest(GrantInterface grant);

	void makeGrantLinks(Object manoGrant);

	String getUrlFor(ApiVersionType type);

	String getSubscriptionUriFor(ApiAndType at, String id);

	Class<?> getVnfInstanceClass();

	Class<?> getVnfThresholdClass();

	ParameterizedTypeReference<List<Class<?>>> getVnfInstanceListParam();

	ParameterizedTypeReference<List<Class<?>>> getListVnfLcmOpOccs();

	ParameterizedTypeReference<List<Class<?>>> getNsdPackageClassList();

	Object createVnfInstanceRequest(String vnfdId, String vnfInstanceName, String vnfInstanceDescription);

	Object getVnfInstanceInstantiateRequest(VnfInstantiate req);

	Class<?> getVnfLcmOpOccsClass();

	Object createVnfInstanceTerminate(CancelModeTypeEnum terminationType, Integer gracefulTerminationTimeout);

	Object getVnfInstanceScaleToLevelRequest(VnfScaleToLevelRequest req);

	Object createVnfInstanceScaleRequest(ScaleTypeEnum scaleTypeEnum, String aspectId, Integer numberOfSteps);

	Object createVnfInstanceHealRequest(VnfHealRequest req);

	Object getVnfInstanceOperateRequest(VnfOperateRequest req);

	Object getVnfInstanceChangeFalvourRequest(ChangeVnfFlavourData req);

	Object getVnfInstanceChangeExtConnRequest(ChangeExtVnfConnRequest req);

	@Nullable
	Object createEvent(UUID uuid, EventMessage event);

	Version getVersion();

	Optional<String> getHeaderVersion(final ApiVersionType apiVersionType);

	Class<?> getNsdPackageClass();

	Object createNsdPackageRequest(Map<String, Object> userDefinedData);

	boolean isMatching(final ApiVersionType verType, @Nullable String version);

	Class<?> getVnfPmJobClass();

	Object createVnfPmJobRequest(PmJob pmJob);

	Object createVnfThresholdRequest(Threshold req);

	Class<?> getVnfIndicatorClass();

	ParameterizedTypeReference<List<Class<?>>> getVnfIndicatorClassList();

	Class<?> getVnfIndicatorSubscriptionClass();

	Object createVnfInstanceSubscriptionRequest(Subscription subscription);

	Object createVnfIndicatorSubscriptionRequest(Subscription subscription);

	Object createVnfFmSubscriptionRequest(Subscription subscription);

	Class<?> getVnfFmSubscriptionClass();

	Object mapVrQanSubscriptionRequest(Subscription o);

	Class<?> getVrQanSubscriptionClass();

	Subscription mapVnfFmSubscription(Object o);

	Subscription mapVrQanSubscriptionSubscription(Object o);

	Subscription mapToPkgmSubscription(Object o);

	Subscription mapToVnfIndicatorSubscription(Object o);

	GrantResponse mapToGrantResponse(Object o);

	NsdPackage mapToNsdPackage(Object o);

	VnfIndicator mapToVnfIndicator(Object o);

	VnfInstance mapToVnfInstance(Object o);

	VnfBlueprint mapToVnfBlueprint(Object o);

	VnfPackage mapToVnfPackage(Object o);

	Threshold mapToThreshold(Object o);

	PmJob mapToPmJob(Object o);
}
