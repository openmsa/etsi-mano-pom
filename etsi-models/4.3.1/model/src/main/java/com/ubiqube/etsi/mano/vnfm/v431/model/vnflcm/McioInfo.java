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
package com.ubiqube.etsi.mano.vnfm.v431.model.vnflcm;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type provides information about an MCIO representing the set of VNFC
 * instances realized by one or a set of OS containers which have been created
 * based on the same VDU. Within the CISM, an MCIO controller monitors the
 * actual state of an MCIO representing the set of VNFC instances realized by
 * one or a set of OS containers and compare it to the desired state as
 * specified in the respective declarative descriptor. It triggers actions
 * toward the CIS to align the actual to the desired state. Monitoring the
 * actual state includes monitoring the number of MCIO instances available at
 * any specific point in time. In addition, an MCIO controller maintains
 * properties and runtime information on the MCIO instances which have been
 * created based on the same VDU. The McioInfo data structure provides the
 * runtime information on the MCIOs obtained from the MCIO controller. NOTE:
 * There are different types of MCIOs. The set of VNFC instances based on the
 * same VDU is represented by one MCIO, e.g. of type Deployment. Each individual
 * VNFC instance is represented by another type of MCIO, e.g. a POD. Runtime
 * information of the set of OS containers realizing an individual VNFC instance
 * is not part of the McioInfo data structure; such runtime information is
 * provided in the ResourceHandle data structure referenced from the
 * VnfcResourceInfo. The McioInfo does not provide runtime information of a
 * constituent VNFC instance created based on a specific VDU. NOTE 1: The type
 * of MCIO as specified in the declarative descriptor of the MCIO, and that can
 * be read from the CISM. EXAMPLE: In case of MCIOs managed by Kubernetes®, the
 * type of MCIO corresponds to the “kind” property of the declarative
 * descriptor. NOTE 2: If the attribute additionalInfo is present, it may
 * contain runtime information on the actual and desired state of the MCIO(s).
 * NOTE 3: When the container infrastructure service is a Kubernetes® instance,
 * the mcioId is the combined values from the kind and name fields of the
 * Kubernetes resource object, separated by a slash. Example:
 * \&quot;Deployment/abcd\&quot;. NOTE 4: When the container infrastructure
 * service is a Kubernetes® instance, the mcioName is the name field of the
 * resource object.
 */
@Schema(description = "This type provides information about an MCIO representing the set of VNFC instances realized by one  or a set of OS containers which have been created based on the same VDU. Within the CISM, an MCIO controller monitors the actual state of an MCIO representing the set of VNFC  instances realized by one or a set of OS containers and compare it to the desired state as specified  in the respective declarative descriptor. It triggers actions toward the CIS to align the actual to  the desired state. Monitoring the actual state includes monitoring the number of MCIO instances available  at any specific point in time. In addition, an MCIO controller maintains properties and runtime information  on the MCIO instances which have been created based on the same VDU. The McioInfo data structure provides the runtime information on the MCIOs obtained from the MCIO controller. NOTE: There are different types of MCIOs. The set of VNFC instances based on the same VDU is represented        by one MCIO, e.g. of type Deployment. Each individual VNFC instance is represented by another type        of MCIO, e.g. a POD.  Runtime information of the set of OS containers realizing an individual VNFC instance is not part of the  McioInfo data structure; such runtime information is provided in the ResourceHandle data structure  referenced from the VnfcResourceInfo. The McioInfo does not provide runtime information of a constituent  VNFC instance created based on a specific VDU. NOTE 1: The type of MCIO as specified in the declarative descriptor of the MCIO, and that can be read from          the CISM. EXAMPLE: In case of MCIOs managed by Kubernetes®, the type of MCIO corresponds to the          “kind” property of the declarative descriptor. NOTE 2: If the attribute additionalInfo is present, it may contain runtime information on the actual and          desired state of the MCIO(s).    NOTE 3: When the container infrastructure service is a Kubernetes® instance, the mcioId is the combined          values from the kind and name fields of the Kubernetes resource object, separated by a slash.          Example: \"Deployment/abcd\".  NOTE 4: When the container infrastructure service is a Kubernetes® instance, the mcioName is the name          field of the resource object.           ")
@Validated

public class McioInfo {
	@JsonProperty("mcioId")
	private String mcioId = null;

	@JsonProperty("mcioName")
	private String mcioName = null;

	@JsonProperty("mcioNamespace")
	private String mcioNamespace = null;

	@JsonProperty("vduId")
	private String vduId = null;

	@JsonProperty("cismId")
	private String cismId = null;

	/**
	 * The type of MCIO. Specific values, their semantics and associated MCIO types
	 * are defined in clause 5.5.4.9. Additional values are also permitted. See note
	 * 1.
	 */
	public enum McioTypeEnum {
		DEPLOYMENT("Deployment"),

		STATEFULSET("Statefulset");

		private final String value;

		McioTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static McioTypeEnum fromValue(final String text) {
			for (final McioTypeEnum b : McioTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("mcioType")
	private McioTypeEnum mcioType = null;

	@JsonProperty("desiredInstances")
	private Integer desiredInstances = null;

	@JsonProperty("availableInstances")
	private Integer availableInstances = null;

	@JsonProperty("additionalInfo")
	private Map<String, String> additionalInfo = null;

	public McioInfo mcioId(final String mcioId) {
		this.mcioId = mcioId;
		return this;
	}

	/**
	 * Get mcioId
	 *
	 * @return mcioId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getMcioId() {
		return mcioId;
	}

	public void setMcioId(final String mcioId) {
		this.mcioId = mcioId;
	}

	public McioInfo mcioName(final String mcioName) {
		this.mcioName = mcioName;
		return this;
	}

	/**
	 * Human readable name of this MCIO. See note 4.
	 *
	 * @return mcioName
	 **/
	@Schema(required = true, description = "Human readable name of this MCIO. See note 4. ")
	@NotNull

	public String getMcioName() {
		return mcioName;
	}

	public void setMcioName(final String mcioName) {
		this.mcioName = mcioName;
	}

	public McioInfo mcioNamespace(final String mcioNamespace) {
		this.mcioNamespace = mcioNamespace;
		return this;
	}

	/**
	 * Namespace of this MCIO.
	 *
	 * @return mcioNamespace
	 **/
	@Schema(required = true, description = "Namespace of this MCIO. ")
	@NotNull

	public String getMcioNamespace() {
		return mcioNamespace;
	}

	public void setMcioNamespace(final String mcioNamespace) {
		this.mcioNamespace = mcioNamespace;
	}

	public McioInfo vduId(final String vduId) {
		this.vduId = vduId;
		return this;
	}

	/**
	 * Get vduId
	 *
	 * @return vduId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getVduId() {
		return vduId;
	}

	public void setVduId(final String vduId) {
		this.vduId = vduId;
	}

	public McioInfo cismId(final String cismId) {
		this.cismId = cismId;
		return this;
	}

	/**
	 * Get cismId
	 *
	 * @return cismId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getCismId() {
		return cismId;
	}

	public void setCismId(final String cismId) {
		this.cismId = cismId;
	}

	public McioInfo mcioType(final McioTypeEnum mcioType) {
		this.mcioType = mcioType;
		return this;
	}

	/**
	 * The type of MCIO. Specific values, their semantics and associated MCIO types
	 * are defined in clause 5.5.4.9. Additional values are also permitted. See note
	 * 1.
	 *
	 * @return mcioType
	 **/
	@Schema(required = true, description = "The type of MCIO. Specific values, their semantics and associated MCIO types are defined in clause  5.5.4.9. Additional values are also permitted. See note 1. ")
	@NotNull

	public McioTypeEnum getMcioType() {
		return mcioType;
	}

	public void setMcioType(final McioTypeEnum mcioType) {
		this.mcioType = mcioType;
	}

	public McioInfo desiredInstances(final Integer desiredInstances) {
		this.desiredInstances = desiredInstances;
		return this;
	}

	/**
	 * Number of desired MCIO instances.
	 *
	 * @return desiredInstances
	 **/
	@Schema(required = true, description = "Number of desired MCIO instances. ")
	@NotNull

	public Integer getDesiredInstances() {
		return desiredInstances;
	}

	public void setDesiredInstances(final Integer desiredInstances) {
		this.desiredInstances = desiredInstances;
	}

	public McioInfo availableInstances(final Integer availableInstances) {
		this.availableInstances = availableInstances;
		return this;
	}

	/**
	 * Number of available MCIO instances.
	 *
	 * @return availableInstances
	 **/
	@Schema(required = true, description = "Number of available MCIO instances. ")
	@NotNull

	public Integer getAvailableInstances() {
		return availableInstances;
	}

	public void setAvailableInstances(final Integer availableInstances) {
		this.availableInstances = availableInstances;
	}

	public McioInfo additionalInfo(final Map<String, String> additionalInfo) {
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
		final McioInfo mcioInfo = (McioInfo) o;
		return Objects.equals(this.mcioId, mcioInfo.mcioId) &&
				Objects.equals(this.mcioName, mcioInfo.mcioName) &&
				Objects.equals(this.mcioNamespace, mcioInfo.mcioNamespace) &&
				Objects.equals(this.vduId, mcioInfo.vduId) &&
				Objects.equals(this.cismId, mcioInfo.cismId) &&
				Objects.equals(this.mcioType, mcioInfo.mcioType) &&
				Objects.equals(this.desiredInstances, mcioInfo.desiredInstances) &&
				Objects.equals(this.availableInstances, mcioInfo.availableInstances) &&
				Objects.equals(this.additionalInfo, mcioInfo.additionalInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mcioId, mcioName, mcioNamespace, vduId, cismId, mcioType, desiredInstances, availableInstances, additionalInfo);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class McioInfo {\n");

		sb.append("    mcioId: ").append(toIndentedString(mcioId)).append("\n");
		sb.append("    mcioName: ").append(toIndentedString(mcioName)).append("\n");
		sb.append("    mcioNamespace: ").append(toIndentedString(mcioNamespace)).append("\n");
		sb.append("    vduId: ").append(toIndentedString(vduId)).append("\n");
		sb.append("    cismId: ").append(toIndentedString(cismId)).append("\n");
		sb.append("    mcioType: ").append(toIndentedString(mcioType)).append("\n");
		sb.append("    desiredInstances: ").append(toIndentedString(desiredInstances)).append("\n");
		sb.append("    availableInstances: ").append(toIndentedString(availableInstances)).append("\n");
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
