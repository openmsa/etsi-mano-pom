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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.vim.AbstractUnitOfWork;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfCreateUow extends AbstractUnitOfWork<NsVnfTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfCreateUow.class);
	@Nonnull
	private final VnfmInterface vnfm;
	@Nonnull
	private final NsVnfTask task;

	public VnfCreateUow(final VirtualTaskV3<NsVnfTask> task, final VnfmInterface vnfm) {
		super(task, VnfCreateNode.class);
		this.vnfm = vnfm;
		this.task = task.getTemplateParameters();
	}

	@Override
	public String execute(final Context3d context) {
		final Servers server = task.getServer();
		final VnfInstance vnfmVnfInstance = vnfm.createVnfInstance(server, task.getVnfdId(), task.getDescription(), task.getToscaName());
		return vnfmVnfInstance.getId().toString();
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		if (null != task.getVimResourceId()) {
			try {
				vnfm.delete(task.getServer(), task.getVimResourceId());
			} catch (final RuntimeException e) {
				LOG.trace("", e);
				LOG.warn("Could not delete instance {}", task.getVimResourceId());
			}
		}
		return null;
	}
}
