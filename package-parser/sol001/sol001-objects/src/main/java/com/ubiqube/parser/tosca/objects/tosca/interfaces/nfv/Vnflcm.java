/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.parser.tosca.objects.tosca.interfaces.nfv;

import com.ubiqube.parser.tosca.OperationDefinition;
import com.ubiqube.parser.tosca.objects.tosca.interfaces.Root;

/**
 * This interface encompasses a set of TOSCA operations corresponding to the VNF LCM operations defined in ETSI GS NFV-IFA 007 as well as to preamble and postamble procedures to the execution of the VNF LCM operations.
 */
public class Vnflcm extends Root {
	private OperationDefinition instantiate;

	private OperationDefinition instantiateStart;

	private OperationDefinition instantiateEnd;

	private OperationDefinition terminate;

	private OperationDefinition terminateStart;

	private OperationDefinition terminateEnd;

	private OperationDefinition modifyInformation;

	private OperationDefinition modifyInformationStart;

	private OperationDefinition modifyInformationEnd;

	private OperationDefinition changeFlavour;

	private OperationDefinition changeFlavourStart;

	private OperationDefinition changeFlavourEnd;

	private OperationDefinition changeExternalConnectivity;

	private OperationDefinition changeExternalConnectivityStart;

	private OperationDefinition changeExternalConnectivityEnd;

	private OperationDefinition operate;

	private OperationDefinition operateStart;

	private OperationDefinition operateEnd;

	private OperationDefinition heal;

	private OperationDefinition healStart;

	private OperationDefinition healEnd;

	private OperationDefinition scale;

	private OperationDefinition scaleStart;

	private OperationDefinition scaleEnd;

	private OperationDefinition scaleToLevel;

	private OperationDefinition scaleToLevelStart;

	private OperationDefinition scaleToLevelEnd;

	private OperationDefinition createSnapshot;

	private OperationDefinition createSnapshotStart;

	private OperationDefinition createSnapshotEnd;

	private OperationDefinition revertToSnapshot;

	private OperationDefinition revertToSnapshotStart;

	private OperationDefinition revertToSnapshotEnd;

	private OperationDefinition changeCurrentPackage;

	private OperationDefinition changeCurrentPackageStart;

	private OperationDefinition changeCurrentPackageEnd;

	public OperationDefinition getInstantiate() {
		return this.instantiate;
	}

	public void setInstantiate(final OperationDefinition instantiate) {
		this.instantiate = instantiate;
	}

	public OperationDefinition getInstantiateStart() {
		return this.instantiateStart;
	}

	public void setInstantiateStart(final OperationDefinition instantiateStart) {
		this.instantiateStart = instantiateStart;
	}

	public OperationDefinition getInstantiateEnd() {
		return this.instantiateEnd;
	}

	public void setInstantiateEnd(final OperationDefinition instantiateEnd) {
		this.instantiateEnd = instantiateEnd;
	}

	public OperationDefinition getTerminate() {
		return this.terminate;
	}

	public void setTerminate(final OperationDefinition terminate) {
		this.terminate = terminate;
	}

	public OperationDefinition getTerminateStart() {
		return this.terminateStart;
	}

	public void setTerminateStart(final OperationDefinition terminateStart) {
		this.terminateStart = terminateStart;
	}

	public OperationDefinition getTerminateEnd() {
		return this.terminateEnd;
	}

	public void setTerminateEnd(final OperationDefinition terminateEnd) {
		this.terminateEnd = terminateEnd;
	}

	public OperationDefinition getModifyInformation() {
		return this.modifyInformation;
	}

	public void setModifyInformation(final OperationDefinition modifyInformation) {
		this.modifyInformation = modifyInformation;
	}

	public OperationDefinition getModifyInformationStart() {
		return this.modifyInformationStart;
	}

	public void setModifyInformationStart(final OperationDefinition modifyInformationStart) {
		this.modifyInformationStart = modifyInformationStart;
	}

	public OperationDefinition getModifyInformationEnd() {
		return this.modifyInformationEnd;
	}

	public void setModifyInformationEnd(final OperationDefinition modifyInformationEnd) {
		this.modifyInformationEnd = modifyInformationEnd;
	}

	public OperationDefinition getChangeFlavour() {
		return this.changeFlavour;
	}

	public void setChangeFlavour(final OperationDefinition changeFlavour) {
		this.changeFlavour = changeFlavour;
	}

	public OperationDefinition getChangeFlavourStart() {
		return this.changeFlavourStart;
	}

	public void setChangeFlavourStart(final OperationDefinition changeFlavourStart) {
		this.changeFlavourStart = changeFlavourStart;
	}

	public OperationDefinition getChangeFlavourEnd() {
		return this.changeFlavourEnd;
	}

	public void setChangeFlavourEnd(final OperationDefinition changeFlavourEnd) {
		this.changeFlavourEnd = changeFlavourEnd;
	}

	public OperationDefinition getChangeExternalConnectivity() {
		return this.changeExternalConnectivity;
	}

	public void setChangeExternalConnectivity(final OperationDefinition changeExternalConnectivity) {
		this.changeExternalConnectivity = changeExternalConnectivity;
	}

	public OperationDefinition getChangeExternalConnectivityStart() {
		return this.changeExternalConnectivityStart;
	}

	public void setChangeExternalConnectivityStart(
			final OperationDefinition changeExternalConnectivityStart) {
		this.changeExternalConnectivityStart = changeExternalConnectivityStart;
	}

	public OperationDefinition getChangeExternalConnectivityEnd() {
		return this.changeExternalConnectivityEnd;
	}

	public void setChangeExternalConnectivityEnd(
			final OperationDefinition changeExternalConnectivityEnd) {
		this.changeExternalConnectivityEnd = changeExternalConnectivityEnd;
	}

	public OperationDefinition getOperate() {
		return this.operate;
	}

	public void setOperate(final OperationDefinition operate) {
		this.operate = operate;
	}

	public OperationDefinition getOperateStart() {
		return this.operateStart;
	}

	public void setOperateStart(final OperationDefinition operateStart) {
		this.operateStart = operateStart;
	}

	public OperationDefinition getOperateEnd() {
		return this.operateEnd;
	}

	public void setOperateEnd(final OperationDefinition operateEnd) {
		this.operateEnd = operateEnd;
	}

	public OperationDefinition getHeal() {
		return this.heal;
	}

	public void setHeal(final OperationDefinition heal) {
		this.heal = heal;
	}

	public OperationDefinition getHealStart() {
		return this.healStart;
	}

	public void setHealStart(final OperationDefinition healStart) {
		this.healStart = healStart;
	}

	public OperationDefinition getHealEnd() {
		return this.healEnd;
	}

	public void setHealEnd(final OperationDefinition healEnd) {
		this.healEnd = healEnd;
	}

	public OperationDefinition getScale() {
		return this.scale;
	}

	public void setScale(final OperationDefinition scale) {
		this.scale = scale;
	}

	public OperationDefinition getScaleStart() {
		return this.scaleStart;
	}

	public void setScaleStart(final OperationDefinition scaleStart) {
		this.scaleStart = scaleStart;
	}

	public OperationDefinition getScaleEnd() {
		return this.scaleEnd;
	}

	public void setScaleEnd(final OperationDefinition scaleEnd) {
		this.scaleEnd = scaleEnd;
	}

	public OperationDefinition getScaleToLevel() {
		return this.scaleToLevel;
	}

	public void setScaleToLevel(final OperationDefinition scaleToLevel) {
		this.scaleToLevel = scaleToLevel;
	}

	public OperationDefinition getScaleToLevelStart() {
		return this.scaleToLevelStart;
	}

	public void setScaleToLevelStart(final OperationDefinition scaleToLevelStart) {
		this.scaleToLevelStart = scaleToLevelStart;
	}

	public OperationDefinition getScaleToLevelEnd() {
		return this.scaleToLevelEnd;
	}

	public void setScaleToLevelEnd(final OperationDefinition scaleToLevelEnd) {
		this.scaleToLevelEnd = scaleToLevelEnd;
	}

	public OperationDefinition getCreateSnapshot() {
		return this.createSnapshot;
	}

	public void setCreateSnapshot(final OperationDefinition createSnapshot) {
		this.createSnapshot = createSnapshot;
	}

	public OperationDefinition getCreateSnapshotStart() {
		return this.createSnapshotStart;
	}

	public void setCreateSnapshotStart(final OperationDefinition createSnapshotStart) {
		this.createSnapshotStart = createSnapshotStart;
	}

	public OperationDefinition getCreateSnapshotEnd() {
		return this.createSnapshotEnd;
	}

	public void setCreateSnapshotEnd(final OperationDefinition createSnapshotEnd) {
		this.createSnapshotEnd = createSnapshotEnd;
	}

	public OperationDefinition getRevertToSnapshot() {
		return this.revertToSnapshot;
	}

	public void setRevertToSnapshot(final OperationDefinition revertToSnapshot) {
		this.revertToSnapshot = revertToSnapshot;
	}

	public OperationDefinition getRevertToSnapshotStart() {
		return this.revertToSnapshotStart;
	}

	public void setRevertToSnapshotStart(final OperationDefinition revertToSnapshotStart) {
		this.revertToSnapshotStart = revertToSnapshotStart;
	}

	public OperationDefinition getRevertToSnapshotEnd() {
		return this.revertToSnapshotEnd;
	}

	public void setRevertToSnapshotEnd(final OperationDefinition revertToSnapshotEnd) {
		this.revertToSnapshotEnd = revertToSnapshotEnd;
	}

	public OperationDefinition getChangeCurrentPackage() {
		return this.changeCurrentPackage;
	}

	public void setChangeCurrentPackage(final OperationDefinition changeCurrentPackage) {
		this.changeCurrentPackage = changeCurrentPackage;
	}

	public OperationDefinition getChangeCurrentPackageStart() {
		return this.changeCurrentPackageStart;
	}

	public void setChangeCurrentPackageStart(final OperationDefinition changeCurrentPackageStart) {
		this.changeCurrentPackageStart = changeCurrentPackageStart;
	}

	public OperationDefinition getChangeCurrentPackageEnd() {
		return this.changeCurrentPackageEnd;
	}

	public void setChangeCurrentPackageEnd(final OperationDefinition changeCurrentPackageEnd) {
		this.changeCurrentPackageEnd = changeCurrentPackageEnd;
	}
}
