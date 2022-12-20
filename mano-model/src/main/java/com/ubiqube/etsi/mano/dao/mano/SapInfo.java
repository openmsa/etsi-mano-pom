package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ubiqube.etsi.mano.dao.mano.v2.CpProtocolInfo;

import lombok.Data;

@Data
@Entity
public class SapInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String sapdId;

	private String sapName;

	private String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<CpProtocolInfo> sapProtocolInfo = new ArrayList<>();

}
