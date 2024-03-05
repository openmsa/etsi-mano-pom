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
package com.ubiqube.etsi.mano.nfvo.service.event;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.vim.ResourceQuota;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.VimUtils;

import jakarta.annotation.PreDestroy;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VrQanService {

	private static final Logger LOG = LoggerFactory.getLogger(VrQanService.class);

	private final VimManager vimManager;
	private final ExecutorService es;
	private final VrQanJpa vrQanJpa;
	private final EventManager em;

	public VrQanService(final VimManager vimManager, final VrQanJpa vrQanJpa, final EventManager em) {
		this.vimManager = vimManager;
		this.vrQanJpa = vrQanJpa;
		this.em = em;
		this.es = Executors.newFixedThreadPool(5);
	}

	@Scheduled(fixedDelay = 60_000)
	public void run() {
		final Iterable<VimConnectionInformation> l = vimManager.findAllVimconnections();
		StreamSupport.stream(l.spliterator(), false).forEach(x -> es.submit(() -> vrQanUpdate(x)));
	}

	public void vrQanUpdate(final VimConnectionInformation vimConnection) {
		try {
			final Optional<VrQan> ovrqan = vrQanJpa.findByVimId(vimConnection.getId());
			final VrQan vrqan = ovrqan.orElseGet(() -> {
				final VrQan vq = new VrQan(vimConnection.getId());
				return vrQanJpa.save(vq);
			});
			final Vim vim = vimManager.getVimById(vimConnection.getId());
			final ResourceQuota pr = vim.getQuota(vimConnection);
			final VrQan diff = VimUtils.compare(pr, vrqan);
			if (diff.haveValue()) {
				LOG.info("Send notification for vim: {} with diff {}", vimConnection.getId(), diff);
				VimUtils.copy(pr, vrqan);
				vrqan.setLastChange(ZonedDateTime.now());
				vrqan.setLastCheck(ZonedDateTime.now());
				vrQanJpa.save(vrqan);
				em.sendNotification(NotificationEvent.VRQAN, vimConnection.getId(), Map.of());
			} else {
				vrqan.setLastCheck(ZonedDateTime.now());
				vrQanJpa.save(vrqan);
			}
		} catch (final RuntimeException e) {
			LOG.trace("", e);
			LOG.error("Error while getting quota on vim {}: {}", vimConnection.getVimId(), e.getMessage());
		}
	}

	/**
	 * Added mainly for test unit.
	 *
	 * @param to   Duration before shutdown
	 * @param unit Unit of the timeout.
	 */
	public void await(final long to, final TimeUnit unit) {
		try {
			es.awaitTermination(to, unit);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new GenericException(e);
		}
	}

	@PreDestroy
	public void onClose() {
		es.shutdown();
	}

	public Optional<VrQan> findByVimId(final UUID id) {
		return vrQanJpa.findByVimId(id);
	}
}
