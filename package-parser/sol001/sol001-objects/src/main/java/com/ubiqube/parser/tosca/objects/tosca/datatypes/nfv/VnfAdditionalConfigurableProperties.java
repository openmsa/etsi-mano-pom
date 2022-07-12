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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * is an empty base type for deriving data types for describing additional
 * configurable properties for a given VNF
 */
public class VnfAdditionalConfigurableProperties extends Root {
	/**
	 * It specifies whether these additional configurable properties are writeable
	 * (TRUE) at any time (i.e. prior to / at instantiation time as well as after
	 * instantiation).or (FALSE) only prior to / at instantiation time. If this
	 * property is not present, the additional configurable properties are writable
	 * anytime.
	 */
	@Valid
	@NotNull
	@JsonProperty("is_writable_anytime")
	private Boolean isWritableAnytime = true;

	@NotNull
	public Boolean getIsWritableAnytime() {
		return this.isWritableAnytime;
	}

	public void setIsWritableAnytime(@NotNull final Boolean isWritableAnytime) {
		this.isWritableAnytime = isWritableAnytime;
	}
}
