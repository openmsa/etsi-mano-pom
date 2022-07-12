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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.InstantiationLevel;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The InstantiationLevels type is a policy type representing all the
 * instantiation levels of resources to be instantiated within a deployment
 * flavour and including default instantiation level in term of the number of
 * VNFC instances to be created as defined in ETSI GS NFV-IFA 011 [1].
 */
public class InstantiationLevels extends Root {
	/**
	 * The default instantiation level for this flavour.
	 */
	@Valid
	@JsonProperty("default_level")
	private String defaultLevel;

	/**
	 * Describes the various levels of resources that can be used to instantiate the
	 * VNF using this flavour.
	 */
	@Valid
	@NotNull
	@JsonProperty("levels")
	@Size(min = 1)
	private Map<String, InstantiationLevel> levels;

	@Valid
	private List<String> targets;

	public String getDefaultLevel() {
		return this.defaultLevel;
	}

	public void setDefaultLevel(final String defaultLevel) {
		this.defaultLevel = defaultLevel;
	}

	@NotNull
	public Map<String, InstantiationLevel> getLevels() {
		return this.levels;
	}

	public void setLevels(@NotNull final Map<String, InstantiationLevel> levels) {
		this.levels = levels;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
