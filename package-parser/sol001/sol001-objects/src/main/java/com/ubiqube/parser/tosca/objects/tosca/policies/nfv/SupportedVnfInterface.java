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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.InterfaceDetails;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * this policy type represents interfaces produced by a VNF, the details to access them and the applicable connection points to use to access these interfacestosca.nodes.nfv.VnfExtCptosca.nodes.nfv.VduCp
 */
public class SupportedVnfInterface extends Root {
	/**
	 * Identifies an interface produced by the VNF.
	 */
	@Valid
	@NotNull
	@JsonProperty("interface_name")
	private String interfaceName;

	/**
	 * Provide additional data to access the interface endpoint
	 */
	@Valid
	@JsonProperty("details")
	private InterfaceDetails details;

	@Valid
	private List<String> targets;

	@NotNull
	public String getInterfaceName() {
		return this.interfaceName;
	}

	public void setInterfaceName(@NotNull final String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public InterfaceDetails getDetails() {
		return this.details;
	}

	public void setDetails(final InterfaceDetails details) {
		this.details = details;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
