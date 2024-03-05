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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.AttributeAssignements;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.service.pkg.bean.ProviderData;
import com.ubiqube.parser.tosca.AttributeAssignement;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnfOnboardingMapperService {
	private final MapperFacade mapper;

	private final List<OnboardVisitor> onboardVisitors;

	private final List<OnboardingPostProcessorVisitor> postProcessors;

	private final CustomOnboarding customOnboarding;

	public VnfOnboardingMapperService(final MapperFacade mapper, final List<OnboardVisitor> onboardVisitors, final List<OnboardingPostProcessorVisitor> postProcessors, final CustomOnboarding customOnboarding) {
		this.mapper = mapper;
		this.onboardVisitors = onboardVisitors;
		this.postProcessors = postProcessors;
		this.customOnboarding = customOnboarding;
	}

	void mapper(final VnfPackageReader vnfPackageReader, final VnfPackage vnfPackage, final ProviderData pd) {
		mapper.map(pd, vnfPackage);
		additionalMapping(pd, vnfPackage);
		final Map<String, String> userData = vnfPackage.getUserDefinedData();
		onboardVisitors.forEach(x -> x.visit(vnfPackage, vnfPackageReader, userData));
		postProcessors.forEach(x -> x.visit(vnfPackage));
		customOnboarding.handleArtifacts(vnfPackage, vnfPackageReader);
	}

	private static void additionalMapping(final ProviderData pd, final VnfPackage vnfPackage) {
		vnfPackage.addVirtualLink(pd.getVirtualLinkReq());
		vnfPackage.addVirtualLink(pd.getVirtualLink1Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink2Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink3Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink4Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink5Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink6Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink7Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink8Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink9Req());
		vnfPackage.addVirtualLink(pd.getVirtualLink10Req());
		final Set<ListKeyPair> nl = vnfPackage.getVirtualLinks().stream().filter(x -> x.getValue() != null).collect(Collectors.toSet());
		final List<AttributeAssignements> opt = Optional.ofNullable(pd.getOverloadedAttributes())
				.map(x -> x.entrySet().stream().map(y -> map(y)).toList())
				.orElseGet(List::of);
		vnfPackage.setOverloadedAttribute(opt);
		vnfPackage.setVirtualLinks(nl);
	}

	private static AttributeAssignements map(final Entry<String, AttributeAssignement> val) {
		return new AttributeAssignements(null, val.getKey(), val.getValue().getValue());
	}
}
