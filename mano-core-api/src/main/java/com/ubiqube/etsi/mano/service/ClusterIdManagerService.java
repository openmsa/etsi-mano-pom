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

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.config.Configurations;
import com.ubiqube.etsi.mano.jpa.config.ConfigurationsJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class ClusterIdManagerService implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(ClusterIdManagerService.class);

	private final ConfigurationsJpa confRepo;

	public ClusterIdManagerService(final ConfigurationsJpa confRepo) {
		this.confRepo = confRepo;
	}

	@Override
	public void run(final String... args) throws Exception {
		//
		final Optional<Configurations> res = confRepo.findById(Constants.CONF_CLUSTER_ID);
		if (res.isPresent()) {
			LOG.info("Server started with cluster id {}", res.get().getWalue());
			return;
		}
		final Configurations conf = new Configurations(Constants.CONF_CLUSTER_ID, UUID.randomUUID().toString());
		final Configurations newConf = confRepo.save(conf);
		LOG.info("Server started with cluster id {}", newConf.getId());
	}

}
