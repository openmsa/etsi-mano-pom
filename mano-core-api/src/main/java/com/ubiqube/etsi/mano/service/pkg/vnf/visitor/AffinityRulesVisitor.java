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
package com.ubiqube.etsi.mano.service.pkg.vnf.visitor;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.AffinityRule;
import com.ubiqube.etsi.mano.service.pkg.bean.AffinityRuleAdapater;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardVisitor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class AffinityRulesVisitor implements OnboardVisitor {
	private final MapperFacade mapper;

	public AffinityRulesVisitor(final MapperFacade mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public void visit(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final Map<String, String> userData) {
		final Set<AffinityRuleAdapater> ar = vnfPackageReader.getAffinityRules(vnfPackage.getUserDefinedData());
		handleAffinity(ar, vnfPackage);

	}

	private void handleAffinity(final Set<AffinityRuleAdapater> ar, final VnfPackage vnfPackage) {
		ar.forEach(x -> {
			vnfPackage.getVnfCompute().stream()
					.filter(y -> x.getTargets().contains(y.getToscaName()))
					.forEach(y -> y.addAffinity(x.getAffinityRule().getToscaName()));
			vnfPackage.getVnfVl().stream()
					.filter(y -> x.getTargets().contains(y.getToscaName()))
					.forEach(y -> y.addAffinity(x.getAffinityRule().getToscaName()));
			// Placement group.
		});
		final Set<AffinityRule> res = ar.stream().map(x -> mapper.map(x.getAffinityRule(), AffinityRule.class)).collect(Collectors.toSet());
		vnfPackage.setAffinityRules(res);
	}

}
