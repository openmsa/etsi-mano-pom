/*
 * SOL003 - VNF Lifecycle Management interface
 * SOL003 - VNF Lifecycle Management interface definition  IMPORTANT: Please note that this file might be not aligned to the current version of the ETSI Group Specification it refers to. In case of discrepancies the published ETSI Group Specification takes precedence.  In clause 4.3.2 of ETSI GS NFV-SOL 003 v2.4.1, an attribute-based filtering mechanism is defined. This mechanism is currently not included in the corresponding OpenAPI design for this GS version. Changes to the attribute-based filtering mechanism are being considered in v2.5.1 of this GS for inclusion in the corresponding future ETSI NFV OpenAPI design. Please report bugs to https://forge.etsi.org/bugzilla/buglist.cgi?component=Nfv-Openapis&list_id=61&product=NFV&resolution=
 *
 * OpenAPI spec version: 1.1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.ubiqube.etsi.mano.model.nslcm.sol003;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This type represents the information that allows addressing a virtualised
 * resource that is used by a VNF instance.
 */
@ApiModel(description = "This type represents the information that allows addressing a virtualised resource that is used by a VNF instance. ")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-06-13T10:04:39.223+02:00")
public class VirtualStorageResourceInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("virtualStorageDescId")
	private String virtualStorageDescId = null;

	@JsonProperty("storageResource")
	private ResourceHandle storageResource = null;

	@JsonProperty("reservationId")
	private String reservationId = null;

	@JsonProperty("metadata")
	private KeyValuePairs metadata = null;

	public VirtualStorageResourceInfo id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Identifier of this VirtualStorageResourceInfo instance.
	 * 
	 * @return id
	 **/
	@JsonProperty("id")
	@ApiModelProperty(required = true, value = "Identifier of this VirtualStorageResourceInfo instance. ")
	@NotNull
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public VirtualStorageResourceInfo virtualStorageDescId(String virtualStorageDescId) {
		this.virtualStorageDescId = virtualStorageDescId;
		return this;
	}

	/**
	 * Identifier of the VirtualStorageDesc in the VNFD.
	 * 
	 * @return virtualStorageDescId
	 **/
	@JsonProperty("virtualStorageDescId")
	@ApiModelProperty(required = true, value = "Identifier of the VirtualStorageDesc in the VNFD. ")
	@NotNull
	public String getVirtualStorageDescId() {
		return virtualStorageDescId;
	}

	public void setVirtualStorageDescId(String virtualStorageDescId) {
		this.virtualStorageDescId = virtualStorageDescId;
	}

	public VirtualStorageResourceInfo storageResource(ResourceHandle storageResource) {
		this.storageResource = storageResource;
		return this;
	}

	/**
	 * Reference to the VirtualStorage resource.
	 * 
	 * @return storageResource
	 **/
	@JsonProperty("storageResource")
	@ApiModelProperty(required = true, value = "Reference to the VirtualStorage resource. ")
	@NotNull
	public ResourceHandle getStorageResource() {
		return storageResource;
	}

	public void setStorageResource(ResourceHandle storageResource) {
		this.storageResource = storageResource;
	}

	public VirtualStorageResourceInfo reservationId(String reservationId) {
		this.reservationId = reservationId;
		return this;
	}

	/**
	 * The reservation identifier applicable to the resource. It shall be present
	 * when an applicable reservation exists.
	 * 
	 * @return reservationId
	 **/
	@JsonProperty("reservationId")
	@ApiModelProperty(value = "The reservation identifier applicable to the resource. It shall be present when an applicable reservation exists. ")
	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public VirtualStorageResourceInfo metadata(KeyValuePairs metadata) {
		this.metadata = metadata;
		return this;
	}

	/**
	 * Metadata about this resource.
	 * 
	 * @return metadata
	 **/
	@JsonProperty("metadata")
	@ApiModelProperty(value = "Metadata about this resource. ")
	public KeyValuePairs getMetadata() {
		return metadata;
	}

	public void setMetadata(KeyValuePairs metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VirtualStorageResourceInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    virtualStorageDescId: ").append(toIndentedString(virtualStorageDescId)).append("\n");
		sb.append("    storageResource: ").append(toIndentedString(storageResource)).append("\n");
		sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
		sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
