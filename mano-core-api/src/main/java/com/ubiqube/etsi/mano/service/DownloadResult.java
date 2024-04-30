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

import java.util.Objects;

public record DownloadResult(String md5, String sha256, String sha512, Long count) {

	@Override
	public int hashCode() {
		return Objects.hash(count, md5, sha256, sha512);
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
		return Objects.equals(count, other.count) && Objects.equals(md5, other.md5) && Objects.equals(sha256, other.sha256) && Objects.equals(sha512, other.sha512);
	}

	@Override
	public String toString() {
		return "DownloadResult [md5=" + md5 + ", sha256=" + sha256 + ", sha512=" + sha512 + ", count=" + count + "]";
	}

	public String md5String() {
		return md5;
	}

	public String sha256String() {
		return sha256;
	}

	public String sha512String() {
		return sha512;
	}
}
