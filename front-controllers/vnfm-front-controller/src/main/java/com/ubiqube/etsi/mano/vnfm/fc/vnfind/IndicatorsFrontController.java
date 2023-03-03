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
package com.ubiqube.etsi.mano.vnfm.fc.vnfind;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;

import jakarta.annotation.Nullable;

public interface IndicatorsFrontController {

	<U> ResponseEntity<List<U>> search(@Nullable String filter, @Nullable String nextpageOpaqueMarker, Class<U> clazz, Consumer<U> makeLink);

	<U> ResponseEntity<List<U>> findByVnfInstanceId(String vnfInstanceId, @Nullable String filter, @Nullable String nextpageOpaqueMarker, Class<U> clazz, Consumer<U> makeLink);

	<U> ResponseEntity<U> findByVnfInstanceIdAndIndicatorId(String vnfInstanceId, String indicatorId, Class<U> clazz, Consumer<U> makeLink);

	/**
	 * These is no vnf ind delete method.
	 *
	 * @param subscriptionId
	 * @return
	 */
	@Deprecated(forRemoval = true)
	ResponseEntity<Void> delete(String subscriptionId);

	/**
	 * These is no vnf ind Subscription.
	 *
	 * @param subscriptionId
	 * @return
	 */
	@Deprecated(forRemoval = true)
	<U> ResponseEntity<U> findById(String subscriptionId);

}