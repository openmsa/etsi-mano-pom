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
package com.ubiqube.parser.tosca.objects.tosca.artifacts.nfv;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.artifacts.deployment.Image;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.ChecksumData;
import com.ubiqube.parser.tosca.scalar.Size;

/**
 * describes the software image which is directly loaded on the virtualisation
 * container realizing of the VDU or is to be loaded on a virtual storage
 * resource
 */
public class SwImage extends Image {
	/**
	 * The size of this software image
	 */
	@Valid
	@NotNull
	@JsonProperty("size")
	private Size size;

	/**
	 * Provider of this software image
	 */
	@Valid
	@JsonProperty("provider")
	private String provider;

	/**
	 * The minimal disk size requirement for this software image
	 */
	@Valid
	@JsonProperty("min_disk")
	private Size minDisk;

	/**
	 * Name of this software image
	 */
	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	/**
	 * Checksum of the software image file
	 */
	@Valid
	@NotNull
	@JsonProperty("checksum")
	private ChecksumData checksum;

	/**
	 * The disk format of a software image is the format of the underlying disk
	 * image
	 */
	@Valid
	@JsonProperty("disk_format")
	private String diskFormat;

	/**
	 * Identifies the operating system used in the software image
	 */
	@Valid
	@JsonProperty("operating_system")
	private String operatingSystem;

	/**
	 * Version of this software image
	 */
	@Valid
	@NotNull
	@JsonProperty("version")
	private String version;

	/**
	 * The container format describes the container file format in which software
	 * image is provided
	 */
	@Valid
	@NotNull
	@JsonProperty("container_format")
	private String containerFormat;

	/**
	 * Identifies the virtualisation environments (e.g. hypervisor) compatible with
	 * this software image
	 */
	@Valid
	@JsonProperty("supported_virtualisation_environments")
	private List<String> supportedVirtualisationEnvironments;

	/**
	 * The minimal RAM requirement for this software image
	 */
	@Valid
	@JsonProperty("min_ram")
	private Size minRam;

	@NotNull
	public Size getSize() {
		return this.size;
	}

	public void setSize(@NotNull final Size size) {
		this.size = size;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(final String provider) {
		this.provider = provider;
	}

	public Size getMinDisk() {
		return this.minDisk;
	}

	public void setMinDisk(final Size minDisk) {
		this.minDisk = minDisk;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}

	@NotNull
	public ChecksumData getChecksum() {
		return this.checksum;
	}

	public void setChecksum(@NotNull final ChecksumData checksum) {
		this.checksum = checksum;
	}

	public String getDiskFormat() {
		return this.diskFormat;
	}

	public void setDiskFormat(final String diskFormat) {
		this.diskFormat = diskFormat;
	}

	public String getOperatingSystem() {
		return this.operatingSystem;
	}

	public void setOperatingSystem(final String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	@NotNull
	public String getVersion() {
		return this.version;
	}

	public void setVersion(@NotNull final String version) {
		this.version = version;
	}

	@NotNull
	public String getContainerFormat() {
		return this.containerFormat;
	}

	public void setContainerFormat(@NotNull final String containerFormat) {
		this.containerFormat = containerFormat;
	}

	public List<String> getSupportedVirtualisationEnvironments() {
		return this.supportedVirtualisationEnvironments;
	}

	public void setSupportedVirtualisationEnvironments(
			final List<String> supportedVirtualisationEnvironments) {
		this.supportedVirtualisationEnvironments = supportedVirtualisationEnvironments;
	}

	public Size getMinRam() {
		return this.minRam;
	}

	public void setMinRam(final Size minRam) {
		this.minRam = minRam;
	}
}
