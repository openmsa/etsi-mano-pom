package com.ubiqube.etsi.mano.dao.mano.v2;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class VipCpInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String cpInstanceId;

	private String cpdId;

	private String vnfExtCpId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<CpProtocolInfo> cpProtocolInfo;

	@ElementCollection
	private Set<String> associatedVnfcCpIds;

	private String vnfLinkPortId;

	@ElementCollection
	private Map<String, String> metadata;

}
