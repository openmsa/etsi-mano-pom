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
package com.ubiqube.parser.tosca.objects.tosca.interfaces.node.lifecycle;

import com.ubiqube.parser.tosca.OperationDefinition;
import com.ubiqube.parser.tosca.objects.tosca.interfaces.Root;

public class Standard extends Root {
	private OperationDefinition create;

	private OperationDefinition configure;

	private OperationDefinition start;

	private OperationDefinition stop;

	private OperationDefinition delete;

	public OperationDefinition getCreate() {
		return this.create;
	}

	public void setCreate(final OperationDefinition create) {
		this.create = create;
	}

	public OperationDefinition getConfigure() {
		return this.configure;
	}

	public void setConfigure(final OperationDefinition configure) {
		this.configure = configure;
	}

	public OperationDefinition getStart() {
		return this.start;
	}

	public void setStart(final OperationDefinition start) {
		this.start = start;
	}

	public OperationDefinition getStop() {
		return this.stop;
	}

	public void setStop(final OperationDefinition stop) {
		this.stop = stop;
	}

	public OperationDefinition getDelete() {
		return this.delete;
	}

	public void setDelete(final OperationDefinition delete) {
		this.delete = delete;
	}
}
