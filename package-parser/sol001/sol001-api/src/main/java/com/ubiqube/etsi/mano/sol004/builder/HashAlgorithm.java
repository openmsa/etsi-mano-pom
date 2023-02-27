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
package com.ubiqube.etsi.mano.sol004.builder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public enum HashAlgorithm {
	MD5("MD5"),
	SHA1("SHA1"),
	SHA_256("SHA-256"),
	SHA_224("SHA-224"),
	SHA_384("SHA-384"),
	SHA_512("SHA-512"),
	SHA3_224("SHA3-224"),
	SHA3_256("SHA3-256"),
	SHA3_384("SHA3-384"),
	SHA3_512("SHA3-512");

	private String value;

	HashAlgorithm(final String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static HashAlgorithm fromValue(final String text) {
		for (final HashAlgorithm b : HashAlgorithm.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
