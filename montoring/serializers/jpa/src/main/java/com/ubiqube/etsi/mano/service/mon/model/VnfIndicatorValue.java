package com.ubiqube.etsi.mano.service.mon.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(VnfIndicatorValueId.class)
public class VnfIndicatorValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private UUID id;
	private OffsetDateTime time;
	private UUID masterJobId;
	private Double value;
	@Id
	private String key;
	@Id
	private UUID vnfInstanceId;
	
	public VnfIndicatorValue() {
		// Nothing.
	}
	
	public VnfIndicatorValue(final String key2, final UUID masterJobId2, final Double value2, final UUID vnfInstanceId2) {
		this.id = UUID.randomUUID();
		this.time = OffsetDateTime.now();
		this.masterJobId = masterJobId2;
		this.value = value2;
		this.key = key2;
		this.vnfInstanceId = vnfInstanceId2;
	}
}
