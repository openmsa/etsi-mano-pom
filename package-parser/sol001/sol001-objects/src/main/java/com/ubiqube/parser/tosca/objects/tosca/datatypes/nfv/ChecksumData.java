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
import java.lang.String;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Describes information about the result of performing a checksum operation over some arbitrary data
 */
public class ChecksumData extends Root {
	/**
	 * Contains the result of applying the algorithm indicated by the algorithm property to the data to which this ChecksumData refers
	 */
	@Valid
	@NotNull
	@JsonProperty("hash")
	private String hash;

	/**
	 * Describes the algorithm used to obtain the checksum value
	 */
	@Valid
	@NotNull
	@JsonProperty("algorithm")
	private String algorithm;

	@NotNull
	public String getHash() {
		return this.hash;
	}

	public void setHash(@NotNull final String hash) {
		this.hash = hash;
	}

	@NotNull
	public String getAlgorithm() {
		return this.algorithm;
	}

	public void setAlgorithm(@NotNull final String algorithm) {
		this.algorithm = algorithm;
	}
}
