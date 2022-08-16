/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.nfvo.service.graph.nfvo.VnfCreateUow;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsVnfCreateSystem extends AbstractVimSystemV3<NsVnfTask> {
	private final VnfmInterface vnfm;

	public NsVnfCreateSystem(final VnfmInterface vnfm, final VimManager vimManager) {
		super(vimManager);
		this.vnfm = vnfm;
	}

	@Override
	protected SystemBuilder getImplementation(final OrchestrationServiceV3<NsVnfTask> orchestrationService, final VirtualTaskV3<NsVnfTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		/**
		 * final NsVnfInstantiateTask nt = new NsVnfInstantiateTask(); final NsVnfTask p
		 * = virtualTask.getTemplateParameters(); //
		 * nt.setVnfPackage(p.getNsPackageVnfPackage());
		 * nt.setChangeType(p.getChangeType());
		 * nt.setExternalNetworks(p.getExternalNetworks());
		 * nt.setFlavourId(p.getFlavourId());
		 * nt.setInstantiationLevelId(p.getInstantiationLevelId());
		 * nt.setLocalizationLanguage(p.getLocalizationLanguage());
		 * nt.setServer(p.getServer()); if (p.getChangeType() == ChangeType.REMOVED) {
		 * nt.setVimResourceId(p.getVimResourceId()); } nt.setToscaName("inst-" +
		 * p.getAlias()); nt.setAlias("inst-" + p.getAlias());
		 * nt.setVnfInstanceName(virtualTask.getAlias()); nt.setType(p.getType());
		 * nt.setVlName(p.getVlInstances()); if (p.getChangeType() !=
		 * ChangeType.REMOVED) {
		 * nt.setVirtualLinks(p.getNsPackageVnfPackage().getVirtualLinks()); }
		 *
		 * // final VnfInstantiateUow instantiateUow = new VnfInstantiateUow(new //
		 * NsInstantiateVt(nt), vnfm); // s.add(new VnfCreateUow(virtualTask, vnfm),
		 * instantiateUow); // final VnfContextExtractorTask contextTask = new
		 * VnfContextExtractorTask(); // final NsdPackage pack = //
		 * nsdPackageJpa.findById(UUID.fromString(p.getNsdId())).orElseThrow(() -> new
		 * // GenericException("Unable to find package [" + p.getNsdId() + "]")); /*
		 * contextTask.setToscaName("extract-" + p.getAlias());
		 * contextTask.setAlias("extract-" + p.getAlias());
		 * contextTask.setVnfdId(p.getVnfdId()); contextTask.setNsdPackage(pack);
		 * contextTask.setServer(p.getServer());
		 * contextTask.setVnfInstanceName(nt.getAlias());
		 */
		// s.add(instantiateUow, new VnfContextExtractorUow(new
		// VnfContextExtractorVt(contextTask), vnfm, pack));
		return orchestrationService.systemBuilderOf(new VnfCreateUow(virtualTask, vnfm));
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

}
