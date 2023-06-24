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

import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundleKey;
import org.springframework.boot.ssl.SslManagerBundle;
import org.springframework.boot.ssl.SslOptions;
import org.springframework.boot.ssl.SslStoreBundle;

/**
 * This provider can load data from anywhere.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class ManoSslStoreProvider implements SslBundle {

	private final DataSource ds;

	public ManoSslStoreProvider(final DataSource ds) {
		this.ds = ds;
	}

	@Override
	public SslStoreBundle getStores() {
		return null;
	}

	@Override
	public SslBundleKey getKey() {
		ds.getClass();
		return null;
	}

	@Override
	public SslOptions getOptions() {
		return null;
	}

	@Override
	public String getProtocol() {
		return null;
	}

	@Override
	public SslManagerBundle getManagers() {
		return null;
	}

}
