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
package com.ubiqube.etsi.mano.dao.mano;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedBase;

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
public class VirtualLinkInfo extends VnfInstantiatedBase {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/**
	 * IdentifierInVnfd: Identifier of the VNF Virtual Link Descriptor (VLD) in the
	 * VNFD.
	 */
	private String vnfVirtualLinkDescId;

	/**
	 * Identifier of the VNFD. Shall be present in case the value differs from the
	 * vnfdId attribute of the VnfInstance (e.g. during a "Change current VNF
	 * package" operation or due to its final failure).
	 */
	private String vfndId;
	/**
	 * Link ports of this VL.
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn
	private Set<LinkPortInfo> vnfLinkPorts;

	/**
	 * Reference to the VirtualNetwork resource providing this VL.
	 */
	@Embedded
	private VimResource networkResource;

	@ElementCollection
	private Map<String, String> metadata;

	@Override
	public UUID getId() {
		return id;
	}
}
