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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvem.v331.controller.nfvmanocim;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ChangeStateRequest;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ManoConfigModificationRequest;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ManoConfigModifications;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ManoEntity;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ManoServiceInterface;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ManoServiceInterfaceModificationRequest;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ManoServiceInterfaceModifications;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class ManoEntityApiController implements ManoEntityApi {

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
	public ResponseEntity<List<ManoServiceInterface>> manoEntityManoInterfacesGet(@Valid final String nextpageOpaqueMarker) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> manoEntityManoInterfacesManoServiceInterfaceIdChangeStatePost(@Valid final ChangeStateRequest body, final String manoServiceInterfaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ManoServiceInterface> manoEntityManoInterfacesManoServiceInterfaceIdGet(final String manoServiceInterfaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ManoServiceInterfaceModifications> manoEntityManoInterfacesManoServiceInterfaceIdPatch(@Valid final ManoServiceInterfaceModificationRequest body, final String manoServiceInterfaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ManoConfigModifications> manoEntityPatch(@Valid final ManoConfigModificationRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
