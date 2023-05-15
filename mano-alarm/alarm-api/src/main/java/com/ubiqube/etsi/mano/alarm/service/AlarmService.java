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
package com.ubiqube.etsi.mano.alarm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.repository.AlarmRepository;

import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class AlarmService {
	private final AlarmRepository alarmRepository;

	public AlarmService(final AlarmRepository alarmRepository) {
		this.alarmRepository = alarmRepository;
	}

	public List<Alarm> find() {
		final Iterable<Alarm> ite = alarmRepository.findAll();
		return StreamSupport.stream(ite.spliterator(), false).toList();
	}

	public Alarm create(final @Nonnull Alarm subs) {
		return alarmRepository.save(subs);
	}

	public void deleteById(final @Nonnull UUID id) {
		alarmRepository.deleteById(id);
	}

	public Optional<Alarm> findById(final @Nonnull UUID id) {
		return alarmRepository.findById(id);
	}

}
