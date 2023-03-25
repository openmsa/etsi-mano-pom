/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.auth.cert.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcContentSignerBuilder;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

class UserDetailSslTest {

	@Test
	void test() throws InvalidKeyException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, OperatorCreationException, IOException {
		final UserDetailSsl ssl = new UserDetailSsl();
		Security.addProvider(new BouncyCastleProvider());
		final X509Certificate credentials = generateCert("CN=test");
		final PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(null, credentials);
		ssl.loadUserDetails(token);
		assertTrue(true);
	}

	private static X509Certificate generateCert(final String issuer) throws IOException, CertificateException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, OperatorCreationException {
		final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		final KeyPair keyPair = keyPairGen.generateKeyPair();
		final X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(new X500Name(issuer),
				BigInteger.ONE, new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 30)),
				new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30)), new X500Name(issuer),
				SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded()));

		final GeneralNames subjectAltNames = new GeneralNames(new GeneralName(GeneralName.iPAddress, "127.0.0.1"));
		certificateBuilder.addExtension(org.bouncycastle.asn1.x509.Extension.subjectAlternativeName, false,
				subjectAltNames);

		final AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder()
				.find("sha256WithRSAEncryption");
		final AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);
		final BcContentSignerBuilder signerBuilder = new BcRSAContentSignerBuilder(sigAlgId, digAlgId);
		final AsymmetricKeyParameter keyp = PrivateKeyFactory.createKey(keyPair.getPrivate().getEncoded());
		final ContentSigner signer = signerBuilder.build(keyp);
		final X509CertificateHolder x509CertificateHolder = certificateBuilder.build(signer);

		final X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(x509CertificateHolder);
		certificate.checkValidity(new Date());
		certificate.verify(keyPair.getPublic());
		return certificate;
	}

}
