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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgLoadbalancerTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;

public final class NsdFactory {
	private NsdFactory() {
		// Nothing.
	}

	public static NsdPackage createPackage() {
		final NsdPackage pkg = new NsdPackage();
		final Set<NsdPackageVnfPackage> vnfPkgs = new LinkedHashSet<>();
		vnfPkgs.add(createNsdPackageVnfPackage01());
		pkg.setVnfPkgIds(vnfPkgs);
		pkg.setVnffgs(createVnffg());
		return pkg;
	}

	private static Set<VnffgDescriptor> createVnffg() {
		final Set<VnffgDescriptor> ret = new LinkedHashSet<>();
		final VnffgDescriptor descriptor = new VnffgDescriptor();
		descriptor.setName("nfpd");
		final List<NfpDescriptor> nfpd = new ArrayList<>();
		nfpd.add(createNfpfd());
		descriptor.setNfpd(nfpd);
		ret.add(descriptor);
		return ret;
	}

	private static NfpDescriptor createNfpfd() {
		final NfpDescriptor ret = new NfpDescriptor();
		final List<VnffgInstance> instances = createInstances();
		ret.setInstances(instances);
		ret.setToscaName("nfp_position_1");
		return ret;
	}

	private static List<VnffgInstance> createInstances() {
		final List<VnffgInstance> ret = new ArrayList<>();
		final VnffgInstance vnffgInstance = new VnffgInstance();
		vnffgInstance.setToscaName(null);
		final List<CpPair> pairs = createPairs();
		vnffgInstance.setPairs(pairs);
		ret.add(vnffgInstance);
		return ret;
	}

	private static List<CpPair> createPairs() {
		final List<CpPair> ret = new ArrayList<>();
		final CpPair cp01 = new CpPair();
		cp01.setToscaName("element_1");
		cp01.setVnf("vnf01");
		ret.add(cp01);
		return ret;
	}

	private static NsdPackageVnfPackage createNsdPackageVnfPackage01() {
		final NsdPackageVnfPackage pkg = new NsdPackageVnfPackage();
		pkg.setToscaName("vnf01");
		return pkg;
	}

	static NsTask createVnf01() {
		final NsVnfTask task = new NsVnfTask();
		task.setChangeType(ChangeType.ADDED);
		task.setToscaName("vnf01");
		task.setAlias("vnf01-0001");
		task.setType(ResourceTypeEnum.VNF_CREATE);
		return task;
	}

	public static NsTask createPortPair() {
		final VnffgPortPairTask task = new VnffgPortPairTask();
		task.setChangeType(ChangeType.ADDED);
		task.setToscaName("element_1");
		task.setAlias("element_1-0001");
		task.setType(ResourceTypeEnum.VNFFG_PORT_PAIR);
		return task;
	}

	public static NsdInstance createNsInstance() {
		final NsdInstance inst = new NsdInstance();
		inst.setId(UUID.randomUUID());
		return inst;
	}

	public static NsTask createLoadbalancer() {
		final VnffgLoadbalancerTask ret = new VnffgLoadbalancerTask();
		ret.setToscaName("nfp_position_1");
		ret.setAlias("nfp_position_1");
		return ret;
	}

}
