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
package com.ubiqube.etsi.mano.service.mon.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public interface MonitoringDataJpa extends CrudRepository<MonitoringData, OffsetDateTime> {

	@Query(value = """
			select time , master_job_id , "key",value ,"text"  from monitoring_data
				where key = :key and master_job_id = :masterJobId
				group by time , master_job_id , "key", value ,"text"
				order by time desc limit 2;
			""", nativeQuery = true)
	List<MonitoringDataProjection> getLastMetrics(@Param("key") String key, @Param("masterJobId") String masterJobId);
}
