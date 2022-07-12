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

import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the information needed to scale an NS instance to a target size.
 */
public class ScaleNsToLevelData extends Root {
	/**
	 * Identifier of the target instantiation level of the current deployment
	 * flavour to which the NS is requested to be scaled. Either instantiation_level
	 * or ns_scale_info shall be provided.
	 */
	@Valid
	@JsonProperty("instantiation_level")
	private String instantiationLevel;

	/**
	 * For each scaling aspect of the current deployment flavour, indicates the
	 * target scale level to which the NS is to be scaled. Either
	 * instantiation_level or ns_scale_info shall be provided.
	 */
	@Valid
	@JsonProperty("ns_scale_info")
	private Map<String, Integer> nsScaleInfo;

	public String getInstantiationLevel() {
		return this.instantiationLevel;
	}

	public void setInstantiationLevel(final String instantiationLevel) {
		this.instantiationLevel = instantiationLevel;
	}

	public Map<String, Integer> getNsScaleInfo() {
		return this.nsScaleInfo;
	}

	public void setNsScaleInfo(final Map<String, Integer> nsScaleInfo) {
		this.nsScaleInfo = nsScaleInfo;
	}
}
