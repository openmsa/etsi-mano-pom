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
package com.ubiqube.etsi.mano.vnfm.controller.vnflcm;

import static com.ubiqube.etsi.mano.Constants.VNFLCMOPOCC_SEARCH_DEFAULT_EXCLUDE_FIELDS;
import static com.ubiqube.etsi.mano.Constants.VNFLCMOPOCC_SEARCH_MANDATORY_FIELDS;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfLcmResourceChanges;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfLcmClassMaping;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfLcmOpOccGenericFrontController;
import com.ubiqube.etsi.mano.vnfm.service.mapping.VnfLcmOpOccMapping;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfLcmOpOccGenericFrontControllerImpl implements VnfLcmOpOccGenericFrontController {
	private final VnfLcmController vnfLcmController;
	private final VnfLcmOpOccMapping vnfLcmOpOccMapping;

	public VnfLcmOpOccGenericFrontControllerImpl(final VnfLcmController vnfLcmController, final VnfLcmOpOccMapping vnfLcmOpOccMapping) {
		this.vnfLcmController = vnfLcmController;
		this.vnfLcmOpOccMapping = vnfLcmOpOccMapping;
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<VnfBlueprint, U> mapper, final Consumer<U> makeLinks, final Class<?> frontClass) {
		return vnfLcmController.search(requestParams, mapper, VNFLCMOPOCC_SEARCH_DEFAULT_EXCLUDE_FIELDS, VNFLCMOPOCC_SEARCH_MANDATORY_FIELDS, makeLinks, frontClass);
	}

	@Override
	public ResponseEntity<Void> lcmOpOccRollback(final UUID id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<Void> lcmOpOccRetry(final UUID id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public <U> ResponseEntity<U> lcmOpOccFindById(final VnfLcmClassMaping mapping, final UUID id, final Consumer<U> makeLink, final BiConsumer<@NotNull U, Object> operationParameter) {
		final VnfBlueprint resultDb = vnfLcmController.vnfLcmOpOccsVnfLcmOpOccIdGet(id);
		final VnfLcmResourceChanges resourceChanged = new VnfLcmResourceChanges();
		resultDb.getTasks().stream()
				.filter(x -> x.getType() == ResourceTypeEnum.VL)
				.map(NetworkTask.class::cast)
				.map(vnfLcmOpOccMapping::mapToVnfInstantiatedVirtualLink)
				.forEach(resourceChanged::addAffectedVirtualLink);
		resultDb.getTasks().stream()
				.filter(x -> x.getType() == ResourceTypeEnum.STORAGE)
				.map(StorageTask.class::cast)
				.map(vnfLcmOpOccMapping::mapToVnfInstantiatedStorage)
				.forEach(resourceChanged::addAffectedVirtualStorage);
		resultDb.getTasks().stream()
				.filter(x -> x.getType() == ResourceTypeEnum.COMPUTE)
				.map(ComputeTask.class::cast)
				.map(vnfLcmOpOccMapping::mapToVnfInstantiatedCompute)
				.forEach(resourceChanged::addAffectedVnfcs);
		resultDb.setResourceChanges(resourceChanged);

		final U ret = mapping.mapToVnfLcmOpOcc(resultDb);
		makeLink.accept(ret);
		switch (resultDb.getOperation()) {
		case INSTANTIATE -> operationParameter.accept(ret, mapping.getInstantiateVnfRequest(resultDb));
		case SCALE -> operationParameter.accept(ret, mapping.getScaleVnfRequest(resultDb));
		case SCALE_TO_LEVEL -> operationParameter.accept(ret, mapping.getScaleVnfToLevelRequest(resultDb));
		case CHANGE_FLAVOUR -> operationParameter.accept(ret, mapping.getChangeVnfFlavourRequest(resultDb));
		case OPERATE -> operationParameter.accept(ret, mapping.getOperateVnfRequest(resultDb));
		case HEAL -> operationParameter.accept(ret, mapping.getHealVnfRequest(resultDb));
		case CHANGE_EXT_CONN -> operationParameter.accept(ret, mapping.getChangeExtVnfConnectivityRequest(resultDb));
		case TERMINATE -> operationParameter.accept(ret, mapping.getTerminateVnfRequest(resultDb));
		case MODIFY_INFO -> operationParameter.accept(ret, mapping.getVnfInfoModificationRequest(resultDb));
		case CREATE_SNAPSHOT -> operationParameter.accept(ret, mapping.getCreateVnfSnapshotRequest(resultDb));
		case REVERT_TO_SNAPSHOT -> operationParameter.accept(ret, mapping.getRevertToVnfSnapshotRequest(resultDb));
		case CHANGE_VNFPKG -> operationParameter.accept(ret, mapping.getChangeCurrentVnfPkgRequest(resultDb));
		default -> throw new IllegalArgumentException("Unexpected value: " + resultDb.getOperation());
		}
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<U> lcmOpOccFail(final UUID id) {
		vnfLcmController.failed(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public <U> ResponseEntity<U> lcmOpOccCancel(final UUID id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

}
