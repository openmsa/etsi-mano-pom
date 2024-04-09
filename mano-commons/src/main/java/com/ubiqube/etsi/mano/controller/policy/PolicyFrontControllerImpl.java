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
package com.ubiqube.etsi.mano.controller.policy;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.policy.ActivationStatusType;
import com.ubiqube.etsi.mano.dao.mano.policy.Policies;
import com.ubiqube.etsi.mano.dao.mano.policy.TransfertStatusType;
import com.ubiqube.etsi.mano.exception.GenericException;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class PolicyFrontControllerImpl implements PolicyFrontController {
	private final PolicyController policyController;

	public PolicyFrontControllerImpl(final PolicyController policyController) {
		this.policyController = policyController;
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final String nextpageOpaqueMarker, final Function<Policies, U> mapper, final Consumer<U> makeLinks) {
		return policyController.search(requestParams, mapper, makeLinks);
	}

	@Override
	public ResponseEntity<Void> deleteById(final UUID safeUUID) {
		policyController.deleteById(safeUUID);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String uuid, final Function<Policies, U> mapper, final Consumer<U> makeLinks) {
		final Policies p = policyController.findById(getSafeUUID(uuid));
		final U e = mapper.apply(p);
		makeLinks.accept(e);
		return ResponseEntity.ok().eTag("" + p.getVersion()).body(e);
	}

	@Override
	public ResponseEntity<Void> deleteByVersion(final String policyId, final String version) {
		policyController.deleteByIdAndVersion(getSafeUUID(policyId), version);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<byte[]> getContentByPolicyIdAndVersion(final String policyId, final String version) {
		final byte[] b = policyController.getContentByPolicyIdAndVersion(getSafeUUID(policyId), version);
		return ResponseEntity.ok(b);
	}

	@Override
	public ResponseEntity<byte[]> getContentBySelectedVersion(final String policyId) {
		final byte[] b = policyController.getContentBySelectedVersion(getSafeUUID(policyId));
		return ResponseEntity.ok(b);
	}

	@Override
	public ResponseEntity<Void> putContent(final String policyId, final String version, final InputStreamSource file) {
		try (InputStream is = file.getInputStream()) {
			policyController.putContent(getSafeUUID(policyId), version, is);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ResponseEntity.accepted().build();
	}

	@Override
	public <U> ResponseEntity<U> create(final Policies body, final Function<Policies, U> mapper, final Consumer<U> makeLinks) {
		Policies p = body;
		p.setActivationStatus(ActivationStatusType.DEACTIVATED);
		p.setTransferStatus(TransfertStatusType.CREATED);
		p = policyController.create(p);
		final U e = mapper.apply(p);
		makeLinks.accept(e);
		return ResponseEntity.status(HttpStatus.CREATED).body(e);
	}

	@Override
	public <U> ResponseEntity<U> modify(final String policyId, final PolicyPatchDto body, final Function<Policies, U> mapper, final Consumer<U> makeLinks) {
		final PolicyPatchDto patch = body;
		final Policies p = policyController.modify(getSafeUUID(policyId), patch);
		final U e = mapper.apply(p);
		makeLinks.accept(e);
		return ResponseEntity.ok(e);
	}

}
