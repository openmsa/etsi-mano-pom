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
package com.ubiqube.etsi.mano.service.vim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;

/**
 * Convert a incoming vimType from ETSI) to our internal vimType. This is a kind
 * of mapper between internal name and outside name.
 *
 * @author olivier
 */
@Service
public class VimTypeConverter {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(VimTypeConverter.class);

	@SuppressWarnings("static-method")
	public void setToInternalType(final VimConnectionInformation vci) {
		if ("OPENSTACK_V3".equals(vci.getVimType())) {
			LOG.warn("⚠️⚠️⚠️ OPENSTACK_V3 is deprecated, use ETSINFV.OPENSTACK_KEYSTONE.V_3. ⚠️⚠️⚠️");
			vci.setVimType("ETSINFV.OPENSTACK_KEYSTONE.V_3");
		}
		if ("ETSINFV.OPENSTACK_KEYSTONE.V_3".equals(vci.getVimType())) {
			vci.setVimType("OPENSTACK_V3");
			return;
		}
		if ("UBINFV.AZURE.V_1".equals(vci.getVimType())) {
			vci.setVimType("AZURE");
			return;
		}
		if ("UBINFV.AWS.V_1".equals(vci.getVimType())) {
			vci.setVimType("AWS");
			return;
		}
		throw new GenericException("Unsupported vim type: " + vci.getVimType());
	}

	@SuppressWarnings("static-method")
	public void setToExternalType(final VimConnectionInformation vci) {
		final String value = switch (vci.getVimType()) {
		case "AZURE" -> "UBINFV.AZURE.V_1";
		case "AWS" -> "UBINFV.AWS.V_1";
		case "OPENSTACK_V3" -> "ETSINFV.OPENSTACK_KEYSTONE.V_3";
		default -> throw new IllegalArgumentException("Unexpected value: " + vci.getVimType());
		};
		vci.setVimType(value);
	}
}
