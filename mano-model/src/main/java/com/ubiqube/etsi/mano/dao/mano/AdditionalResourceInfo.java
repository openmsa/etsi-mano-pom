package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class AdditionalResourceInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	private String hostName;

	private String persistentVolume;

	@ElementCollection
	private Map<String, String> additionalInfo;
}
