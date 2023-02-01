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

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.ubiqube.etsi.mano.utils.ToStringIgnore;
import com.ubiqube.etsi.mano.utils.ToStringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Setter
@Getter
@Entity
public class ExtManagedVirtualLinkDataEntity implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String vnfVirtualLinkDescId;

	// 3.3.1
	private String vnfdId;

	private String vimConnectionId;

	private String resourceProviderId;

	private String resourceId;

	private String vimLevelResourceType;

	/**
	 * @since 4.3.1
	 */
	@OneToOne
	private AdditionalResourceInfo vimLevelAdditionalResourceInfo;

	/**
	 * @since 4.3.1
	 */
	private String containerNamespace;

	/**
	 * @Sonce 4.3.1
	 */
	@OneToMany
	private List<NetAttDefResourceInfo> vnfNetAttDefResource;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<LinkPortInfo> vnfLinkPorts;

	// 3.3.1
	private String extManagedMultisiteVirtualLinkId;

	@ToStringIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private GrantResponse grants;

	@ToStringIgnore
	@ManyToOne
	private VnfInstance vnfInstance;

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}
}
