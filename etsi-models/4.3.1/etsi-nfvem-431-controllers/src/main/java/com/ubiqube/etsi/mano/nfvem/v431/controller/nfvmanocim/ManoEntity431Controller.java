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

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ChangeStateRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoConfigModificationRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoConfigModifications;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoEntity;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoServiceInterface;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoServiceInterfaceModificationRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim.ManoServiceInterfaceModifications;

@RestController
public class ManoEntity431Controller implements ManoEntity431Api {

	@Override
	public ResponseEntity<Void> manoEntityChangeStatePost(@Valid final ChangeStateRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ManoEntity> manoEntityGet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<ManoServiceInterface>> manoEntityManoInterfacesGet(@Valid final String filter, @Valid final String allFields, @Valid final String fields, @Valid final String excludeFields, @Valid final String excludeDefault, @Valid final String nextpageOpaqueMarker) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> manoEntityManoInterfacesManoServiceInterfaceIdChangeStatePost(final String manoServiceInterfaceId, @Valid final ChangeStateRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ManoServiceInterface> manoEntityManoInterfacesManoServiceInterfaceIdGet(final String manoServiceInterfaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ManoServiceInterfaceModifications> manoEntityManoInterfacesManoServiceInterfaceIdPatch(final String manoServiceInterfaceId, @Valid final ManoServiceInterfaceModificationRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ManoConfigModifications> manoEntityPatch(@Valid final ManoConfigModificationRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
