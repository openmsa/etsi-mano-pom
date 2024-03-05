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
package com.ubiqube.etsi.mano.service.mon;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.controller.AlarmClient;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AlarmDto;
import com.ubiqube.etsi.mano.dao.mano.TriggerDefinition;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class DummyAlarm implements ExternalAlarm {
	private final AlarmClient alarmClient;

	public DummyAlarm(final AlarmClient alarmClient) {
		this.alarmClient = alarmClient;
	}

	@Override
	public String registerAlarm(final VnfIndicator vnfIndicator) {
		final AlarmDto alarm = new AlarmDto();
		final TriggerDefinition trigger = vnfIndicator.getTriggers().entrySet().iterator().next().getValue();
		alarm.setConditions(trigger.getCondition());
//		final ResponseEntity<Alarm> ret = alarmClient.createAlarm(alarm);
//		final Alarm reta = Objects.requireNonNull(ret.getBody());
//		return reta.getId().toString();
		return "";
	}

	@Override
	public void remove(final UUID removedLiveInstance) {
//		alarmClient.deleteAlaram(removedLiveInstance);
	}

}
