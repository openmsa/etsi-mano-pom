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
package com.ubiqube.parser.tosca.objects.tosca.artifacts;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.Artifact;

public class Root extends Artifact {
	@Valid
	private String description;

	@Valid
	private String file;

	@Valid
	private String repository;

	@Valid
	@JsonProperty("deploy_path")
	private String deployPath;

	@Valid
	@JsonProperty("artifact_version")
	private String artifactVersion;

	@Valid
	private String type;

	@Valid
	@JsonProperty("checksum_algorithm")
	private String checksumAlgorithm;

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String getFile() {
		return this.file;
	}

	@Override
	public void setFile(final String file) {
		this.file = file;
	}

	@Override
	public String getRepository() {
		return this.repository;
	}

	@Override
	public void setRepository(final String repository) {
		this.repository = repository;
	}

	@Override
	public String getDeployPath() {
		return this.deployPath;
	}

	@Override
	public void setDeployPath(final String deployPath) {
		this.deployPath = deployPath;
	}

	@Override
	public String getArtifactVersion() {
		return this.artifactVersion;
	}

	@Override
	public void setArtifactVersion(final String artifactVersion) {
		this.artifactVersion = artifactVersion;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String getChecksumAlgorithm() {
		return this.checksumAlgorithm;
	}

	@Override
	public void setChecksumAlgorithm(final String checksumAlgorithm) {
		this.checksumAlgorithm = checksumAlgorithm;
	}
}
