package com.ubiqube.api.rs.endpoints.nfvo;

import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.api.ejb.nfvo.vnf.SubscriptionRepository;
import com.ubiqube.api.entities.repository.RepositoryElement;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.repository.RepositoryService;
import com.ubiqube.api.rs.exception.etsi.NotFoundException;

/**
 * A single way to handle subscrption storage.
 *
 * @author ovi@ubiqube.com
 *
 */
public class SubscriptionDao {
	private static final String NVFO_DATAFILE_BASE_PATH = "Datafiles/NFVO";
	private static final String REPOSITORY_SUBSCRIPTION_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/subscriptions";
	private final ObjectMapper mapper = new ObjectMapper();
	private RepositoryService repositoryService;

	public SubscriptionDao() {
		try {
			final InitialContext jndiContext = new InitialContext();
			repositoryService = (RepositoryService) jndiContext.lookup(RepositoryService.RemoteJNDIName);

		} catch (final NamingException e) {
			throw new GenericException(e);
		}
	}

	public SubscriptionRepository getSubscription(String subscriptionId) {
		final String uri = REPOSITORY_SUBSCRIPTION_BASE_PATH + "/" + subscriptionId + ".json";
		verify(uri);
		final RepositoryElement repositoryElement = repositoryService.getElement(uri);
		final byte[] repositoryContent = repositoryService.getRepositoryElementContent(repositoryElement);
		final String content = new String(repositoryContent);
		try {
			return mapper.readValue(content, SubscriptionRepository.class);
		} catch (final Exception e) {
			throw new GenericException(e);
		}
	}

	private void verify(String _uri) {
		try {
			if (!repositoryService.exists(_uri)) {
				throw new NotFoundException("Object not found ");
			}
		} catch (final ServiceException e) {
			throw new GenericException(e);
		}
	}

	public void delete(String _subscriptionId) {
		final String uri = REPOSITORY_SUBSCRIPTION_BASE_PATH + "/" + _subscriptionId + ".json";
		verify(_subscriptionId);
		final RepositoryElement repositoryElement = repositoryService.getElement(uri);
		repositoryService.deleteRepositoryElement(repositoryElement, "ncroot");
		return;
	}

	public SubscriptionRepository save(SubscriptionRepository _subscriptionRepository) {
		String saveId = _subscriptionRepository.getSubscriptionsPkgmSubscription().getId();
		if (null == saveId) {
			saveId = UUID.randomUUID().toString();
			_subscriptionRepository.getSubscriptionsPkgmSubscription().setId(saveId);
		}

		final String uri = REPOSITORY_SUBSCRIPTION_BASE_PATH + "/" + saveId + ".json";
		try {
			final String str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(_subscriptionRepository);
			repositoryService.addFile(uri, "SOL005", "", str, "ncroot");
		} catch (final Exception e) {
			throw new GenericException(e);
		}

		return _subscriptionRepository;
	}
}
