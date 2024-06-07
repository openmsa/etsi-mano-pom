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
package com.ubiqube.etsi.mano.service.pkg.vnf.visitor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ScalingAspect;
import com.ubiqube.etsi.mano.dao.mano.VduInstantiationLevel;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfComputeAspectDelta;
import com.ubiqube.etsi.mano.dao.mano.VnfInstantiationLevels;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardVisitor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VisitorUtils;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VduLevel;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.InstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInitialDelta;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduScalingAspectDeltas;

import jakarta.annotation.Priority;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Priority(100)
@Service
public class ScalingVisitor implements OnboardVisitor {

	@Override
	public void visit(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final Map<String, String> userData) {
		final Set<ScalingAspect> scalingAspects = vnfPackageReader.getScalingAspects(vnfPackage.getUserDefinedData());
		final List<InstantiationLevels> instantiationLevels = vnfPackageReader.getInstatiationLevels(vnfPackage.getUserDefinedData());
		final List<VduInstantiationLevels> vduInstantiationLevel = vnfPackageReader.getVduInstantiationLevels(vnfPackage.getUserDefinedData());
		final List<VduInitialDelta> vduInitialDeltas = vnfPackageReader.getVduInitialDelta(vnfPackage.getUserDefinedData());
		final List<VduScalingAspectDeltas> vduScalingAspectDeltas = vnfPackageReader.getVduScalingAspectDeltas(vnfPackage.getUserDefinedData());
		rebuildVduScalingAspects(vnfPackage, instantiationLevels, vduInstantiationLevel, vduInitialDeltas, vduScalingAspectDeltas, scalingAspects);
	}

	@SuppressWarnings("boxing")
	private static void rebuildVduScalingAspects(final VnfPackage vnfPackage, final List<InstantiationLevels> instantiationLevels, final List<VduInstantiationLevels> vduInstantiationLevels,
			final List<VduInitialDelta> vduInitialDeltas, final List<VduScalingAspectDeltas> vduScalingAspectDeltas, final Set<ScalingAspect> scalingAspects) {
		// flattern the instantiation levels. levels(demo,premium) -> ScaleInfo(name,
		// scaleLevel)
		instantiationLevels.stream()
				.forEach(x -> {
					vnfPackage.setDefaultInstantiationLevel(x.getDefaultLevel());
					x.getLevels()
                            .forEach((levelId, value1) -> value1.getScaleInfo()
                                    .forEach((aspectId, value) -> {
                                        final VnfInstantiationLevels il =
                                                new VnfInstantiationLevels(levelId, aspectId,
                                                        value.getScaleLevel());
                                        vnfPackage.addInstantiationLevel(il);
                                    }));
				});
		vduInstantiationLevels.forEach(x -> {
			final Set<VduInstantiationLevel> ils = x.getLevels().entrySet().stream().map(y -> {
				final VduInstantiationLevel vduInstantiationLevel = new VduInstantiationLevel();
				vduInstantiationLevel.setLevelName(y.getKey());
				vduInstantiationLevel.setNumberOfInstances(y.getValue().getNumberOfInstances());
				return vduInstantiationLevel;
			}).collect(Collectors.toSet());

			x.getTargets().forEach(y -> {
				final VnfCompute vnfCompute = VisitorUtils.findVnfCompute(vnfPackage, y);
				ils.forEach(z -> z.setVnfCompute(vnfCompute));
				vnfCompute.setInstantiationLevel(ils);
			});
		});
		vduScalingAspectDeltas.forEach(x -> x.getTargets().forEach(y -> {
			final VnfCompute vnfc = VisitorUtils.findVnfCompute(vnfPackage, y);
			final VduInitialDelta init = findVduInitialDelta(vduInitialDeltas, y);
			int level = 1;
			int numInst = init.getInitialDelta().getNumberOfInstances();
			final ScalingAspect aspect = scalingAspects.stream().filter(z -> z.getName().equals(x.getAspect())).findFirst().orElse(new ScalingAspect());
			vnfc.addScalingAspectDeltas(new VnfComputeAspectDelta(x.getAspect(), "initial_delta", init.getInitialDelta().getNumberOfInstances(), 0, aspect.getMaxScaleLevel(), y, numInst));
			if (null == aspect.getStepDeltas()) {
				return;
			}
			// XXX Unreachable code.
			// Missing 0 => initial inst level.
			for (final String delta : aspect.getStepDeltas()) {
				final VduLevel step = x.getDeltas().get(delta);
				numInst += step.getNumberOfInstances();
				vnfc.addScalingAspectDeltas(new VnfComputeAspectDelta(x.getAspect(), delta, step.getNumberOfInstances(), level, aspect.getMaxScaleLevel(), y, numInst));
				level++;
			}
		}));
		// Minimal instance at instantiate time.
		vduInitialDeltas.forEach(x -> x.getTargets().forEach(y -> {
			final VnfCompute vnfc = VisitorUtils.findVnfCompute(vnfPackage, y);
			vnfc.setInitialNumberOfInstance(x.getInitialDelta().getNumberOfInstances());
		}));
	}

	private static VduInitialDelta findVduInitialDelta(final List<VduInitialDelta> vduInitialDeltas, final String y) {
		return vduInitialDeltas.stream().filter(x -> x.getTargets().contains(y)).findFirst().orElseThrow(() -> new GenericException("Could not find initial level for vdu " + y));
	}

}
