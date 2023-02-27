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
package com.ubiqube.etsi.mano.dao.mano.dto.nsi;

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
public class NsInstanceDto {
	private String id = null;

	private String nsInstanceName = null;

	private String nsInstanceDescription = null;

	private String nsdId = null;

	private String nsdInfoId = null;

	private String flavourId = null;

	private InstantiationState nsState;

	private List<VnfInstanceDto> vnfInstance = new ArrayList<>();

	private List<NsVirtualLinkInfoDto> virtualLinkInfo = new ArrayList<>();

	private List<VnffgDescriptor> vnffgInfo = new ArrayList<>();

	private List<ScaleInfo> nsScaleStatus = new ArrayList<>();

}
