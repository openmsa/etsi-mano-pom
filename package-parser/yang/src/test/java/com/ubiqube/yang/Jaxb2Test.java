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

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import urn.etsi.nfv.yang.etsi.nfv.descriptors.v20220308.Nfv;
import urn.etsi.nfv.yang.etsi.nfv.descriptors.v20220308.nfv.Vnfd;

class Jaxb2Test {

	@Test
	void testName() throws Exception {
		final Marshaller marshaller = JAXBContext.newInstance(urn.etsi.nfv.yang.etsi.nfv.descriptors.v20220308.Nfv.class).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final Nfv nfv = new Nfv();
		final Vnfd vnfd = new Vnfd();
		vnfd.setId("id");
		nfv.setVnfd(List.of(vnfd));
		marshaller.marshal(nfv, System.out);
	}

	@Test
	void testName2() throws Exception {
		final Unmarshaller marshaller = JAXBContext.newInstance(urn.etsi.nfv.yang.etsi.nfv.descriptors.v20220308.Nfv.class).createUnmarshaller();
		final File is = new File("src/main/resources/data2.xml");
		final Nfv obj = (Nfv) marshaller.unmarshal(is);
		System.out.println("" + obj.getVnfd());
	}

}
