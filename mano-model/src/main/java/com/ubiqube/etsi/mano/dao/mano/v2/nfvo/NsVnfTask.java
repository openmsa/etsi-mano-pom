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
package com.ubiqube.etsi.mano.dao.mano.v2.nfvo;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Entity
@Getter
@Setter
public class NsVnfTask extends NsTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private NsdVnfPackageCopy nsPackageVnfPackage;

	private String description;

	private String vnfdId;

	@OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Set<ExternalPortRecord> nsExternalNetworks = new LinkedHashSet<>();

	/**
	 * VNFM to use if any.
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Servers server;

	private String flavourId;

	private String instantiationLevelId;

	private String localizationLanguage;

	private String nsdId;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vlInstances;

	public NsVnfTask() {
		super(ResourceTypeEnum.VNF_CREATE);
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public void setId(final UUID id) {
		this.id = id;
	}

	@Override
	public NsTask copy() {
		final NsVnfTask task = new NsVnfTask();
		super.copy(task);
		task.setNsPackageVnfPackage(nsPackageVnfPackage);
		task.setDescription(description);
		task.setVnfdId(vnfdId);
		task.setNsExternalNetworks(nsExternalNetworks);
		task.setServer(server);
		task.setFlavourId(flavourId);
		task.setInstantiationLevelId(instantiationLevelId);
		task.setLocalizationLanguage(localizationLanguage);
		task.setNsdId(nsdId);
		task.setVlInstances(vlInstances);
		return task;
	}

}
