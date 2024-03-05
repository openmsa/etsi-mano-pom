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
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import java.util.UUID;
import java.util.function.BiFunction;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.AbstractUnitOfWork;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class NsUow extends AbstractUnitOfWork<NsdTask> {

	private final NsdTask nsdTask;

	private final VnfInstanceLcm nsLcmOpOccsService;

	private final BiFunction<Servers, UUID, VnfBlueprint> func;

	public NsUow(final VirtualTaskV3<NsdTask> task, final VnfInstanceLcm nsLcmOpOccsService) {
		super(task, NsdInstantiateNode.class);
		this.nsdTask = task.getTemplateParameters();
		this.nsLcmOpOccsService = nsLcmOpOccsService;
		func = nsLcmOpOccsService::vnfLcmOpOccsGet;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final VnfInstantiate instantiateRequest = createInstantiateRequest();
		instantiateRequest.setFlavourId(nsdTask.getFlavourId());
		instantiateRequest.setInstantiationLevelId(nsdTask.getInstantiationLevelId());
		instantiateRequest.setLocalizationLanguage(nsdTask.getLocalizationLanguage());
		instantiateRequest.setVimConnectionInfo(nsdTask.getVimConnectionInformations().stream().toList());
		final VnfBlueprint lcm = nsLcmOpOccsService.instantiate(nsdTask.getServer(), nsdTask.getNsInstanceId(), instantiateRequest);
		final VnfBlueprint result = UowUtils.waitLcmCompletion(lcm, func, nsdTask.getServer());
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			throw new GenericException("NSD LCM Failed: " + result.getError().getDetail());
		}
		return lcm.getId().toString();
	}

	private VnfInstantiate createInstantiateRequest() {
		final VnfInstantiate inst = new VnfInstantiate();
		// inst.setExtManagedVirtualLinks(nsdTask.getExtCps())
		// inst.setExtVirtualLinks(nsdTask.getExtCps())
		inst.setFlavourId(nsdTask.getFlavourId());
		inst.setInstantiationLevelId(nsdTask.getInstantiationLevelId());
		inst.setLocalizationLanguage(nsdTask.getLocalizationLanguage());
		inst.setVimConnectionInfo(nsdTask.getVimConnectionInformations().stream().toList());
		return inst;
	}

	@Override
	@Nullable
	public String rollback(final Context3d context) {
		final VnfBlueprint lcm = nsLcmOpOccsService.terminate(nsdTask.getServer(), nsdTask.getNsInstanceId(), null, 0);
		final VnfBlueprint result = UowUtils.waitLcmCompletion(lcm, func, nsdTask.getServer());
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			throw new GenericException("NSD LCM Failed: " + result.getError().getDetail());
		}
		return lcm.getId().toString();
	}
}
