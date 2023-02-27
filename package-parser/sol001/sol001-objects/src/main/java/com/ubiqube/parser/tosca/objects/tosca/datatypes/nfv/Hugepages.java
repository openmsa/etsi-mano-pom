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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.scalar.Size;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Supports the specification of requirements on a particular hugepage size in terms of total memory needs.
 */
public class Hugepages extends Root {
	/**
	 * Specifies the size of the hugepage.
	 */
	@Valid
	@NotNull
	@JsonProperty("hugepage_size")
	private Size hugepageSize;

	/**
	 * Specifies the total size required for all the hugepages of the size indicated by hugepage_size.
	 */
	@Valid
	@NotNull
	@JsonProperty("requested_size")
	private Size requestedSize;

	@NotNull
	public Size getHugepageSize() {
		return this.hugepageSize;
	}

	public void setHugepageSize(@NotNull final Size hugepageSize) {
		this.hugepageSize = hugepageSize;
	}

	@NotNull
	public Size getRequestedSize() {
		return this.requestedSize;
	}

	public void setRequestedSize(@NotNull final Size requestedSize) {
		this.requestedSize = requestedSize;
	}
}
