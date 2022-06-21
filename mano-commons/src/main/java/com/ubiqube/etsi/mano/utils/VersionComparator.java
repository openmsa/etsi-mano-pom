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
package com.ubiqube.etsi.mano.utils;

import java.util.Comparator;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VersionComparator implements Comparator<Version> {

	@Override
	public int compare(final Version o1, final Version o2) {
		if (o1 == null || o2 == null) {
			return 0;
		}
		if (o1.getMajor() != o1.getMajor()) {

		}
		return 0;
	}

}
