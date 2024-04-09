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

import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.SecurityGroup;
import com.ubiqube.etsi.mano.service.mapping.SecurityGroupMapping;
import com.ubiqube.etsi.mano.service.pkg.bean.SecurityGroupAdapter;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardVisitor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

/**
 * Have a dependency on VNFExtCp.
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class SecurityGroupVisitor implements OnboardVisitor {
	private final SecurityGroupMapping securityGroupMapping;

	public SecurityGroupVisitor(final SecurityGroupMapping securityGroupMapping) {
		this.securityGroupMapping = securityGroupMapping;
	}

	@Override
	public void visit(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final Map<String, String> userData) {
		final Set<SecurityGroupAdapter> sgAdapters = vnfPackageReader.getSecurityGroups(userData);
		final Set<VnfExtCp> vnfExtCp = vnfPackage.getVnfExtCp();
		handleSecurityGroups(sgAdapters, vnfPackage, vnfExtCp);
	}

	private void handleSecurityGroups(final Set<SecurityGroupAdapter> sgAdapters, final VnfPackage vnfPackage, final Set<VnfExtCp> vnfExtCp) {
		sgAdapters.forEach(x -> {
			vnfPackage.getVnfCompute().stream()
					.filter(y -> x.getTargets().contains(y.getToscaName()))
					.forEach(y -> y.addSecurityGroups(x.getSecurityGroup().getToscaName()));
			vnfExtCp.stream()
					.filter(y -> x.getTargets().contains(y.getToscaName()))
					.forEach(y -> y.addSecurityGroup(x.getSecurityGroup().getToscaName()));
		});
		final Set<SecurityGroup> res = sgAdapters.stream().map(x -> securityGroupMapping.map(x.getSecurityGroup())).collect(Collectors.toSet());
		vnfPackage.setSecurityGroups(res);
	}

}
