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
package com.ubiqube.etsi.mano.grammar;

import jakarta.annotation.Nonnull;

public class DocumentStatus {
	public enum Status {
		REFUSED,
		VALIDATED,
		NOSTATE
	}

	public DocumentStatus(final Status status) {
		this.status = status;
	}

	@Nonnull
	private Status status;
	private boolean result;

	public Status getStatus() {
		return status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(final boolean result) {
		this.result = result;
	}

}
