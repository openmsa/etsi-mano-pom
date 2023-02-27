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

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Attachment;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Abstract.Storage;

public class BlockStorage extends Storage {
	@Valid
	@JsonProperty("volume_id")
	private String volumeId;

	@Valid
	@JsonProperty("snapshot_id")
	private String snapshotId;

	/**
	 * Caps.
	 */
	private Attachment attachment;

	public String getVolumeId() {
		return this.volumeId;
	}

	public void setVolumeId(final String volumeId) {
		this.volumeId = volumeId;
	}

	public String getSnapshotId() {
		return this.snapshotId;
	}

	public void setSnapshotId(final String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public Attachment getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final Attachment attachment) {
		this.attachment = attachment;
	}
}
