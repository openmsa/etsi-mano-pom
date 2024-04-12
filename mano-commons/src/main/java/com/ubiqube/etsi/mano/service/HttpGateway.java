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
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
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

	Class<?> getPkgmSubscriptionRequest();

	Class<?> getVnfIndicatorValueChangeSubscriptionClass();

	Class<?> getVnfIndicatorValueChangeSubscriptionRequest();

	Class<?> getGrantRequest();

	Class<?> getGrantResponse();

	Object createGrantRequest(GrantInterface grant);

	void makeGrantLinks(Object manoGrant);

	String getUrlFor(ApiVersionType type);

	String getSubscriptionUriFor(ApiAndType at, String id);

	Class<?> getVnfInstanceClass();

	ParameterizedTypeReference<List<Class<?>>> getVnfInstanceListParam();

	ParameterizedTypeReference<List<Class<?>>> getListVnfLcmOpOccs();

	ParameterizedTypeReference<List<Class<?>>> getNsdPackageClassList();

	Object createVnfInstanceRequest(String vnfdId, String vnfInstanceName, String vnfInstanceDescription);

	Class<?> getVnfInstanceInstantiateRequestClass();

	Class<?> getVnfLcmOpOccs();

	Object createVnfInstanceTerminate(CancelModeTypeEnum terminationType, Integer gracefulTerminationTimeout);

	Class<?> getVnfInstanceScaleToLevelRequest();

	Object createVnfInstanceScaleRequest(ScaleTypeEnum scaleTypeEnum, String aspectId, Integer numberOfSteps);

	Object createVnfInstanceHealRequest(String cause);

	Class<?> getVnfInstanceScaleRequest();

	Class<?> getVnfInstanceHealRequest();

	Class<?> getVnfInstanceOperateRequest();

	Class<?> getVnfInstanceChangeExtConnRequest();

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

	Class<?> getVnfInstanceSubscriptionRequest();

	Class<?> getVnfInstanceSubscriptionClass();

	Class<?> getVnfIndicatorClass();

	ParameterizedTypeReference<List<Class<?>>> getVnfIndicatorClassList();

	Class<?> getVnfIndicatorSubscriptionClass();

	Class<?> getVnfIndicatorRequest();

	Object createVnfInstanceSubscriptionRequest(Subscription subscription);

	Object createVnfIndicatorSubscriptionRequest(Subscription subscription);

	Object createVnfFmSubscriptionRequest(Subscription subscription);

	Class<?> getVnfFmSubscriptionRequest();

	Class<?> getVnfFmSubscriptionClass();

	Class<?> getVrQanSubscriptionRequest();

	Class<?> getVrQanSubscriptionClass();
}
