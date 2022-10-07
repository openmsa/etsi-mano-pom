package com.ubiqube.etsi.mano.service.mon.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VnfIndicatorValueId implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	private UUID vnfInstanceId;
	private String key;

	@Override
	public int hashCode() {
		return Objects.hash(key, vnfInstanceId);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VnfIndicatorValueId other = (VnfIndicatorValueId) obj;
		return Objects.equals(key, other.key) && Objects.equals(vnfInstanceId, other.vnfInstanceId);
	}

}
