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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;


public class TriggerDefinition extends Root {
	@JsonProperty("event")
	private String event;
	
	@JsonProperty("condition")
	private List<Object> condition;
	
	@JsonProperty("action")
	private List<ActivityListDefinition> action;
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public List<Object> getCondition() {
		return condition;
	}

	public void setCondition(List<Object> condition) {
		this.condition = condition;
	}

	public List<ActivityListDefinition> getAction() {
		return action;
	}

	public void setAction(List<ActivityListDefinition> action) {
		this.action = action;
	}
}
