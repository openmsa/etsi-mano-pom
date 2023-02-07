package com.ubiqube.etsi.mano.nfvo.notification.vnfind;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.service.VnfmInterface;

import jakarta.annotation.Nonnull;

public class VnfLcmInterface {

	private static final Logger LOG = LoggerFactory.getLogger(VnfLcmInterface.class);

	private final VnfmInterface vnfm;

	public VnfLcmInterface(final VnfmInterface vnfm) {
		this.vnfm = vnfm;
	}

	public VnfBlueprint vnfLcmScaleAction(final @Nonnull String vnfInstanceId, final Servers server, final Map<String, Object> inputs) {
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
		return vnfm.vnfScale(server, vnfInstanceId, vnfScaleRequest);
	}

	public VnfBlueprint vnfLcmHealAction(final @Nonnull String vnfInstanceId, final Servers server, final Map<String, Object> inputs) {
		final VnfHealRequest vnfHealRequest = new VnfHealRequest();
		for (final Map.Entry<String, Object> c : inputs.entrySet()) {
			final Map<String, String> d = (Map<String, String>) c.getValue();
			final Object value = d.entrySet().iterator().next().getValue();
			vnfHealRequest.setCause(value.toString());
		}
		LOG.info("VNF Heal with cause {} launched", vnfHealRequest.getCause());
		return vnfm.vnfHeal(server, vnfInstanceId, vnfHealRequest);
	}

}
