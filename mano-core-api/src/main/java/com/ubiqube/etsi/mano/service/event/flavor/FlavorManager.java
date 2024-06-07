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
package com.ubiqube.etsi.mano.service.event.flavor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCpu;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualMemory;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.dto.Flavor;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class FlavorManager {
	private static final Logger LOG = LoggerFactory.getLogger(FlavorManager.class);
	private final VimManager vimManager;

	public FlavorManager(final VimManager vimManager) {
		this.vimManager = vimManager;
	}

	public List<VimComputeResourceFlavourEntity> getFlavors(final VimConnectionInformation vimConnectionInformation, final Set<VnfCompute> vnfCompute) {
		final Vim vim = vimManager.getVimById(vimConnectionInformation.getId());
		final boolean canCreate = vim.canCreateFlavor();
		return matchExact(vnfCompute, vimConnectionInformation, vim, canCreate);
	}

	private List<VimComputeResourceFlavourEntity> matchExact(final Set<VnfCompute> vnfCompute, final VimConnectionInformation vimConnectionInformation, final Vim vim, final boolean exactMatch) {
		final List<Flavor> flavors = vim.getFlavorList(vimConnectionInformation);
		final List<VimComputeResourceFlavourEntity> listVcrfe = new ArrayList<>();
		final Map<String, VnfCompute> cache = new HashMap<>();
		vnfCompute.forEach(x -> {
			final String flvId = findOrCreateFlavor(vimConnectionInformation, x, x.getDiskSize(), flavors, exactMatch, cache);
			final VimComputeResourceFlavourEntity vcrfe = new VimComputeResourceFlavourEntity();
			vcrfe.setVimConnectionId(vimConnectionInformation.getVimId());
			vcrfe.setResourceProviderId(vim.getType());
			vcrfe.setVimFlavourId(flvId);
			final VimComputeResourceFlavourEntity vcre = new VimComputeResourceFlavourEntity(vcrfe);
			vcre.setVnfdVirtualComputeDescId(x.getToscaName());
			listVcrfe.add(vcre);
		});
		return listVcrfe;
	}

	private String findOrCreateFlavor(final VimConnectionInformation vimConnectionInformation, final VnfCompute comp, final long disk, final List<Flavor> flavors, final boolean exactMatch, final Map<String, VnfCompute> cache) {
		final VirtualCpu vCpu = comp.getVirtualCpu();
		final VirtualMemory vMem = comp.getVirtualMemory();
		final Optional<Entry<String, VnfCompute>> str = getFlavorFromCache(cache, vCpu, vMem, disk);
		if (str.isPresent()) {
			return str.get().getKey();
		}
		final List<Flavor> basicFlavor = findByBasicAttribute(vimConnectionInformation, flavors, vCpu, vMem, comp.getDiskSize(), exactMatch);
		if (basicFlavor.isEmpty()) {
			final String flv = createFlavor(vimConnectionInformation, vCpu, vMem, comp.getToscaName(), disk);
			cache.put(flv, comp);
			LOG.info("Flavor created: {}", flv);
			return flv;
		}
		if (haveAdditionalRequirement(vCpu, vMem)) {
			final List<Flavor> flv2 = findMetaFlavors(basicFlavor, vCpu, vMem);
			if (flv2.isEmpty()) {
				final String flv = createFlavor(vimConnectionInformation, vCpu, vMem, comp.getToscaName(), disk);
				cache.put(flv, comp);
				LOG.info("Flavor created with specs: {}", flv);
				return flv;
			}
			return flv2.getFirst().getId();
		}
		return basicFlavor.getFirst().getId();
	}

	private static Optional<Entry<String, VnfCompute>> getFlavorFromCache(final Map<String, VnfCompute> cache, final VirtualCpu vCpu, final VirtualMemory vMem, final long disk) {
		return cache.entrySet().stream().filter(x -> {
			final VnfCompute vnfc = x.getValue();
			final boolean cpuEq = isEqual(vnfc.getVirtualCpu(), vCpu);
			final boolean memEq = isEqual(vnfc.getVirtualMemory(), vMem);
			final boolean diskEq = vnfc.getDiskSize() == disk;
			return cpuEq && memEq && diskEq;
		}).findFirst();
	}

	private static boolean isEqual(final VirtualMemory left, final VirtualMemory right) {
		if (!Objects.equals(left.getNumaEnabled(), right.getNumaEnabled()) || (left.getVirtualMemSize() != right.getVirtualMemSize())) {
			return false;
		}
		final Map<String, String> a = left.getVduMemRequirements();
		final Map<String, String> b = right.getVduMemRequirements();
		return compareMap(a, b);
	}

	private static boolean isEqual(final VirtualCpu left, final VirtualCpu right) {
		if (left.getNumVirtualCpu() != right.getNumVirtualCpu()) {
			return false;
		}
		final Map<String, String> a = left.getVduCpuRequirements();
		final Map<String, String> b = right.getVduCpuRequirements();
		return compareMap(a, b);
	}

	private static boolean compareMap(final @Nullable Map<String, String> aIn, final @Nullable Map<String, String> bIn) {
		final Map<String, String> a = Optional.ofNullable(aIn).orElse(Map.of());
		final Map<String, String> b = Optional.ofNullable(bIn).orElse(Map.of());
		return a.equals(b);
	}

	private String createFlavor(final VimConnectionInformation vimConnectionInformation, final VirtualCpu vCpu, final VirtualMemory vMem, final String toscaName, final long disk) {
		final Vim vim = vimManager.getVimById(vimConnectionInformation.getId());
		final Map<String, String> metadata = new HashMap<>(Optional.ofNullable(vCpu.getVduCpuRequirements()).orElse(Map.of()));
		metadata.putAll(Optional.ofNullable(vMem.getVduMemRequirements()).orElse(Map.of()));
		return vim.createFlavor(vimConnectionInformation, buildFlavorName(toscaName), vCpu.getNumVirtualCpu(), vMem.getVirtualMemSize(), disk, metadata);
	}

	private static String buildFlavorName(final String toscaName) {
		return UUID.randomUUID().toString();
	}

	private static List<Flavor> findMetaFlavors(final List<Flavor> basicFlavor, final VirtualCpu vCpu, final VirtualMemory vMem) {
		return basicFlavor.stream().filter(x -> match(x, vCpu, vMem)).toList();
	}

	private static boolean match(final Flavor flv, final VirtualCpu vCpu, final VirtualMemory vMem) {
		final Map<String, String> add = Optional.ofNullable(flv.getAdditional()).orElse(Map.of());
		final Map<String, String> cpuReq = Optional.ofNullable(vCpu.getVduCpuRequirements()).orElse(Map.of());
		final Map<String, String> memReq = Optional.ofNullable(vMem.getVduMemRequirements()).orElse(Map.of());
		final Map<String, String> allReq = new HashMap<>(cpuReq);
		allReq.putAll(memReq);
		if (add.isEmpty()) {
			return allReq.isEmpty();
		}
		return add.equals(allReq);
	}

	private static boolean haveAdditionalRequirement(final VirtualCpu vCpu, final VirtualMemory vMem) {
		final boolean vCpuReq = !Optional.ofNullable(vCpu.getVduCpuRequirements()).orElse(Map.of()).isEmpty();
		final boolean memReq = !Optional.ofNullable(vMem.getVduMemRequirements()).orElse(Map.of()).isEmpty();
		return vCpuReq || memReq;
	}

	private List<Flavor> findByBasicAttribute(final VimConnectionInformation vimConnectionInformation, final List<Flavor> flavors, final VirtualCpu vCpu, final VirtualMemory vMem, final long diskSize, final boolean exactMatch) {
		if (exactMatch) {
			return flavors.stream().filter(exactMatch(vimConnectionInformation, vCpu, vMem, diskSize)).toList();
		}
		return flavors.stream().filter(nearestMatch(vCpu, vMem, diskSize)).toList();
	}

	private static Predicate<? super Flavor> nearestMatch(final VirtualCpu vCpu, final VirtualMemory vMem, final long diskSize) {
		return x -> ((vCpu.getNumVirtualCpu() <= x.getVcpus()) || (vMem.getVirtualMemSize() <= x.getRam() /* || diskSize <= x.getDisk() */));
	}

	private Predicate<? super Flavor> exactMatch(final VimConnectionInformation vc, final VirtualCpu vCpu, final VirtualMemory vMem, final long diskSize) {
		return x -> {
			final boolean cpu = (vCpu.getNumVirtualCpu() == x.getVcpus());
			final boolean disk = diskSize == x.getDisk();
			final boolean mem = isMatching(vc, vMem.getVirtualMemSize(), x.getRam());
			return cpu && disk && mem;
		};
	}

	boolean isMatching(final VimConnectionInformation vc, final long left, final long right) {
		final Vim vim = vimManager.getVimById(vc.getId());
		return vim.isEqualMemFlavor(left, right);
	}
}
