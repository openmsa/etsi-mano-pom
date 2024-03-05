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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class MinaRemoteSession implements RemoteSession {

	private long timeout = 5_000L;
	private ClientSession session;

	public MinaRemoteSession(final String host, final String username, final int port) {
		final SshClient client = SshClient.setUpDefaultClient();
		try {
			this.session = client.connect(username, host, port)
					.verify(5000)
					.getSession();
			session.auth().verify();
		} catch (final IOException e) {
			throw new RemoteException(e);
		}
	}

	@Override
	public void setTimeOut(final long timeout) {
		this.timeout = timeout;
	}

	@Override
	public Result exec(final String cmd) {
		try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
				ChannelExec channel = session.createExecChannel(cmd)) {
			channel.setOut(responseStream);
			channel.setErr(errorStream);
			channel.open().verify(timeout, TimeUnit.SECONDS);
			channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), timeout);
			final String error = new String(errorStream.toByteArray());
			return new MinaResult(cmd, error, channel.getExitStatus());
		} catch (final IOException e) {
			throw new RemoteException(e);
		}
	}

	@Override
	public Result push(final InputStream is, final String target) {
		final SftpClientFactory factory = SftpClientFactory.instance();
		try (SftpClient sftpClient = factory.createSftpClient(session);
				final OutputStream out = sftpClient.write(target)) {
			is.transferTo(out);
		} catch (final IOException e) {
			throw new RemoteException(e);
		}
		return null;
	}

}
