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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ubiqube.etsi.mano.exception.GenericException;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class VimType {
	private static final Pattern PATTERN = Pattern.compile("(?<registrant>[A-Z0-9\\-_]+)\\.(?<vimName>[A-Z0-9\\-_]+)(\\.V_(?<major>[0-9]+)(_(?<minor>[0-9]+))?)?");
	private final String registrant;
	private final String vimName;
	@Nullable
	private final Integer major;
	@Nullable
	private final Integer minor;

	public VimType(final String registrant, final String vimName) {
		this(registrant, vimName, null);
	}

	public VimType(final String registrant, final String vimName, final @Nullable Integer major) {
		this(registrant, vimName, major, null);
	}

	public VimType(final String registrant, final String vimName, @Nullable final Integer major, @Nullable final Integer minor) {
		this.registrant = registrant;
		this.vimName = vimName;
		this.major = major;
		this.minor = minor;
	}

	public static VimType of(final String str) {
		final Matcher m = PATTERN.matcher(str);
		if (!m.find()) {
			throw new GenericException("Could not match a valid vimType: " + str);
		}
		final String reg = Objects.requireNonNull(m.group("registrant"));
		final String vn = Objects.requireNonNull(m.group("vimName"));
		final Integer ma = Optional.ofNullable(m.group("major")).map(Integer::parseInt).orElse(null);
		final Integer mi = Optional.ofNullable(m.group("minor")).map(Integer::parseInt).orElse(null);
		return new VimType(reg, vn, ma, mi);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(registrant).append(".").append(vimName);
		if (major != null) {
			sb.append(".V_").append(major);
		}
		if (minor != null) {
			sb.append(".V_").append(minor);
		}
		return sb.toString();
	}
}
