package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ubiqube.etsi.mano.dao.mano.alarm.ResourceHandle;

import lombok.Data;

@Data
@Entity
public class NetAttDefResourceInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String netAttDefResourceInfoId;

	private ResourceHandle netAttDefResource;

	@ElementCollection
	private List<String> associatedExtCpId;

	@ElementCollection
	private List<String> associatedVnfcCpId;

}
