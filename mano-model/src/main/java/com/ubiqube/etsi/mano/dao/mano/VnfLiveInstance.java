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

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditListener.class)
@Getter
@Setter
public class VnfLiveInstance implements BaseEntity, Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	private VnfInstance vnfInstance;

	private String instantiationLevel;

	@OneToOne(fetch = FetchType.EAGER)
	private VnfTask task;

	@ManyToOne(fetch = FetchType.LAZY)
	private VnfBlueprint blueprint;

	/**
	 * VIM resourceId.
	 */
	@Column(length = 9_000)
	private String resourceId;

	private String vimConnectionId;

	@Embedded
	private Audit audit;

	public VnfLiveInstance() {
		// Nothing.
	}

	public VnfLiveInstance(final VnfInstance vnfInstance, final String instantiationLevel, final VnfTask task, final VnfBlueprint blueprint, final String resourceId,
			final String vimConnectionId) {
		this.vnfInstance = vnfInstance;
		this.instantiationLevel = instantiationLevel;
		this.blueprint = blueprint;
		this.task = task;
		this.resourceId = resourceId;
		this.vimConnectionId = vimConnectionId;
	}

}
