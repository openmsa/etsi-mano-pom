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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the VIM specific information used for selecting VIM specific
 * capabilities when setting the boot data.
 */
public class BootDataVimSpecificProperties extends Root {
	/**
	 * Discriminator for the different types of the VIM information.
	 */
	@Valid
	@NotNull
	@JsonProperty("vim_type")
	private String vimType;

	/**
	 * Properties used for selecting VIM specific capabilities when setting the boot
	 * data
	 */
	@Valid
	@NotNull
	@JsonProperty("properties")
	private Map<String, String> properties;

	@NotNull
	public String getVimType() {
		return this.vimType;
	}

	public void setVimType(@NotNull final String vimType) {
		this.vimType = vimType;
	}

	@NotNull
	public Map<String, String> getProperties() {
		return this.properties;
	}

	public void setProperties(@NotNull final Map<String, String> properties) {
		this.properties = properties;
	}
}
