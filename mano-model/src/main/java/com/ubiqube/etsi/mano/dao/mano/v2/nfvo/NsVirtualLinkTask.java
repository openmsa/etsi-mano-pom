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

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;

import jakarta.persistence.CascadeType;
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
public class NsVirtualLinkTask extends NsTask {

	public NsVirtualLinkTask() {
		super(ResourceTypeEnum.VL);
	}

	/** Serial. */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private NsVirtualLink nsVirtualLink;

	@Override
	public NsTask copy() {
		final NsVirtualLinkTask task = new NsVirtualLinkTask();
		super.copy(task);
		task.setNsVirtualLink(nsVirtualLink);
		return task;
	}

}
