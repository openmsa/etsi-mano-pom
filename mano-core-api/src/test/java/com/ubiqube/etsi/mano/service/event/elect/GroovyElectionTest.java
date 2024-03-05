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
package com.ubiqube.etsi.mano.service.event.elect;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.config.properties.ManoElectionProperties;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.exception.GenericException;

class GroovyElectionTest {
	private static final String PATH = "/tmp/test-elect/";
	private final ManoElectionProperties props = new ManoElectionProperties();

	@Test
	void testName() throws Exception {
		final File f = new File(PATH);
		f.mkdirs();
		props.setScriptPath(PATH);
		unlink(PATH, "test.elect");
		final GroovyElection ge = new GroovyElection(props);
		final List<VimConnectionInformation> vims = List.of();
		final GrantResponse grant = new GrantResponse();
		final Set<VnfCompute> vnfcs = Set.of();
		final Set<VnfStorage> storages = Set.of();
		ge.doElection(vims, grant, vnfcs, storages);
		assertTrue(true);
	}

	private static void unlink(final String path2, final String string) {
		final File f = new File(path2, string);
		f.delete();
	}

	@Test
	void testScript() throws Exception {
		final File f = new File(PATH);
		f.mkdirs();
		props.setScriptPath(PATH);
		writeScript(PATH, "test.elect");
		final GroovyElection ge = new GroovyElection(props);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		final List<VimConnectionInformation> vims = List.of(vim01);
		final GrantResponse grant = new GrantResponse();
		final Set<VnfCompute> vnfcs = Set.of();
		final Set<VnfStorage> storages = Set.of();
		ge.doElection(vims, grant, vnfcs, storages);
		assertTrue(true);
	}

	private static void writeScript(final String path2, final String fileName) {
		try (FileOutputStream fos = new FileOutputStream(new File(path2, fileName))) {
			fos.write("""
					package nfvo.election.openstack.v3;

					""".toString().getBytes());
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}
}
