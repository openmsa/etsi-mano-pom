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
package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.pkg.ConnectionPoint;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Entity
public class VnfExtCp extends ConnectionPoint implements Serializable, ToscaEntity {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String toscaId;

	private String toscaName;

	private String state;

	private String externalVirtualLink;

	private String internalVirtualLink;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn
	private Set<VirtualNicReq> virtualNetworkInterfaceRequirements;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> securityGroup;

	private boolean computeNode;

	public void addSecurityGroup(final String toscaName2) {
		if (null == securityGroup) {
			securityGroup = new LinkedHashSet<>();
		}
		securityGroup.add(toscaName2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return (prime * result) + Objects.hash(computeNode, externalVirtualLink, id, internalVirtualLink, securityGroup, state, toscaId, toscaName, virtualNetworkInterfaceRequirements);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		final VnfExtCp other = (VnfExtCp) obj;
		return (computeNode == other.computeNode) && Objects.equals(externalVirtualLink, other.externalVirtualLink) && Objects.equals(id, other.id) && Objects.equals(internalVirtualLink, other.internalVirtualLink) && Objects.equals(securityGroup, other.securityGroup) && Objects.equals(state, other.state) && Objects.equals(toscaId, other.toscaId) && Objects.equals(toscaName, other.toscaName) && Objects.equals(virtualNetworkInterfaceRequirements, other.virtualNetworkInterfaceRequirements);
	}

}
