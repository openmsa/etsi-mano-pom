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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.PrivAES128;
import org.snmp4j.security.PrivAES192;
import org.snmp4j.security.PrivAES256;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.MonGenericException;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@Service
public class Snmp3Poller extends AbstractSnmpPoller {
	private static final String SECURITY_NAME = "securityName";
	private static final Logger LOG = LoggerFactory.getLogger(Snmp3Poller.class);

	public Snmp3Poller(@Qualifier("topicJmsTemplate") final JmsTemplate jmsTemplate, final ConfigurableApplicationContext configurableApplicationContext) {
		super(jmsTemplate, configurableApplicationContext);
	}

	@JmsListener(destination = Constants.QUEUE_SNMP3_DATA_POLLING, concurrency = "5")
	public void onEvent(@Nonnull final BatchPollingJob batchPollingJob) {
		getMetrics(batchPollingJob);
	}

	@Override
	protected PDU getResponse(final BatchPollingJob pj) {
		final MonConnInformation conn = pj.getConnection();
		final ScopedPDU pdu = createScopePdu(pj.getMetrics());
		setupSecurity();
		final UserTarget<Address> target = createUserTarget(conn);
		final MPv3 mPv3 = createMpv3(conn);
		final String securityName = conn.getAccessInfo().getSecurityName();
		try (final Snmp snmp = new Snmp(new DefaultUdpTransportMapping())) {
			((MPv3) snmp.getMessageProcessingModel(MPv3.ID)).setSecurityModels(mPv3.getSecurityModels());
			snmp.getMessageDispatcher().addMessageProcessingModel(mPv3);
			snmp.listen();
			snmp.getUSM().addUser(new OctetString(securityName),
					createUsmUser(conn));
			final ResponseEvent<Address> response = snmp.send(pdu, target);
			return response.getResponse();
		} catch (final IOException e) {
			throw new MonGenericException(e);
		}
	}

	private static MPv3 createMpv3(final MonConnInformation conn) {
		final USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
		usm.setEngineDiscoveryEnabled(true);
		final MPv3 mpv3 = new MPv3(usm);
		usm.addUser(createUsmUser(conn));
		final SecurityModels securityModels = new SecurityModels();
		securityModels.addSecurityModel(usm);
		mpv3.setSecurityModels(securityModels);
		return mpv3;
	}

	private static UsmUser createUsmUser(final MonConnInformation conn) {
		final String securityName = conn.getAccessInfo().getSecurityName();
		final String privacyPassphrase = conn.getAccessInfo().getPrivacyPassphrase();
		final OID authenticationProtocol = toOid(conn.getAccessInfo().getAuthenticationProtocol());
		final String authenticationPassphrase = conn.getAccessInfo().getAuthenticationPassphrase();
		final OID privacyProtocol = toOid(conn.getAccessInfo().getPrivacyProtocol());
		return new UsmUser(new OctetString(securityName),
				authenticationProtocol, new OctetString(authenticationPassphrase),
				privacyProtocol, new OctetString(privacyPassphrase));
	}

	private static OID toOid(final @Nullable String oid) {
		if (null == oid) {
			return null;
		}
		final String key = oid.toLowerCase();
		return switch (key) {
		case "md5" -> AuthMD5.ID;
		case "sha" -> AuthSHA.ID;
		case "des" -> PrivDES.ID;
		case "3des" -> Priv3DES.ID;
		case "aes128" -> PrivAES128.ID;
		case "aes192" -> PrivAES192.ID;
		case "aes256" -> PrivAES256.ID;
		default -> {
			LOG.warn("Unknown protocol {}, falling back to unencrypted", key);
			yield null;
		}
		};
	}

	private static UserTarget<Address> createUserTarget(final MonConnInformation conn) {
		final String endpoint = conn.getInterfaceInfo().getEndpoint();
		final String user = conn.getAccessInfo().getSecurityName();
		final Address address = GenericAddress.parse(endpoint);
		final UserTarget<Address> target = new UserTarget<>();
		target.setAddress(address);
		target.setRetries(5);
		target.setTimeout(1000);
		target.setVersion(SnmpConstants.version3);
		target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(new OctetString(user)));
		return target;
	}

	private static void setupSecurity() {
		SecurityProtocols.getInstance().addDefaultProtocols();
		SecurityProtocols.getInstance().addAuthenticationProtocol(new AuthMD5());
		SecurityProtocols.getInstance().addPrivacyProtocol(new PrivDES());
	}

	private static ScopedPDU createScopePdu(final List<Metric> metrics) {
		final ScopedPDU pdu = new ScopedPDU();
		final List<VariableBinding> mets = metrics.stream()
				.map(Metric::getMetricName)
				.map(OID::new)
				.map(VariableBinding::new)
				.toList();
		pdu.addAll(mets);
		pdu.setType(PDU.GET);
		return pdu;
	}
}
