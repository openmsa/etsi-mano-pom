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
 * This type defines the additional parameters for the VNF instance to be
 * created associated with an NS instance. It shall comply with the provisions
 * defined in Table 6.5.3.22-1. NOTE 1: This attribute allows at VNF
 * instantiation the use of a VNFD different from the one specified in the NSD
 * with vnfProfileId provided the two VNFDs refer to the same
 * vnfdExtInvariantId. NOTE 2: A VnfProfile, NsProfile or PnfProfile may contain
 * multiple VersionDependencies as it may describe the version dependencies of
 * the descriptor referenced in the profile or of other descriptors with the
 * same external invariant identifier. NOTE 3: The overridingVersionDependency
 * attribute may only be present if the overrridingVnfdId attribute is present.
 */
@Schema(description = "This type defines the additional parameters for the VNF instance to be created associated with an NS instance. It shall comply with the provisions defined in Table 6.5.3.22-1. NOTE 1:  This attribute allows at VNF instantiation the use of a VNFD different from the one specified in the NSD with vnfProfileId provided the two VNFDs refer to the same vnfdExtInvariantId. NOTE 2: A VnfProfile, NsProfile or PnfProfile may contain multiple VersionDependencies as it may describe the version dependencies of the descriptor referenced in the profile or of other descriptors with the same external invariant identifier. NOTE 3:  The overridingVersionDependency attribute may only be present if the overrridingVnfdId attribute is present. ")
@Validated

public class ParamsForVnf {
	@JsonProperty("vnfProfileId")
	private String vnfProfileId = null;

	@JsonProperty("overridingVnfdId")
	private String overridingVnfdId = null;

	@JsonProperty("overridingVersionDependency")
	@Valid
	private List<OverridingVersionDependency> overridingVersionDependency = null;

	@JsonProperty("vnfInstanceName")
	private String vnfInstanceName = null;

	@JsonProperty("vnfInstanceDescription")
	private String vnfInstanceDescription = null;

	@JsonProperty("vnfConfigurableProperties")
	private Map<String, String> vnfConfigurableProperties = null;

	@JsonProperty("metadata")
	private Map<String, String> metadata = null;

	@JsonProperty("extensions")
	private Map<String, String> extensions = null;

	@JsonProperty("additionalParams")
	private Map<String, String> additionalParams = null;

	public ParamsForVnf vnfProfileId(final String vnfProfileId) {
		this.vnfProfileId = vnfProfileId;
		return this;
	}

	/**
	 * Get vnfProfileId
	 *
	 * @return vnfProfileId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getVnfProfileId() {
		return vnfProfileId;
	}

	public void setVnfProfileId(final String vnfProfileId) {
		this.vnfProfileId = vnfProfileId;
	}

	public ParamsForVnf overridingVnfdId(final String overridingVnfdId) {
		this.overridingVnfdId = overridingVnfdId;
		return this;
	}

	/**
	 * Get overridingVnfdId
	 *
	 * @return overridingVnfdId
	 **/
	@Schema(description = "")

	public String getOverridingVnfdId() {
		return overridingVnfdId;
	}

	public void setOverridingVnfdId(final String overridingVnfdId) {
		this.overridingVnfdId = overridingVnfdId;
	}

	public ParamsForVnf overridingVersionDependency(final List<OverridingVersionDependency> overridingVersionDependency) {
		this.overridingVersionDependency = overridingVersionDependency;
		return this;
	}

	public ParamsForVnf addOverridingVersionDependencyItem(final OverridingVersionDependency overridingVersionDependencyItem) {
		if (this.overridingVersionDependency == null) {
			this.overridingVersionDependency = new ArrayList<>();
		}
		this.overridingVersionDependency.add(overridingVersionDependencyItem);
		return this;
	}

	/**
	 * If present, information in each overridingVersionDependency replaces the
	 * versionDependency in the runtime information that the NFVO keeps about the
	 * VnfProfile, NsProfile or PnfProfile indicated in the
	 * OverridingVersionDependency. Only the versionDependency in the VnfProfile,
	 * NsProfile or PnfProfile with the same dependentConstituentId as in the
	 * overridingVersionDependency is replaced. See note 2. If no versionDependency
	 * with the dependentConstituentId indicated in the overridingVersionDependency
	 * exists in the VnfProfile, NsProfile or PnfProfile, the new versionDependency
	 * is added to the runtime information that the NFVO keeps about the profile.
	 * See note 3.
	 *
	 * @return overridingVersionDependency
	 **/
	@Schema(description = "If present, information in each overridingVersionDependency replaces  the versionDependency in the runtime information that the NFVO keeps about the VnfProfile, NsProfile or PnfProfile indicated in the OverridingVersionDependency. Only the versionDependency in the VnfProfile, NsProfile or PnfProfile with the same dependentConstituentId as in the overridingVersionDependency is replaced. See note 2. If no versionDependency with the dependentConstituentId indicated in the overridingVersionDependency exists in the VnfProfile, NsProfile or PnfProfile, the new versionDependency is added to the runtime information that the NFVO keeps about the profile. See note 3. ")
	@Valid
	public List<OverridingVersionDependency> getOverridingVersionDependency() {
		return overridingVersionDependency;
	}

	public void setOverridingVersionDependency(final List<OverridingVersionDependency> overridingVersionDependency) {
		this.overridingVersionDependency = overridingVersionDependency;
	}

	public ParamsForVnf vnfInstanceName(final String vnfInstanceName) {
		this.vnfInstanceName = vnfInstanceName;
		return this;
	}

	/**
	 * Human-readable name of the VNF instance to be created.
	 *
	 * @return vnfInstanceName
	 **/
	@Schema(description = "Human-readable name of the VNF instance to be created. ")

	public String getVnfInstanceName() {
		return vnfInstanceName;
	}

	public void setVnfInstanceName(final String vnfInstanceName) {
		this.vnfInstanceName = vnfInstanceName;
	}

	public ParamsForVnf vnfInstanceDescription(final String vnfInstanceDescription) {
		this.vnfInstanceDescription = vnfInstanceDescription;
		return this;
	}

	/**
	 * Human-readable description of the VNF instance to be created.
	 *
	 * @return vnfInstanceDescription
	 **/
	@Schema(description = "Human-readable description of the VNF instance to be created. ")

	public String getVnfInstanceDescription() {
		return vnfInstanceDescription;
	}

	public void setVnfInstanceDescription(final String vnfInstanceDescription) {
		this.vnfInstanceDescription = vnfInstanceDescription;
	}

	public ParamsForVnf vnfConfigurableProperties(final Map<String, String> vnfConfigurableProperties) {
		this.vnfConfigurableProperties = vnfConfigurableProperties;
		return this;
	}

	/**
	 * Get vnfConfigurableProperties
	 *
	 * @return vnfConfigurableProperties
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getVnfConfigurableProperties() {
		return vnfConfigurableProperties;
	}

	public void setVnfConfigurableProperties(final Map<String, String> vnfConfigurableProperties) {
		this.vnfConfigurableProperties = vnfConfigurableProperties;
	}

	public ParamsForVnf metadata(final Map<String, String> metadata) {
		this.metadata = metadata;
		return this;
	}

	/**
	 * Get metadata
	 *
	 * @return metadata
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public ParamsForVnf extensions(final Map<String, String> extensions) {
		this.extensions = extensions;
		return this;
	}

	/**
	 * Get extensions
	 *
	 * @return extensions
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getExtensions() {
		return extensions;
	}

	public void setExtensions(final Map<String, String> extensions) {
		this.extensions = extensions;
	}

	public ParamsForVnf additionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
		return this;
	}

	/**
	 * Get additionalParams
	 *
	 * @return additionalParams
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ParamsForVnf paramsForVnf = (ParamsForVnf) o;
		return Objects.equals(this.vnfProfileId, paramsForVnf.vnfProfileId) &&
				Objects.equals(this.overridingVnfdId, paramsForVnf.overridingVnfdId) &&
				Objects.equals(this.overridingVersionDependency, paramsForVnf.overridingVersionDependency) &&
				Objects.equals(this.vnfInstanceName, paramsForVnf.vnfInstanceName) &&
				Objects.equals(this.vnfInstanceDescription, paramsForVnf.vnfInstanceDescription) &&
				Objects.equals(this.vnfConfigurableProperties, paramsForVnf.vnfConfigurableProperties) &&
				Objects.equals(this.metadata, paramsForVnf.metadata) &&
				Objects.equals(this.extensions, paramsForVnf.extensions) &&
				Objects.equals(this.additionalParams, paramsForVnf.additionalParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vnfProfileId, overridingVnfdId, overridingVersionDependency, vnfInstanceName, vnfInstanceDescription, vnfConfigurableProperties, metadata, extensions, additionalParams);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ParamsForVnf {\n");

		sb.append("    vnfProfileId: ").append(toIndentedString(vnfProfileId)).append("\n");
		sb.append("    overridingVnfdId: ").append(toIndentedString(overridingVnfdId)).append("\n");
		sb.append("    overridingVersionDependency: ").append(toIndentedString(overridingVersionDependency)).append("\n");
		sb.append("    vnfInstanceName: ").append(toIndentedString(vnfInstanceName)).append("\n");
		sb.append("    vnfInstanceDescription: ").append(toIndentedString(vnfInstanceDescription)).append("\n");
		sb.append("    vnfConfigurableProperties: ").append(toIndentedString(vnfConfigurableProperties)).append("\n");
		sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
		sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
		sb.append("    additionalParams: ").append(toIndentedString(additionalParams)).append("\n");
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
