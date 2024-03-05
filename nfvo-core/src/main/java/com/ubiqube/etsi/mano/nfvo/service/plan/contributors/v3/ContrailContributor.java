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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PortTupleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PtLinkNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceTemplateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NetworkPolicyNode;
import com.ubiqube.etsi.mano.tf.entities.NetworkPolicyTask;
import com.ubiqube.etsi.mano.tf.entities.PortTupleTask;
import com.ubiqube.etsi.mano.tf.entities.PtLinkTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceInstanceTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceTemplateTask;

@Service
public class ContrailContributor extends AbstractNsdContributorV3<Object> {

	protected ContrailContributor(final NsLiveInstanceJpa nsLiveInstanceJpa) {
		super(nsLiveInstanceJpa);
	}

	@Override
	public List<SclableResources<Object>> contribute(final NsdPackage bundle, final NsBlueprint parameters) {
		final UUID instanceId = parameters.getInstance().getId();
		final List<SclableResources<Object>> ret = new ArrayList<>();
		bundle.getVnffgs().stream().forEach(x -> {
			final ServiceTemplateTask st = createTask(ServiceTemplateTask::new);
			st.setType(ResourceTypeEnum.TF_SERVICE_TEMPLATE);
			st.setToscaName(x.getName());
			st.setInstanceId(instanceId);
			ret.add(create(ServiceTemplateNode.class, st.getClass(), x.getName(), 1, st, parameters.getInstance(), parameters));
			x.getNfpd().stream().flatMap(y -> y.getInstances().stream()).forEach(y -> {
				final ServiceInstanceTask siTask = createTask(ServiceInstanceTask::new);
				siTask.setType(ResourceTypeEnum.TF_SERVICE_INSTANCE);
				siTask.setToscaName(y.getToscaName());
				siTask.setServiceTemplateId(st.getToscaName());
				siTask.setInstanceId(instanceId);
				final CpPair cp = y.getPairs().get(0);
				siTask.setCpPorts(cp);
				ret.add(create(ServiceInstanceNode.class, siTask.getClass(), siTask.getToscaName(), 1, siTask, parameters.getInstance(), parameters));
				y.getPairs().forEach(z -> {
					final PortTupleTask portTuple = createTask(PortTupleTask::new);
					portTuple.setType(ResourceTypeEnum.TF_PORT_TUPLE);
					portTuple.setToscaName(z.getToscaName());
					portTuple.setServiceInstanceName(siTask.getToscaName());
					portTuple.setInstanceId(instanceId);
					ret.add(create(PortTupleNode.class, portTuple.getClass(), z.getToscaName(), 1, portTuple, parameters.getInstance(), parameters));
					final PtLinkTask ptLinkTask = createTask(PtLinkTask::new);
					ptLinkTask.setType(ResourceTypeEnum.TF_PT_LINK);
					ptLinkTask.setToscaName(z.getToscaName());
					ptLinkTask.setLeftPortId(z.getIngress());
					ptLinkTask.setRightPortId(z.getEgress());
					ptLinkTask.setPortTupleName(z.getToscaName());
					ptLinkTask.setInstanceId(instanceId);
					ret.add(create(PtLinkNode.class, ptLinkTask.getClass(), ptLinkTask.getToscaName(), 1, ptLinkTask, parameters.getInstance(), parameters));
				});
				//
				final NetworkPolicyTask npt = createTask(NetworkPolicyTask::new);
				npt.setType(ResourceTypeEnum.TF_NETWORK_POLICY);
				npt.setClassifier(x.getClassifier());
				npt.setLeftId(cp.getIngressVl());
				npt.setRightId(cp.getEgressVl());
				npt.setInstanceId(instanceId);
				npt.setToscaName(y.getToscaName());
				ret.add(create(NetworkPolicyNode.class, npt.getClass(), npt.getToscaName(), 1, npt, parameters.getInstance(), parameters));
			});
		});
		return ret;
	}

}
