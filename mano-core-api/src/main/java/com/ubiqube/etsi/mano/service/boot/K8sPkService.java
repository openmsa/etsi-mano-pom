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
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
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

import jakarta.annotation.Nullable;

/**
 * The role of this service is to create and store a RSA private key for k8s
 * communications. There must be only one PK in the luster, and it should
 * persist after a reboot.
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Component
public class K8sPkService {

	private static final Logger LOG = LoggerFactory.getLogger(K8sPkService.class);

	private static final String K8S_PRIVATE_KEY = "k8s.private-key";
	private static final String K8S_PUBLIC_KEY = "k8s.public-key";

	private final ConfigurationsJpa configurations;

	private final KeyPair keyPair;

	public K8sPkService(final ConfigurationsJpa configurations) {
		this.configurations = configurations;
		keyPair = run();
	}

	public KeyPair run() {
		final KeyPair kp = loadKeys();
		if (null != kp) {
			return kp;
		}
		LOG.info("Creating a Private key for K8s communications.");
		return generateAndSaveKey();
	}

	private @Nullable KeyPair loadKeys() {
		final Optional<Configurations> privDbOpt = configurations.findById(K8S_PRIVATE_KEY);
		if (privDbOpt.isEmpty()) {
			return null;
		}
		final Object o = decodePem(privDbOpt.get().getWalue());
		if (o instanceof final PEMKeyPair pkp) {
			return convert(pkp);
		}
		if (!(o instanceof final PrivateKeyInfo pki)) {
			throw new GenericException("Could not load private key of type :" + o.getClass());
		}
		final Optional<Configurations> pubDbOpt = configurations.findById(K8S_PUBLIC_KEY);
		if (pubDbOpt.isEmpty()) {
			return null;
		}
		final String pubDb = pubDbOpt.get().getWalue();
		final RSAPublicKey pub = decodePem(pubDb);
		final PrivateKey privKey = converter(pki);
		return new KeyPair(pub, privKey);
	}

	private static PrivateKey converter(final PrivateKeyInfo pki) {
		final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		try {
			return converter.getPrivateKey(pki);
		} catch (final PEMException e) {
			throw new GenericException(e);
		}
	}

	private static KeyPair convert(final PEMKeyPair pkp) {
		final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		try {
			return converter.getKeyPair(pkp);
		} catch (final PEMException e) {
			throw new GenericException(e);
		}
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

	private KeyPair generateAndSaveKey() {
		final KeyPair kp = generateKeyPair("RSA", 4096);
		save(K8S_PRIVATE_KEY, kp.getPrivate());
		save(K8S_PUBLIC_KEY, kp.getPublic());
		return kp;
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

	public String getPrivateKey() {
		return pemEncode(keyPair.getPrivate());
	}

	public String createCsr(final String object) {
		final JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
		ContentSigner signer;
		try {
			signer = csBuilder.build(keyPair.getPrivate());
		} catch (final OperatorCreationException e) {
			throw new GenericException(e);
		}
		final X500Name subject = new X500Name(object);
		final PKCS10CertificationRequestBuilder original = new JcaPKCS10CertificationRequestBuilder(subject, keyPair.getPublic());
		final PKCS10CertificationRequestBuilder rq = new PKCS10CertificationRequestBuilder(original);
		final PKCS10CertificationRequest csr = rq.build(signer);
		return pemEncode(csr);
	}
}
