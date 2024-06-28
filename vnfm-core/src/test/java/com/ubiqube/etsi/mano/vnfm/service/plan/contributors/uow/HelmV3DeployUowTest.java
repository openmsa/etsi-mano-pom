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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.HelmVt;

@ExtendWith(MockitoExtension.class)
class HelmV3DeployUowTest {
	@Mock
	private K8sClient client;
	@Mock
	private K8sServerInfoJpa serverInfoJpa;
	@Mock
	private VnfPackageRepository vnfRepo;
	@Mock
	private Context3d context;
	@Mock
	private ManoResource resource;

	@Test
	void test() {
		final HelmTask nt = new HelmTask();
		final McIops mciop = new McIops();
		final SoftwareImage si = new SoftwareImage();
		si.setImagePath("tmp");
		mciop.setArtifacts(Map.of("img", si));
		nt.setMciop(mciop);
		final VirtualTaskV3<HelmTask> vt = new HelmVt(nt);
		assertNotNull(vt.getType());
		final Servers servers = new Servers();
		VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> vci = createVci();
		final HelmV3DeployUow uow = new HelmV3DeployUow(vt, client, vci, vnfRepo, servers);
		when(vnfRepo.getBinary(any(), any())).thenReturn(resource);
		when(resource.getInputStream()).thenReturn(InputStream.nullInputStream());
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testNotFound() throws IOException {
		final HelmTask nt = new HelmTask();
		final McIops mciop = new McIops();
		final SoftwareImage si = new SoftwareImage();
		si.setImagePath("tmp");
		mciop.setArtifacts(Map.of("img", si));
		nt.setMciop(mciop);
		final VirtualTaskV3<HelmTask> vt = new HelmVt(nt);
		final Servers servers = new Servers();
		VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> vci = createVci();
		final HelmV3DeployUow uow = new HelmV3DeployUow(vt, client, vci, vnfRepo, servers);
		when(vnfRepo.getBinary(any(), any())).thenReturn(resource);
		InputStream isMock = Mockito.mock(InputStream.class);
		when(resource.getInputStream()).thenReturn(isMock);
		when(isMock.transferTo(any())).thenThrow(IOException.class);
		assertThrows(GenericException.class, () -> uow.execute(context));
		assertTrue(true);
	}

	private VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> createVci() {
		VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> vci = new VimConnectionInformation<>();
		vci.setAccessInfo(new KubernetesV1Auth());
		vci.getAccessInfo().setClientCertificateData("YmFzZTY0Cg==");
		vci.getAccessInfo().setClientKeyData("YmFzZTY0Cg==");
		vci.setInterfaceInfo(new K8sInterfaceInfo());
		vci.getInterfaceInfo().setCertificateAuthorityData("YmFzZTY0Cg==");
		return vci;
	}

	@Test
	void testTransfertError() throws IOException {
		final HelmTask nt = new HelmTask();
		final McIops mciop = new McIops();
		final SoftwareImage si = new SoftwareImage();
		si.setImagePath("tmp");
		mciop.setArtifacts(Map.of("img", si));
		nt.setMciop(mciop);
		final VirtualTaskV3<HelmTask> vt = new HelmVt(nt);
		final Servers servers = new Servers();
		VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> vci = createVci();
		final HelmV3DeployUow uow = new HelmV3DeployUow(vt, client, vci, vnfRepo, servers);
		when(vnfRepo.getBinary(any(), any())).thenReturn(resource);
		final InputStream inputStream = Mockito.mock(InputStream.class);
		when(inputStream.transferTo(any())).thenThrow(IOException.class);
		when(resource.getInputStream()).thenReturn(inputStream);
		assertThrows(GenericException.class, () -> uow.execute(context));
	}

	@Test
	void testRollback() {
		final HelmTask nt = new HelmTask();
		final VirtualTaskV3<HelmTask> vt = new HelmVt(nt);
		final Servers servers = new Servers();
		VimConnectionInformation<K8sInterfaceInfo, KubernetesV1Auth> vci = new VimConnectionInformation<>();
		final HelmV3DeployUow uow = new HelmV3DeployUow(vt, client, vci, vnfRepo, servers);
		uow.rollback(context);
		assertTrue(true);
	}
}
