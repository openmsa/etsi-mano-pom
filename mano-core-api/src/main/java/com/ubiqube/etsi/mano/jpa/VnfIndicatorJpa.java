package com.ubiqube.etsi.mano.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;

public interface VnfIndicatorJpa extends CrudRepository<VnfIndicator, UUID>{
	
	VnfIndicator findByName(String name);

}
