/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.v2.AbstractTask;

public class TestTask extends AbstractTask {

	private final ResourceTypeEnum Rtype;

	public TestTask(final ResourceTypeEnum type) {
		this.Rtype = type;
	}

	@Override
	public void setVimReservationId(final String reservationId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setResourceGroupId(final String resourceGroupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setZoneId(final String zoneId) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResourceTypeEnum getType() {
		return Rtype;
	}

	@Override
	public ScaleInfo getScaleInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
