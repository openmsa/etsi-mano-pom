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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.scalar.Size;
import java.lang.String;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * VirtualFileStorageData describes file storage requirements associated with compute resources in a particular VDU
 */
public class VirtualFileStorageData extends Root {
	/**
	 * Size of virtualized storage resource
	 */
	@Valid
	@NotNull
	@JsonProperty("size_of_storage")
	private Size sizeOfStorage;

	/**
	 * The shared file system protocol (e.g. NFS, CIFS)
	 */
	@Valid
	@NotNull
	@JsonProperty("file_system_protocol")
	private String fileSystemProtocol;

	@NotNull
	public Size getSizeOfStorage() {
		return this.sizeOfStorage;
	}

	public void setSizeOfStorage(@NotNull final Size sizeOfStorage) {
		this.sizeOfStorage = sizeOfStorage;
	}

	@NotNull
	public String getFileSystemProtocol() {
		return this.fileSystemProtocol;
	}

	public void setFileSystemProtocol(@NotNull final String fileSystemProtocol) {
		this.fileSystemProtocol = fileSystemProtocol;
	}
}
