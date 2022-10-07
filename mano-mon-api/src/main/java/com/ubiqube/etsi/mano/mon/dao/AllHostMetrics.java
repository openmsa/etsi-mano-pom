package com.ubiqube.etsi.mano.mon.dao;

import java.util.List;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.pm.PmType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllHostMetrics {

	private List<TelemetryMetricsResult> telemetryMetricsResult;
	
	private PmType pmType;
	
	private String metricName;
	
	private UUID vnfInstanceId;
	
	private UUID masterJobId;
	
	public AllHostMetrics(List<TelemetryMetricsResult> telemetryMetricsResult, PmType pmType, String metricName, UUID vnfInstanceId, UUID masterJobId) {
		this.telemetryMetricsResult = telemetryMetricsResult;
		this.pmType = pmType;
		this.metricName = metricName;
		this.vnfInstanceId = vnfInstanceId;
		this.masterJobId = masterJobId;
	}
}
