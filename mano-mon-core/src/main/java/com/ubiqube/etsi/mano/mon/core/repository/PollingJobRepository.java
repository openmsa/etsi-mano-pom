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
package com.ubiqube.etsi.mano.mon.core.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;

/**
 *
 * @author olivier
 *
 */
public interface PollingJobRepository extends CrudRepository<BatchPollingJob, UUID> {

	@Query(value = """
			select *
			from batch_polling_job bpj
			where
				last_run + interval '1 second' * interval <= ?1
				or
				last_run is NULL
			""", nativeQuery = true)
	List<BatchPollingJob> findRunnableJobs(ZonedDateTime now);
}
