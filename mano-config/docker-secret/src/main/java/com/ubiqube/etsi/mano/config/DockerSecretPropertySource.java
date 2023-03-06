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
package com.ubiqube.etsi.mano.config;

import java.util.Map;
import java.util.Objects;

import org.springframework.core.env.EnumerablePropertySource;

import jakarta.annotation.Nullable;

public class DockerSecretPropertySource extends EnumerablePropertySource<Map<String, Object>> {

	private final Map<String, Object> secrets;

	public DockerSecretPropertySource(final Map<String, Object> secrets) {
		super("Docker secrets");
		this.secrets = secrets;
	}

	@Override
	public String[] getPropertyNames() {
		return secrets.keySet().toArray(new String[0]);
	}

	@Override
	public Object getProperty(final String nameIn) {
		return secrets.get(nameIn);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = super.hashCode();
		return (prime * result) + Objects.hash(secrets);
	}

	@Override
	public boolean equals(final @Nullable Object obj) {
		if (this == obj) {
			return true;
		}
		if ((null == obj) || !super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		final DockerSecretPropertySource other = (DockerSecretPropertySource) obj;
		return Objects.equals(secrets, other.secrets);
	}

}
