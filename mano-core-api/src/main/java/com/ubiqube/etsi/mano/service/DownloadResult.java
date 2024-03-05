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
package com.ubiqube.etsi.mano.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record DownloadResult(byte[] md5, byte[] sha256, byte[] sha512, Long count) {

	@Override
	public int hashCode() {
		return Objects.hash(Arrays.hashCode(md5), Arrays.hashCode(sha256), Arrays.hashCode(sha512), count);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final DownloadResult other = (DownloadResult) obj;
		return Arrays.equals(md5, other.md5) && Arrays.equals(sha256, other.sha256) && Arrays.equals(sha512, other.sha512) && (count == other.count);
	}

	@Override
	public String toString() {
		return "DownloadResult [md5=" + Arrays.toString(md5) + ", sha256=" + Arrays.toString(sha256) + ", sha512=" + Arrays.toString(sha512) + ", count=" + count + "]";
	}

	public String md5String() {
		return toString(md5);
	}

	public String sha256String() {
		return toString(sha256);
	}

	public String sha512String() {
		return toString(sha512);
	}

	private static String toString(final byte[] hash) {
		return IntStream.range(0, hash.length)
				.mapToObj(x -> hash[x])
				.map(b -> String.format("%02X", b))
				.collect(Collectors.joining())
				.toLowerCase();
	}
}
