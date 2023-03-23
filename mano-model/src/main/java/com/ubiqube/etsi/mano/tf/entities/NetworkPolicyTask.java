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
package com.ubiqube.etsi.mano.tf.entities;

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.Classifier;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;

import jakarta.persistence.Entity;
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
public class NetworkPolicyTask extends NsTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	private Classifier classifier;

	/**
	 * Tosca name.
	 */
	private String serviceInstance;
	/**
	 * Tosca name.
	 */
	private String leftId;

	/**
	 * Tosca name.
	 */
	private String rightId;

	private UUID instanceId;

	public NetworkPolicyTask() {
		super(ResourceTypeEnum.TF_NETWORK_POLICY);
	}

	@Override
	public NsTask copy() {
		final NetworkPolicyTask task = new NetworkPolicyTask();
		super.copy(task);
		task.setClassifier(classifier);
		task.setServiceInstance(serviceInstance);
		task.setLeftId(leftId);
		task.setRightId(rightId);
		task.setInstanceId(instanceId);
		return task;
	}
}
