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
package com.ubiqube.etsi.mano.nfvo.service.mapping.nsinstance;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingStepMapping;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NsInstanceMapping {

	ListKeyPair map(ListKeyPair o);

	VnfScalingStepMapping map(VnfScalingStepMapping o);

	VnfScalingLevelMapping map(VnfScalingLevelMapping o);

	ForwarderMapping map(ForwarderMapping o);

	Classifier map(Classifier o);

	NfpDescriptor map(NfpDescriptor o);

	VnffgInstance map(VnffgInstance o);

	CpPair map(CpPair o);
}
