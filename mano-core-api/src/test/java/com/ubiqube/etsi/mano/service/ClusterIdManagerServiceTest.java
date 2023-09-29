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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.config.Configurations;
import com.ubiqube.etsi.mano.jpa.config.ConfigurationsJpa;

@ExtendWith(MockitoExtension.class)
class ClusterIdManagerServiceTest {
	@Mock
	private ConfigurationsJpa confRepo;

	private ClusterIdManagerService createService() {
		return new ClusterIdManagerService(confRepo);
	}

	@Test
	void testAllreadyPresent() throws Exception {
		final ClusterIdManagerService srv = createService();
		final Configurations conf = new Configurations();
		when(confRepo.findById(Constants.CONF_CLUSTER_ID)).thenReturn(Optional.of(conf));
		srv.run(null);
		assertTrue(true);
	}

	@Test
	void testNotPresent() throws Exception {
		final ClusterIdManagerService srv = createService();
		when(confRepo.findById(Constants.CONF_CLUSTER_ID)).thenReturn(Optional.empty());
		final Configurations conf = new Configurations();
		when(confRepo.save(any())).thenReturn(conf);
		srv.run(null);
		assertTrue(true);
	}

}
