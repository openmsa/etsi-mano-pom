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

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Setter
@Getter
@Entity
public class MonitoringData implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private OffsetDateTime time;
	private String masterJobId;
	@Nullable
	private Double value;
	@Nullable
	private String text;
	private String key;
	private boolean status;
	private String resourceId;

	@SuppressWarnings("null")
	public MonitoringData() {
		time = OffsetDateTime.now();
	}

	public MonitoringData(final String key2, final String masterJobId2, final OffsetDateTime timestamp, @Nullable final Double value2, @Nullable final String text, final String resourceId, final boolean status) {
		this.id = UUID.randomUUID();
		this.time = timestamp;
		this.masterJobId = masterJobId2;
		this.value = value2;
		this.text = text;
		this.key = key2;
		this.status = status;
		this.resourceId = resourceId;

	}

}
