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
package com.ubiqube.etsi.mano.dao.mano.v2.nfvo;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import com.ubiqube.etsi.mano.dao.mano.Audit;
import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.Auditable;
import com.ubiqube.etsi.mano.dao.mano.NsVlConnectivityType;
import com.ubiqube.etsi.mano.dao.mano.NsVlProfile;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ToscaEntity;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsVlLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsVlStepMapping;

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
@EntityListeners(AuditListener.class)
public class NsVirtualLink implements ToscaEntity, Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String toscaName;
	private String state;
	private String toscaId;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private NsVlProfile nsVlProfile;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private NsVlConnectivityType vlConnectivityType;

	@ManyToOne
	private NsdPackage nsdPackage;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> testAccess;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vnffg;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<NsVlStepMapping> stepMapping;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<NsVlLevelMapping> levelMapping;

	@Embedded
	private Audit audit;

	public void addVnffg(final String name) {
		if (null == vnffg) {
			vnffg = new HashSet<>();
		}
		vnffg.add(name);
	}

	public void addStepMapping(final NsVlStepMapping mapping) {
		if (null == stepMapping) {
			stepMapping = new HashSet<>();
		}
		stepMapping.add(mapping);
	}

	public void addLevelMapping(final NsVlLevelMapping mapping) {
		if (null == levelMapping) {
			levelMapping = new HashSet<>();
		}
		levelMapping.add(mapping);
	}

}
