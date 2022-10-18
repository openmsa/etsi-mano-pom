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

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ubiqube.etsi.mano.dao.mano.MonitoringParams;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;

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
public class MonitoringTask extends VnfTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	private MonitoringParams monitoringParams;

	@ManyToOne(fetch = FetchType.EAGER)
	private VnfCompute vnfCompute;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private VnfIndicator vnfIndicator;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private VnfInstance vnfInstance;

	private String parentAlias;

	@Override
	public VnfTask copy() {
		final MonitoringTask t = new MonitoringTask();
		super.copy(t);
		t.setMonitoringParams(monitoringParams);
		t.setVnfCompute(vnfCompute);
		t.setVnfIndicator(vnfIndicator);
		t.setParentAlias(parentAlias);
		t.setVnfInstance(vnfInstance);
		return t;
	}
}
