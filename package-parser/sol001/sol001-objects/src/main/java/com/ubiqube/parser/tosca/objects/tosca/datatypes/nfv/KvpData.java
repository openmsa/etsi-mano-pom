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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import java.util.Map;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes a set of key-value pairs information used to customize a
 * virtualised compute resource at boot time by using only key-value pairs data.
 */
public class KvpData extends Root {
	/**
	 * A map of strings that contains a set of key-value pairs that describes the
	 * information for configuring the virtualised compute resource.
	 */
	@Valid
	@JsonProperty("data")
	private Map<String, String> data;

	public Map<String, String> getData() {
		return this.data;
	}

	public void setData(final Map<String, String> data) {
		this.data = data;
	}
}
