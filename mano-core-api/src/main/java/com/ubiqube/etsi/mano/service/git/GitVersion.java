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
package com.ubiqube.etsi.mano.service.git;

import java.time.ZonedDateTime;

public record GitVersion(String version, String abbrev, String desccribe, boolean dirty, long commitCount, ZonedDateTime date) {
	public static GitVersion of(final String version, final String abbrev, final String desccribe, final boolean dirty, final long commitCount, final ZonedDateTime date) {
		return new GitVersion(version, abbrev, desccribe, dirty, commitCount, date);
	}
}
