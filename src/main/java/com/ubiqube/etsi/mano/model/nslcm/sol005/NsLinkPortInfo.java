package com.ubiqube.etsi.mano.model.nslcm.sol005;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This type represents information about a link port of a VL instance. It shall
 * comply with the provisions defined in Table 6.5.3.55-1.
 */
@ApiModel(description = "This type represents information about a link port of a VL instance. It shall comply with the provisions defined in Table 6.5.3.55-1. ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-07T10:02:43.347+02:00")

public class NsLinkPortInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("resourceHandle")
	private ResourceHandle resourceHandle = null;

	@JsonProperty("nsCpHandle")
	@Valid
	private List<NsCpHandle> nsCpHandle = null;

	public NsLinkPortInfo id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Identifier of this link port as provided by the entity that has created the
	 * link port.
	 * 
	 * @return id
	 **/
	@ApiModelProperty(required = true, value = "Identifier of this link port as provided by the entity that has created the link port. ")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public NsLinkPortInfo resourceHandle(final ResourceHandle resourceHandle) {
		this.resourceHandle = resourceHandle;
		return this;
	}

	/**
	 * Identifier of the virtualised network resource realizing this link port.
	 * 
	 * @return resourceHandle
	 **/
	@ApiModelProperty(required = true, value = "Identifier of the virtualised network resource realizing this link port. ")
	@NotNull

	@Valid

	public ResourceHandle getResourceHandle() {
		return resourceHandle;
	}

	public void setResourceHandle(final ResourceHandle resourceHandle) {
		this.resourceHandle = resourceHandle;
	}

	public NsLinkPortInfo nsCpHandle(final List<NsCpHandle> nsCpHandle) {
		this.nsCpHandle = nsCpHandle;
		return this;
	}

	public NsLinkPortInfo addNsCpHandleItem(final NsCpHandle nsCpHandleItem) {
		if (this.nsCpHandle == null) {
			this.nsCpHandle = new ArrayList<>();
		}
		this.nsCpHandle.add(nsCpHandleItem);
		return this;
	}

	/**
	 * Identifier of the CP/SAP instance to be connected to this link port. The
	 * value refers to a vnfExtCpInfo item in the VnfInstance, or a pnfExtCpInfo
	 * item in the PnfInfo, or a sapInfo item in the NS instance. There shall be at
	 * most one link port associated with any connection point instance.
	 * 
	 * @return nsCpHandle
	 **/
	@ApiModelProperty(value = "Identifier of the CP/SAP instance to be connected to this link port. The value refers to a vnfExtCpInfo item in the VnfInstance, or a pnfExtCpInfo item in the PnfInfo, or a sapInfo item in the NS instance. There shall be at most one link port associated with any connection point instance. ")

	@Valid

	public List<NsCpHandle> getNsCpHandle() {
		return nsCpHandle;
	}

	public void setNsCpHandle(final List<NsCpHandle> nsCpHandle) {
		this.nsCpHandle = nsCpHandle;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final NsLinkPortInfo nsLinkPortInfo = (NsLinkPortInfo) o;
		return Objects.equals(this.id, nsLinkPortInfo.id) &&
				Objects.equals(this.resourceHandle, nsLinkPortInfo.resourceHandle) &&
				Objects.equals(this.nsCpHandle, nsLinkPortInfo.nsCpHandle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, resourceHandle, nsCpHandle);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NsLinkPortInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    resourceHandle: ").append(toIndentedString(resourceHandle)).append("\n");
		sb.append("    nsCpHandle: ").append(toIndentedString(nsCpHandle)).append("\n");
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
