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
package com.ubiqube.etsi.mano.vnfm.v431.model.grant;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents additional resource information which resource and
 * resource type specific, and which is available from the VIM or the CISM or
 * the resource provider. * NOTE: At least one attribute shall be present.
 */
@Schema(description = "This type represents additional resource information which resource and resource type specific, and which is available from the VIM or the CISM or the resource provider. * NOTE: At least one attribute shall be present. ")
@Validated

public class AdditionalResourceInfo {
	@JsonProperty("hostName")
	private String hostName = null;

	@JsonProperty("persistentVolume")
	private String persistentVolume = null;

	@JsonProperty("additionalInfo")
	private Map<String, String> additionalInfo = null;

	public AdditionalResourceInfo hostName(final String hostName) {
		this.hostName = hostName;
		return this;
	}

	/**
	 * Name of the host where the resource is allocated. It shall be present for
	 * compute resources in the scope of the CISM and shall be absent otherwise. See
	 * note.
	 *
	 * @return hostName
	 **/
	@Schema(description = "Name of the host where the resource is allocated. It shall be present for compute resources in the scope of the CISM and shall be absent otherwise. See note. ")

	public String getHostName() {
		return hostName;
	}

	public void setHostName(final String hostName) {
		this.hostName = hostName;
	}

	public AdditionalResourceInfo persistentVolume(final String persistentVolume) {
		this.persistentVolume = persistentVolume;
		return this;
	}

	/**
	 * Name of the persistent volume to which the persistent volume claim
	 * representing the storage resource is bound. It may be present for storage
	 * resources in the scope of the CISM and shall be absent otherwise. See note.
	 *
	 * @return persistentVolume
	 **/
	@Schema(description = "Name of the persistent volume to which the persistent volume claim representing the storage resource is bound. It may be present for storage resources in the scope of the CISM and shall be absent otherwise. See note. ")

	public String getPersistentVolume() {
		return persistentVolume;
	}

	public void setPersistentVolume(final String persistentVolume) {
		this.persistentVolume = persistentVolume;
	}

	public AdditionalResourceInfo additionalInfo(final Map<String, String> additionalInfo) {
		this.additionalInfo = additionalInfo;
		return this;
	}

	/**
	 * Get additionalInfo
	 *
	 * @return additionalInfo
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(final Map<String, String> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final AdditionalResourceInfo additionalResourceInfo = (AdditionalResourceInfo) o;
		return Objects.equals(this.hostName, additionalResourceInfo.hostName) &&
				Objects.equals(this.persistentVolume, additionalResourceInfo.persistentVolume) &&
				Objects.equals(this.additionalInfo, additionalResourceInfo.additionalInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hostName, persistentVolume, additionalInfo);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AdditionalResourceInfo {\n");

		sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
		sb.append("    persistentVolume: ").append(toIndentedString(persistentVolume)).append("\n");
		sb.append("    additionalInfo: ").append(toIndentedString(additionalInfo)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
