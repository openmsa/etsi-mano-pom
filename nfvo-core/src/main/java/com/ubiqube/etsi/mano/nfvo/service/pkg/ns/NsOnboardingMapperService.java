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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;
import com.ubiqube.etsi.mano.service.pkg.vnf.CustomOnboarding;

/**
 *
 * @author olivier.vignaud
 *
 */
@Service
public class NsOnboardingMapperService {

	private final List<NsOnboardingVisitor> onboardVisitors;

	private final List<NsOnboardingPostProcessor> postProcessors;

	private final CustomOnboarding customOnboarding;

	public NsOnboardingMapperService(final List<NsOnboardingVisitor> onboardVisitors, final List<NsOnboardingPostProcessor> postProcessors, final CustomOnboarding customOnboarding) {
		super();
		this.onboardVisitors = onboardVisitors;
		this.postProcessors = postProcessors;
		this.customOnboarding = customOnboarding;
	}

	void mapper(final NsPackageProvider packageReader, final NsdPackage pkg) {
		final Map<String, String> userData = pkg.getUserDefinedData();
		onboardVisitors.forEach(x -> x.visit(pkg, packageReader, userData));
		postProcessors.forEach(x -> x.visit(pkg));
		customOnboarding.handleArtifacts(pkg, packageReader);
	}
}
