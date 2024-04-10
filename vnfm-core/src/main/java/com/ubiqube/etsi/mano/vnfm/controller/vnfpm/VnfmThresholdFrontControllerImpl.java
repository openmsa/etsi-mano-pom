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
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import static com.ubiqube.etsi.mano.Constants.VNFTHR_SEARCH_DEFAULT_EXCLUDE_FIELDS;
import static com.ubiqube.etsi.mano.Constants.VNFTHR_SEARCH_MANDATORY_FIELDS;

import java.net.URI;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.vnfm.fc.vnfpm.VnfmThresholdFrontController;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfmThresholdFrontControllerImpl implements VnfmThresholdFrontController {
	private final VnfmThresholdController vnfmThresholdController;

	public VnfmThresholdFrontControllerImpl(final VnfmThresholdController vnfmThresholdController) {
		this.vnfmThresholdController = vnfmThresholdController;
	}

	@Override
	public <U> ResponseEntity<U> thresholdsCreate(final Threshold req, final Function<Threshold, U> mapper, final Consumer<U> makeLink, final Function<U, String> getSelfLink) {
		final Threshold res = vnfmThresholdController.save(req);
		final U ret = mapper.apply(res);
		makeLink.accept(ret);
		final String link = getSelfLink.apply(ret);
		return ResponseEntity.created(URI.create(link)).body(ret);
	}

	@Override
	public ResponseEntity<Void> deleteById(final String thresholdId) {
		vnfmThresholdController.delete(UUID.fromString(thresholdId));
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String thresholdId, final Function<Threshold, U> mapper, final Consumer<U> makeLink) {
		final com.ubiqube.etsi.mano.dao.mano.pm.Threshold res = vnfmThresholdController.findById(UUID.fromString(thresholdId));
		final U ret = mapper.apply(res);
		makeLink.accept(ret);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final @Nullable String nextpageOpaqueMarker, final Function<Threshold, U> mapper, final Consumer<U> makeLink, final Class<?> frontClass) {
		return vnfmThresholdController.search(requestParams, mapper, VNFTHR_SEARCH_DEFAULT_EXCLUDE_FIELDS, VNFTHR_SEARCH_MANDATORY_FIELDS, makeLink, frontClass);
	}

	@Override
	public <U> ResponseEntity<U> patch(final String thresholdId, final Object body, final Function<Threshold, U> mapper) {
		throw new UnsupportedOperationException();
	}

}
