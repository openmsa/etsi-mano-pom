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
package com.ubiqube.etsi.mano.service.mon.data;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class FailureDetails {
	private static final Logger LOG = LoggerFactory.getLogger(FailureDetails.class);

	private URI type;

	private String title;

	@Column(nullable = true)
	private long status;

	@Column(length = 500)
	private String detail;

	private String instance;

	public FailureDetails() {
		// Nothing.
	}

	public FailureDetails(final long _status, final String _detail) {
		try {
			instance = URI.create("http//" + InetAddress.getLocalHost().getCanonicalHostName()).toString();
		} catch (final UnknownHostException e) {
			LOG.warn("", e);
		}
		status = _status;
		detail = _detail;
	}

}
