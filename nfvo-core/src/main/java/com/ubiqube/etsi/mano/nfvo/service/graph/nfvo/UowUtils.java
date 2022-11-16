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

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.InstantiationStatusType;
import com.ubiqube.etsi.mano.dao.mano.NsdChangeType;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
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
	
	
	public static Boolean isVnfLcmRunning(final UUID vnfInstanceId, final BiFunction<Servers, UUID, List<VnfBlueprint>> func, final Servers server) {
		List<VnfBlueprint> li = func.apply(server, vnfInstanceId);
		List<VnfBlueprint> liFilteredByVnf = li.stream().filter(x-> x.getVnfInstance().getId().toString().equals(vnfInstanceId.toString())).collect(Collectors.toList());
		List<VnfBlueprint> liFilteredByState =  liFilteredByVnf.stream().filter(x -> (x.getOperationStatus() == OperationStatusType.STARTING || x.getOperationStatus() == OperationStatusType.PROCESSING ))
				.collect(Collectors.toList());
		if(!liFilteredByState.isEmpty()) {
			LOG.info("VNF Lcm operation running with state: {}, cannot launch scale or heal at this time", InstantiationStatusType.STARTING);
			return true;
		}
		return false;
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
