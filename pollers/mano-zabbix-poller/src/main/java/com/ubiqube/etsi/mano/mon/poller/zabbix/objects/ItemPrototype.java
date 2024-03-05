/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.mon.poller.zabbix.objects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPrototype {
	private String name;
	private String description;
	private ItemType type;
	private String key;
	private String delay;
	private String history;
	private String units;
	@JsonProperty("value_type")
	private ValueType valueType;
	private String trends;
	private ValueMap valuemap;
	@JsonProperty("application_prototypes")
	private List<ApplicationPrototype> applicationPrototypes;
	@JsonProperty("trigger_prototypes")
	private List<TriggerPrototype> triggerPrototypes;
	List<Preprocessing> preprocessing;
	@JsonProperty("master_item")
	private MasterItem masterItem;
}
