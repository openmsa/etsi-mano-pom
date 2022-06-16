package com.ubiqube.etsi.mano.em.v361.model.vnflcm;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents request parameters for the \&quot;Create VNF
 * Snapshot\&quot; operation.
 */
@Schema(description = "This type represents request parameters for the \"Create VNF Snapshot\" operation. ")
@Validated

public class CreateVnfSnapshotRequest {
	@JsonProperty("vnfSnapshotInfoId")
	private String vnfSnapshotInfoId = null;

	@JsonProperty("vnfcInstanceId")
	private String vnfcInstanceId = null;

	@JsonProperty("additionalParams")
	private Map<String, String> additionalParams = null;

	@JsonProperty("userDefinedData")
	private Map<String, String> userDefinedData = null;

	public CreateVnfSnapshotRequest vnfSnapshotInfoId(final String vnfSnapshotInfoId) {
		this.vnfSnapshotInfoId = vnfSnapshotInfoId;
		return this;
	}

	/**
	 * Get vnfSnapshotInfoId
	 *
	 * @return vnfSnapshotInfoId
	 **/
	@Schema(description = "")

	public String getVnfSnapshotInfoId() {
		return vnfSnapshotInfoId;
	}

	public void setVnfSnapshotInfoId(final String vnfSnapshotInfoId) {
		this.vnfSnapshotInfoId = vnfSnapshotInfoId;
	}

	public CreateVnfSnapshotRequest vnfcInstanceId(final String vnfcInstanceId) {
		this.vnfcInstanceId = vnfcInstanceId;
		return this;
	}

	/**
	 * Get vnfcInstanceId
	 *
	 * @return vnfcInstanceId
	 **/
	@Schema(description = "")

	public String getVnfcInstanceId() {
		return vnfcInstanceId;
	}

	public void setVnfcInstanceId(final String vnfcInstanceId) {
		this.vnfcInstanceId = vnfcInstanceId;
	}

	public CreateVnfSnapshotRequest additionalParams(final Map<String, String> additionalParams) {
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

	public CreateVnfSnapshotRequest userDefinedData(final Map<String, String> userDefinedData) {
		this.userDefinedData = userDefinedData;
		return this;
	}

	/**
	 * Get userDefinedData
	 *
	 * @return userDefinedData
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getUserDefinedData() {
		return userDefinedData;
	}

	public void setUserDefinedData(final Map<String, String> userDefinedData) {
		this.userDefinedData = userDefinedData;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final CreateVnfSnapshotRequest createVnfSnapshotRequest = (CreateVnfSnapshotRequest) o;
		return Objects.equals(this.vnfSnapshotInfoId, createVnfSnapshotRequest.vnfSnapshotInfoId) &&
				Objects.equals(this.vnfcInstanceId, createVnfSnapshotRequest.vnfcInstanceId) &&
				Objects.equals(this.additionalParams, createVnfSnapshotRequest.additionalParams) &&
				Objects.equals(this.userDefinedData, createVnfSnapshotRequest.userDefinedData);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vnfSnapshotInfoId, vnfcInstanceId, additionalParams, userDefinedData);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class CreateVnfSnapshotRequest {\n");

		sb.append("    vnfSnapshotInfoId: ").append(toIndentedString(vnfSnapshotInfoId)).append("\n");
		sb.append("    vnfcInstanceId: ").append(toIndentedString(vnfcInstanceId)).append("\n");
		sb.append("    additionalParams: ").append(toIndentedString(additionalParams)).append("\n");
		sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
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
