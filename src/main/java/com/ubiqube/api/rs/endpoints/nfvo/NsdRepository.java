package com.ubiqube.api.rs.endpoints.nfvo;

import java.util.UUID;

import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfo;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfoIdGetResponse;

public class NsdRepository extends AbstractGenericRepository<NsDescriptorsNsdInfoIdGetResponse> {
	private final static String REPOSITORY_NVFO_NSD_DATAFILE_BASE_PATH = "Datafiles/NFVO/nsd";

	@Override
	String getUriForId(String _id) {
		return REPOSITORY_NVFO_NSD_DATAFILE_BASE_PATH + "/" + _id + "/nsd.json";
	}

	@Override
	String setId(NsDescriptorsNsdInfoIdGetResponse _entity) {
		final NsDescriptorsNsdInfo nsdInfo = _entity.getNsdInfo();
		final String id = nsdInfo.getId();
		if (null == id) {
			nsdInfo.setId(UUID.randomUUID().toString());
		}

		return nsdInfo.getId();
	}

	@Override
	Class getClazz() {
		return NsDescriptorsNsdInfoIdGetResponse.class;
	}

}
