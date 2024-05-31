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
package com.ubiqube.etsi.mano.service.mon.data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Entity
@Setter
@Getter
public class BatchPollingJob<I extends InterfaceInfo, A extends AccessInfo> {
	/**
	 * Internal ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/***
	 * Resource Id to poll.
	 */
	@Nonnull
	private String resourceId;

	/**
	 * Metrics to poll.
	 */
	@Nonnull
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Metric> metrics;

	/**
	 * Connection ID. Linked to where/how to poll question.
	 */
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, targetEntity = MonConnInformation.class)
	private MonConnInformation<I, A> connection;

	/**
	 * Interval between polling ticks.
	 */
	private long interval;

	/**
	 * Last time the metric have been polled (SUCCESS / FAILURE )
	 */
	private ZonedDateTime lastRun;

	@Override
	public String toString() {
		return "BatchPollingJob [id=" + id + ", resourceId=" + resourceId + ", connectionId=" + connection + ", interval=" + interval + ", lastRun=" + lastRun + "]";
	}

}
