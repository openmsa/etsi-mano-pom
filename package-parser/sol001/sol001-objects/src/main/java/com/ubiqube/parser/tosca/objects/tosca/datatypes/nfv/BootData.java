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
import javax.validation.Valid;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the information used to customize a virtualised compute resource at boot time.
 */
public class BootData extends Root {
	/**
	 * A set of key-value pairs for configuring a virtual compute resource.
	 */
	@Valid
	@JsonProperty("kvp_data")
	private KvpData kvpData;

	/**
	 * Properties used for selecting VIM specific capabilities when setting the boot data.
	 */
	@Valid
	@JsonProperty("vim_specific_properties")
	private BootDataVimSpecificProperties vimSpecificProperties;

	/**
	 * A string content or a file for configuring a virtual compute resource.
	 */
	@Valid
	@JsonProperty("content_or_file_data")
	private ContentOrFileData contentOrFileData;

	public KvpData getKvpData() {
		return this.kvpData;
	}

	public void setKvpData(final KvpData kvpData) {
		this.kvpData = kvpData;
	}

	public BootDataVimSpecificProperties getVimSpecificProperties() {
		return this.vimSpecificProperties;
	}

	public void setVimSpecificProperties(final BootDataVimSpecificProperties vimSpecificProperties) {
		this.vimSpecificProperties = vimSpecificProperties;
	}

	public ContentOrFileData getContentOrFileData() {
		return this.contentOrFileData;
	}

	public void setContentOrFileData(final ContentOrFileData contentOrFileData) {
		this.contentOrFileData = contentOrFileData;
	}
}
