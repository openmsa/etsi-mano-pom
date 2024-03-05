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
package com.ubiqube.etsi.mano.service.mon.dto;

import java.util.List;

import com.ubiqube.etsi.mano.service.mon.data.Metric;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PollingJob {
	public PollingJob() {
	}

	/***
	 * Resource Id to poll.
	 */
	@NotNull
	private String resourceId;

	/**
	 * Metrics to poll.
	 */
	@NotNull
	@Size(min = 1)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Metric> metrics;

	/**
	 * Interval in seconds between polling ticks.
	 */
	@Min(1)
	private long interval;

	@NotNull
	@Nonnull
	private ConnInfo connection;
}
