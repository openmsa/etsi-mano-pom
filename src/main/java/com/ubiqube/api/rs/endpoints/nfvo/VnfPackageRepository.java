package com.ubiqube.api.rs.endpoints.nfvo;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPkgInfo;
import com.ubiqube.api.interfaces.repository.RepositoryService;

public class VnfPackageRepository extends AbstractGenericRepository<VnfPkgInfo> {
	private final ObjectMapper mapper = new ObjectMapper();
	private RepositoryService repositoryService;

	private final static String REPOSITORY_NVFO_DATAFILE_BASE_PATH = "Datafiles/NFVO/vnf_packages";

	@Override
	String getUriForId(String _id) {
		return REPOSITORY_NVFO_DATAFILE_BASE_PATH + "/" + _id + "/vnfd.json";
	}

	@Override
	String setId(VnfPkgInfo _entity) {
		final String id = _entity.getId();
		if (null == id) {
			_entity.setId(UUID.randomUUID().toString());
		}

		return _entity.getId();
	}

	@Override
	Class getClazz() {
		return VnfPkgInfo.class;
	}

}
