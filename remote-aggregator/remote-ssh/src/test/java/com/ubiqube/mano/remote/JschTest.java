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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.mano.remote;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 *
 * @author olivier
 *
 */
@SuppressWarnings("static-method")
class JschTest {

	private final JSch jsch = new JSch();

	void testName() throws Exception {
		final Session sess = jsch.getSession("ncuser", "10.31.1.29");
		final Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		sess.setConfig(config);
		sess.connect();
		final ChannelExec ec = (ChannelExec) sess.openChannel("exec");
		ec.setCommand("ls -la");
		ec.connect();
		assertTrue(true);
	}

	@Test
	void testDummy() throws Exception {
		assertTrue(true);
	}
}
