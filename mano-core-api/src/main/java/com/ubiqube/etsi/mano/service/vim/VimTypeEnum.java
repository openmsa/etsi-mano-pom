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
package com.ubiqube.etsi.mano.service.vim;

public enum VimTypeEnum {
	ETSINFV_OPENSTACK_KEYSTONE_V_2("ETSINFV.OPENSTACK_KEYSTONE.V_2"),
	OPENSTACK_V3("ETSINFV.OPENSTACK_KEYSTONE.V_3"),
	ETSINFV_OPENSTACK_KEYSTONE_V_3("ETSINFV.OPENSTACK_KEYSTONE.V_3"),
	// K8S
	UBINFV_CAPI_V1("UBINFV.KUBERNETES.V_1");

	private final String value;

	VimTypeEnum(final String value) {
		this.value = value;
	}

}
