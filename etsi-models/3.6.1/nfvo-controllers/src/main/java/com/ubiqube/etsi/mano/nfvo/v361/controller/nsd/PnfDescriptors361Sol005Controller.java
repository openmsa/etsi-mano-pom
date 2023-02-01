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
package com.ubiqube.etsi.mano.nfvo.v361.controller.nsd;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.CreatePnfdInfoRequest;
import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.PnfdInfo;
import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.PnfdInfoModifications;

@RestController
public class PnfDescriptors361Sol005Controller implements PnfDescriptors361Sol005Api {

	@Override
	public ResponseEntity<List<PnfdInfo>> pnfDescriptorsGet(@Valid final String filter, @Valid final String nextpageOpaqueMarker) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> pnfDescriptorsPnfdInfoIdArtifactsArtifactPathGet(final String pnfdInfoId, final String artifactPath, @Valid final String includeSignatures) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> pnfDescriptorsPnfdInfoIdDelete(final String pnfdInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PnfdInfo> pnfDescriptorsPnfdInfoIdGet(final String pnfdInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> pnfDescriptorsPnfdInfoIdManifestGet(final String pnfdInfoId, @Valid final String includeSignatures) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PnfdInfoModifications> pnfDescriptorsPnfdInfoIdPatch(final String pnfdInfoId, @Valid final PnfdInfoModifications body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> pnfDescriptorsPnfdInfoIdPnfdContentGet(final String pnfdInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> pnfDescriptorsPnfdInfoIdPnfdContentPut(final String pnfdInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> pnfDescriptorsPnfdInfoIdPnfdGet(final String pnfdInfoId, @Valid final String includeSignatures) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PnfdInfo> pnfDescriptorsPost(final String contentType, @Valid final CreatePnfdInfoRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
