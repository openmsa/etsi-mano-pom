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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.ExternalManagedVirtualLink;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.vim.AbstractUnitOfWork;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfInstantiateUow extends AbstractUnitOfWork<NsVnfInstantiateTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfInstantiateUow.class);
	@Nonnull
	private final VnfmInterface vnfm;
	@Nonnull
	private final NsVnfInstantiateTask task;
	@Nonnull
	private final BiFunction<Servers, UUID, VnfBlueprint> func;

	public VnfInstantiateUow(final VirtualTaskV3<NsVnfInstantiateTask> task, final VnfmInterface vnfm) {
		super(task, VnfInstantiateNode.class);
		this.task = task.getTemplateParameters();
		this.vnfm = vnfm;
		func = vnfm::vnfLcmOpOccsGet;
	}

	@Override
	@Nullable
	public String execute(final Context3d context) {
		final String inst = context.get(VnfCreateNode.class, task.getVnfInstanceName());
		final List<ExternalManagedVirtualLink> net = task.getParam().getForwardMapping().stream().map(x -> {
			final String resource = context.get(Network.class, x.getVlName());
			return createExmvl(x, resource);
		})
				.filter(Objects::nonNull)
				.toList();
		final VnfInstantiate request = createRequest();
		request.setExtManagedVirtualLinks(net);
		final VnfBlueprint res = vnfm.vnfInstatiate(task.getServer(), inst, request);
		final VnfBlueprint result = UowUtils.waitLcmCompletion(res, func, task.getServer());
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			final String details = Optional.ofNullable(result.getError()).map(FailureDetails::getDetail).orElse("[No content]");
			throw new GenericException("VNF LCM Failed: " + details + " With state:  " + result.getOperationStatus());
		}
		return inst;
	}

	private ExternalManagedVirtualLink createExmvl(final ForwarderMapping x, final String vimResource) {
		final ExternalManagedVirtualLink ext = new ExternalManagedVirtualLink();
		ext.setId(UUID.randomUUID());
		ext.setResourceId(vimResource);
		ext.setExtManagedVirtualLinkId(x.getVirtualLinkPort());
		ext.setResourceProviderId("ETSI-MANO-VNFM");
		ext.setVnfVirtualLinkDescId(x.getVirtualLinkPort());
		ext.setVimId(task.getServer().getId().toString());
		return ext;
	}

	private VnfInstantiate createRequest() {
		final VnfInstantiate inst = new VnfInstantiate();
		// inst.setExtManagedVirtualLinks(task.getExternalNetworks())
		// inst.setExtVirtualLinks(extVirtualLinks)
		inst.setFlavourId(task.getFlavourId());
		inst.setInstantiationLevelId(task.getInstantiationLevelId());
		inst.setLocalizationLanguage(task.getLocalizationLanguage());
		// inst.setVimConnectionInfo(task.getVimConnectionInfo())
		return inst;
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		try {
			final VnfInstance inst = vnfm.getVnfInstance(task.getServer(), task.getVimResourceId());
			if (inst.getInstantiationState() == InstantiationState.NOT_INSTANTIATED) {
				return null;
			}
		} catch (final WebClientResponseException.NotFound e) {
			LOG.trace("Not found exception, ignoring.", e);
			return null;
		}
		final VnfBlueprint lcm = vnfm.vnfTerminate(task.getServer(), task.getVimResourceId());
		if (lcm == null) {
			return null;
		}
		final VnfBlueprint result = UowUtils.waitLcmCompletion(lcm, func, task.getServer());
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			throw new GenericException("VNF LCM Failed: " + result.getError().getDetail());
		}
		return result.getId().toString();
	}
}
