package com.ubiqube.api.rs.endpoints.nfvo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.api.entities.repository.RepositoryElement;

public abstract class AbstractGenericRepository<T> extends AbstractRepository<T> {
	private final ObjectMapper mapper = new ObjectMapper();

	abstract String getUriForId(String _id);

	abstract String setId(T _entity);

	@Override
	public final T get(String _id) {
		final String uri = getUriForId(_id);
		verify(uri);
		final RepositoryElement repositoryElement = repositoryService.getElement(uri);
		final byte[] repositoryContent = repositoryService.getRepositoryElementContent(repositoryElement);
		final String content = new String(repositoryContent);
		try {
			final T t;
			return (T) mapper.readValue(content, getClazz());
		} catch (final Exception e) {
			throw new GenericException(e);
		}
	}

	abstract Class getClazz();

	@Override
	public final void delete(String _id) {
		final String uri = getUriForId(_id);
		verify(_id);
		final RepositoryElement repositoryElement = repositoryService.getElement(uri);
		repositoryService.deleteRepositoryElement(repositoryElement, "ncroot");
	}

	@Override
	public final T save(T _entity) {
		final String saveId = setId(_entity);

		final String uri = getUriForId(saveId);
		try {
			final String str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(_entity);
			repositoryService.addFile(uri, "SOL005", "", str, "ncroot");
		} catch (final Exception e) {
			throw new GenericException(e);
		}

		return _entity;
	}

}
