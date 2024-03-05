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
package com.ubiqube.etsi.mano.nfvo.notification.vnfind;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.UowUtils;
import com.ubiqube.etsi.mano.service.VnfmInterface;

import jakarta.annotation.Nonnull;

@Service
public class VnfLcmInterface {

	private static final Logger LOG = LoggerFactory.getLogger(VnfLcmInterface.class);

	private final VnfmInterface vnfm;

	private final VnfInstanceLcm vnfLcmOpOccsService;

	public VnfLcmInterface(final VnfmInterface vnfm, final VnfInstanceLcm vnfLcmOpOccsService) {
		this.vnfm = vnfm;
		this.vnfLcmOpOccsService = vnfLcmOpOccsService;
	}

	public VnfBlueprint vnfLcmScaleAction(final @Nonnull UUID vnfInstanceId, final Servers server, final Map<String, Object> inputs) {
		final VnfScaleRequest vnfScaleRequest = new VnfScaleRequest();
		for (final Map.Entry<String, Object> c : inputs.entrySet()) {
			final Map<String, String> d = (Map<String, String>) c.getValue();
			final Object value = d.entrySet().iterator().next().getValue();
			switch (c.getKey()) {
			case "type":
				if ("scale_out".equals(value)) {
					vnfScaleRequest.setType(ScaleTypeEnum.OUT);
				}
				if ("scale_in".equals(value)) {
					vnfScaleRequest.setType(ScaleTypeEnum.IN);
				}
				break;
			case "aspect":
				vnfScaleRequest.setAspectId(value.toString());
				break;
			case "number_of_steps":
				vnfScaleRequest.setNumberOfSteps(Integer.parseInt(value.toString()));
				break;
			default:
				throw new GenericException("Unknown operation: " + c.getKey());
			}
		}
		if (vnfScaleRequest.getNumberOfSteps() == null) {
			vnfScaleRequest.setNumberOfSteps(1);
		}
		LOG.info("VNF Scale {} launched", vnfScaleRequest.getType());
		final VnfBlueprint res = vnfm.vnfScale(server, vnfInstanceId, vnfScaleRequest);
		return waitForLcmOpOcc(res, vnfLcmOpOccsService::vnfLcmOpOccsGet, server);
	}

	public VnfBlueprint vnfLcmHealAction(final @Nonnull UUID vnfInstanceId, final Servers server, final Map<String, Object> inputs) {
		final VnfHealRequest vnfHealRequest = new VnfHealRequest();
		for (final Map.Entry<String, Object> c : inputs.entrySet()) {
			final Map<String, String> d = (Map<String, String>) c.getValue();
			final Object value = d.entrySet().iterator().next().getValue();
			vnfHealRequest.setCause(value.toString());
		}
		LOG.info("VNF Heal with cause {} launched", vnfHealRequest.getCause());
		final VnfBlueprint res = vnfm.vnfHeal(server, vnfInstanceId, vnfHealRequest);
		return waitForLcmOpOcc(res, vnfLcmOpOccsService::vnfLcmOpOccsGet, server);
	}

	private static VnfBlueprint waitForLcmOpOcc(final VnfBlueprint vnfLcmOpOccs, final BiFunction<Servers, UUID, VnfBlueprint> func, final Servers server) {
		final VnfBlueprint result = UowUtils.waitLcmCompletion(vnfLcmOpOccs, func, server);
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			final String details = Optional.ofNullable(result.getError()).map(FailureDetails::getDetail).orElse("[No content]");
			throw new GenericException("VNF LCM Failed: " + details + " With state:  " + result.getOperationStatus());
		}
		return result;
	}
}
