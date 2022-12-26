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
package com.ubiqube.etsi.mano.controller.nfv.cim;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.nfvmanocim.ManoEntityFrontController;

@Service
public class ManoEntityController implements ManoEntityFrontController {

	@Override
	public ResponseEntity<Void> changeStatus(final Object body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> find(final Class<U> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<List<U>> interfaceSearch(final String filter, final Class<U> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> interfaceChangeState(final String manoServiceInterfaceId, final Object body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> interfaceFindById(final String manoServiceInterfaceId, final Class<U> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> interfacePatch(final String manoServiceInterfaceId, final Object body, final Class<U> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> patch(final Object body, final Class<U> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
