/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
