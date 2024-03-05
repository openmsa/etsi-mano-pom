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
package com.ubiqube.etsi.mano.service.mon.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VnfIndicatorMonitoringDataId implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	private UUID vnfcId;
	private String key;

	@Override
	public int hashCode() {
		return Objects.hash(key, vnfcId);
	}

	@Override
	public boolean equals(final @Nullable Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final VnfIndicatorMonitoringDataId other = (VnfIndicatorMonitoringDataId) obj;
		return Objects.equals(key, other.key) && Objects.equals(vnfcId, other.vnfcId);
	}

}
