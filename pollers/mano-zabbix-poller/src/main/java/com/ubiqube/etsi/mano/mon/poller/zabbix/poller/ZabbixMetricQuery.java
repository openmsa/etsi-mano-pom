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
package com.ubiqube.etsi.mano.mon.poller.zabbix.poller;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.mon.MonGenericException;

public class ZabbixMetricQuery {

	public List<String> result(final String hostName, final int port, final String metric) {
		try (final Socket socket = new Socket(hostName, port)) {
			final byte[] pkt = buildPayload(metric);
			socket.getOutputStream().write(pkt);
			socket.getOutputStream().flush();
			final int il = getLength(socket.getInputStream());
			return getPayload(socket.getInputStream(), il);
		} catch (final IOException e) {
			throw new MonGenericException(e);
		}
	}

	private static List<String> getPayload(final InputStream is, final int size) throws IOException {
		final List<String> ret = new ArrayList<>();
		final byte[] payload = new byte[size];
		final int read = is.read(payload);
		if (read != size) {
			throw new MonGenericException("Read " + read + ", size: " + size);
		}
		int start = 0;
		for (int i = 0; i < size; i++) {
			if (payload[i] == 0) {
				final byte[] target = new byte[i - start];
				System.arraycopy(payload, start, target, 0, i - start);
				ret.add(new String(target));
				start = i;
			}
		}
		final byte[] target = new byte[size - start];
		System.arraycopy(payload, start, target, 0, size - start);
		ret.add(new String(target));
		return ret;
	}

	private static int getLength(final InputStream is) throws IOException {
		final byte[] fullHeader = new byte[13];
		final byte[] lengthBuff = new byte[4];
		final int i = is.read(fullHeader);
		if (i != fullHeader.length) {
			throw new MonGenericException("Error while reading ZBX header: " + i);
		}
		System.arraycopy(fullHeader, 5, lengthBuff, 0, 4);
		final ByteBuffer wrap = ByteBuffer.wrap(lengthBuff).order(ByteOrder.LITTLE_ENDIAN);
		return wrap.getInt();
	}

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(final byte[] bytes) {
		final char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			final int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[(j * 2) + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	private static byte[] buildPayload(final String metric) {
		final byte[] data = metric.getBytes();
		final byte[] header = {
				'Z', 'B', 'X', 'D', '\1',
				(byte) (data.length & 0xFF),
				(byte) ((data.length >> 8) & 0xFF),
				(byte) ((data.length >> 16) & 0xFF),
				(byte) ((data.length >> 24) & 0xFF),
				'\0', '\0', '\0', '\0' };

		final byte[] packet = new byte[header.length + data.length];
		System.arraycopy(header, 0, packet, 0, header.length);
		System.arraycopy(data, 0, packet, header.length, data.length);
		return packet;
	}
}
