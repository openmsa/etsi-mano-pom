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
package com.ubiqube.etsi.mano.dao.mano.vnffg;

import java.util.Set;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.Classifier;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VnffgPostTask extends NsTask {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@OneToOne
	private Classifier classifier;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ListKeyPair> chain;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private VnffgDescriptor vnffg;

	private String srcPort;

	private String dstPort;

	public VnffgPostTask() {
		super(ResourceTypeEnum.VNFFG_POST);
	}

	@Override
	public NsTask copy() {
		final VnffgPostTask task = new VnffgPostTask();
		super.copy(task);
		task.setClassifier(classifier);
		task.setChain(chain);
		task.setVnffg(vnffg);
		task.setDstPort(dstPort);
		return task;
	}
}
