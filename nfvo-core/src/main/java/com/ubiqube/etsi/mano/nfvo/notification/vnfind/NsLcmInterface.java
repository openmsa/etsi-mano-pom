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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsByStepsData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleType;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScalingDirectionType;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.controller.nslcm.NsInstanceControllerService;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.UowUtils;

@Service
public class NsLcmInterface {

	private static final Logger LOG = LoggerFactory.getLogger(NsLcmInterface.class);

	private final NsInstanceControllerService nsInstanceControllerService;

	private final NsBlueprintJpa nsBlueprintJpa;

	public NsLcmInterface(final NsInstanceControllerService nsInstanceControllerService, final NsBlueprintJpa nsBlueprintJpa) {
		this.nsInstanceControllerService = nsInstanceControllerService;
		this.nsBlueprintJpa = nsBlueprintJpa;
	}

	public void nsLcmScaleAction(final UUID nsInstanceId, final Map<String, Object> inputs) {
		final NsScale nsInst = createNsCaleRequest(inputs);
		LOG.info("NS Scale {} {} : {} launched", nsInst.getScaleNsData().getScaleNsByStepsData().getScalingDirection(), nsInstanceId, nsInst);
		final NsBlueprint nsBlueprint = nsInstanceControllerService.scale(nsInstanceId, nsInst);
		final NsBlueprint result = UowUtils.waitNSLcmCompletion(nsBlueprint, nsBlueprintJpa);
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			final String details = Optional.ofNullable(result.getError()).map(FailureDetails::getDetail).orElse("[No content]");
			throw new GenericException("NS LCM Failed: " + details + " With state:  " + result.getOperationStatus());
		}
	}

	private static NsScale createNsCaleRequest(final Map<String, Object> inputs) {
		final NsScale nsInst = new NsScale();
		nsInst.setScaleType(ScaleType.NS);
		final ScaleNsByStepsData snbsd = mapScaleNsByStepData(inputs);
		final ScaleNsData scaleNsData = new ScaleNsData();
		scaleNsData.setScaleNsByStepsData(snbsd);
		nsInst.setScaleNsData(scaleNsData);
		return nsInst;
	}

	private static ScaleNsByStepsData mapScaleNsByStepData(final Map<String, Object> inputs) {
		final ScaleNsByStepsData snbsd = new ScaleNsByStepsData();
		final Map<String, String> d = (Map<String, String>) inputs.get("scale_ns_by_steps_data");
		for (final Map.Entry<String, String> c : d.entrySet()) {
			final Object value = c.getValue();
			switch (c.getKey()) {
			case "scaling_direction":
				if ("scale_out".equals(value)) {
					snbsd.setScalingDirection(ScalingDirectionType.OUT);
				}
				if ("scale_in".equals(value)) {
					snbsd.setScalingDirection(ScalingDirectionType.IN);
				}
				break;
			case "aspect":
				snbsd.setAspectId(value.toString());
				break;
			case "number_of_steps":
				snbsd.setNumberOfSteps(Integer.parseInt(value.toString()));
				break;
			default:
				break;
			}
		}
		if (snbsd.getNumberOfSteps() == null) {
			snbsd.setNumberOfSteps(1);
		}
		return snbsd;
	}

}
