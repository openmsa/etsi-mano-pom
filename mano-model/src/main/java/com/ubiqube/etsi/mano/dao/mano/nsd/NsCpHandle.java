package com.ubiqube.etsi.mano.dao.mano.nsd;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class NsCpHandle implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String vnfInstanceId;

	private String vnfExtCpInstanceId;

	private String pnfInfoId;

	private String pnfExtCpInstanceId;

	private String nsInstanceId;

	private String nsSapInstanceId;

}
