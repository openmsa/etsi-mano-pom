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
package com.ubiqube.mano.remote;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class MinaResult implements Result {

	private final String out;
	private final String err;
	private final int status;

	public MinaResult(final String out, final String err, final int status) {
		this.out = out;
		this.err = err;
		this.status = status;
	}

	@Override
	public String outToString() {
		return out;
	}

	@Override
	public String errToString() {
		return err;
	}

	public int getStatus() {
		return status;
	}

}
