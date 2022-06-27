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
			LOG.info("Server started with cluster id {}", res.get());
			return;
		}
		final Configurations conf = new Configurations(Constants.CONF_CLUSTER_ID, UUID.randomUUID().toString());
		final Configurations newConf = confRepo.save(conf);
		LOG.info("Server started with cluster id {}", newConf.getId());
	}

}
