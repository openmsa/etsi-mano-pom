package com.ubiqube.etsi.mano.service.mon.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.service.mon.model.VnfIndicatorValue;

public interface VnfIndicatorValueJpa extends CrudRepository<VnfIndicatorValue, UUID> {
	
	VnfIndicatorValue findByKeyAndVnfInstanceId(String key, UUID vnfInstanceId);
	// Nothing.
}