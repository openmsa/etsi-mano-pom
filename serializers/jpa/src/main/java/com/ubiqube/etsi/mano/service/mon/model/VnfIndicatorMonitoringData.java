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
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(VnfIndicatorMonitoringDataId.class)
public class VnfIndicatorMonitoringData implements Serializable {
	private static final long serialVersionUID = 1L;
	private UUID id;
	private OffsetDateTime time;
	private UUID masterJobId;
	private Double value;
	@Id
	private String key;
	@Id
	private UUID vnfcId;

	public VnfIndicatorMonitoringData() {
		// Nothing.
	}

	public VnfIndicatorMonitoringData(final String key2, final UUID masterJobId2, final Double value2, final UUID vnfcId) {
		this.id = UUID.randomUUID();
		this.time = OffsetDateTime.now();
		this.masterJobId = masterJobId2;
		this.value = value2;
		this.key = key2;
		this.vnfcId = vnfcId;
	}
}
