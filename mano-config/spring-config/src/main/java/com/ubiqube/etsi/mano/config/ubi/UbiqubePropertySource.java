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
package com.ubiqube.etsi.mano.config.ubi;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.lang.Nullable;

public class UbiqubePropertySource extends EnumerablePropertySource<Map<String, Object>> {

	private static final Logger LOG = LoggerFactory.getLogger(UbiqubePropertySource.class);

	private final ResourceBundle props;

	protected UbiqubePropertySource(final String name, final ResourceBundle rb) {
		super(name);
		this.props = rb;
	}

	@Override
	public String[] getPropertyNames() {
		return props.keySet().toArray(new String[0]);
	}

	@Override
	public Object getProperty(final String nameIn) {
		try {
			return props.getObject(nameIn);
		} catch (final RuntimeException e) {
			LOG.trace("", e);
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = super.hashCode();
		return (prime * result) + (props == null ? 0 : props.hashCode());
	}

	@Override
	public boolean equals(final @Nullable Object obj) {
		if (this == obj) {
			return true;
		}
		if ((null == obj) || !super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		final UbiqubePropertySource other = (UbiqubePropertySource) obj;
		return Objects.equals(props, other.props);
	}

}
