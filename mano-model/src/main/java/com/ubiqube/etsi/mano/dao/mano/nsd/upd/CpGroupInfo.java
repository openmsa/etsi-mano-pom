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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author olivier
 *
 */
public class CpGroupInfo implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@JsonProperty("cpPairInfo")
	@Valid
	private List<CpPairInfo> cpPairInfo;

	/**
	 * Identifies a rule to apply to forward traffic to the ingress CPs or SAPs of
	 * the group. Permitted values: * ALL = Traffic flows shall be forwarded
	 * simultaneously to all CPs or SAPs of the group. * LB = Traffic flows shall be
	 * forwarded to one CP or SAP of the group selected based on a loadbalancing
	 * algorithm.
	 */
	public enum ForwardingBehaviourEnum {
		ALL("ALL"),

		LB("LB");

		private final String value;

		ForwardingBehaviourEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ForwardingBehaviourEnum fromValue(final String text) {
			for (final ForwardingBehaviourEnum b : ForwardingBehaviourEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("forwardingBehaviour")
	private ForwardingBehaviourEnum forwardingBehaviour;

	@JsonProperty("forwardingBehaviourInputParameters")
	private ForwardingBehaviourInputParameters forwardingBehaviourInputParameters;

}
