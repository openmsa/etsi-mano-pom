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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
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
	private static final String K8S_PUBLIC_KEY = "k8s.public-key";

	private final ConfigurationsJpa configurations;

	private KeyPair keyPair;

	public K8sPkService(final ConfigurationsJpa configurations) {
		super();
		this.configurations = configurations;
		run();
	}

	public void run() {
		if (loadKeys()) {
			return;
		}
		LOG.info("Creating a Private key for K8s communications.");
		generateAndSaveKey();
	}

	private boolean loadKeys() {
		final Optional<Configurations> privDbOpt = configurations.findById(K8S_PRIVATE_KEY);
		final Optional<Configurations> pubDbOpt = configurations.findById(K8S_PUBLIC_KEY);
		if (privDbOpt.isEmpty() || pubDbOpt.isEmpty()) {
			return false;
		}
		final String privDb = privDbOpt.get().getWalue();
		final String pubDb = pubDbOpt.get().getWalue();
		final RSAPrivateKey priv = decodePem(privDb);
		final RSAPublicKey pub = decodePem(pubDb);
		keyPair = new KeyPair(pub, priv);
		return true;
	}

	@SuppressWarnings("unchecked")
	private static <T> T decodePem(final String pem) {
		try (Reader reader = new StringReader(pem);
				PEMParser pemParser = new PEMParser(reader)) {
			return (T) pemParser.readObject();
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private void generateAndSaveKey() {
		keyPair = generateKeyPair("RSA", 4096);
		save(K8S_PRIVATE_KEY, keyPair.getPrivate());
		save(K8S_PUBLIC_KEY, keyPair.getPublic());
	}

	private Configurations save(final String k8sKey, final Key key) {
		final String pemKey = pemEncode(key);
		final Configurations cf = new Configurations(k8sKey, pemKey);
		return configurations.save(cf);
	}

	private static String pemEncode(final Object key) {
		try (final Writer out = new StringWriter();
				final PemWriter pw = new PemWriter(out);) {
			pw.writeObject(new JcaMiscPEMGenerator(key));
			pw.flush();
			return out.toString();
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

	public String createCsr(final String object) {
		final X500Name subject = new X500Name(object);
		final PKCS10CertificationRequestBuilder original = new JcaPKCS10CertificationRequestBuilder(subject, keyPair.getPublic());
		final PKCS10CertificationRequestBuilder rq = new PKCS10CertificationRequestBuilder(original);
		final JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
		ContentSigner signer;
		try {
			signer = csBuilder.build(keyPair.getPrivate());
		} catch (final OperatorCreationException e) {
			throw new GenericException(e);
		}
		final PKCS10CertificationRequest csr = rq.build(signer);
		return pemEncode(csr);
	}
}
