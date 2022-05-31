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

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class ColumnEncryptor implements AttributeConverter<String, String> {

	private static final String AES = "AES";
	private static final String SECRET = "YL,Ke.LHrxjt/ub,6KrH(Kq6376ZC%.P";

	private final Key key;
	private final Cipher cipher;

	public ColumnEncryptor() {
		key = new SecretKeySpec(SECRET.getBytes(), AES);
		try {
			cipher = Cipher.getInstance(AES);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public String convertToDatabaseColumn(final String attribute) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(final String dbData) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
			throw new IllegalStateException(e);
		}
	}

}
