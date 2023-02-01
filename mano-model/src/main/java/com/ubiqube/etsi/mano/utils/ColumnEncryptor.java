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
package com.ubiqube.etsi.mano.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jakarta.persistence.AttributeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class ColumnEncryptor implements AttributeConverter<String, String> {

	private static final String AES = "AES/GCM/NoPadding";
	private static final String SECRET = "YL,Ke.LHrxjt/ub,6KrH(Kq6376ZC%.P";

	private static final int GCM_TAG_LENGTH = 16;
	private static final byte[] GCM_IV = { 53, 25, -105, 34, -59, -79, 4, -75, -13, -54, 61, -126 };

	private final Key key;

	public ColumnEncryptor() {
		Security.addProvider(new BouncyCastleProvider());
		key = new SecretKeySpec(SECRET.getBytes(), AES);
	}

	@Override
	public String convertToDatabaseColumn(final String attribute) {
		try {
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, key, createParamSpec());
			return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
		} catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(final String dbData) {
		try {
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, key, createParamSpec());
			return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new IllegalStateException(e);
		}
	}

	private static GCMParameterSpec createParamSpec() {
		return new GCMParameterSpec(GCM_TAG_LENGTH * 8, GCM_IV);
	}
}
