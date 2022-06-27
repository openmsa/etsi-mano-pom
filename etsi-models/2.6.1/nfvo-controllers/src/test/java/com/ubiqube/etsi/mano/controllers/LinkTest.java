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
