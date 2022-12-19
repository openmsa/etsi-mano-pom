package com.ubiqube.etsi.mano.dao.mano.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ubiqube.etsi.mano.dao.mano.alarm.ResourceHandle;

import lombok.Data;

@Data
@Entity
public class VirtualCpInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String cpInstanceId;

	private String cpdId;

	private ResourceHandle resourceHandle;

	private String vnfExtCpId;

	@OneToMany
	private List<CpProtocolInfo> cpProtocolInfo;

	@ElementCollection
	private List<String> vduIds = new ArrayList<>();

	@OneToMany
	private List<AdditionalServiceInfo> additionalServiceInfo;

	@ElementCollection
	private Map<String, String> metadata;

}
