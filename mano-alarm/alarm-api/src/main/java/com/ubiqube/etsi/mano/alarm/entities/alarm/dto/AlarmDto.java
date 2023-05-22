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
package com.ubiqube.etsi.mano.alarm.entities.alarm.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Getter
@Setter
public class AlarmDto {
	@NotNull
	@Size(min = 1)
	private List<Metrics> metrics;

	private List<Transform> transforms;

	private List<Aggregates> aggregates;

	@JsonDeserialize(converter = JsonConverter.class)
	@NotNull
	private String conditions;

	private boolean state;

	private OffsetDateTime lastChange;

	private OffsetDateTime lastRaised;

	@NotNull
	@Valid
	private SubscriptionDto subscription;
}
