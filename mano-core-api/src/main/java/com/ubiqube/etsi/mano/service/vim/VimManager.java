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
package com.ubiqube.etsi.mano.service.vim;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.dao.mano.common.GeoPoint;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.VimCapability;
import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.CnfServerJpa;
import com.ubiqube.etsi.mano.jpa.VimConnectionInformationJpa;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;
import com.ubiqube.etsi.mano.service.SystemService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.vim.dto.SwImage;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VimManager {

	private static final Logger LOG = LoggerFactory.getLogger(VimManager.class);

	private final List<Vim> vims;

	private final VimConnectionInformationJpa vimConnectionInformationJpa;

	private final SystemService systemService;

	private final CnfServerJpa cnfServerJpa;

	private final VrQanJpa vrQanJpa;

	private final EventManager em;

	private final ManoSearch manoSearch;

	private final CnfInformationsMapping cnfMapper;

	private final VimTypeConverter vimTypeConverter;

	public VimManager(final List<Vim> vims, final VimConnectionInformationJpa vimConnectionInformationJpa, final SystemService systemService,
			final CnfServerJpa cnfServerJpa, final VrQanJpa vrQanJpa, final EventManager em, final ManoSearch manoSearch, final CnfInformationsMapping cnfMapper, final VimTypeConverter vimTypeConverter) {
		this.vims = vims;
		this.vimConnectionInformationJpa = vimConnectionInformationJpa;
		this.systemService = systemService;
		this.cnfServerJpa = cnfServerJpa;
		this.vrQanJpa = vrQanJpa;
		this.em = em;
		this.manoSearch = manoSearch;
		this.cnfMapper = cnfMapper;
		this.vimTypeConverter = vimTypeConverter;
		init();
	}

	private Map<UUID, Vim> init() {
		final Map<UUID, Vim> vimAssociation = new HashMap<>();
		vims.forEach(x -> {
			final Set<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> vimsId = vimConnectionInformationJpa.findByVimType(x.getType());
			associateVims(vimsId, x, vimAssociation);
		});
		return vimAssociation;
	}

	private static void associateVims(final Set<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> vimsIs, final Vim vim, final Map<UUID, Vim> vimAssociation) {
		vimsIs.forEach(x -> vimAssociation.put(x.getId(), vim));
	}

	public Vim getVimById(final UUID id) {
		final Map<UUID, Vim> vimAssociation = init();
		return Optional.ofNullable(vimAssociation.get(id)).orElseThrow(() -> new NotFoundException("No such Vim: " + id));
	}

	public void rebuildCache() {
		init();
	}

	public VimConnectionInformation findVimById(final UUID id) {
		return vimConnectionInformationJpa.findById(id).orElseThrow(() -> new NotFoundException("No connection Id " + id));
	}

	public VimConnectionInformation findVimByVimId(final String id) {
		return vimConnectionInformationJpa.findByVimId(id).orElseThrow(() -> new NotFoundException("No connection vimId " + id));
	}

	public Set<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> getVimByType(final String type) {
		return vimConnectionInformationJpa.findByVimType(type);
	}

	public VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> save(final VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> x) {
		return vimConnectionInformationJpa.save(x);
	}

	public Optional<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> findOptionalVimByVimId(final String vimId) {
		return vimConnectionInformationJpa.findByVimId(vimId);
	}

	public Iterable<VimConnectionInformation> findAllVimconnections() {
		return vimConnectionInformationJpa.findAll();
	}

	public void getVimByDistance(final GeoPoint point) {
		manoSearch.getByDistance(VimConnectionInformation.class, point.getLat(), point.getLng());
	}

	@Transactional(TxType.REQUIRED)
	public VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo> registerIfNeeded(final VimConnectionInformation x) {
		synchronized (VimManager.class) {
			final Optional<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> vim = vimConnectionInformationJpa.findByVimId(x.getVimId());
			if (vim.isPresent()) {
				return vim.get();
			}
			vimTypeConverter.setToInternalType(x);
			return registerVim(x);
		}
	}

	@Transactional
	public void deleteVim(final UUID id) {
		vimConnectionInformationJpa.deleteById(id);
		systemService.deleteByVimOrigin(id);
		rebuildCache();
	}

	@Transactional
	public VimConnectionInformation register(final VimConnectionInformation vci) {
		vimTypeConverter.setToInternalType(vci);
		checkUniqueness(vci);
		checkVimConnectivity(vci);
		registerVim(vci);
		return callVrqan(vci);
	}

	private void checkUniqueness(final VimConnectionInformation vci) {
		vimConnectionInformationJpa.findByVimId(vci.getVimId()).ifPresent(x -> {
			throw new GenericException("Vim " + x.getVimId() + " is already present.");
		});
	}

	private void checkVimConnectivity(final VimConnectionInformation vci) {
		final Vim vim = findVim(vci);
		vim.authenticate(vci);
	}

	private Vim findVim(final VimConnectionInformation vci) {
		return vims.stream()
				.filter(x -> x.getType().equals(vci.getVimType())).findFirst()
				.orElseThrow(() -> new GenericException("Could not find vim [" + vci.getVimType() + "]"));
	}

	private VimConnectionInformation registerVim(final VimConnectionInformation vci) {
		if (vimTypeConverter.isVim(vci)) {
			extractCapabilities(vci);
		}
		mergeCnf(vci);
		vci.getAccessInfo().setId(null);
		vci.getInterfaceInfo().setId(null);
		final VimConnectionInformation n = vimConnectionInformationJpa.save(vci);
		systemService.registerVim(n);
		init();
		return n;
	}

	private void mergeCnf(final VimConnectionInformation vci) {
		final Optional<CnfServer> cnfServer = cnfServerJpa.findById(UUID.fromString(vci.getVimId()));
		if (cnfServer.isEmpty()) {
			LOG.info("No CNF information for vim: {}", vci.getVimId());
			return;
		}
		final CnfInformations cnfi = cnfServer.get().getInfo();
		final CnfInformations cni = cnfMapper.map(cnfi);
		vci.setCnfInfo(cni);
	}

	private void extractCapabilities(final VimConnectionInformation vci) {
		final Vim vim = findVim(vci);
		final List<VimCapability> caps = vim.getCaps(vci);
		vci.setVimCapabilities(caps.stream().collect(Collectors.toSet()));
	}

	public VimConnectionInformation refresh(final UUID id) {
		final VimConnectionInformation vci = vimConnectionInformationJpa.findById(id).orElseThrow(() -> new GenericException("Unable to find vim " + id));
		extractCapabilities(vci);
		return vimConnectionInformationJpa.save(vci);
	}

	public List<SoftwareImage> getDetailedImageList(final VimConnectionInformation vimConn) {
		final Vim vim = findVim(vimConn);
		@Nonnull
		final Storage storage = vim.storage(vimConn);
		final List<SwImage> preList = storage.getImageList();
		return preList.stream().map(x -> mapper(storage, x)).toList();
	}

	private static SoftwareImage mapper(final Storage storage, final SwImage x) {
		return storage.getImageDetail(x.getVimResourceId());
	}

	@Transactional
	public VimConnectionInformation callVrqan(final VimConnectionInformation vci) {

		try {
			final Optional<VrQan> ovrqan = vrQanJpa.findByVimId(vci.getId());
			final VrQan vrqan = ovrqan.orElseGet(() -> {
				final VrQan vq = new VrQan(vci.getId());
				return vrQanJpa.save(vq);
			});

			final Vim vim = getVimById(vci.getId());
			final ResourceQuota pr = vim.getQuota(vci);
			final VrQan diff = VimUtils.compare(pr, vrqan);
			if (diff.haveValue()) {
				LOG.info("Send notification for vim: {} with diff {}", vci.getId(), diff);
				VimUtils.copy(pr, vrqan);
				vrqan.setLastChange(ZonedDateTime.now());
				vrqan.setLastCheck(ZonedDateTime.now());
				vrQanJpa.save(vrqan);
				em.sendNotification(NotificationEvent.VRQAN, vci.getId(), Map.of());
			} else {
				vrqan.setLastCheck(ZonedDateTime.now());
				vrQanJpa.save(vrqan);
			}
		} catch (final RuntimeException e) {
			LOG.error("", e);
		}
		return vci;
	}
}
