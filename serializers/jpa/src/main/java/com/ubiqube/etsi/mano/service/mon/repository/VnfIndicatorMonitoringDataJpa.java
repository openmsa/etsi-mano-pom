package com.ubiqube.etsi.mano.service.mon.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.service.mon.model.VnfIndicatorMonitoringData;

public interface VnfIndicatorMonitoringDataJpa extends CrudRepository<VnfIndicatorMonitoringData, UUID> {

	VnfIndicatorMonitoringData findByKeyAndVnfcId(String key, UUID vnfcId);
}
