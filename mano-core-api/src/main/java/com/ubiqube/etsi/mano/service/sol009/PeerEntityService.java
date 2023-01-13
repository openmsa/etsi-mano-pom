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
package com.ubiqube.etsi.mano.service.sol009;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntity;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntityComponent;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntityManoApplicationState;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoService;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.NfvoSpecificInfo;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ResoruceMgmtModeSupportEnum;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.SupportedPackageFormats;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.VnfdFormatEnum;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.VnfmSpecificInfo;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.AdministrativeState;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.PeerEntityEnum;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.NfvoService;

@Service
public class PeerEntityService {
	private final Optional<NfvoService> nfvoService;

	public PeerEntityService(final Optional<NfvoService> nfvoService) {
		this.nfvoService = nfvoService;
	}

	public ManoEntity getMe() {
		final ManoEntity me = new ManoEntity();
		// me.setCismSpecificInfo(null)
		me.setDescription(null);
		final ManoEntityManoApplicationState state = new ManoEntityManoApplicationState();
		state.setAdministrativeState(AdministrativeState.UNLOCKED);
		state.setOperationalState(OperationalStateType.STARTED);
		state.setUsageState(UsageStateEnum.IN_USE);
		me.setManoApplicationState(state);
		me.setManoConfigurableParams(null);
		final ManoEntityComponent comp = new ManoEntityComponent();
		comp.setId(null);
		comp.setManoServiceIds(List.of());
		final List<ManoEntityComponent> comps = List.of(comp);
		me.setManoEntityComponents(comps);

		final ManoService srv = new ManoService();
		srv.setDescription("");
		srv.setId(null);
		srv.setManoServiceInterfaceIds(Set.of(""));
		srv.setName("");
		final List<ManoService> srvs = List.of(srv);
		me.setManoServices(srvs);
		me.setName("");

		final List<SupportedPackageFormats> supNsd = extractAndSetPackageFormat();
		me.setProvider("vnfm-ubiqube");
		me.setSoftwareInfo(Map.of());
		me.setSoftwareVersion("0.0.1-SNAPSHOT");
		if (nfvoService.isPresent()) {
			setNfvoSpecific(me, supNsd);
		} else {
			setVnfmSpecific(me, supNsd);
		}
		// me.setVimSpecificInfo();
		return me;
	}

	private static void setVnfmSpecific(final ManoEntity me, final List<SupportedPackageFormats> supNsd) {
		final VnfmSpecificInfo vnfm = new VnfmSpecificInfo();
		vnfm.setManagedVnfInstanceInfos(List.of(""));
		vnfm.setResoruceMgmtModeSupport(ResoruceMgmtModeSupportEnum.BOTH);
		vnfm.setSupportedVnfdFormats(supNsd);
		me.setVnfmSpecificInfo(vnfm);
		me.setType(PeerEntityEnum.VNFM);
	}

	private static void setNfvoSpecific(final ManoEntity me, final List<SupportedPackageFormats> supNsd) {
		me.setType(PeerEntityEnum.NFVO);
		final NfvoSpecificInfo nfvo = new NfvoSpecificInfo();
		nfvo.setId(null);
		nfvo.setMaxOnboardedNsdNum(Integer.MAX_VALUE);
		nfvo.setMaxOnboardedVnfPkgNum(Integer.MAX_VALUE);
		nfvo.setSupportedNsdFormats(supNsd);
		nfvo.setSupportedVnfdFormats(supNsd.stream().collect(Collectors.toSet()));
		me.setNfvoSpecificInfo(nfvo);
	}

	private List<SupportedPackageFormats> extractAndSetPackageFormat() {
		final List<SupportedPackageFormats> ret = new ArrayList<>();
		final PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
		try {
			final Resource[] res = pmrpr.getResources("classpath:tosca-class-*");
			for (final Resource resource : res) {
				try (final URLClassLoader urlLoader = URLClassLoader.newInstance(new URL[] { resource.getURL() }, this.getClass().getClassLoader());
						final InputStream stream = urlLoader.getResourceAsStream("META-INF/tosca-resources.properties")) {
					final Properties props = new Properties();

					props.load(stream);
					final SupportedPackageFormats spf = new SupportedPackageFormats();
					spf.setStandardVersion(props.getProperty("version"));
					spf.setVnfdFormat(VnfdFormatEnum.TOSCA);
					ret.add(spf);
				}
			}
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ret;
	}
}
