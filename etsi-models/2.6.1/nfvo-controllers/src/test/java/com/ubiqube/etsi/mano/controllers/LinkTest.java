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
package com.ubiqube.etsi.mano.controllers;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.common.v261.model.Link;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfPkgInfoLinks;
import com.ubiqube.etsi.mano.nfvo.v261.controller.vnf.VnfPackage261Sol003Api;

/**
 *
 * @author olivier
 *
 */
class LinkTest {

	@Test
	void testName() throws Exception {
		final VnfPkgInfoLinks links = new VnfPkgInfoLinks();

		final Link self = new Link();
		self.setHref(linkTo(methodOn(VnfPackage261Sol003Api.class).vnfPackagesVnfPkgIdGet("value")).withSelfRel().getHref());
		links.self(self);
		assertTrue(self.getHref().startsWith("/vnfpkgm"));
	}
}
