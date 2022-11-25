package com.ubiqube.etsi.mano.nfvo.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;

@Service
@Transactional
public class VnfmRegisterService implements CommandLineRunner {

	private final EventManager eventManager;
	private final ServersJpa serversJpa;

	public VnfmRegisterService(final EventManager eventManager, final ServersJpa serversJpa) {
		this.eventManager = eventManager;
		this.serversJpa = serversJpa;
	}

	@Override
	public void run(final String... args) throws Exception {
		final List<Servers> servers = serversJpa.findByServerStatusIn(Arrays.asList(PlanStatusType.FAILED));
		servers.forEach(x -> eventManager.sendAction(ActionType.REGISTER_SERVER, x.getId()));
	}

}
