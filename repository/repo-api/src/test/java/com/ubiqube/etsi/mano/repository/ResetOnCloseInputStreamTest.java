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
package com.ubiqube.etsi.mano.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class ResetOnCloseInputStreamTest {

	@Test
	void testName() throws IOException {
		final InputStream mainIs = this.getClass().getResourceAsStream("/test-input-stream.txt");
		final ResetOnCloseInputStream is = new ResetOnCloseInputStream(mainIs);
		final int i = is.read();
		assertEquals(84, i);
		is.close();
	}
}
