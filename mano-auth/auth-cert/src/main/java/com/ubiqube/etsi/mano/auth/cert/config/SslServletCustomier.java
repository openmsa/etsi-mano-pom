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
package com.ubiqube.etsi.mano.auth.cert.config;

import javax.sql.DataSource;

import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

/**
 * Set our customized provider. Add Component on it to enable it.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class SslServletCustomier implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	private final DataSource ds;

	public SslServletCustomier(final DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void customize(final ConfigurableServletWebServerFactory factory) {
		final SslBundles budles = new DefaultSslBundleRegistry("name", new ManoSslStoreProvider(ds));
		factory.setSslBundles(budles);
	}
}
