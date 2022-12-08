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
package com.ubiqube.etsi.mano.dao.mano.dto;

import java.util.Map;
import java.util.Set;

import com.ubiqube.etsi.mano.dao.mano.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.dao.mano.vnfi.VimCapability;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VimConnectionInfoDto {

	private String vimId;

	private String vimType;

	private Map<String, String> interfaceInfo;

	private Map<String, String> accessInfo;

	private Map<String, String> extra;

	private CnfInformations cnfInfo;
	/**
	 * Capabilities of the vim. Read VimCapabilites Enum to figure out what we can
	 * do.
	 */
	private Set<VimCapability> vimCapabilities;

}
