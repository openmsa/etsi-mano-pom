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
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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
public class NsdTask extends NsTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	private UUID nsdId;

	private UUID nsInstanceId;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> virtualLinks;

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Servers server;

	private String flavourId;

	private String instantiationLevelId;

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Set<VnfExtCp> extCps;

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Set<VimConnectionInformation> vimConnectionInformations;

	private String localizationLanguage;

	@OneToOne(fetch = FetchType.LAZY)
	private NsdPackageNsdPackage nsdParam;

	public NsdTask() {
		super(ResourceTypeEnum.NSD_CREATE);
	}

	@Override
	public NsTask copy() {
		final NsdTask task = new NsdTask();
		super.copy(task);
		task.setNsdId(nsdId);
		task.setNsInstanceId(nsInstanceId);
		task.setVirtualLinks(virtualLinks);
		task.setServer(server);
		task.setFlavourId(flavourId);
		task.setInstantiationLevelId(instantiationLevelId);
		task.setExtCps(extCps);
		task.setVimConnectionInformations(vimConnectionInformations);
		task.setLocalizationLanguage(localizationLanguage);
		task.setNsdParam(nsdParam);
		return task;
	}

}
