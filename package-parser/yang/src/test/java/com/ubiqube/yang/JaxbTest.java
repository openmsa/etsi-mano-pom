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
package com.ubiqube.yang;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.yang.objects.Nfv;
import com.ubiqube.yang.objects.Vdu;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

class JaxbTest {

	@Test
	void testName() throws Exception {
		final Marshaller marshaller = JAXBContext.newInstance(Nfv.class).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final List<Vdu> lst = List.of(new Vdu("vdu-name"), new Vdu("vdu-name2"));
		marshaller.marshal(new Nfv("name", lst), System.out);
	}
}
