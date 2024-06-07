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

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.vim.AbstractUnitOfWork;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Extract VNF instance information and put them inside the WF context.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfContextExtractorUow extends AbstractUnitOfWork<NsVnfExtractorTask> {
	private static final Pattern pVl = Pattern.compile("virtual_link_(?<idx>\\d+)");
	@Nonnull
	private final VnfmInterface vnfm;
	@Nonnull
	private final NsdPackage pack;
	@Nonnull
	private final NsVnfExtractorTask task;

	public VnfContextExtractorUow(final VirtualTaskV3<NsVnfExtractorTask> task, final VnfmInterface vnfm, final NsdPackage pack) {
		super(task, VnfExtractorNode.class);
		this.vnfm = vnfm;
		this.pack = pack;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final String vnfInstanceId = context.get(VnfCreateNode.class, task.getToscaName());
		final VnfInstance inst = vnfm.getVnfInstance(task.getServer(), vnfInstanceId);
		Optional.ofNullable(inst.getInstantiatedVnfInfo())
				.map(BlueprintParameters::getExtCpInfo)
				.ifPresent(z -> z.forEach(x -> {
					final NsdPackageVnfPackage vnfd = findVnfd(inst.getVnfdId());
					final int idx = toscaNameToVlId(x.getCpdId());
					// virtual_link(_x) -> forwardName
					final ListKeyPair vl = vnfd.getVirtualLinks().stream().filter(y -> y.getIdx() == idx).findFirst().orElseThrow(() -> new GenericException("unable to find index " + idx));
					// forwad to VL
					final ExtVirtualLinkDataEntity extVl = findExtVl(inst.getInstantiatedVnfInfo().getExtVirtualLinkInfo(), x.getAssociatedVnfVirtualLinkId());
					context.add(VnfPortNode.class, vl.getValue(), extVl.getResourceId());
				}));
		return null;
	}

	private static ExtVirtualLinkDataEntity findExtVl(final Set<ExtVirtualLinkDataEntity> extVirtualLinkInfo, final String associatedVnfVirtualLinkId) {
		return extVirtualLinkInfo.stream()
				.filter(x -> x.getId().toString().equals(associatedVnfVirtualLinkId))
				.findFirst()
				.orElseThrow(() -> new GenericException("Unable to find " + associatedVnfVirtualLinkId));
	}

	private String forwardToVl(final String net) {
		return pack.getVnffgs().stream()
				.flatMap(x -> x.getNfpd().stream())
				.flatMap(x -> x.getInstances().stream())
				.flatMap(x -> x.getPairs().stream())
				.map(x -> {
					if (x.getEgress().equals(net)) {
						return x.getEgressVl();
					}
					if (x.getIngress().equals(net)) {
						return x.getIngressVl();
					}
					return null;
				})
				.filter(Objects::nonNull)
				.findFirst()
				.orElseThrow(() -> new GenericException("Unable to find VL " + net));
	}

	private static int toscaNameToVlId(final String cpdId) {
		if ("virtual_link".equals(cpdId)) {
			return 0;
		}
		final Matcher m = pVl.matcher(cpdId);
		if (!m.matches()) {
			throw new GenericException("Unable to match 'virtual_link_' in " + cpdId);
		}
		return Integer.parseInt(m.group("idx"));
	}

	private NsdPackageVnfPackage findVnfd(final String vnfdId) {
		return pack.getVnfPkgIds().stream()
				.filter(x -> x.getVnfPackage().getVnfdId().equals(vnfdId)).findFirst()
				.orElseThrow(() -> new GenericException("Unable to find vnfd id " + vnfdId));
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		// Nothing.
		return null;
	}

}
