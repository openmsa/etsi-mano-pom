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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;

@ExtendWith(MockitoExtension.class)
class NsdVnfContributorV3Test {
	@Mock
	private NsLiveInstanceJpa nsLiveJpa;
	@Mock
	private NsScaleStrategyV3 nsScaleStrategy;
	@Mock
	private ServersJpa serversJpa;
	@Mock
	private VnfPackageService vnfPackageService;

	@Test
	void testName() throws Exception {
		final NsdVnfContributorV3 nvc = new NsdVnfContributorV3(nsLiveJpa, nsScaleStrategy, serversJpa, vnfPackageService);
		final NsdPackage bundle = new NsdPackage();
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInst = new NsdInstance();
		nsInst.setVnfPkgIds(Set.of());
		blueprint.setNsInstance(nsInst);
		final List<SclableResources<Object>> res = nvc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void testOne() throws Exception {
		final UUID id = UUID.randomUUID();
		final NsdVnfContributorV3 nvc = new NsdVnfContributorV3(nsLiveJpa, nsScaleStrategy, serversJpa, vnfPackageService);
		final NsdPackage bundle = new NsdPackage();
		bundle.setId(UUID.randomUUID());
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInst = new NsdInstance();
		final NsdVnfPackageCopy pkg01 = new NsdVnfPackageCopy();
		pkg01.setToscaName("pkg01");
		pkg01.setVirtualLinks(Set.of());
		pkg01.setVnfdId(id.toString());
		nsInst.setVnfPkgIds(Set.of(pkg01));
		blueprint.setNsInstance(nsInst);
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		final Optional<VnfPackage> pkgOpt = Optional.of(vnfPkg01);
		when(vnfPackageService.findByVnfdIdOpt(anyString())).thenReturn(pkgOpt);
		final List<SclableResources<Object>> res = nvc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(3, res.size());
		TestUtils.assertNameNotNull(res);
	}

	@Test
	void testServer_No_VnfmInfo() throws Exception {
		final UUID id = UUID.randomUUID();
		final NsdVnfContributorV3 nvc = new NsdVnfContributorV3(nsLiveJpa, nsScaleStrategy, serversJpa, vnfPackageService);
		final NsdPackage bundle = new NsdPackage();
		bundle.setId(UUID.randomUUID());
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInst = new NsdInstance();
		final NsdVnfPackageCopy pkg01 = new NsdVnfPackageCopy();
		pkg01.setToscaName("pkg01");
		pkg01.setVirtualLinks(Set.of());
		pkg01.setVnfdId(id.toString());
		nsInst.setVnfPkgIds(Set.of(pkg01));
		blueprint.setNsInstance(nsInst);
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		final Optional<VnfPackage> pkgOpt = Optional.of(vnfPkg01);
		when(vnfPackageService.findByVnfdIdOpt(anyString())).thenReturn(pkgOpt);
		//
		vnfPkg01.setVnfmInfo(Set.of());
		final Servers srv01 = Servers.builder().url(URI.create("http://localhost/")).build();
		when(serversJpa.findByServerTypeAndServerStatusIn(eq(ServerType.VNFM), any())).thenReturn(List.of(srv01));
		final List<SclableResources<Object>> res = nvc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(3, res.size());
		TestUtils.assertNameNotNull(res);
	}

	@Test
	void testServer_With_VnfmInfo_Not_Found() throws Exception {
		final UUID id = UUID.randomUUID();
		final NsdVnfContributorV3 nvc = new NsdVnfContributorV3(nsLiveJpa, nsScaleStrategy, serversJpa, vnfPackageService);
		final NsdPackage bundle = new NsdPackage();
		bundle.setId(UUID.randomUUID());
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInst = new NsdInstance();
		final NsdVnfPackageCopy pkg01 = new NsdVnfPackageCopy();
		pkg01.setToscaName("pkg01");
		pkg01.setVirtualLinks(Set.of());
		pkg01.setVnfdId(id.toString());
		nsInst.setVnfPkgIds(Set.of(pkg01));
		blueprint.setNsInstance(nsInst);
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		final Optional<VnfPackage> pkgOpt = Optional.of(vnfPkg01);
		when(vnfPackageService.findByVnfdIdOpt(anyString())).thenReturn(pkgOpt);
		//
		vnfPkg01.setVnfmInfo(Set.of("caps01"));
		final Servers srv01 = Servers.builder()
				.capabilities(Set.of())
				.url(URI.create("http://localhost/"))
				.build();
		when(serversJpa.findByServerTypeAndServerStatusIn(eq(ServerType.VNFM), any())).thenReturn(List.of(srv01));
		assertThrows(GenericException.class, () -> nvc.contribute(bundle, blueprint));
	}

	@Test
	void testServer_With_VnfmInfo_Found() throws Exception {
		final UUID id = UUID.randomUUID();
		final NsdVnfContributorV3 nvc = new NsdVnfContributorV3(nsLiveJpa, nsScaleStrategy, serversJpa, vnfPackageService);
		final NsdPackage bundle = new NsdPackage();
		bundle.setId(UUID.randomUUID());
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInst = new NsdInstance();
		final NsdVnfPackageCopy pkg01 = new NsdVnfPackageCopy();
		pkg01.setToscaName("pkg01");
		pkg01.setVirtualLinks(Set.of());
		pkg01.setVnfdId(id.toString());
		nsInst.setVnfPkgIds(Set.of(pkg01));
		blueprint.setNsInstance(nsInst);
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		final Optional<VnfPackage> pkgOpt = Optional.of(vnfPkg01);
		when(vnfPackageService.findByVnfdIdOpt(anyString())).thenReturn(pkgOpt);
		//
		vnfPkg01.setVnfmInfo(Set.of("caps01"));
		final Servers srv01 = Servers.builder()
				.capabilities(Set.of("caps01"))
				.url(URI.create("http://localhost/"))
				.build();
		when(serversJpa.findByServerTypeAndServerStatusIn(eq(ServerType.VNFM), any())).thenReturn(List.of(srv01));
		final List<SclableResources<Object>> res = nvc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(3, res.size());
		TestUtils.assertNameNotNull(res);
	}

	@Test
	void testVlRebuild() throws Exception {
		final UUID id = UUID.randomUUID();
		final NsdVnfContributorV3 nvc = new NsdVnfContributorV3(nsLiveJpa, nsScaleStrategy, serversJpa, vnfPackageService);
		final NsdPackage bundle = new NsdPackage();
		bundle.setId(UUID.randomUUID());
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdInstance nsInst = new NsdInstance();
		final NsdVnfPackageCopy pkg01 = new NsdVnfPackageCopy();
		final ForwarderMapping fm01 = new ForwarderMapping();
		fm01.setForwardingName("fm01");
		pkg01.setForwardMapping(Set.of(fm01));
		pkg01.setToscaName("pkg01");
		final ListKeyPair vl01 = new ListKeyPair();
		pkg01.setVirtualLinks(Set.of(vl01));
		pkg01.setVnfdId(id.toString());
		nsInst.setVnfPkgIds(Set.of(pkg01));
		blueprint.setNsInstance(nsInst);
		final VnfPackage vnfPkg01 = new VnfPackage();
		vnfPkg01.setVnfdId(id.toString());
		final Optional<VnfPackage> pkgOpt = Optional.of(vnfPkg01);
		when(vnfPackageService.findByVnfdIdOpt(anyString())).thenReturn(pkgOpt);
		final List<SclableResources<Object>> res = nvc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(3, res.size());
		TestUtils.assertNameNotNull(res);
	}
}
