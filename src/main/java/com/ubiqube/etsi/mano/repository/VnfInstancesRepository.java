package com.ubiqube.etsi.mano.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.api.entities.repository.RepositoryElement;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.repository.RepositoryService;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.nslcm.sol003.VnfInstance;

@Repository
public class VnfInstancesRepository extends AbstractGenericRepository<VnfInstance> {
	private static final Logger LOG = LoggerFactory.getLogger(VnfInstancesRepository.class);
	private static final String REPOSITORY_VNF_INSTANCE_DATAFILE_BASE_PATH = "Datafiles/NFVO/vnf_instances";

	@Inject
	public VnfInstancesRepository(ObjectMapper _mapper, RepositoryService _repositoryService) {
		super(_mapper, _repositoryService);
	}

	@Override
	String getUriForId(String _id) {
		return REPOSITORY_VNF_INSTANCE_DATAFILE_BASE_PATH + "/" + _id + "/vnfInstance.json";
	}

	@Override
	String setId(VnfInstance _entity) {
		final String id = _entity.getId();
		if (null == id) {
			_entity.setId(UUID.randomUUID().toString());
		}

		return _entity.getId();
	}

	@Override
	Class<?> getClazz() {
		return VnfInstance.class;
	}

	public List<VnfInstance> query() {
		final List<VnfInstance> ret = new ArrayList<>();
		List<String> listFilesInFolder;
		try {
			listFilesInFolder = repositoryService.doSearch(REPOSITORY_VNF_INSTANCE_DATAFILE_BASE_PATH, "vnfInstance.json");
		} catch (final ServiceException e) {
			throw new GenericException(e);
		}
		for (final String string : listFilesInFolder) {
			LOG.debug("Loading: {}", string);
			final RepositoryElement repositoryElement = repositoryService.getElement(string);
			final byte[] repositoryContent = repositoryService.getRepositoryElementContent(repositoryElement);
			final String content = new String(repositoryContent);
			try {
				final VnfInstance vnfinstance = (VnfInstance) mapper.readValue(content, getClazz());
				ret.add(vnfinstance);

			} catch (final Exception e) {
				throw new GenericException(e);
			}
		}
		return ret;
	}
}
