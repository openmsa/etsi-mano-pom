package com.ubiqube.etsi.mano.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.api.entities.repository.RepositoryElement;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.repository.RepositoryService;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.nslcm.sol005.NsInstancesNsInstance;

@Service
public class NsInstanceRepository extends AbstractGenericRepository<NsInstancesNsInstance> {
	private static final String REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH = "Datafiles/NFVO/ns_instances";

	private static final Logger LOG = LoggerFactory.getLogger(NsInstanceRepository.class);

	public NsInstanceRepository(ObjectMapper _mapper, RepositoryService _repositoryService) {
		super(_mapper, _repositoryService);
	}

	@Override
	String getUriForId(String _id) {
		return REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH + '/' + _id + "/nsInstance.json";
	}

	@Override
	String setId(NsInstancesNsInstance _entity) {
		final String id = _entity.getId();
		if (null == id) {
			_entity.setId(UUID.randomUUID().toString());
		}

		return _entity.getId();
	}

	@Override
	Class<?> getClazz() {
		return NsInstancesNsInstance.class;
	}

	public List<NsInstancesNsInstance> query() {
		final List<NsInstancesNsInstance> ret = new ArrayList<>();
		List<String> listFilesInFolder;
		try {
			listFilesInFolder = repositoryService.doSearch(REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH, "nsInstance.json");
		} catch (final ServiceException e) {
			throw new GenericException(e);
		}
		for (final String string : listFilesInFolder) {
			LOG.debug("Loading: {}", string);
			final RepositoryElement repositoryElement = repositoryService.getElement(string);
			final byte[] repositoryContent = repositoryService.getRepositoryElementContent(repositoryElement);
			final String content = new String(repositoryContent);
			try {
				final NsInstancesNsInstance vnfinstance = (NsInstancesNsInstance) mapper.readValue(content, getClazz());
				ret.add(vnfinstance);

			} catch (final Exception e) {
				throw new GenericException(e);
			}
		}
		return ret;
	}
}
