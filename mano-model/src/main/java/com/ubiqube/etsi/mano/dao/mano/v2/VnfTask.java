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
package com.ubiqube.etsi.mano.dao.mano.v2;

import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
@EntityListeners(AuditListener.class)
public abstract class VnfTask extends AbstractTask {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	private String vimReservationId;

	@Enumerated(EnumType.STRING)
	private ResourceTypeEnum type;

	private String zoneId;

	private String resourceGroupId;

	@ManyToOne(fetch = FetchType.LAZY)
	private VnfBlueprint blueprint;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ScaleInfo scaleInfo;

	private String vimConnectionId;

	public VnfTask copy(final VnfTask t) {
		super.copy(t);
		t.setVimReservationId(vimReservationId);
		t.setType(type);
		t.setZoneId(zoneId);
		t.setResourceGroupId(resourceGroupId);
		t.setBlueprint(blueprint);
		t.setScaleInfo(scaleInfo);
		t.setVimConnectionId(vimConnectionId);
		return t;
	}

	public abstract VnfTask copy();

}
