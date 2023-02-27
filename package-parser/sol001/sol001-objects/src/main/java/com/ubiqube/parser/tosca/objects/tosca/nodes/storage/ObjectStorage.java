/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.parser.tosca.objects.tosca.nodes.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.scalar.Size;
import jakarta.validation.Valid;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Endpoint;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Abstract.Storage;

public class ObjectStorage extends Storage {
	@Valid
	@JsonProperty("maxsize")
	private Size maxsize;

	/**
	 * Caps.
	 */
	private Endpoint storageEndpoint;

	public Size getMaxsize() {
		return this.maxsize;
	}

	public void setMaxsize(final Size maxsize) {
		this.maxsize = maxsize;
	}

	public Endpoint getStorageEndpoint() {
		return this.storageEndpoint;
	}

	public void setStorageEndpoint(final Endpoint storageEndpoint) {
		this.storageEndpoint = storageEndpoint;
	}
}
