/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.service.graph.nfvo;

import java.util.UUID;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

/**
 *
 * @author olivier
 *
 */
public class UowUtils {

	private static final Logger LOG = LoggerFactory.getLogger(UowUtils.class);

	private UowUtils() {
		// Nothing.
	}

	/**
	 * We should add a Max wait ?
	 *
	 * @param vnfLcmOpOccs
	 * @param vnfm
	 * @return
	 */
	public static VnfBlueprint waitLcmCompletion(final VnfBlueprint vnfLcmOpOccs, final BiFunction<Servers, UUID, VnfBlueprint> func, final Servers server) {
		VnfBlueprint tmp = vnfLcmOpOccs;
		OperationStatusType state = OperationStatusType.PROCESSING;
		while ((state == OperationStatusType.PROCESSING) || (OperationStatusType.STARTING == state)) {
			tmp = func.apply(server, vnfLcmOpOccs.getId());
			state = tmp.getOperationStatus();
			LOG.debug("Instantiate polling: {} => {}", tmp.getId(), state);
			sleepSeconds(3);
		}
		LOG.info("VNF Lcm complete with state: {}", state);
		return tmp;
	}

	private static void sleepSeconds(final long seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (final InterruptedException e) {
			LOG.warn("Interrupted exception.", e);
			Thread.currentThread().interrupt();
		}
	}

}
