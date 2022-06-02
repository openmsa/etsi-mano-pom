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
package com.ubiqube.etsi.mano.service.boot;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubiqube.etsi.mano.dao.mano.config.Configurations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.config.ConfigurationsJpa;

/**
 * The role of this service is to create and store a RSA private key for k8s
 * communications. There must be only one PK in the luster, and it should
 * persist after a reboot.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Component
public class K8sPkService {

	private static final Logger LOG = LoggerFactory.getLogger(K8sPkService.class);

	private static final String K8S_PRIVATE_KEY = "k8s.private-key";

	private final ConfigurationsJpa configurations;

	private String privateKey;

	public K8sPkService(final ConfigurationsJpa configurations) {
		super();
		this.configurations = configurations;
		run();
	}

	public void run() {
		final Optional<Configurations> conf = configurations.findById(K8S_PRIVATE_KEY);
		if (conf.isPresent()) {
			LOG.info("K8s private key already exist (skipping)");
			this.privateKey = conf.get().getWalue();
			return;
		}
		LOG.info("Creating a Private key for K8s communications.");
		final KeyPair keyPair = generateKeyPair("RSA", 4096);
		try (final Writer out = new StringWriter();
				final PemWriter pw = new PemWriter(out);) {
			pw.writeObject(new JcaMiscPEMGenerator(keyPair));
			pw.flush();
			final Configurations cf = new Configurations(K8S_PRIVATE_KEY, out.toString());
			configurations.save(cf);
			this.privateKey = cf.getWalue();
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private static KeyPair generateKeyPair(final String alg, final int keySize) {
		try {
			final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(alg);
			keyPairGenerator.initialize(keySize);
			return keyPairGenerator.generateKeyPair();
		} catch (final NoSuchAlgorithmException e) {
			throw new GenericException(e);
		}
	}

	public String getPrivateKey() {
		return privateKey;
	}

}
