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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

import jakarta.annotation.Nullable;

/**
 *
 * @author olivier
 *
 */
@Service
public class ComputeContributor extends AbstractVnfmContributor<Object> {

	private static final Logger LOG = LoggerFactory.getLogger(ComputeContributor.class);

	private final ScalingStrategy scalingStrategy;
	private final VnfInstanceServiceVnfm vnfInstanceServiceVnfm;

	protected ComputeContributor(final VnfLiveInstanceJpa vnfInstanceJpa, final ScalingStrategy scalingStrategy, final VnfInstanceServiceVnfm vnfInstanceServiceVnfm) {
		super(vnfInstanceJpa);
		this.scalingStrategy = scalingStrategy;
		this.vnfInstanceServiceVnfm = vnfInstanceServiceVnfm;
	}

	@Override
	public List<SclableResources<Object>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		final VnfPackage vnfPackage = bundle;
		final Instance vnfInstance = vnfInstanceServiceVnfm.findById(parameters.getInstance().getId());
		final Set<ScaleInfo> scaling = merge(parameters, vnfInstance);
		final List<SclableResources<Object>> ret = new ArrayList<>();
		vnfPackage.getVnfCompute().forEach(x -> {
			final ComputeTask computeTask = createTask(ComputeTask::new);
			computeTask.setVnfCompute(x);
			computeTask.setType(ResourceTypeEnum.COMPUTE);
			computeTask.setToscaName(x.getToscaName());
			final NumberOfCompute numInst = scalingStrategy.getNumberOfCompute(parameters, vnfPackage, scaling, x, parameters.getVnfInstance());
			LOG.debug("{} -> {}", x.getToscaName(), numInst);
			ret.add(create(Compute.class, computeTask.getClass(), x.getToscaName(), numInst.getWanted(), computeTask, parameters.getInstance(), parameters));
			x.getStorages().forEach(y -> {
				final StorageTask st = createTask(StorageTask::new);
				st.setType(ResourceTypeEnum.STORAGE);
				st.setVnfStorage(findVnfStorage(vnfPackage, y));
				st.setToscaName(x.getToscaName() + "-" + y);
				ret.add(create(Storage.class, st.getClass(), st.getToscaName(), 1, st, parameters.getInstance(), parameters));
			});
			x.getPorts().forEach(y -> {
				final VnfPortTask pt = createTask(VnfPortTask::new);
				pt.setToscaName(y.getToscaName());
				pt.setChangeType(ChangeType.ADDED);
				pt.setType(ResourceTypeEnum.LINKPORT);
				pt.setVnfLinkPort(y);
				if (pt.getVnfLinkPort().getVirtualLink() == null) {
					final Optional<String> vlName = findVlName(vnfPackage, y.getToscaName());
					if (vlName.isEmpty()) {
						return;
					}
					final ExtManagedVirtualLinkDataEntity external = findExtManagedInfo(parameters, vlName.get());
					if (null == external) {
						LOG.warn("Impossible to find VL {}, will try after GrantRequest", vlName.get());
					}
					pt.setExternal(external);
				}
				ret.add(create(VnfPortNode.class, pt.getClass(), pt.getToscaName(), 1, pt, parameters.getInstance(), parameters));
			});
			x.getMonitoringParameters().forEach(y -> {
				final MonitoringTask mt = createTask(MonitoringTask::new);
				mt.setType(ResourceTypeEnum.MONITORING);
				mt.setToscaName(x.getToscaName() + "-" + y.getName());
				mt.setParentAlias(x.getToscaName());
				mt.setMonitoringParams(y);
				mt.setVnfCompute(x);
				mt.setVnfInstance(parameters.getInstance());
				ret.add(create(Monitoring.class, mt.getClass(), mt.getToscaName(), 1, mt, parameters.getInstance(), parameters));
			});
		});

		for (final com.ubiqube.etsi.mano.dao.mano.VnfIndicator vnfIndicator : bundle.getVnfIndicator()) {
			final VnfIndicatorTask vnfIndicatorTask = createTask(VnfIndicatorTask::new);
			vnfIndicatorTask.setVnfIndicator(vnfIndicator);
			vnfIndicatorTask.setType(ResourceTypeEnum.VNF_INDICATOR);
			vnfIndicatorTask.setToscaName(vnfIndicator.getName());
			vnfIndicatorTask.setName(vnfIndicator.getName());
			ret.add(create(VnfIndicator.class, vnfIndicatorTask.getClass(), vnfIndicatorTask.getName(), 1, vnfIndicatorTask, parameters.getInstance(), parameters));

//			for (final MonitoringParams monitoringParams : vnfIndicator.getMonitoringParameters()) {
//				final MonitoringTask mt = createTask(MonitoringTask::new);
//				mt.setType(ResourceTypeEnum.MONITORING);
//				mt.setToscaName(vnfIndicator.getName() + "-" + monitoringParams.getName());
//				mt.setParentAlias(vnfIndicator.getName());
//				mt.setVnfIndicator(vnfIndicator);
//				mt.setMonitoringParams(monitoringParams);
//				mt.setVnfInstance(parameters.getInstance());
//				ret.add(create(Monitoring.class, mt.getClass(), mt.getToscaName(), 1, mt, parameters.getInstance(), parameters));
//			}
		}
		return ret;
	}

	private static @Nullable ExtManagedVirtualLinkDataEntity findExtManagedInfo(final VnfBlueprint plan, final String vlName) {
		if (null == plan.getParameters().getExtManagedVirtualLinks()) {
			return null;
		}
		return plan.getParameters().getExtManagedVirtualLinks().stream()
				.filter(x -> x.getVnfVirtualLinkDescId().equals(vlName))
				.findFirst()
				.orElse(null);
	}

	private static Optional<String> findVlName(final VnfPackage vnfPackage, final String toscaName) {
		return vnfPackage.getVirtualLinks().stream()
				.filter(x -> x.getValue() != null)
				.filter(x -> x.getValue().equals(toscaName))
				.map(ComputeContributor::getVl)
				.findFirst();
	}

	private static String getVl(final ListKeyPair kp) {
		if (kp.getIdx() == 0) {
			return "virtual_link";
		}
		return "virtual_link_" + kp.getIdx();
	}

	private static VnfStorage findVnfStorage(final VnfPackage vnfPackage, final String y) {
		return vnfPackage.getVnfStorage().stream()
				.filter(x -> x.getToscaName().equals(y))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Unable to find storage: [" + y + "]"));
	}

	private static Set<ScaleInfo> merge(final Blueprint plan, final Instance vnfInstance) {
		final Set<ScaleInfo> tmp = vnfInstance.getInstantiatedVnfInfo().getScaleStatus().stream()
				.filter(x -> notIn(x.getAspectId(), plan.getParameters().getScaleStatus()))
				.map(x -> new ScaleInfo(x.getAspectId(), x.getScaleLevel()))
				.collect(Collectors.toSet());
		tmp.addAll(plan.getParameters().getScaleStatus());
		return tmp;
	}

	private static boolean notIn(final String aspectId, final Set<? extends ScaleInfo> scaleInfos) {
		return scaleInfos.stream().noneMatch(x -> x.getAspectId().equals(aspectId));
	}

}
