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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ExtCpInfo;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfExtractorVt;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;

@ExtendWith(MockitoExtension.class)
class VnfContextExtractorUowTest {
	@Mock
	private VnfmInterface vnfmInterface;

	@Test
	void testBasic() throws Exception {
		final NsVnfExtractorTask rTask = new NsVnfExtractorTask();
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(rTask);
		final NsdPackage nsPkg = new NsdPackage();
		final VnfInstance vnfInstance = new VnfInstance();
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final VnfContextExtractorUow srv = new VnfContextExtractorUow(task, vnfmInterface, nsPkg);
		final Context3d ctx = new TestContext3d();
		srv.execute(ctx);
		assertTrue(true);
	}

	@Test
	void testFailVlNotFound() throws Exception {
		final UUID id = UUID.randomUUID();
		final UUID cpdId = UUID.randomUUID();
		final NsVnfExtractorTask rTask = new NsVnfExtractorTask();
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(rTask);
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageVnfPackage nsPkgVnf01 = new NsdPackageVnfPackage();
		nsPkgVnf01.setVirtualLinks(Set.of());
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		nsPkgVnf01.setVnfPackage(vnfPkg01);
		nsPkg.setVnfPkgIds(Set.of(nsPkgVnf01));
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters instInfo = new BlueprintParameters();
		final ExtCpInfo ext01 = new ExtCpInfo();
		ext01.setCpdId("virtual_link");
		instInfo.setExtCpInfo(Set.of(ext01));
		vnfInstance.setInstantiatedVnfInfo(instInfo);
		vnfInstance.setVnfdId(id.toString());
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final VnfContextExtractorUow srv = new VnfContextExtractorUow(task, vnfmInterface, nsPkg);
		final Context3d ctx = new TestContext3d();
		assertThrows(GenericException.class, () -> srv.execute(ctx));
	}

	@Test
	void testFailExtVl() throws Exception {
		final UUID id = UUID.randomUUID();
		final UUID cpdId = UUID.randomUUID();
		final NsVnfExtractorTask rTask = new NsVnfExtractorTask();
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(rTask);
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageVnfPackage nsPkgVnf01 = new NsdPackageVnfPackage();
		final ListKeyPair vl01 = new ListKeyPair();
		nsPkgVnf01.setVirtualLinks(Set.of(vl01));
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		nsPkgVnf01.setVnfPackage(vnfPkg01);
		nsPkg.setVnfPkgIds(Set.of(nsPkgVnf01));
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters instInfo = new BlueprintParameters();
		final ExtCpInfo ext01 = new ExtCpInfo();
		ext01.setCpdId("virtual_link");
		final ExtVirtualLinkDataEntity extVl01 = new ExtVirtualLinkDataEntity();
		extVl01.setId(cpdId);
		instInfo.setExtVirtualLinkInfo(Set.of(extVl01));
		instInfo.setExtCpInfo(Set.of(ext01));
		vnfInstance.setInstantiatedVnfInfo(instInfo);
		vnfInstance.setVnfdId(id.toString());
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final VnfContextExtractorUow srv = new VnfContextExtractorUow(task, vnfmInterface, nsPkg);
		final Context3d ctx = new TestContext3d();
		assertThrows(GenericException.class, () -> srv.execute(ctx));
	}

	@Test
	void testOk() throws Exception {
		final UUID id = UUID.randomUUID();
		final UUID cpdId = UUID.randomUUID();
		final NsVnfExtractorTask rTask = new NsVnfExtractorTask();
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(rTask);
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageVnfPackage nsPkgVnf01 = new NsdPackageVnfPackage();
		final ListKeyPair vl01 = new ListKeyPair();
		nsPkgVnf01.setVirtualLinks(Set.of(vl01));
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		nsPkgVnf01.setVnfPackage(vnfPkg01);
		nsPkg.setVnfPkgIds(Set.of(nsPkgVnf01));
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters instInfo = new BlueprintParameters();
		final ExtCpInfo ext01 = new ExtCpInfo();
		ext01.setAssociatedVnfVirtualLinkId(cpdId.toString());
		ext01.setCpdId("virtual_link");
		final ExtVirtualLinkDataEntity extVl01 = new ExtVirtualLinkDataEntity();
		extVl01.setId(cpdId);
		instInfo.setExtVirtualLinkInfo(Set.of(extVl01));
		instInfo.setExtCpInfo(Set.of(ext01));
		vnfInstance.setInstantiatedVnfInfo(instInfo);
		vnfInstance.setVnfdId(id.toString());
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final VnfContextExtractorUow srv = new VnfContextExtractorUow(task, vnfmInterface, nsPkg);
		final Context3d ctx = new TestContext3d();
		srv.execute(ctx);
		assertTrue(true);
	}

	@Test
	void testOk001() throws Exception {
		final UUID id = UUID.randomUUID();
		final UUID cpdId = UUID.randomUUID();
		final NsVnfExtractorTask rTask = new NsVnfExtractorTask();
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(rTask);
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageVnfPackage nsPkgVnf01 = new NsdPackageVnfPackage();
		final ListKeyPair vl01 = new ListKeyPair();
		vl01.setIdx(1);
		nsPkgVnf01.setVirtualLinks(Set.of(vl01));
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		nsPkgVnf01.setVnfPackage(vnfPkg01);
		nsPkg.setVnfPkgIds(Set.of(nsPkgVnf01));
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters instInfo = new BlueprintParameters();
		final ExtCpInfo ext01 = new ExtCpInfo();
		ext01.setAssociatedVnfVirtualLinkId(cpdId.toString());
		ext01.setCpdId("virtual_link_1");
		final ExtVirtualLinkDataEntity extVl01 = new ExtVirtualLinkDataEntity();
		extVl01.setId(cpdId);
		instInfo.setExtVirtualLinkInfo(Set.of(extVl01));
		instInfo.setExtCpInfo(Set.of(ext01));
		vnfInstance.setInstantiatedVnfInfo(instInfo);
		vnfInstance.setVnfdId(id.toString());
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final VnfContextExtractorUow srv = new VnfContextExtractorUow(task, vnfmInterface, nsPkg);
		final Context3d ctx = new TestContext3d();
		srv.execute(ctx);
		assertTrue(true);
	}

	@Test
	void testBadVl() throws Exception {
		final UUID id = UUID.randomUUID();
		final UUID cpdId = UUID.randomUUID();
		final NsVnfExtractorTask rTask = new NsVnfExtractorTask();
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(rTask);
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageVnfPackage nsPkgVnf01 = new NsdPackageVnfPackage();
		final ListKeyPair vl01 = new ListKeyPair();
		vl01.setIdx(1);
		nsPkgVnf01.setVirtualLinks(Set.of(vl01));
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		nsPkgVnf01.setVnfPackage(vnfPkg01);
		nsPkg.setVnfPkgIds(Set.of(nsPkgVnf01));
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters instInfo = new BlueprintParameters();
		final ExtCpInfo ext01 = new ExtCpInfo();
		ext01.setAssociatedVnfVirtualLinkId(cpdId.toString());
		ext01.setCpdId("vl_1");
		final ExtVirtualLinkDataEntity extVl01 = new ExtVirtualLinkDataEntity();
		extVl01.setId(cpdId);
		instInfo.setExtVirtualLinkInfo(Set.of(extVl01));
		instInfo.setExtCpInfo(Set.of(ext01));
		vnfInstance.setInstantiatedVnfInfo(instInfo);
		vnfInstance.setVnfdId(id.toString());
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final VnfContextExtractorUow srv = new VnfContextExtractorUow(task, vnfmInterface, nsPkg);
		final Context3d ctx = new TestContext3d();
		assertThrows(GenericException.class, () -> srv.execute(ctx));
	}

	@Test
	void testRollback() throws Exception {
		final NsVnfExtractorTask rTask = new NsVnfExtractorTask();
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(rTask);
		final NsdPackage nsPkg = new NsdPackage();
		final VnfInstance vnfInstance = new VnfInstance();
		final VnfContextExtractorUow srv = new VnfContextExtractorUow(task, vnfmInterface, nsPkg);
		final Context3d ctx = new TestContext3d();
		srv.rollback(ctx);
		assertTrue(true);
	}
}
