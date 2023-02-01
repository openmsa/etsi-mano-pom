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

import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents a Change state operation occurrence.
 */
@Schema(description = "This type represents a Change state operation occurrence. ")
@Validated

public class ChangeStateOpOcc {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("operationState")
	private ChangeStateOpOccStateEnumType operationState = null;

	@JsonProperty("stateEnteredTime")
	private OffsetDateTime stateEnteredTime = null;

	@JsonProperty("startTime")
	private OffsetDateTime startTime = null;

	@JsonProperty("managedObjectRef")
	private ManoManagedObjectReference managedObjectRef = null;

	@JsonProperty("changeOperationalStateRequest")
	private ChangeOperationalStateEnumType changeOperationalStateRequest = null;

	@JsonProperty("changeAdministrativeStateRequest")
	private ChangeAdministrativeStateEnumType changeAdministrativeStateRequest = null;

	@JsonProperty("operationParams")
	private Object operationParams = null;

	public ChangeStateOpOcc id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * An identifier with the intention of being globally unique.
	 *
	 * @return id
	 **/
	@Schema(required = true, description = "An identifier with the intention of being globally unique. ")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public ChangeStateOpOcc operationState(final ChangeStateOpOccStateEnumType operationState) {
		this.operationState = operationState;
		return this;
	}

	/**
	 * Get operationState
	 *
	 * @return operationState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ChangeStateOpOccStateEnumType getOperationState() {
		return operationState;
	}

	public void setOperationState(final ChangeStateOpOccStateEnumType operationState) {
		this.operationState = operationState;
	}

	public ChangeStateOpOcc stateEnteredTime(final OffsetDateTime stateEnteredTime) {
		this.stateEnteredTime = stateEnteredTime;
		return this;
	}

	/**
	 * Date-time stamp. Representation: String formatted according to IETF RFC 3339.
	 *
	 * @return stateEnteredTime
	 **/
	@Schema(required = true, description = "Date-time stamp. Representation: String formatted according to IETF RFC 3339. ")
	@NotNull

	@Valid
	public OffsetDateTime getStateEnteredTime() {
		return stateEnteredTime;
	}

	public void setStateEnteredTime(final OffsetDateTime stateEnteredTime) {
		this.stateEnteredTime = stateEnteredTime;
	}

	public ChangeStateOpOcc startTime(final OffsetDateTime startTime) {
		this.startTime = startTime;
		return this;
	}

	/**
	 * Date-time stamp. Representation: String formatted according to IETF RFC 3339.
	 *
	 * @return startTime
	 **/
	@Schema(required = true, description = "Date-time stamp. Representation: String formatted according to IETF RFC 3339. ")
	@NotNull

	@Valid
	public OffsetDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(final OffsetDateTime startTime) {
		this.startTime = startTime;
	}

	public ChangeStateOpOcc managedObjectRef(final ManoManagedObjectReference managedObjectRef) {
		this.managedObjectRef = managedObjectRef;
		return this;
	}

	/**
	 * Get managedObjectRef
	 *
	 * @return managedObjectRef
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ManoManagedObjectReference getManagedObjectRef() {
		return managedObjectRef;
	}

	public void setManagedObjectRef(final ManoManagedObjectReference managedObjectRef) {
		this.managedObjectRef = managedObjectRef;
	}

	public ChangeStateOpOcc changeOperationalStateRequest(final ChangeOperationalStateEnumType changeOperationalStateRequest) {
		this.changeOperationalStateRequest = changeOperationalStateRequest;
		return this;
	}

	/**
	 * Get changeOperationalStateRequest
	 *
	 * @return changeOperationalStateRequest
	 **/
	@Schema(description = "")

	@Valid
	public ChangeOperationalStateEnumType getChangeOperationalStateRequest() {
		return changeOperationalStateRequest;
	}

	public void setChangeOperationalStateRequest(final ChangeOperationalStateEnumType changeOperationalStateRequest) {
		this.changeOperationalStateRequest = changeOperationalStateRequest;
	}

	public ChangeStateOpOcc changeAdministrativeStateRequest(final ChangeAdministrativeStateEnumType changeAdministrativeStateRequest) {
		this.changeAdministrativeStateRequest = changeAdministrativeStateRequest;
		return this;
	}

	/**
	 * Get changeAdministrativeStateRequest
	 *
	 * @return changeAdministrativeStateRequest
	 **/
	@Schema(description = "")

	@Valid
	public ChangeAdministrativeStateEnumType getChangeAdministrativeStateRequest() {
		return changeAdministrativeStateRequest;
	}

	public void setChangeAdministrativeStateRequest(final ChangeAdministrativeStateEnumType changeAdministrativeStateRequest) {
		this.changeAdministrativeStateRequest = changeAdministrativeStateRequest;
	}

	public ChangeStateOpOcc operationParams(final Object operationParams) {
		this.operationParams = operationParams;
		return this;
	}

	/**
	 * Input parameters of the change state operation. This attribute shall be
	 * formatted according to the request data type of the related change state
	 * operation. The following mapping between operationType and the data type of
	 * this attribute shall apply: - CHANGE_STATE: ChangeStateRequest This attribute
	 * shall be present if this data type is returned in a response to reading an
	 * individual resource, and may be present according to the chosen attribute
	 * selector parameter if this data type is returned in a response to a query of
	 * a container resource.
	 *
	 * @return operationParams
	 **/
	@Schema(description = "Input parameters of the change state operation. This attribute shall be formatted according to the request data type of the related change state operation. The following mapping between operationType and the data type of this attribute shall apply:   - CHANGE_STATE: ChangeStateRequest  This attribute shall be present if this data type is returned in a response to reading an individual resource, and may be present according to the chosen attribute selector parameter if this data type is returned in a response to a query of a container resource. ")

	public Object getOperationParams() {
		return operationParams;
	}

	public void setOperationParams(final Object operationParams) {
		this.operationParams = operationParams;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ChangeStateOpOcc changeStateOpOcc = (ChangeStateOpOcc) o;
		return Objects.equals(this.id, changeStateOpOcc.id) &&
				Objects.equals(this.operationState, changeStateOpOcc.operationState) &&
				Objects.equals(this.stateEnteredTime, changeStateOpOcc.stateEnteredTime) &&
				Objects.equals(this.startTime, changeStateOpOcc.startTime) &&
				Objects.equals(this.managedObjectRef, changeStateOpOcc.managedObjectRef) &&
				Objects.equals(this.changeOperationalStateRequest, changeStateOpOcc.changeOperationalStateRequest) &&
				Objects.equals(this.changeAdministrativeStateRequest, changeStateOpOcc.changeAdministrativeStateRequest) &&
				Objects.equals(this.operationParams, changeStateOpOcc.operationParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, operationState, stateEnteredTime, startTime, managedObjectRef, changeOperationalStateRequest, changeAdministrativeStateRequest, operationParams);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ChangeStateOpOcc {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    operationState: ").append(toIndentedString(operationState)).append("\n");
		sb.append("    stateEnteredTime: ").append(toIndentedString(stateEnteredTime)).append("\n");
		sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
		sb.append("    managedObjectRef: ").append(toIndentedString(managedObjectRef)).append("\n");
		sb.append("    changeOperationalStateRequest: ").append(toIndentedString(changeOperationalStateRequest)).append("\n");
		sb.append("    changeAdministrativeStateRequest: ").append(toIndentedString(changeAdministrativeStateRequest)).append("\n");
		sb.append("    operationParams: ").append(toIndentedString(operationParams)).append("\n");
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
