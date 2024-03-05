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
package com.ubiqube.etsi.mano.service.mon;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;
import com.ubiqube.etsi.mano.service.mon.poller.snmp.Snmp3Poller;
import com.ubiqube.etsi.mano.service.mon.poller.snmp.SnmpPoller;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@ExtendWith(MockitoExtension.class)
class SnmpTest {
	@Mock
	private JmsTemplate jmsTemplate;
	@Mock
	private ConfigurableApplicationContext configurableApplicationContext;
	@Mock
	private ConfigurableListableBeanFactory configurableListableBeanFactory;

	@Test
	void testSnmpPoller() {
		final MonConnInformation conn = new MonConnInformation();
		conn.setAccessInfo(Map.of("community", "ubiqube"));
		conn.setInterfaceInfo(Map.of("endpoint", "udp:10.31.1.248/161"));
		conn.setConnType("SNMP");
		final SnmpPoller sp = new SnmpPoller(jmsTemplate, configurableApplicationContext);
		final BatchPollingJob pj = new BatchPollingJob();
		pj.setId(UUID.randomUUID());
		pj.setConnection(conn);
		final List<Metric> metrics = new ArrayList<>();
		metrics.add(new Metric("1.3.6.1.2.1.1.1", null));
		metrics.add(new Metric("1.3.6.1.2.1.2.1", null));
		pj.setMetrics(metrics);
		mockQueueName();
		sp.onEvent(pj);
		assertTrue(true);
	}

	@Test
	void testSnmpV3Poller() {
		final MonConnInformation conn = new MonConnInformation();
		conn.setAccessInfo(Map.of("securityName", "ovi",
				"privacyPassphrase", "	ubiqube123",
				"authenticationProtocol", "md5",
				"authenticationPassphrase", "ubiqube123",
				"privacyProtocol", "des"));
		conn.setInterfaceInfo(Map.of("endpoint", "udp:10.31.1.247/161"));
		conn.setConnType("SNMP3");
		final Snmp3Poller sp = new Snmp3Poller(jmsTemplate, configurableApplicationContext);
		final BatchPollingJob pj = new BatchPollingJob();
		pj.setId(UUID.randomUUID());
		pj.setConnection(conn);
		final List<Metric> metrics = new ArrayList<>();
		metrics.add(new Metric("1.3.6.1.2.1.2.1", null));
		metrics.add(new Metric("1.3.6.1.2.1.2.2.1.10", null));
		metrics.add(new Metric("1.3.6.1.2.1.2.2.1.16", null));
		pj.setMetrics(metrics);
		mockQueueName();
		sp.onEvent(pj);
		assertTrue(true);
	}

	private void mockQueueName() {
		when(configurableApplicationContext.getBeanFactory()).thenReturn(configurableListableBeanFactory);
		when(configurableListableBeanFactory.resolveEmbeddedValue(anyString())).thenReturn("test");
	}

	void snmpV3() throws Exception {
		final ScopedPDU pdu = new ScopedPDU();
		pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.1"))); // ifNumber
		pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.10"))); // ifInOctets
		pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.16"))); // ifOutOctets
		pdu.setType(PDU.GET);

		SecurityProtocols.getInstance().addDefaultProtocols();
		SecurityProtocols.getInstance().addAuthenticationProtocol(new AuthMD5());
		SecurityProtocols.getInstance().addPrivacyProtocol(new PrivDES());
		final Address address = GenericAddress.parse("udp:10.31.1.247/161");
		final UserTarget<Address> target = new UserTarget<>();
		target.setAddress(address);
		target.setRetries(5);
		target.setTimeout(1000);
		target.setVersion(SnmpConstants.version3);
		target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(new OctetString("ovi")));

		final USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
		usm.setEngineDiscoveryEnabled(true);
		final MPv3 mPv3 = new MPv3(usm);
		final UsmUser user = new UsmUser(new OctetString("ovi"), AuthMD5.ID, new OctetString("ubiqube123"), PrivDES.ID, new OctetString("ubiqube123"));
		usm.addUser(user);

		final Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
		// SecurityModels.getInstance().addSecurityModel(usm);
		final SecurityModels securityModels = new SecurityModels();
		securityModels.addSecurityModel(usm);
		mPv3.setSecurityModels(securityModels);
		((MPv3) snmp.getMessageProcessingModel(MPv3.ID)).setSecurityModels(securityModels);
		snmp.getMessageDispatcher().addMessageProcessingModel(mPv3);
		snmp.listen();
		snmp.getUSM().addUser(new OctetString("ovi"),
				new UsmUser(new OctetString("ovi"), AuthMD5.ID,
						new OctetString("ubiqube123"), PrivDES.ID,
						new OctetString("ubiqube123")));
		final ResponseEvent<Address> response = snmp.send(pdu, target);
		if (response.getResponse() == null) {
			// request timed out
			System.out.println("Time out.");
		} else {
			System.out.println("Received response from: " + response.getPeerAddress());
			// dump response PDU
			System.out.println(response.getResponse().toString());
		}
		assertTrue(true);
	}
}
