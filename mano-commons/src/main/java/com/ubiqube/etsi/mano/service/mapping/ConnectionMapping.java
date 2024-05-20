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
package com.ubiqube.etsi.mano.service.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mapstruct.Mapper;

import com.ubiqube.etsi.mano.dao.mano.vim.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.InterfaceInfo;

@Mapper
public interface ConnectionMapping {
	default Map<String, String> map(final InterfaceInfo value) {
		if (null == value) {
			return Map.of();
		}
		final Map<String, String> ret = new HashMap<>();
		ret.put("endpoint", value.getEndpoint());
		if (value.isNonStrictSsl()) {
			ret.put("non-strict-ssl", "true");
		}
		Optional.ofNullable(value.getNatHost()).ifPresent(x -> ret.put("nat-host", x));

		Optional.ofNullable(value.getConnectionTimeout()).ifPresent(x -> ret.put("connection-timeout", "" + x));
		Optional.ofNullable(value.getReadTimeout()).ifPresent(x -> ret.put("read-timeout", "" + x));
		ret.put("read-timeout", "" + value.getRetry());
		Optional.ofNullable(value.getRegionName()).ifPresent(x -> ret.put("regionName", x));
		Optional.ofNullable(value.getSdnEndpoint()).ifPresent(x -> ret.put("sdn-endpoint", x));
		return ret;
	}

	default Map<String, String> map(final AccessInfo value) {
		if (null == value) {
			return Map.of();
		}
		final Map<String, String> ret = new HashMap<>();
		Optional.ofNullable(value.getUsername()).ifPresent(x -> ret.put("username", x));
		Optional.ofNullable(value.getPassword()).ifPresent(x -> ret.put("password", x));

		Optional.ofNullable(value.getUserDomain()).ifPresent(x -> ret.put("userDomain", x));
		Optional.ofNullable(value.getProject()).ifPresent(x -> ret.put("project", x));
		Optional.ofNullable(value.getProjectId()).ifPresent(x -> ret.put("projectId", x));
		Optional.ofNullable(value.getProjectDomain()).ifPresent(x -> ret.put("projectDomain", x));
		Optional.ofNullable(value.getProjectName()).ifPresent(x -> ret.put("projectName", x));
		return ret;
	}

}
