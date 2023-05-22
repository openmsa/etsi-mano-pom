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
package com.ubiqube.etsi.mano.alarm.service.transform;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;
import com.ubiqube.etsi.mano.alarm.service.AlarmContext;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class TransformService {

	private final Map<String, TransformFunction> tf;

	public TransformService(final List<TransformFunction> funcs) {
		tf = funcs.stream().collect(Collectors.toMap(x -> x.getName(), x -> x));
	}

	public void transform(final AlarmContext ctx, final Transform transform) {
		final TransformFunction func = tf.get(transform.getFunction());
		Objects.requireNonNull(func, "Unknown function: " + transform.getFunction());
		func.apply(ctx, transform);
	}

	public List<String> checkErrors(final List<Transform> transforms) {
		return transforms.stream().filter(x -> !tf.containsKey(x.getFunction())).map(x -> x.getFunction()).distinct().toList();
	}

}
