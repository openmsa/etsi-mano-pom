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
