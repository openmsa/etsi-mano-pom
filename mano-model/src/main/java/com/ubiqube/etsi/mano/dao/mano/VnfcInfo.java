package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class VnfcInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	private String vduId;

	private String vnfcResourceInfoId;

	private OperationalStateType vnfcState;

	@ElementCollection
	private Map<String, String> vnfcConfigurableProperties;

}
