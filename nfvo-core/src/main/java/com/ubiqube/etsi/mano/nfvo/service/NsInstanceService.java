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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVirtualLinkJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVnfPackageJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdInstanceJpa;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

import jakarta.transaction.Transactional;

@Service
public class NsInstanceService {
	private final NsVirtualLinkJpa nsVirtualLinkJpa;

	private final NsVnfPackageJpa vnfPackageJpa;

	private final NsdInstanceJpa nsdInstanceJpa;

	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	private final GrammarParser grammarParser;

	private final ManoSearch manoSearch;

	public NsInstanceService(final NsVirtualLinkJpa nsVirtualLinkJpa, final NsVnfPackageJpa vnfPackageJpa,
			final NsdInstanceJpa nsdInstanceJpa, final NsLiveInstanceJpa nsLiveInstanceJpa, final GrammarParser grammarParser,
			final ManoSearch manoSearch) {
		this.nsVirtualLinkJpa = nsVirtualLinkJpa;
		this.vnfPackageJpa = vnfPackageJpa;
		this.nsdInstanceJpa = nsdInstanceJpa;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.manoSearch = manoSearch;
		this.grammarParser = grammarParser;
	}

	public int countLiveInstanceOfVirtualLink(final NsdInstance nsInstance, final String toscaName) {
		final List<NsLiveInstance> res = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(nsInstance, toscaName, NsVirtualLinkTask.class);
		return res.size();
	}

	public int countLiveInstanceOfVnf(final NsdInstance nsInstance, final String toscaName) {
		final List<NsLiveInstance> res = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(nsInstance, toscaName, NsVnfTask.class);
		return res.stream().collect(Collectors.groupingBy(x -> x.getNsTask().getToscaName())).size();
	}

	public int countLiveInstanceOfNsd(final NsdInstance nsInstance, final String toscaName) {
		final List<NsLiveInstance> res = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(nsInstance, toscaName, NsdTask.class);
		return res.size();
	}

	public Set<NsVirtualLink> findVlsByNsInstance(final NsdPackage nsdInfo) {
		return nsVirtualLinkJpa.findByNsdPackage(nsdInfo);
	}

	public Set<VnfPackage> findVnfPackageByNsInstance(final NsdPackage nsdInfo) {
		return vnfPackageJpa.findByNsdPackages_NsdPackage_Id(nsdInfo.getId());
	}

	public NsdInstance save(final NsdInstance nsInstance) {
		return nsdInstanceJpa.save(nsInstance);
	}

	public void delete(final UUID nsInstanceUuid) {
		nsdInstanceJpa.deleteById(nsInstanceUuid);
	}

	@Transactional
	public NsdInstance findById(final UUID nsUuid) {
		final NsdInstance inst = nsdInstanceJpa.findById(nsUuid).orElseThrow(() -> new NotFoundException("NS instance Not found " + nsUuid));
		inst.setInstantiationState(isLive(nsUuid));
		return inst;
	}

	private InstantiationState isLive(final UUID nsUuid) {
		return nsLiveInstanceJpa.findByNsInstanceId(nsUuid).isEmpty() ? InstantiationState.NOT_INSTANTIATED : InstantiationState.INSTANTIATED;
	}

	public List<NsdInstance> query(final String filter) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		return manoSearch.getCriteria(nodes.getNodes(), NsdInstance.class);
	}

	public boolean isInstantiated(final NsdPackage nsPackage) {
		return 0 != nsdInstanceJpa.countByNsdInfo(nsPackage);
	}

	public Iterable<NsdInstance> findAll() {
		return nsdInstanceJpa.findAll();
	}

	public List<NsLiveInstance> findByNsInstanceId(final UUID id) {
		return nsLiveInstanceJpa.findByNsInstanceId(id);
	}

}
