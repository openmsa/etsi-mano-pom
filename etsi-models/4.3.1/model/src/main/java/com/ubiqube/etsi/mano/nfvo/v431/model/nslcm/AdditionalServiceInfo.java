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
package com.ubiqube.etsi.mano.nfvo.v431.model.nslcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents the additional service information of the virtual CP
 * instance used to expose properties of the virtual CP to NFV-MANO. NOTE: This
 * attribute shall only be present if additional information is needed to
 * identify the service termination within the VNF, such as for example a URL
 * path information in an HTTP request required to allow a single virtual CP IP
 * address to be used for several HTTP based services that use the same port
 * number.
 */
@Schema(description = "This type represents the additional service information of the virtual CP instance used to expose properties of the virtual CP to NFV-MANO. NOTE: This attribute shall only be present if additional information is needed to identify the service termination within the VNF, such as for example a URL path information in an HTTP request required to allow a single virtual CP IP address to be used for several HTTP based services that use the same port number.")
@Validated

public class AdditionalServiceInfo {
	@JsonProperty("portInfo")
	@Valid
	private List<ServicePortInfo> portInfo = new ArrayList<>();

	@JsonProperty("serviceInfo")
	@Valid
	private Map<String, String> serviceInfo = null;

	public AdditionalServiceInfo portInfo(final List<ServicePortInfo> portInfo) {
		this.portInfo = portInfo;
		return this;
	}

	public AdditionalServiceInfo addPortInfoItem(final ServicePortInfo portInfoItem) {
		this.portInfo.add(portInfoItem);
		return this;
	}

	/**
	 * Service port numbers exposed by the virtual CP instance.
	 *
	 * @return portInfo
	 **/
	@Schema(required = true, description = "Service port numbers exposed by the virtual CP instance. ")
	@NotNull
	@Valid
	public List<ServicePortInfo> getPortInfo() {
		return portInfo;
	}

	public void setPortInfo(final List<ServicePortInfo> portInfo) {
		this.portInfo = portInfo;
	}

	public AdditionalServiceInfo serviceInfo(final Map<String, String> serviceInfo) {
		this.serviceInfo = serviceInfo;
		return this;
	}

	/**
	 * Service matching information exposed by the virtual CP instance. See note.
	 *
	 * @return serviceInfo
	 **/
	@Schema(description = "Service matching information exposed by the virtual CP instance. See note. ")
	@Valid
	public Map<String, String> getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(final Map<String, String> serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final AdditionalServiceInfo additionalServiceInfo = (AdditionalServiceInfo) o;
		return Objects.equals(this.portInfo, additionalServiceInfo.portInfo) &&
				Objects.equals(this.serviceInfo, additionalServiceInfo.serviceInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(portInfo, serviceInfo);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AdditionalServiceInfo {\n");

		sb.append("    portInfo: ").append(toIndentedString(portInfo)).append("\n");
		sb.append("    serviceInfo: ").append(toIndentedString(serviceInfo)).append("\n");
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
