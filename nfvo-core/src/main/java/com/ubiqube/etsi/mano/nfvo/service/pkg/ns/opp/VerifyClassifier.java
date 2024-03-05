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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.opp;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingPostProcessor;

/**
 *
 * @author olivier
 *
 */
@Service
public class VerifyClassifier implements NsOnboardingPostProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(VerifyClassifier.class);

	@Override
	public void visit(final NsdPackage nsPackage) {
		final Set<VnffgDescriptor> vnffgd = Optional.ofNullable(nsPackage.getVnffgs()).orElse(Set.of());
		vnffgd.stream().map(VnffgDescriptor::getClassifier).forEach(VerifyClassifier::checkClassifier);
	}

	private static void checkClassifier(final Classifier cla) {
		// IP protocol must be TCP or UDP, if port range is given.
		if ((cla.getDestinationPortRangeMax() != null) || (cla.getDestinationPortRangeMin() != null) || (cla.getSourcePortRangeMax() != null) || (cla.getSourcePortRangeMin() != null)) {
			if (cla.getProtocol() == null) {
				throw new GenericException("IP protocol must be TCP or UDP, if port range is given.");
			}
			LOG.debug("classifier with port range accepted.");
		}
	}
}
