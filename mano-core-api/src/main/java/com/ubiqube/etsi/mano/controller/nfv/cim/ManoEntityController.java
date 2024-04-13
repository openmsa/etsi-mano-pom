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
package com.ubiqube.etsi.mano.controller.nfv.cim;

import java.util.List;
import java.util.function.Function;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.nfvmanocim.ManoEntityFrontController;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntity;
import com.ubiqube.etsi.mano.dao.mano.sol009.iface.ManoServiceInterface;
import com.ubiqube.etsi.mano.service.InterfaceInfoService;
import com.ubiqube.etsi.mano.service.sol009.PeerEntityService;

@Service
public class ManoEntityController implements ManoEntityFrontController {
	private final PeerEntityService peerEntiityService;
	private final InterfaceInfoService interfaceInfoService;

	public ManoEntityController(final PeerEntityService peerEntiityService, final @Lazy InterfaceInfoService interfaceInfoService) {
		this.peerEntiityService = peerEntiityService;
		this.interfaceInfoService = interfaceInfoService;
	}

	@Override
	public ResponseEntity<Void> changeStatus(final Object body) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> find(final Function<ManoEntity, U> mapper) {
		final ManoEntity me = peerEntiityService.getMe();
		final U ret = mapper.apply(me);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<List<U>> interfaceSearch(final String filter, final Function<ManoServiceInterface, U> mapper) {
		final List<ManoServiceInterface> db = interfaceInfoService.getInterfaceEndpoint();
		final List<U> ret = db.stream().map(mapper::apply).toList();
		return ResponseEntity.ok(ret);
	}

	@Override
	public ResponseEntity<Void> interfaceChangeState(final String manoServiceInterfaceId, final Object body) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> interfaceFindById(final String manoServiceInterfaceId, final Function<ManoEntity, U> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> interfacePatch(final String manoServiceInterfaceId, final Object body, final Function<ManoEntity, U> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> patch(final Object body, final Function<ManoEntity, U> mapper) {
		throw new UnsupportedOperationException();
	}

}
