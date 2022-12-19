package com.ubiqube.etsi.mano.dao.mano.v2;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;

import lombok.Data;

@Data
@Entity
public class VnfInfoModifications implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String vnfInstanceName;

	private String vnfInstanceDescription;

	@ElementCollection
	private Map<String, String> vnfConfigurableProperties;

	@ElementCollection
	private Map<String, String> metadata;

	@ElementCollection
	private Map<String, String> extensions;

	@ElementCollection
	@Valid
	private Map<String, VimConnectionInformation> vimConnectionInfo;

	private String vnfdId;

	private String vnfProvider;

	private String vnfProductName;

	private String vnfSoftwareVersion;

	private String vnfdVersion;

}
