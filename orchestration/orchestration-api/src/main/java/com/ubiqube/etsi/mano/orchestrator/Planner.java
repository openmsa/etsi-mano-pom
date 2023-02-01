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
package com.ubiqube.etsi.mano.orchestrator;

import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 * @param <P>
 *
 */
public interface Planner<U> {

	ExecutionGraph implement(PreExecutionGraphV3<U> plan);

	OrchExecutionResults<U> execute(ExecutionGraph imp, final OrchExecutionListener<U> listener);

}