/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.service.event.jms;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;
import com.ubiqube.etsi.mano.service.event.ActionMessage;
import com.ubiqube.etsi.mano.service.event.VnfmActionComtroller;
import com.ubiqube.etsi.mano.vnfm.service.event.VnfmActionController;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
@Transactional(TxType.NEVER)
public class VnfmActionsControllerImpl implements VnfmActionComtroller {

	private final VnfmActionController actionController;

	public VnfmActionsControllerImpl(final VnfmActionController actionController) {
		this.actionController = actionController;
	}

	@Override
	@Transactional(TxType.NEVER)
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NEVER)
	public void onEvent(final ActionMessage ev) {
		TenantHolder.setTenantId(ev.getTenantId());
		actionController.dispatch(ev.getActionType(), ev.getObjectId(), ev.getParameters());
	}

}
