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

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

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
public class SubNetworkTask extends VnfTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Embedded
	private L3Data l3Data;

	private IpPool ipPool;

	private String parentName;

	public SubNetworkTask() {
		// Nothing.
	}

	public SubNetworkTask(final L3Data l3Data, final IpPool ipPool, final String parentName) {
		this.l3Data = l3Data;
		this.ipPool = ipPool;
		setToscaName("sub-" + parentName);
		this.parentName = parentName;
	}

	@Override
	public VnfTask copy() {
		final SubNetworkTask t = new SubNetworkTask();
		super.copy(t);
		t.setL3Data(l3Data);
		t.setIpPool(ipPool);
		t.setParentName(parentName);
		return t;
	}

}
