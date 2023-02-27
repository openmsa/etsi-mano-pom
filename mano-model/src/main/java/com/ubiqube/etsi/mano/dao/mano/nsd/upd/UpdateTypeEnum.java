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
package com.ubiqube.etsi.mano.dao.mano.nsd.upd;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public enum UpdateTypeEnum {
	ADD_VNF("ADD_VNF"),
	REMOVE_VNF("REMOVE_VNF"),
	INSTANTIATE_VNF("INSTANTIATE_VNF"),
	CHANGE_VNF_DF("CHANGE_VNF_DF"),
	OPERATE_VNF("OPERATE_VNF"),
	MODIFY_VNF_INFORMATION("MODIFY_VNF_INFORMATION"),
	CHANGE_EXTERNAL_VNF_CONNECTIVITY("CHANGE_EXTERNAL_VNF_CONNECTIVITY"),
	CHANGE_VNFPKG("CHANGE_VNFPKG"),
	MOVE_VNF("MOVE_VNF"),
	ADD_VNFFG("ADD_VNFFG"),
	REMOVE_VNFFG("REMOVE_VNFFG"),
	UPDATE_VNFFG("UPDATE_VNFFG");

	private String value;

	UpdateTypeEnum(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static UpdateTypeEnum fromValue(final String text) {
		for (final UpdateTypeEnum b : UpdateTypeEnum.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
