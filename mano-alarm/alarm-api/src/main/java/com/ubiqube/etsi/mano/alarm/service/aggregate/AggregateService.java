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
package com.ubiqube.etsi.mano.alarm.service.aggregate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.service.AlarmContext;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class AggregateService {

	private final Map<String, AggregateFunction> af;

	public AggregateService(final List<AggregateFunction> funcs) {
		af = funcs.stream().collect(Collectors.toMap(x -> x.getName(), x -> x));
	}

	public void aggregate(final AlarmContext ctx, final Aggregates aggregate) {
		final AggregateFunction func = af.get(aggregate.getFunction());
		Objects.requireNonNull(func, "Unknown function: " + aggregate.getFunction());
		func.apply(ctx, aggregate);
	}

	public List<String> checkErrors(final List<Aggregates> aggregates) {
		return aggregates.stream().filter(x -> !af.containsKey(x.getFunction())).map(x -> x.getFunction()).distinct().toList();
	}

}
