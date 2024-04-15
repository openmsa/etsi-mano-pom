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
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.utils.Version;

/**
 *
 * @author olivier
 *
 */
public class HttpGatewayBad extends AbstractHttpGateway {

	@Override
	public Class<?> getVnfPackageClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfPackageRequest(final Map<String, String> userDefinedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfPackageClassList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfPackageSubscriptionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPkgmSubscriptionRequest(final Subscription req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorValueChangeSubscriptionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorValueChangeSubscriptionRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object mapGrantRequest(final GrantInterface o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getGrantResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGrantRequest(final GrantInterface grant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeGrantLinks(final Object manoGrant) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSubscriptionUriFor(final ApiAndType at, final String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfInstanceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfThresholdClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfInstanceListParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getListVnfLcmOpOccs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getNsdPackageClassList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceRequest(final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVnfInstanceInstantiateRequestClass(final VnfInstantiate req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfLcmOpOccs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceTerminate(final CancelModeTypeEnum terminationType, final Integer gracefulTerminationTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVnfInstanceScaleToLevelRequest(final VnfScaleToLevelRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceScaleRequest(final ScaleTypeEnum scaleTypeEnum, final String aspectId, final Integer numberOfSteps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceHealRequest(final VnfHealRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVnfInstanceOperateRequest(final VnfOperateRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVnfInstanceChangeFalvourRequest(final ChangeVnfFlavourData req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVnfInstanceChangeExtConnRequest(final ChangeExtVnfConnRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createEvent(final UUID uuid, final EventMessage event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getNsdPackageClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createNsdPackageRequest(final Map<String, Object> userDefinedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfPmJobClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfPmJobRequest(final PmJob pmJob) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfThresholdRequest(final Threshold req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfIndicatorClassList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorSubscriptionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceSubscriptionRequest(final Subscription subscription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfIndicatorSubscriptionRequest(final Subscription subscription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfFmSubscriptionRequest(final Subscription subscription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfFmSubscriptionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object mapVrQanSubscriptionRequest(final Subscription o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVrQanSubscriptionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subscription mapSubscription(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subscription mapVrQanSubscriptionSubscription(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subscription mapToPkgmSubscription(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subscription mapToVnfIndicatorSubscription(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrantResponse mapToGrantResponse(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NsdPackage mapToNsdPackage(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VnfIndicator mapToVnfIndicator(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VnfInstance mapToVnfInstance(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VnfBlueprint mapToVnfBlueprint(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VnfPackage mapToVnfPackage(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Threshold mapToThreshold(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PmJob mapToPmJob(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
