package com.ubiqube.etsi.mano.repository;

/**
 *
 * @author olivier
 *
 */
public abstract class AbstractBaseResource implements ManoResource {
	private long size;
	private String fileName;

	public AbstractBaseResource(final long size, final String fileName) {
		this.size = size;
		this.fileName = fileName;
	}

	@Override
	public final long getSize() {
		return size;
	}

	public final void setSize(final long size) {
		this.size = size;
	}

	@Override
	public final String getFileName() {
		return fileName;
	}

	public final void setFileName(final String fileName) {
		this.fileName = fileName;
	}

}
