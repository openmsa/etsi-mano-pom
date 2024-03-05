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
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.mano.remote;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
class MainTest {

	private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

	@Test
	void mainTest001() {
		final SshClient client = SshClient.setUpDefaultClient();
		client.start();
		try (ClientSession session = client.connect("ncuser", "10.31.1.29", 22)
				.verify(5000)
				.getSession()) {
			session.auth().verify();
			try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
					ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
					ChannelExec channel = session.createExecChannel("ls -lha")) {
				channel.setOut(responseStream);
				channel.setErr(errorStream);
				channel.open().verify(30, TimeUnit.SECONDS);
				try (OutputStream pipedIn = channel.getInvertedIn()) {
					pipedIn.write("ls -lha".getBytes());
					pipedIn.flush();
				}
				channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
						TimeUnit.SECONDS.toMillis(30));
				final String error = new String(errorStream.toByteArray());
				System.out.println("error> " + error);
				System.out.println("ok> " + responseStream.toString());
				System.out.println("ec> " + channel.getExitStatus());
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	void testScp() {
		final SshClient client = SshClient.setUpDefaultClient();
		client.start();
		try (ClientSession session = client.connect("ncuser", "10.31.1.29", 22)
				.verify(5000)
				.getSession()) {
			session.auth().verify();
			//
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test003() {
		final SshClient client = SshClient.setUpDefaultClient();
		client.start();
		try (ClientSession session = client.connect("ncuser", "10.31.1.29", 22)
				.verify(5000)
				.getSession()) {
			final ByteArrayInputStream bais = new ByteArrayInputStream("test".getBytes());
			session.auth().verify();
			final SftpClientFactory factory = SftpClientFactory.instance();
			try (SftpClient sftpClient = factory.createSftpClient(session)) {
				final OutputStream out = sftpClient.write("Videos/test");
				bais.transferTo(out);
				bais.close();
				out.close();
			}
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
