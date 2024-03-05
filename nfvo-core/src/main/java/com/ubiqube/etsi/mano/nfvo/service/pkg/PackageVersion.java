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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.pkg;

import jakarta.annotation.Nullable;

public class PackageVersion {
	@Nullable
	private String flavorId;
	@Nullable
	private String name;
	@Nullable
	private String version;

	public PackageVersion(final String versionIn) {
		final String[] parts = versionIn.split("/");
		if (parts.length == 3) {
			flavorId = parts[0];
			name = parts[1];
			version = parts[2];
		} else if (parts.length == 2) {
			name = parts[0];
			version = parts[1];
		} else if (parts.length == 1) {
			name = parts[0];
		}
	}

	public @Nullable String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(final String flavorId) {
		this.flavorId = flavorId;
	}

	public @Nullable String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public @Nullable String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public int countPart() {
		int cnt = 0;
		if (name != null) {
			cnt++;
		}
		if (flavorId != null) {
			cnt++;
		}
		if (version != null) {
			cnt++;
		}
		return cnt;
	}

	@Override
	public @Nullable String toString() {
		if (countPart() == 1) {
			return name;
		}
		if (countPart() == 2) {
			return name + "/" + version;
		}
		if (countPart() == 3) {
			return flavorId + "/" + name + "/" + version;
		}
		return "error";
	}
}
