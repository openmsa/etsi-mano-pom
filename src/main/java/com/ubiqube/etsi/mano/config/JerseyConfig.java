package com.ubiqube.etsi.mano.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.ubiqube.etsi.mano.controller.vnf.sol003.VnfPkgSol003;
import com.ubiqube.etsi.mano.controller.vnf.sol005.VnfPkgSol005;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(VnfPkgSol005.class);
		register(VnfPkgSol003.class);
	}
}
