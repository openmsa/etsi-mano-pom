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
package com.ubiqube.etsi.mano.nfvo.v261.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.common.v261.model.Link;
import com.ubiqube.etsi.mano.common.v261.model.lcmgrant.Grant;
import com.ubiqube.etsi.mano.common.v261.model.nslcm.VnfInstance;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscription;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.controller.subscription.ApiAndType;
import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.GrantInterface;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
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
import com.ubiqube.etsi.mano.nfvo.v261.model.lcmgrant.GrantRequest;
import com.ubiqube.etsi.mano.nfvo.v261.model.lcmgrant.GrantRequestLinks;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsd.sol005.CreateNsdInfoRequest;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsd.sol005.NsdInfo;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsperfo.PmJobsCreatePmJobRequest;
import com.ubiqube.etsi.mano.nfvo.v261.model.vnf.CreateVnfPkgInfoRequest;
import com.ubiqube.etsi.mano.service.AbstractHttpGateway;
import com.ubiqube.etsi.mano.service.NfvoFactory;
import com.ubiqube.etsi.mano.service.VnfmFactory;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.utils.Version;
import com.ubiqube.etsi.mano.vnfm.v261.model.nslcm.CreateVnfRequest;
import com.ubiqube.etsi.mano.vnfm.v261.model.nslcm.LccnSubscriptionRequest;
import com.ubiqube.etsi.mano.vnfm.v261.model.nslcm.ScaleVnfRequest;
import com.ubiqube.etsi.mano.vnfm.v261.model.nslcm.TerminateVnfRequest;
import com.ubiqube.etsi.mano.vnfm.v261.model.nslcm.TerminateVnfRequest.TerminationTypeEnum;
import com.ubiqube.etsi.mano.vnfm.v261.model.nslcm.VnfLcmOpOcc;
import com.ubiqube.etsi.mano.vnfm.v261.model.nsperfo.CreateThresholdRequest;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnfind.VnfIndicator;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnfind.VnfIndicatorSubscription;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnfind.VnfIndicatorSubscriptionRequest;
import com.ubiqube.etsi.mano.vnfm.v261.model.vrqan.VrQuotaAvailSubscription;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud {@literal {@literal <ovi@ubiqube.com>}}
 *
 */
@Service
public class VnfmGateway261 extends AbstractHttpGateway {

	private static final Logger LOG = LoggerFactory.getLogger(VnfmGateway261.class);

	private final NfvoFactory nfvoFactory;
	private final VnfmFactory vnfmFactory;
	private final MapperFacade mapper;

	public VnfmGateway261(final ObjectProvider<VnfmFactory> vnfmFactory, final ObjectProvider<NfvoFactory> nfvoFactory, final MapperFacade mapper) {
		this.vnfmFactory = vnfmFactory.getIfAvailable();
		this.nfvoFactory = nfvoFactory.getIfAvailable();
		this.mapper = mapper;
	}

	@Override
	public Class<?> getVnfPackageClass() {
		return VnfPkgInfo.class;
	}

	@Override
	public Class<?> getVnfPackageSubscriptionClass() {
		return PkgmSubscription.class;
	}

	@Override
	public Class<?> getVnfIndicatorValueChangeSubscriptionClass() {
		return VnfIndicatorSubscription.class;
	}

	@Override
	public Class<?> getVnfIndicatorValueChangeSubscriptionRequest() {
		return VnfIndicatorSubscriptionRequest.class;
	}

	@Override
	public Class<?> getGrantResponse() {
		return Grant.class;
	}

	@Override
	public void makeGrantLinks(final Object manoGrant) {
		if (manoGrant instanceof final GrantRequest grant) {
			vnfmFactory.makeGrantRequestLink(grant);
		}
	}

	@Override
	public Class<?> getVnfInstanceClass() {
		return VnfInstance.class;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfInstanceListParam() {
		final ParameterizedTypeReference<List<VnfInstance>> res = new ParameterizedTypeReference<>() {
			// Nothing.
		};
		return (ParameterizedTypeReference<List<Class<?>>>) (Object) res;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getListVnfLcmOpOccs() {
		final ParameterizedTypeReference<List<VnfLcmOpOcc>> res = new ParameterizedTypeReference<>() {
			// Nothing.
		};
		return (ParameterizedTypeReference<List<Class<?>>>) (Object) res;
	}

	@Override
	public Object createVnfInstanceRequest(final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription) {
		final var req = new CreateVnfRequest();
		req.setVnfdId(vnfdId);
		req.setVnfInstanceDescription(vnfInstanceDescription);
		req.setVnfInstanceName(vnfInstanceName);
		return req;
	}

	@Override
	public Class<?> getVnfLcmOpOccs() {
		return VnfLcmOpOcc.class;
	}

	@Override
	public Object createVnfInstanceTerminate(final CancelModeTypeEnum terminationType, final Integer gracefulTerminationTimeout) {
		final TerminateVnfRequest ret = new TerminateVnfRequest();
		ret.setTerminationType(TerminationTypeEnum.fromValue(terminationType.toString()));
		ret.setGracefulTerminationTimeout(gracefulTerminationTimeout);
		return ret;
	}

	@Override
	public Object createVnfInstanceScaleRequest(final ScaleTypeEnum scaleType, final String aspectId, final Integer numberOfSteps) {
		final var req = new ScaleVnfRequest();
		req.setAspectId(aspectId);
		req.setNumberOfSteps(numberOfSteps);
		if (ScaleTypeEnum.IN.equals(scaleType)) {
			req.setType(ScaleVnfRequest.TypeEnum.IN);
		}
		if (ScaleTypeEnum.OUT.equals(scaleType)) {
			req.setType(ScaleVnfRequest.TypeEnum.OUT);
		}
		return req;
	}

	@Override
	public Object createEvent(final UUID subscriptionId, final EventMessage event) {
		return switch (event.getNotificationEvent()) {
		case VNF_PKG_ONCHANGE, VNF_PKG_ONDELETION -> nfvoFactory.createVnfPackageChangeNotification(subscriptionId, event);
		case VNF_PKG_ONBOARDING -> nfvoFactory.createNotificationVnfPackageOnboardingNotification(subscriptionId, event);
		case VNF_INDICATOR_VALUE_CHANGED -> vnfmFactory.createVnfIndicatorValueChangeNotification(subscriptionId, event);
		default -> {
			LOG.warn("Could not find event.");
			yield null;
		}
		};
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfPackageClassList() {
		final ParameterizedTypeReference<List<VnfPackage>> res = new ParameterizedTypeReference<>() {
			// Nothing.
		};
		return (ParameterizedTypeReference<List<Class<?>>>) (Object) res;
	}

	@Override
	public Version getVersion() {
		return new Version("2.6.1");
	}

	@Override
	public Class<?> createVnfPackageRequest(final Map<String, String> userDefinedData) {
		return CreateVnfPkgInfoRequest.class;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getNsdPackageClassList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getNsdPackageClass() {
		return NsdInfo.class;
	}

	@Override
	public Object createNsdPackageRequest(final Map<String, Object> userDefinedData) {
		final CreateNsdInfoRequest req = new CreateNsdInfoRequest();
		req.setUserDefinedData(userDefinedData.entrySet().stream().map(x -> Map.entry(x.getKey(), x.getValue().toString())).collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
		return req;
	}

	@Override
	public Object createGrantRequest(final GrantInterface grant) {
		final GrantRequest g = mapper.map(grant, GrantRequest.class);
		final GrantRequestLinks links = new GrantRequestLinks();
		final Link link = new Link();
		link.setHref("http://");
		links.setVnfInstance(link);
		links.setVnfLcmOpOcc(link);
		g.setLinks(links);
		return g;
	}

	@Override
	public Class<?> getVnfPmJobClass() {
		return PmJob.class;
	}

	@Override
	public Object createVnfPmJobRequest(final PmJob pmJob) {
		return mapper.map(pmJob, PmJobsCreatePmJobRequest.class);
	}

	@Override
	public Object createVnfThresholdRequest(final Threshold reqIn) {
		return mapper.map(reqIn, CreateThresholdRequest.class);
	}

	@Override
	public String getSubscriptionUriFor(final ApiAndType at, final String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorClass() {
		return VnfIndicator.class;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfIndicatorClassList() {
		final ParameterizedTypeReference<List<VnfIndicator>> res = new ParameterizedTypeReference<>() {
			// Nothing.
		};
		return (ParameterizedTypeReference<List<Class<?>>>) (Object) res;
	}

	@Override
	public Class<?> getVnfIndicatorSubscriptionClass() {
		return VnfIndicatorSubscriptionRequest.class;
	}

	@Override
	public Object createVnfInstanceSubscriptionRequest(final Subscription subscription) {
		return mapper.map(subscription, LccnSubscriptionRequest.class);
	}

	@Override
	public Object createVnfIndicatorSubscriptionRequest(final Subscription subscription) {
		return mapper.map(subscription, VnfIndicatorSubscriptionRequest.class);
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
	public Class<?> getVrQanSubscriptionClass() {
		return VrQuotaAvailSubscription.class;
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
	public com.ubiqube.etsi.mano.dao.mano.VnfIndicator mapToVnfIndicator(final Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ubiqube.etsi.mano.dao.mano.VnfInstance mapToVnfInstance(final Object o) {
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

	@Override
	public Object mapGrantRequest(final GrantInterface o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object mapVrQanSubscriptionRequest(final Subscription o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPkgmSubscriptionRequest(final Subscription req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfThresholdClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVnfInstanceInstantiateRequestClass(final VnfInstantiate req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVnfInstanceScaleToLevelRequest(final VnfScaleToLevelRequest req) {
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

}
