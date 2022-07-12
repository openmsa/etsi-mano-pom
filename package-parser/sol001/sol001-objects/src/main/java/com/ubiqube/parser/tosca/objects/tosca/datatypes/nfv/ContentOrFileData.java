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

import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes a string content or a file information used to customize a
 * virtualised compute resource at boot time by using string content or file.
 */
public class ContentOrFileData extends Root {
	/**
	 * The URL address when inject a file into the virtualised compute resource. The
	 * content shall comply with IETF RFC 3986 [8].
	 */
	@Valid
	@JsonProperty("destination_path")
	private String destinationPath;

	/**
	 * A map of strings that contains a set of key-value pairs that carries the
	 * dynamic deployment values which used to replace the corresponding variable
	 * parts in the file as identify by a URL as described in source_path. Shall be
	 * present if "source_path" is present and shall be absent otherwise..
	 */
	@Valid
	@JsonProperty("data")
	private Map<String, String> data;

	/**
	 * The URL to a file contained in the VNF package used to customize a
	 * virtualised compute resource. The content shall comply with IETF RFC 3986
	 * [8].
	 */
	@Valid
	@JsonProperty("source_path")
	private String sourcePath;

	/**
	 * The string information used to customize a virtualised compute resource at
	 * boot time.
	 */
	@Valid
	@JsonProperty("content")
	private String content;

	public String getDestinationPath() {
		return this.destinationPath;
	}

	public void setDestinationPath(final String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public Map<String, String> getData() {
		return this.data;
	}

	public void setData(final Map<String, String> data) {
		this.data = data;
	}

	public String getSourcePath() {
		return this.sourcePath;
	}

	public void setSourcePath(final String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}
}
