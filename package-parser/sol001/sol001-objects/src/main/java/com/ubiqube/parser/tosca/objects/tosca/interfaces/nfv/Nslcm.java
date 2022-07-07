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
package com.ubiqube.parser.tosca.objects.tosca.interfaces.nfv;

import com.ubiqube.parser.tosca.OperationDefinition;
import com.ubiqube.parser.tosca.objects.tosca.interfaces.Root;

/**
 * This interface encompasses a set of TOSCA operations corresponding to NS LCM operations defined in ETSI GS NFV-IFA 013. as well as to preamble and postamble procedures to the execution of the NS LCM operations.
 */
public class Nslcm extends Root {
	private OperationDefinition instantiateStart;

	private OperationDefinition instantiate;

	private OperationDefinition instantiateEnd;

	private OperationDefinition terminateStart;

	private OperationDefinition terminate;

	private OperationDefinition terminateEnd;

	private OperationDefinition updateStart;

	private OperationDefinition update;

	private OperationDefinition updateEnd;

	private OperationDefinition scaleStart;

	private OperationDefinition scale;

	private OperationDefinition scaleEnd;

	private OperationDefinition healStart;

	private OperationDefinition heal;

	private OperationDefinition healEnd;

	public OperationDefinition getInstantiateStart() {
		return this.instantiateStart;
	}

	public void setInstantiateStart(final OperationDefinition instantiateStart) {
		this.instantiateStart = instantiateStart;
	}

	public OperationDefinition getInstantiate() {
		return this.instantiate;
	}

	public void setInstantiate(final OperationDefinition instantiate) {
		this.instantiate = instantiate;
	}

	public OperationDefinition getInstantiateEnd() {
		return this.instantiateEnd;
	}

	public void setInstantiateEnd(final OperationDefinition instantiateEnd) {
		this.instantiateEnd = instantiateEnd;
	}

	public OperationDefinition getTerminateStart() {
		return this.terminateStart;
	}

	public void setTerminateStart(final OperationDefinition terminateStart) {
		this.terminateStart = terminateStart;
	}

	public OperationDefinition getTerminate() {
		return this.terminate;
	}

	public void setTerminate(final OperationDefinition terminate) {
		this.terminate = terminate;
	}

	public OperationDefinition getTerminateEnd() {
		return this.terminateEnd;
	}

	public void setTerminateEnd(final OperationDefinition terminateEnd) {
		this.terminateEnd = terminateEnd;
	}

	public OperationDefinition getUpdateStart() {
		return this.updateStart;
	}

	public void setUpdateStart(final OperationDefinition updateStart) {
		this.updateStart = updateStart;
	}

	public OperationDefinition getUpdate() {
		return this.update;
	}

	public void setUpdate(final OperationDefinition update) {
		this.update = update;
	}

	public OperationDefinition getUpdateEnd() {
		return this.updateEnd;
	}

	public void setUpdateEnd(final OperationDefinition updateEnd) {
		this.updateEnd = updateEnd;
	}

	public OperationDefinition getScaleStart() {
		return this.scaleStart;
	}

	public void setScaleStart(final OperationDefinition scaleStart) {
		this.scaleStart = scaleStart;
	}

	public OperationDefinition getScale() {
		return this.scale;
	}

	public void setScale(final OperationDefinition scale) {
		this.scale = scale;
	}

	public OperationDefinition getScaleEnd() {
		return this.scaleEnd;
	}

	public void setScaleEnd(final OperationDefinition scaleEnd) {
		this.scaleEnd = scaleEnd;
	}

	public OperationDefinition getHealStart() {
		return this.healStart;
	}

	public void setHealStart(final OperationDefinition healStart) {
		this.healStart = healStart;
	}

	public OperationDefinition getHeal() {
		return this.heal;
	}

	public void setHeal(final OperationDefinition heal) {
		this.heal = heal;
	}

	public OperationDefinition getHealEnd() {
		return this.healEnd;
	}

	public void setHealEnd(final OperationDefinition healEnd) {
		this.healEnd = healEnd;
	}
}
