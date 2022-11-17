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

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;

import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditListener.class)
@Getter
@Setter
public class VnfIndicatorTask extends VnfTask {

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	private VnfIndicator vnfIndicator;

	@Override
	public VnfTask copy() {
		final VnfIndicatorTask t = new VnfIndicatorTask();
		super.copy(t);
		t.setName(name);
		t.setVnfIndicator(vnfIndicator);
		return t;
	}

}
