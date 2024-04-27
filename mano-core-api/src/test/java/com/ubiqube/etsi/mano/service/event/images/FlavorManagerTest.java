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
package com.ubiqube.etsi.mano.service.event.images;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCpu;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualMemory;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.event.flavor.FlavorManager;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.dto.Flavor;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("static-method")
class FlavorManagerTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private Vim vim;

	private List<Flavor> createFlavorList() {
		// id: 1, disk: 5, cpu: 2, ram: 500_000_000
		final Flavor f1 = createFlavor1();
		// id: 2, disk: 15, cpu: 4, ram: 2_000_000_000
		final Flavor f2 = createFlavor2();
		// id: 22, disk: 15, cpu: 4, ram: 2_000_000_000, a: b
		final Flavor f22 = createFlavor22();
		return List.of(f1, f2, f22);
	}

	/**
	 * Create empty Compute.
	 *
	 * @return
	 */
	private VnfCompute createEmptyCompute() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		vnf.setVirtualMemory(virtualMemory);
		return vnf;
	}

	/**
	 * Match 22 expect on disk.
	 *
	 * @return
	 */
	private VnfCompute createCompute22DontMatchDisk() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(4);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVduMemRequirements(Map.of("a", "b"));
		virtualMemory.setVirtualMemSize(2_000_000_000);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(5);
		return vnf;
	}

	/**
	 * Match 22 expect on disk.
	 *
	 * @return
	 */
	private VnfCompute createCompute22DontMatchCpu() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(2);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVduMemRequirements(Map.of("a", "b"));
		virtualMemory.setVirtualMemSize(2_000_000_000);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);
		return vnf;
	}

	/**
	 * Match flavor 22.
	 *
	 * @return
	 */
	private VnfCompute createCompute22() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(4);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVduMemRequirements(Map.of("a", "b"));
		virtualMemory.setVirtualMemSize(2_000_000_000);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);
		return vnf;
	}

	private VnfCompute createCompute22MetadataMissmatch() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(4);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVduMemRequirements(Map.of("a", "c"));
		virtualMemory.setVirtualMemSize(2_000_000_000);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);
		return vnf;
	}

	private VnfCompute createCompute2NotRam() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(4);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVirtualMemSize(16);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);
		return vnf;
	}

	private VnfCompute createCompute2() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(4);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVirtualMemSize(2_000_000_000);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);
		return vnf;
	}

	private VnfCompute createCompute2WithMetadata() {
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(4);
		virtualCpu.setVduCpuRequirements(Map.of("z", "z"));
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVirtualMemSize(2_000_000_000);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);
		return vnf;
	}

	private Flavor createFlavor22() {
		final Flavor f22 = new Flavor();
		f22.setId("22");
		f22.setDisk(15);
		f22.setRam(2_000_000_000);
		f22.setVcpus(4);
		f22.setName("f22");
		f22.setAdditional(Map.of("a", "b"));
		return f22;
	}

	private Flavor createFlavor2() {
		final Flavor f2 = new Flavor();
		f2.setId("2");
		f2.setDisk(15);
		f2.setRam(2_000_000_000);
		f2.setVcpus(4);
		f2.setName("f2");
		return f2;
	}

	private Flavor createFlavor1() {
		final Flavor f1 = new Flavor();
		f1.setId("1");
		f1.setDisk(5);
		f1.setRam(500_000_000);
		f1.setVcpus(2);
		f1.setName("f1");
		return f1;
	}

	private FlavorManager getFlavorList() {
		final FlavorManager fm = new FlavorManager(vimManager);
		when(vimManager.getVimById(any())).thenReturn(vim);
		when(vim.getFlavorList(any())).thenReturn(createFlavorList());
		return fm;
	}

	@Test
	void testFlavorNoExact() throws Exception {
		final FlavorManager fm = getFlavorList();
		final VimConnectionInformation vimConnectionInformation = new VimConnectionInformation();
		vimConnectionInformation.setId(UUID.randomUUID());
		final VnfCompute vnf = createEmptyCompute();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		assertEquals("1", flv.get(0).getVimFlavourId());
	}

	@Test
	void testFlavorNoMetadata() throws Exception {
		final FlavorManager fm = getFlavorList();
		final VimConnectionInformation vimConnectionInformation = new VimConnectionInformation();
		vimConnectionInformation.setId(UUID.randomUUID());
		final VnfCompute vnf = createCompute2();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		assertEquals("2", flv.get(0).getVimFlavourId());
	}

	@Test
	void testFlavorCreate() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		when(vim.createFlavor(any(), any(), anyLong(), anyLong(), anyLong(), any())).thenReturn("3");
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = createEmptyCompute();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		assertEquals("3", flv.get(0).getVimFlavourId());
	}

	@Test
	void testFlavorNotRam() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = createCompute2NotRam();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		// Comparison are impossible.
		assertEquals(null, flv.get(0).getVimFlavourId());
	}

	@Test
	void testFlavorNotCpuMetadata() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = createCompute2WithMetadata();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		// Comparison are impossible.
		assertEquals(null, flv.get(0).getVimFlavourId());
	}

	@Test
	void testFlavorMatchF22WithExtAttr() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = createCompute22();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		when(vim.isEqualMemFlavor(2_000_000_000L, 500_000_000L)).thenReturn(false);
		when(vim.isEqualMemFlavor(2_000_000_000L, 2_000_000_000L)).thenReturn(true);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		assertEquals("22", flv.get(0).getVimFlavourId());
	}

	/**
	 * Disk change don't match 22
	 *
	 * @throws Exception
	 */
	@Test
	void testFlavorDontMatchF22() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = createCompute22DontMatchDisk();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		assertEquals(null, flv.get(0).getVimFlavourId());
	}

	/**
	 * Match flavor 22 with extended attributes.
	 *
	 * @throws Exception
	 */
	@Test
	void testFlavorMatchF2WithExtendedAttrMetadataMissmatch() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = createCompute22MetadataMissmatch();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		when(vim.isEqualMemFlavor(2_000_000_000, 2_000_000_000)).thenReturn(true);
		when(vim.isEqualMemFlavor(2_000_000_000L, 500_000_000L)).thenReturn(false);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		assertEquals(null, flv.get(0).getVimFlavourId());
	}

	/**
	 * Match flavor 22 with extended attributes.
	 *
	 * @throws Exception
	 */
	@Test
	void testFlavorMatchF2WithExtendedAttr() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = createCompute22();
		final Set<VnfCompute> vnfCompute = Set.of(vnf);
		when(vim.isEqualMemFlavor(2_000_000_000, 2_000_000_000)).thenReturn(true);
		when(vim.isEqualMemFlavor(2_000_000_000L, 500_000_000L)).thenReturn(false);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(1, flv.size());
		assertEquals("22", flv.get(0).getVimFlavourId());
	}

	/**
	 * Select 2 compute using cache for the second one.
	 *
	 * @throws Exception
	 */
	@Test
	void testFlavorCacheTest() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		when(vim.createFlavor(any(), any(), anyLong(), anyLong(), anyLong(), any())).thenReturn("3", "4");
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(64);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVduMemRequirements(Map.of("a", "b"));
		virtualMemory.setVirtualMemSize(17);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);
		final VnfCompute vnf2 = new VnfCompute();
		vnf2.setVirtualCpu(virtualCpu);
		vnf2.setVirtualMemory(virtualMemory);
		vnf2.setDiskSize(15);
		final Set<VnfCompute> vnfCompute = Set.of(vnf, vnf2);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(2, flv.size());
		assertEquals("3", flv.get(0).getVimFlavourId());
		assertEquals("3", flv.get(1).getVimFlavourId());
	}

	@Test
	void testFlavorMakeSureCacheAdditionalTest() throws Exception {
		final FlavorManager fm = getFlavorList();
		when(vim.canCreateFlavor()).thenReturn(true);
		when(vim.createFlavor(any(), any(), anyLong(), anyLong(), anyLong(), any())).thenReturn("3", "4");
		final VimConnectionInformation vimConnectionInformation = createVimConnection();
		final VnfCompute vnf = new VnfCompute();
		final VirtualCpu virtualCpu = new VirtualCpu();
		virtualCpu.setNumVirtualCpu(64);
		vnf.setVirtualCpu(virtualCpu);
		final VirtualMemory virtualMemory = new VirtualMemory();
		virtualMemory.setVduMemRequirements(Map.of("a", "b"));
		virtualMemory.setVirtualMemSize(17);
		vnf.setVirtualMemory(virtualMemory);
		vnf.setDiskSize(15);

		final VnfCompute vnf2 = new VnfCompute();
		final VirtualMemory virtualMemory2 = new VirtualMemory();
		virtualMemory2.setVduMemRequirements(Map.of("a", "F"));
		virtualMemory2.setVirtualMemSize(17);
		vnf2.setVirtualCpu(virtualCpu);
		vnf2.setVirtualMemory(virtualMemory2);
		vnf2.setDiskSize(15);
		final Set<VnfCompute> vnfCompute = Set.of(vnf, vnf2);
		final List<VimComputeResourceFlavourEntity> flv = fm.getFlavors(vimConnectionInformation, vnfCompute);
		assertEquals(2, flv.size());
		assertEquals("3", flv.get(0).getVimFlavourId());
		assertEquals("4", flv.get(1).getVimFlavourId());
	}

	private VimConnectionInformation createVimConnection() {
		final VimConnectionInformation vimConnectionInformation = new VimConnectionInformation();
		vimConnectionInformation.setId(UUID.randomUUID());
		return vimConnectionInformation;
	}

}
