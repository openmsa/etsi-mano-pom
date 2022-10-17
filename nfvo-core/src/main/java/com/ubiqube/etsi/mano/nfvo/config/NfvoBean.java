package com.ubiqube.etsi.mano.nfvo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ubiqube.etsi.mano.docker.DockerService;
import com.ubiqube.etsi.mano.docker.JibDockerService;

@SuppressWarnings("static-method")
@Configuration
public class NfvoBean {

	@Bean
	public DockerService dockerService() {
		return new JibDockerService();
	}
}
