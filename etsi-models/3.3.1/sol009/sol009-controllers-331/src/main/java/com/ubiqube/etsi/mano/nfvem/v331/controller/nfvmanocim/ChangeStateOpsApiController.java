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

import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ChangeStateOpOcc;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class ChangeStateOpsApiController implements ChangeStateOpsApi {

	@Override
	public ResponseEntity<ChangeStateOpOcc> changeStateOpsChangeStateOpOccIdGet(final String changeStateOpOccId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<ChangeStateOpOcc>> changeStateOpsGet(@Valid final String nextpageOpaqueMarker) {
		// TODO Auto-generated method stub
		return null;
	}

}
