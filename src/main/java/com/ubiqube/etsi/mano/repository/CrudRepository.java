package com.ubiqube.etsi.mano.repository;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CrudRepository<T> {

	@Nonnull
	T get(@Nonnull String id);

	void delete(@Nonnull String id);

	@Nonnull
	T save(@Nonnull T entity);

	@Nonnull
	List<T> query(@Nullable String filter);

	void storeObject(String _id, Object _object, String _filename);

	void storeBinary(String _id, InputStream _stream, String _filename);

	byte[] getBinary(String _id, String _filename);

	byte[] getBinary(String _id, String _filename, int min, Integer max);
}
