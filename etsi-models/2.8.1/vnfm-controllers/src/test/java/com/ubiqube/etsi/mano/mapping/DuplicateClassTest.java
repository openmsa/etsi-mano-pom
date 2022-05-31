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
package com.ubiqube.etsi.mano.mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class DuplicateClassTest {

	@Test
	void scanDuplicatedClasses() throws IOException {
		final MultiValueMap<Path, Path> all = new LinkedMultiValueMap<>();
		Files.walk(Paths.get("/home/olivier/workspace/workspace17.1.1/ubi-etsi-mano/etsi-models/2.8.1"))
				.filter(Files::isRegularFile)
				.filter(x -> x.getFileName().toString().endsWith(".java"))
				.forEach(x -> all.add(x.getFileName(), x));

		all.entrySet().stream().filter(x -> x.getValue().size() > 1).forEach(x -> {
			System.out.println(" - " + x.getKey() + " " + x.getValue());
			final List<Path> v = x.getValue();
			v.remove(0);
			v.forEach(y -> {
				System.out.println("   - " + y);
				// y.toFile().delete();
			});

		});
	}
}
