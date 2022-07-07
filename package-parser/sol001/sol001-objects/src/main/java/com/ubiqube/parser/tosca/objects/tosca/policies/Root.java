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
package com.ubiqube.parser.tosca.objects.tosca.policies;

import java.util.Map;

import javax.validation.Valid;

import com.ubiqube.parser.tosca.PolicyDefinition;
import com.ubiqube.parser.tosca.api.ToscaInernalBase;

public class Root extends ToscaInernalBase {
	@Valid
	private Map<String, PolicyDefinition> triggers;

	public Map<String, PolicyDefinition> getTriggers() {
		return this.triggers;
	}

	public void setTriggers(final Map<String, PolicyDefinition> triggers) {
		this.triggers = triggers;
	}
}
