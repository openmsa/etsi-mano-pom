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

import java.util.Set;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class NsVnfInstantiateTask extends NsTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	/**
	 * VNFM to use if any.
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Servers server;

	private String flavourId;

	private String instantiationLevelId;

	private String localizationLanguage;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private NsdVnfPackageCopy param;

	private String vnfInstanceName;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vlInstances;

	public NsVnfInstantiateTask() {
		super(ResourceTypeEnum.VNF_INSTANTIATE);
	}

	@Override
	public NsTask copy() {
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		super.copy(task);
		task.setServer(server);
		task.setFlavourId(flavourId);
		task.setInstantiationLevelId(instantiationLevelId);
		task.setLocalizationLanguage(localizationLanguage);
		task.setParam(param);
		task.setVnfInstanceName(vnfInstanceName);
		task.setVlInstances(vlInstances);
		return task;
	}
}
