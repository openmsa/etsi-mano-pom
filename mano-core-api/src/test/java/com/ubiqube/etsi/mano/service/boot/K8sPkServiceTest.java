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
package com.ubiqube.etsi.mano.service.boot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.jpa.config.ConfigurationsJpa;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("static-method")
class K8sPkServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(K8sPkServiceTest.class);

	@Mock
	private ConfigurationsJpa configurations;

	@Test
	void testName() throws Exception {
		final K8sPkService serv = new K8sPkService(configurations);
		final String str = serv.createCsr("CN=test,O=system:masters");
		assertNotNull(str);
		LOG.debug(str);
	}
}
