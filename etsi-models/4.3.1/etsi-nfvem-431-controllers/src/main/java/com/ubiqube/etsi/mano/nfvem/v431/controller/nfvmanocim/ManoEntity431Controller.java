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
package com.ubiqube.etsi.mano.nfvem.v431.controller.nfvmanocim;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.nfvmanocim.ManoEntityFrontController;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ChangeStateRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoConfigModificationRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoConfigModifications;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoEntity;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoServiceInterface;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoServiceInterfaceModificationRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoServiceInterfaceModifications;

@RestController
public class ManoEntity431Controller implements ManoEntity431Api {
	private final ManoEntityFrontController manoEntityFrontController;

	public ManoEntity431Controller(final ManoEntityFrontController manoEntityFrontController) {
		this.manoEntityFrontController = manoEntityFrontController;
	}

	@Override
	public ResponseEntity<Void> manoEntityChangeStatePost(final ChangeStateRequest body) {
		return manoEntityFrontController.changeStatus(body);
	}

	@Override
	public ResponseEntity<ManoEntity> manoEntityGet() {
		return manoEntityFrontController.find(ManoEntity.class);
	}

	@Override
	public ResponseEntity<List<ManoServiceInterface>> manoEntityManoInterfacesGet(final String filter, final String allFields, final String fields, final String excludeFields, final String excludeDefault, final String nextpageOpaqueMarker) {
		return manoEntityFrontController.interfaceSearch(filter, ManoServiceInterface.class);
	}

	@Override
	public ResponseEntity<Void> manoEntityManoInterfacesManoServiceInterfaceIdChangeStatePost(final String manoServiceInterfaceId, final ChangeStateRequest body) {
		return manoEntityFrontController.interfaceChangeState(manoServiceInterfaceId, body);
	}

	@Override
	public ResponseEntity<ManoServiceInterface> manoEntityManoInterfacesManoServiceInterfaceIdGet(final String manoServiceInterfaceId) {
		return manoEntityFrontController.interfaceFindById(manoServiceInterfaceId, ManoServiceInterface.class);
	}

	@Override
	public ResponseEntity<ManoServiceInterfaceModifications> manoEntityManoInterfacesManoServiceInterfaceIdPatch(final String manoServiceInterfaceId, final ManoServiceInterfaceModificationRequest body) {
		return manoEntityFrontController.interfacePatch(manoServiceInterfaceId, body, ManoServiceInterfaceModifications.class);
	}

	@Override
	public ResponseEntity<ManoConfigModifications> manoEntityPatch(final ManoConfigModificationRequest body) {
		return manoEntityFrontController.patch(body, ManoConfigModifications.class);
	}

}
