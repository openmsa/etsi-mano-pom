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
package com.ubiqube.etsi.mano.service.mon.poller.snmp;

import java.io.IOException;
import java.util.List;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.mon.MonGenericException;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;

import jakarta.annotation.Nonnull;

@Service
public class SnmpPoller extends AbstractSnmpPoller<InterfaceInfo, SnmpV2AuthInfo> {

	public SnmpPoller(@Qualifier("topicJmsTemplate") final JmsTemplate jmsTemplate, final ConfigurableApplicationContext configurableApplicationContext) {
		super(jmsTemplate, configurableApplicationContext);
	}

	@JmsListener(destination = Constants.QUEUE_SNMP_DATA_POLLING, concurrency = "5")
	public void onEvent(@Nonnull final BatchPollingJob<InterfaceInfo, SnmpV2AuthInfo> batchPollingJob) {
		getMetrics(batchPollingJob);
	}

	@Override
	protected PDU getResponse(final BatchPollingJob<InterfaceInfo, SnmpV2AuthInfo> pj) {
		final MonConnInformation<InterfaceInfo, SnmpV2AuthInfo> conn = pj.getConnection();
		final InterfaceInfo ii = conn.getInterfaceInfo();
		final SnmpV2AuthInfo ai = conn.getAccessInfo();
		final String endpoint = ii.getEndpoint();
		final String community = ai.getCommunity();
		final List<Metric> metrics = pj.getMetrics();
		final PDU pdu = createPdu(metrics);
		try (final Snmp snmp = new Snmp(new DefaultUdpTransportMapping())) {
			snmp.listen();

			final Address address = GenericAddress.parse(endpoint);
			final CommunityTarget<Address> target = new CommunityTarget<>();
			target.setCommunity(new OctetString(community));
			target.setAddress(address);
			target.setVersion(SnmpConstants.version1);

			final ResponseEvent<Address> response = snmp.send(pdu, target);
			return response.getResponse();
		} catch (final IOException e) {
			throw new MonGenericException(e);
		}
	}

	private static PDU createPdu(final List<Metric> metrics) {
		final PDU pdu = new PDU();
		final List<VariableBinding> oids = metrics.stream()
				.map(Metric::getMetricName)
				.map(OID::new)
				.map(VariableBinding::new)
				.toList();
		pdu.addAll(oids);
		pdu.setType(PDU.GETNEXT);
		return pdu;
	}
}
