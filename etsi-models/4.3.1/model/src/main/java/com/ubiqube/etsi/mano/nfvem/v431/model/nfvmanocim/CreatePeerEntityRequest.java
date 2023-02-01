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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents request parameters for the creation of a new peer entity
 * resource. * NOTE: The value of the \&quot;peerEntityId\&quot; attribute is
 * the same as the value of the \&quot;id\&quot; attribute in the
 * \&quot;ManoEntity\&quot; of the corresponding NFV-MANO functional entity that
 * acts as peer entity, and shall follow the uniqueness requirements set out in
 * clause 5.6.2.2 for the \&quot;id\&quot; attribute.
 */
@Schema(description = "This type represents request parameters for the creation of a new peer entity resource. * NOTE: The value of the \"peerEntityId\" attribute is the same as the value of the \"id\" attribute         in the \"ManoEntity\" of the corresponding NFV-MANO functional entity that acts as peer entity,         and shall follow the uniqueness requirements set out in clause 5.6.2.2 for the \"id\" attribute. ")
@Validated

public class CreatePeerEntityRequest {
	@JsonProperty("peerEntityId")
	private String peerEntityId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("type")
	private PeerEntityEnumType type = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("consumedManoInterfaces")
	@Valid
	private Map<String, ConsumedManoInterfaceInfo> consumedManoInterfaces = null;

	@JsonProperty("peerEntityState")
	private CreatePeerEntityRequestPeerEntityState peerEntityState = null;

	public CreatePeerEntityRequest peerEntityId(final String peerEntityId) {
		this.peerEntityId = peerEntityId;
		return this;
	}

	/**
	 * An identifier with the intention of being globally unique.
	 *
	 * @return peerEntityId
	 **/
	@Schema(required = true, description = "An identifier with the intention of being globally unique. ")
	@NotNull

	public String getPeerEntityId() {
		return peerEntityId;
	}

	public void setPeerEntityId(final String peerEntityId) {
		this.peerEntityId = peerEntityId;
	}

	public CreatePeerEntityRequest name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * Human-readable name of the peer functional entity.
	 *
	 * @return name
	 **/
	@Schema(required = true, description = "Human-readable name of the peer functional entity. ")
	@NotNull

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public CreatePeerEntityRequest type(final PeerEntityEnumType type) {
		this.type = type;
		return this;
	}

	/**
	 * Get type
	 *
	 * @return type
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public PeerEntityEnumType getType() {
		return type;
	}

	public void setType(final PeerEntityEnumType type) {
		this.type = type;
	}

	public CreatePeerEntityRequest description(final String description) {
		this.description = description;
		return this;
	}

	/**
	 * Human-readable description of the peer functional entity.
	 *
	 * @return description
	 **/
	@Schema(description = "Human-readable description of the peer functional entity. ")

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public CreatePeerEntityRequest consumedManoInterfaces(final Map<String, ConsumedManoInterfaceInfo> consumedManoInterfaces) {
		this.consumedManoInterfaces = consumedManoInterfaces;
		return this;
	}

	public CreatePeerEntityRequest putConsumedManoInterfacesItem(final String key, final ConsumedManoInterfaceInfo consumedManoInterfacesItem) {
		if (this.consumedManoInterfaces == null) {
			this.consumedManoInterfaces = new HashMap<>();
		}
		this.consumedManoInterfaces.put(key, consumedManoInterfacesItem);
		return this;
	}

	/**
	 * Initial information of the interface consumed by the NFV-MANO functional
	 * entity from the peer functional entity.
	 *
	 * @return consumedManoInterfaces
	 **/
	@Schema(description = "Initial information of the interface consumed by the NFV-MANO functional entity from the peer functional entity. ")
	@Valid
	public Map<String, ConsumedManoInterfaceInfo> getConsumedManoInterfaces() {
		return consumedManoInterfaces;
	}

	public void setConsumedManoInterfaces(final Map<String, ConsumedManoInterfaceInfo> consumedManoInterfaces) {
		this.consumedManoInterfaces = consumedManoInterfaces;
	}

	public CreatePeerEntityRequest peerEntityState(final CreatePeerEntityRequestPeerEntityState peerEntityState) {
		this.peerEntityState = peerEntityState;
		return this;
	}

	/**
	 * Get peerEntityState
	 *
	 * @return peerEntityState
	 **/
	@Schema(description = "")

	@Valid
	public CreatePeerEntityRequestPeerEntityState getPeerEntityState() {
		return peerEntityState;
	}

	public void setPeerEntityState(final CreatePeerEntityRequestPeerEntityState peerEntityState) {
		this.peerEntityState = peerEntityState;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final CreatePeerEntityRequest createPeerEntityRequest = (CreatePeerEntityRequest) o;
		return Objects.equals(this.peerEntityId, createPeerEntityRequest.peerEntityId) &&
				Objects.equals(this.name, createPeerEntityRequest.name) &&
				Objects.equals(this.type, createPeerEntityRequest.type) &&
				Objects.equals(this.description, createPeerEntityRequest.description) &&
				Objects.equals(this.consumedManoInterfaces, createPeerEntityRequest.consumedManoInterfaces) &&
				Objects.equals(this.peerEntityState, createPeerEntityRequest.peerEntityState);
	}

	@Override
	public int hashCode() {
		return Objects.hash(peerEntityId, name, type, description, consumedManoInterfaces, peerEntityState);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class CreatePeerEntityRequest {\n");

		sb.append("    peerEntityId: ").append(toIndentedString(peerEntityId)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    consumedManoInterfaces: ").append(toIndentedString(consumedManoInterfaces)).append("\n");
		sb.append("    peerEntityState: ").append(toIndentedString(peerEntityState)).append("\n");
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
