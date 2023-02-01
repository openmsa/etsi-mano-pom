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
package com.ubiqube.etsi.mano.dao.mano.nsd.upd;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.dao.mano.nsd.wan.ConnectivityServiceEndpointInformation;
import com.ubiqube.etsi.mano.dao.mano.nsd.wan.MscsEndpointInformation;

import lombok.Data;

/**
 *
 * @author olivier
 *
 */
@Data
public class ModifyWanConnectionInfoData implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@JsonProperty("wanConnectionInfoId")
	private String wanConnectionInfoId = null;

	@JsonProperty("mscsName")
	private String mscsName = null;

	@JsonProperty("mscsDescription")
	private String mscsDescription = null;

	@JsonProperty("mscsEndpoints")
	@Valid
	private List<MscsEndpointInformation> mscsEndpoints = null;

	@JsonProperty("removeMscsEndpointIds")
	@Valid
	private List<String> removeMscsEndpointIds = null;

	@JsonProperty("connectivityServiceEndpoints")
	@Valid
	private List<ConnectivityServiceEndpointInformation> connectivityServiceEndpoints = null;

	@JsonProperty("removeConnectivityServiceEndpoints")
	@Valid
	private List<String> removeConnectivityServiceEndpoints = null;

}
