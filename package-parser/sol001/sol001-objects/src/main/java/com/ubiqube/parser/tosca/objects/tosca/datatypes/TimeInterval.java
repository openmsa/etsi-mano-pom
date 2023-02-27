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
package com.ubiqube.parser.tosca.objects.tosca.datatypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class TimeInterval extends Root {
	@Valid
	@NotNull
	@JsonProperty("start_time")
	private ZonedDateTime startTime;

	@Valid
	@NotNull
	@JsonProperty("end_time")
	private ZonedDateTime endTime;

	@NotNull
	public ZonedDateTime getStartTime() {
		return this.startTime;
	}

	public void setStartTime(@NotNull final ZonedDateTime startTime) {
		this.startTime = startTime;
	}

	@NotNull
	public ZonedDateTime getEndTime() {
		return this.endTime;
	}

	public void setEndTime(@NotNull final ZonedDateTime endTime) {
		this.endTime = endTime;
	}
}
