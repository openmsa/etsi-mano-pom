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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.vnfm.service.plan;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VduInstantiationLevel;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfComputeAspectDelta;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.NsScaleType;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class ScalingStrategyV2 implements ScalingStrategy {
	private final ScaleByStep scaleByStep;

	public ScalingStrategyV2(final ScaleByStep scaleByStep) {
		this.scaleByStep = scaleByStep;
	}

	@Override
	public NumberOfCompute getNumberOfCompute(final VnfBlueprint blueprint, final VnfPackage bundle, final @Nullable Set<ScaleInfo> scaling, final VnfCompute compute, final VnfInstance instance) {
		final PlanOperationType operation = blueprint.getOperation();
		if ((operation == PlanOperationType.INSTANTIATE) || (operation == PlanOperationType.TERMINATE)) {
			return handleInstantiate(blueprint, bundle, compute);
		}
		return handleScale(blueprint, bundle, compute, instance);
	}

	private static NumberOfCompute handleInstantiate(final VnfBlueprint plan, final VnfPackage vnfPackage, final VnfCompute compute) {
		final String level = Optional.ofNullable(plan.getParameters().getInstantiationLevelId()).orElseGet(vnfPackage::getDefaultInstantiationLevel);
		final Optional<VduInstantiationLevel> newLevel = compute.getInstantiationLevel().stream().filter(x -> x.getLevelName().equals(level)).findFirst();
		if (newLevel.isPresent()) {
			return new NumberOfCompute(0, newLevel.get().getNumberOfInstances(), null);
		}
		return new NumberOfCompute(0, 1, null);
	}

	private NumberOfCompute handleScale(final VnfBlueprint plan, final VnfPackage vnfPackage, final VnfCompute compute, final VnfInstance vnfInstance) {
		return switch (getScalingType(plan.getParameters())) {
		case NS_SCALE_LEVEL_INST_LEVEL -> handleInstantiationLevel(plan.getParameters().getInstantiationLevelId(), compute);
		case NS_SCALE_LEVEL_SCALE_INFO -> handleScaleInfo(plan.getParameters().getScaleStatus(), compute);
		case NS_SCALE_STEP -> scaleByStep.handleStep(plan.getParameters(), vnfPackage, compute, vnfInstance);
		default -> throw new IllegalArgumentException("Unexpected value: " + getScalingType(plan.getParameters()));
		};
	}

	private static NumberOfCompute handleScaleInfo(final Set<ScaleInfo> scaleStatus, final VnfCompute compute) {
		final List<ScaleInfo> aspect = scaleStatus.stream()
				.filter(x -> contains(x, compute.getScalingAspectDeltas()))
				.toList();
		if (aspect.isEmpty()) {
			// Rebuild initial.
		}
		final VnfComputeAspectDelta scaleInfo = aspect.stream().map(x -> find(x.getScaleLevel(), compute.getScalingAspectDeltas())).findFirst().orElseThrow();
		final int s = scaleInfo.getNumberOfInstances();
		return new NumberOfCompute(0, s, new ScaleInfo(scaleInfo.getLevel() + "", s));
	}

	private static VnfComputeAspectDelta find(final int scaleLevel, final Set<VnfComputeAspectDelta> scalingAspectDeltas) {
		return scalingAspectDeltas.stream().filter(x -> x.getLevel() == scaleLevel).findFirst().orElseThrow();
	}

	private static boolean contains(final ScaleInfo scaleInfo, final Set<VnfComputeAspectDelta> scalingAspectDeltas) {
		return scalingAspectDeltas.stream().anyMatch(x -> x.getAspectName().equals(scaleInfo.getAspectId()));
	}

	private static NumberOfCompute handleInstantiationLevel(final String instantiationLevelId, final VnfCompute compute) {
		final Optional<VduInstantiationLevel> scaleInfo = compute.getInstantiationLevel().stream().filter(x -> x.getLevelName().equals(instantiationLevelId)).findFirst();
		if (scaleInfo.isEmpty()) {
			final Integer s = Optional.ofNullable(compute.getInitialNumberOfInstance()).orElse(0);
			return new NumberOfCompute(0, s, new ScaleInfo(instantiationLevelId, s));
		}
		final int s = scaleInfo.get().getNumberOfInstances();
		return new NumberOfCompute(0, s, new ScaleInfo(scaleInfo.get().getLevelName(), s));
	}

	private static NsScaleType getScalingType(final BlueprintParameters params) {
		if (params.getInstantiationLevelId() != null) {
			return NsScaleType.NS_SCALE_LEVEL_INST_LEVEL;
		}
		if (null != params.getAspectId()) {
			return NsScaleType.NS_SCALE_STEP;
		}
		if ((null != params.getScaleStatus()) && !params.getScaleStatus().isEmpty()) {
			return NsScaleType.NS_SCALE_LEVEL_INST_LEVEL;
		}
		throw new GenericException("Unknown scaling mode.");
	}

}
