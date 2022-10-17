package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3;

import java.util.List;

import javax.annotation.Priority;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.ExternalCpTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

@Service
@Priority(100)
public class VnfExtCpContributor extends AbstractVnfmContributorV3<ExternalCpTask> {

	protected VnfExtCpContributor(final VnfLiveInstanceJpa vnfInstanceJpa) {
		super(vnfInstanceJpa);
	}

	@Override
	public List<SclableResources<ExternalCpTask>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		return bundle.getVnfExtCp().stream().map(vnfExtCp -> {
			final ExternalCpTask task = createTask(ExternalCpTask::new);
			task.setToscaName(vnfExtCp.getToscaName());
			task.setChangeType(ChangeType.ADDED);
			task.setType(ResourceTypeEnum.VNF_EXTCP);
			task.setVnfExtCp(vnfExtCp);
			task.setPort(true);
			return create(VnfExtCp.class, task.getClass(), task.getToscaName(), 1, task, parameters.getInstance(), parameters);
		})
				.toList();
	}

}
