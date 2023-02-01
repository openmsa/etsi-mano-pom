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
package com.ubiqube.etsi.mano.dao.mano.nsd.upd;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.utils.ToStringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
public class UpdateRequest implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	private UpdateTypeEnum updateType;

	@JsonProperty("addVnfIstance")
	@Valid
	private List<VnfInstanceData> addVnfIstance;

	@JsonProperty("removeVnfInstanceId")
	@Valid
	private List<String> removeVnfInstanceId;

	/**
	 * Store UUID of live instances;
	 */
	private List<UUID> realVnfInstanceToRemove;

	@JsonProperty("instantiateVnfData")
	@Valid
	private List<InstantiateVnfData> instantiateVnfData;

	@JsonProperty("terminateVnfData")
	@Valid
	private List<TerminateVnfData> terminateVnfData;

	@JsonProperty("changeVnfFlavourData")
	@Valid
	private List<ChangeVnfFlavourData> changeVnfFlavourData;

	@JsonProperty("operateVnfData")
	@Valid
	private List<OperateVnfData> operateVnfData;

	@JsonProperty("modifyVnfInfoData")
	@Valid
	private List<ModifyVnfInfoData> modifyVnfInfoData;

	@JsonProperty("changeExtVnfConnectivityData")
	@Valid
	private List<ChangeExtVnfConnectivityData> changeExtVnfConnectivityData;

	@JsonProperty("changeVnfPackageData")
	@Valid
	private List<ChangeVnfPackageData> changeVnfPackageData;

	@JsonProperty("addSap")
	@Valid
	private List<SapData> addSap;

	@JsonProperty("removeSapId")
	@Valid
	private List<String> removeSapId;

	@JsonProperty("addNestedNsData")
	@Valid
	private List<NestedNsInstanceData> addNestedNsData;

	@JsonProperty("removeNestedNsId")
	@Valid
	private List<String> removeNestedNsId;

	@JsonProperty("assocNewNsdVersionData")
	private AssocNewNsdVersionData assocNewNsdVersionData;

	@JsonProperty("moveVnfInstanceData")
	@Valid
	private List<MoveVnfInstanceData> moveVnfInstanceData;

	@JsonProperty("addVnffg")
	@Valid
	private List<AddVnffgData> addVnffg;

	@JsonProperty("removeVnffgId")
	@Valid
	private List<String> removeVnffgId;

	@JsonProperty("updateVnffg")
	@Valid
	private List<UpdateVnffgData> updateVnffg;

	@JsonProperty("changeNsFlavourData")
	private ChangeNsFlavourData changeNsFlavourData;

	@JsonProperty("addPnfData")
	@Valid
	private List<AddPnfData> addPnfData;

	@JsonProperty("modifyPnfData")
	@Valid
	private List<ModifyPnfData> modifyPnfData;

	@JsonProperty("removePnfId")
	@Valid
	private List<String> removePnfId;

	@JsonProperty("modifyWanConnectionInfoData")
	@Valid
	private List<ModifyWanConnectionInfoData> modifyWanConnectionInfoData;

	@JsonProperty("updateTime")
	private OffsetDateTime updateTime;

	@JsonProperty("createSnapshotData")
	private CreateVnfSnapshotData createSnapshotData;

	@JsonProperty("revertVnfToSnapshotData")
	private RevertVnfToSnapshotData revertVnfToSnapshotData;

	@JsonProperty("deleteVnfSnapshotData")
	private DeleteVnfSnapshotData deleteVnfSnapshotData;

	@JsonProperty("addNsVirtualLinkData")
	@Valid
	private List<AddNsVirtualLinkData> addNsVirtualLinkData;

	@JsonProperty("deleteNsVirtualLinkId")
	@Valid
	private List<String> deleteNsVirtualLinkId;

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}

}
