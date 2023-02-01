/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.em.v351.model.vnffm;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.em.v351.model.lcmcoord.Link;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links for this resource.
 */
@Schema(description = "Links for this resource. ")
@Validated

public class AlarmLinks {
	@JsonProperty("self")
	private Link self = null;

	@JsonProperty("objectInstance")
	private Link objectInstance = null;

	public AlarmLinks self(final Link self) {
		this.self = self;
		return this;
	}

	/**
	 * Get self
	 *
	 * @return self
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getSelf() {
		return self;
	}

	public void setSelf(final Link self) {
		this.self = self;
	}

	public AlarmLinks objectInstance(final Link objectInstance) {
		this.objectInstance = objectInstance;
		return this;
	}

	/**
	 * Get objectInstance
	 *
	 * @return objectInstance
	 **/
	@Schema(description = "")

	@Valid
	public Link getObjectInstance() {
		return objectInstance;
	}

	public void setObjectInstance(final Link objectInstance) {
		this.objectInstance = objectInstance;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final AlarmLinks alarmLinks = (AlarmLinks) o;
		return Objects.equals(this.self, alarmLinks.self) &&
				Objects.equals(this.objectInstance, alarmLinks.objectInstance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(self, objectInstance);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AlarmLinks {\n");

		sb.append("    self: ").append(toIndentedString(self)).append("\n");
		sb.append("    objectInstance: ").append(toIndentedString(objectInstance)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
