package com.ubiqube.etsi.mano.dao.mano.v2;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.ubiqube.etsi.mano.dao.mano.CpProtocolInfoEntity.LayerProtocolEnum;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressDataEntity;

import lombok.Data;

@Data
@Entity
public class CpProtocolInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private LayerProtocolEnum layerProtocol;

	@OneToOne
	private IpOverEthernetAddressDataEntity ipOverEthernet;
}
