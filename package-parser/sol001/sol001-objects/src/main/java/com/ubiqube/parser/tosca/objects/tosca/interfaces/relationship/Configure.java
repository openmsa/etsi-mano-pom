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
package com.ubiqube.parser.tosca.objects.tosca.interfaces.relationship;

import com.ubiqube.parser.tosca.OperationDefinition;
import com.ubiqube.parser.tosca.objects.tosca.interfaces.Root;

public class Configure extends Root {
	private OperationDefinition preConfigureSource;

	private OperationDefinition preConfigureTarget;

	private OperationDefinition postConfigureSource;

	private OperationDefinition postConfigureTarget;

	private OperationDefinition addTarget;

	private OperationDefinition addSource;

	private OperationDefinition targetChanged;

	private OperationDefinition removeTarget;

	public OperationDefinition getPreConfigureSource() {
		return this.preConfigureSource;
	}

	public void setPreConfigureSource(final OperationDefinition preConfigureSource) {
		this.preConfigureSource = preConfigureSource;
	}

	public OperationDefinition getPreConfigureTarget() {
		return this.preConfigureTarget;
	}

	public void setPreConfigureTarget(final OperationDefinition preConfigureTarget) {
		this.preConfigureTarget = preConfigureTarget;
	}

	public OperationDefinition getPostConfigureSource() {
		return this.postConfigureSource;
	}

	public void setPostConfigureSource(final OperationDefinition postConfigureSource) {
		this.postConfigureSource = postConfigureSource;
	}

	public OperationDefinition getPostConfigureTarget() {
		return this.postConfigureTarget;
	}

	public void setPostConfigureTarget(final OperationDefinition postConfigureTarget) {
		this.postConfigureTarget = postConfigureTarget;
	}

	public OperationDefinition getAddTarget() {
		return this.addTarget;
	}

	public void setAddTarget(final OperationDefinition addTarget) {
		this.addTarget = addTarget;
	}

	public OperationDefinition getAddSource() {
		return this.addSource;
	}

	public void setAddSource(final OperationDefinition addSource) {
		this.addSource = addSource;
	}

	public OperationDefinition getTargetChanged() {
		return this.targetChanged;
	}

	public void setTargetChanged(final OperationDefinition targetChanged) {
		this.targetChanged = targetChanged;
	}

	public OperationDefinition getRemoveTarget() {
		return this.removeTarget;
	}

	public void setRemoveTarget(final OperationDefinition removeTarget) {
		this.removeTarget = removeTarget;
	}
}
