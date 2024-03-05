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
package com.ubiqube.etsi.mano.service.mon.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public interface MonitoringDataJpa extends CrudRepository<MonitoringData, UUID> {

	@Query(value = """
			select time , master_job_id as masterJobId, "key",value ,"text", resource_id as resourceId  from monitoring_data
				where key = :key and resource_id = :masterJobId
				group by time , master_job_id , "key", value ,"text", resource_id
				order by time desc limit 2;
			""", nativeQuery = true)
	List<MonitoringDataProjection> getLastMetrics(@Param("masterJobId") String masterJobId, @Param("key") String key);
}
