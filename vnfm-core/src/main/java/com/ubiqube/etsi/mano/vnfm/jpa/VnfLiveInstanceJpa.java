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
package com.ubiqube.etsi.mano.vnfm.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public interface VnfLiveInstanceJpa extends CrudRepository<VnfLiveInstance, UUID> {

	int countByVnfInstanceAndTaskToscaName(VnfInstance vnfInstance, String id);

	List<VnfLiveInstance> findByIdAndVnfInstance(UUID id, VnfInstance vnfInstance);

	VnfLiveInstance findByTaskId(UUID id);

	@Query("select vli, t from VnfLiveInstance vli join VnfTask t on t.id = vli.task.id where vli.vnfInstance = ?1 AND type(t) = 'ComputeTask'")
	List<VnfLiveInstance> findByVnfInstanceAndTaskVnfComputeNotNull(VnfInstance vnfLiveInstance);

	@Query("select vli, t from VnfLiveInstance vli join VnfTask t on t.id = vli.task.id where vli.vnfInstance = ?1 AND t.toscaName = ?2 ORDER BY vli.audit.createdOn DESC")
	List<VnfLiveInstance> findByTaskVnfInstanceAndToscaName(VnfInstance vnfInstance, String alias);

	@Query("select vli, t from VnfLiveInstance vli join VnfTask t on t.id = vli.task.id where vli.vnfInstance = ?1 AND type(t) = ?2")
	List<VnfLiveInstance> findByVnfInstanceIdAndClass(VnfInstance vnfInstance, Class<?> clazz);

	@Query("select count(vli) from VnfLiveInstance vli join VnfTask t on t.id = vli.task.id where vli.vnfInstance = ?1 AND type(t) = ?2 AND t.toscaName = ?3")
	Integer countByVnfInstanceIdAndClassAndToscaName(VnfInstance vnfInstance, Class<?> clazz, String toscaName);

	List<VnfLiveInstance> findByVnfInstanceId(UUID id);

	@NotNull
	List<VnfLiveInstance> findByVnfInstance(VnfInstance vnfInstance);

	long countByVnfInstance(Instance vnfInstance);

	List<VnfLiveInstance> findByResourceIdIn(List<String> ids);
}
