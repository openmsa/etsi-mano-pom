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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfInstantiateVt;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;

@ExtendWith(MockitoExtension.class)
class VnfInstantiateUowTest {
	@Mock
	private VnfmInterface vnfmInterface;

	@Test
	void testOk() throws Exception {
		final VnfBlueprint vnfBlueprint = new VnfBlueprint();
		vnfBlueprint.setId(UUID.randomUUID());
		vnfBlueprint.setOperationStatus(OperationStatusType.COMPLETED);
		when(vnfmInterface.vnfLcmOpOccsGet(any(), any())).thenReturn(vnfBlueprint);
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		final NsdVnfPackageCopy param = new NsdVnfPackageCopy();
		param.setForwardMapping(Set.of());
		task.setParam(param);
		final VirtualTaskV3<NsVnfInstantiateTask> vTask = new NsVnfInstantiateVt(task);
		final VnfInstantiateUow srv = new VnfInstantiateUow(vTask, vnfmInterface);
		final Context3d context = new TestContext3d();
		when(vnfmInterface.vnfInstatiate(any(), any(), any())).thenReturn(vnfBlueprint);
		srv.execute(context);
		assertTrue(true);
	}

	@Test
	void testFail() throws Exception {
		final VnfBlueprint vnfBlueprint = new VnfBlueprint();
		vnfBlueprint.setId(UUID.randomUUID());
		vnfBlueprint.setOperationStatus(OperationStatusType.FAILED);
		when(vnfmInterface.vnfLcmOpOccsGet(any(), any())).thenReturn(vnfBlueprint);
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		final NsdVnfPackageCopy param = new NsdVnfPackageCopy();
		param.setForwardMapping(Set.of());
		task.setParam(param);
		final VirtualTaskV3<NsVnfInstantiateTask> vTask = new NsVnfInstantiateVt(task);
		final VnfInstantiateUow srv = new VnfInstantiateUow(vTask, vnfmInterface);
		final Context3d context = new TestContext3d();
		when(vnfmInterface.vnfInstatiate(any(), any(), any())).thenReturn(vnfBlueprint);
		assertThrows(GenericException.class, () -> srv.execute(context));
	}

	@Test
	void testWithVl() throws Exception {
		final VnfBlueprint vnfBlueprint = new VnfBlueprint();
		vnfBlueprint.setId(UUID.randomUUID());
		vnfBlueprint.setOperationStatus(OperationStatusType.COMPLETED);
		when(vnfmInterface.vnfLcmOpOccsGet(any(), any())).thenReturn(vnfBlueprint);
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		final Servers srv = Servers.builder().id(UUID.randomUUID()).url(URI.create("http://localhost/")).build();
		task.setServer(srv);
		final NsdVnfPackageCopy param = new NsdVnfPackageCopy();
		final ForwarderMapping fw01 = new ForwarderMapping();
		param.setForwardMapping(Set.of(fw01));
		task.setParam(param);
		final VirtualTaskV3<NsVnfInstantiateTask> vTask = new NsVnfInstantiateVt(task);
		final VnfInstantiateUow instantiateUow = new VnfInstantiateUow(vTask, vnfmInterface);
		final Context3d context = new TestContext3d();
		when(vnfmInterface.vnfInstatiate(any(), any(), any())).thenReturn(vnfBlueprint);
		instantiateUow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		final VirtualTaskV3<NsVnfInstantiateTask> vTask = new NsVnfInstantiateVt(task);
		final VnfInstantiateUow instantiateUow = new VnfInstantiateUow(vTask, vnfmInterface);
		final Context3d ctx = null;
		final VnfInstance vnfInstance = new VnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final String res = instantiateUow.rollback(ctx);
		assertNull(res);
	}

	@Test
	void testRollbackNotFound() {
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		final VirtualTaskV3<NsVnfInstantiateTask> vTask = new NsVnfInstantiateVt(task);
		final VnfInstantiateUow instantiateUow = new VnfInstantiateUow(vTask, vnfmInterface);
		final Context3d ctx = null;
		when(vnfmInterface.getVnfInstance(any(), any())).thenThrow(WebClientResponseException.NotFound.class);
		final String res = instantiateUow.rollback(ctx);
		assertNull(res);
	}

	/**
	 * Don't know what is this condition : When calling Terminate the return is
	 * null.
	 */
	@Test
	void testRollbackInstantiated() {
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		final VirtualTaskV3<NsVnfInstantiateTask> vTask = new NsVnfInstantiateVt(task);
		final VnfInstantiateUow instantiateUow = new VnfInstantiateUow(vTask, vnfmInterface);
		final Context3d ctx = null;
		final VnfInstance vnfInstance = new VnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		final String res = instantiateUow.rollback(ctx);
		assertNull(res);
	}

	@Test
	void testRollbackInstantiatedThenTerminate() {
		final VnfBlueprint vnfBlueprint = new VnfBlueprint();
		vnfBlueprint.setId(UUID.randomUUID());
		vnfBlueprint.setOperationStatus(OperationStatusType.COMPLETED);
		when(vnfmInterface.vnfLcmOpOccsGet(any(), any())).thenReturn(vnfBlueprint);
		final NsVnfInstantiateTask task = new NsVnfInstantiateTask();
		final VirtualTaskV3<NsVnfInstantiateTask> vTask = new NsVnfInstantiateVt(task);
		final VnfInstantiateUow instantiateUow = new VnfInstantiateUow(vTask, vnfmInterface);
		final Context3d ctx = null;
		final VnfInstance vnfInstance = new VnfInstance();
		vnfInstance.setInstantiationState(InstantiationState.INSTANTIATED);
		when(vnfmInterface.getVnfInstance(any(), any())).thenReturn(vnfInstance);
		when(vnfmInterface.vnfTerminate(any(), any())).thenReturn(vnfBlueprint);
		final String res = instantiateUow.rollback(ctx);
		assertNotNull(res);
	}

}
