/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.alarm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.AlarmException;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;
import com.ubiqube.etsi.mano.alarm.repository.AlarmRepository;
import com.ubiqube.etsi.mano.alarm.service.aggregate.AggregateService;
import com.ubiqube.etsi.mano.alarm.service.transform.TransformService;

import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class AlarmService {
	private final AlarmRepository alarmRepository;
	private final TransformService transformService;
	private final AggregateService aggregateService;

	public AlarmService(final AlarmRepository alarmRepository, final TransformService transformService, final AggregateService aggregateService) {
		this.alarmRepository = alarmRepository;
		this.transformService = transformService;
		this.aggregateService = aggregateService;
	}

	public List<Alarm> find() {
		final Iterable<Alarm> ite = alarmRepository.findAll();
		return StreamSupport.stream(ite.spliterator(), false).toList();
	}

	public Alarm create(final @Nonnull Alarm subs) {
		checkTransforms(subs.getTransforms());
		checkAggregates(subs.getAggregates());
		return alarmRepository.save(subs);
	}

	public void deleteById(final @Nonnull UUID id) {
		alarmRepository.deleteById(id);
	}

	public Optional<Alarm> findById(final @Nonnull UUID id) {
		return alarmRepository.findById(id);
	}

	private void checkAggregates(final List<Aggregates> aggregates) {
		final List<String> errors = aggregateService.checkErrors(aggregates);
		if (!errors.isEmpty()) {
			throw new AlarmException("Following aggregates are not defined: " + errors);
		}
	}

	private void checkTransforms(final List<Transform> list) {
		final List<String> errors = transformService.checkErrors(list);
		if (!errors.isEmpty()) {
			throw new AlarmException("Following functions are not defined: " + errors);
		}
	}

}
