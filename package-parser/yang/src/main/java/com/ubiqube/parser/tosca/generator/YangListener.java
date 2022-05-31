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
package com.ubiqube.parser.tosca.generator;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.opendaylight.mdsal.binding.model.api.Enumeration;
import org.opendaylight.mdsal.binding.model.api.Type;

public interface YangListener {

	void startDocument();

	void startClass(String className, String typeComment);

	void terminateClass();

	void onDataTypeExtend(String derivedFrom);

	void startEnum(@NonNull String identifier);

	void addEnum(List<Enumeration> enumerations);

	void endEnum();

	void startField(String name, Type returnType);

	void onFieldTerminate();

	void terminateDocument();
}
