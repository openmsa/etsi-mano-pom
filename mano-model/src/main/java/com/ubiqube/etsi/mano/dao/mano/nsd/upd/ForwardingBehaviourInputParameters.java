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
public class ForwardingBehaviourInputParameters implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	/**
	 * May be included if forwarding behaviour is equal to LB. Shall not be included
	 * otherwise. Permitted values: * ROUND_ROBIN * LEAST_CONNECTION * LEAST_TRAFFIC
	 * * LEAST_RESPONSE_TIME * CHAINED_FAILOVER * SOURCE_IP_HASH * SOURCE_MAC_HASH
	 */
	public enum AlgortihmNameEnum {
		ROUND_ROBIN("ROUND_ROBIN"),

		LEAST_CONNECTION("LEAST_CONNECTION"),

		LEAST_TRAFFIC("LEAST_TRAFFIC"),

		LEAST_RESPONSE_TIME("LEAST_RESPONSE_TIME"),

		CHAINED_FAILOVER("CHAINED_FAILOVER"),

		SOURCE_IP_HASH("SOURCE_IP_HASH"),

		SOURCE_MAC_HASH("SOURCE_MAC_HASH");

		private final String value;

		AlgortihmNameEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static AlgortihmNameEnum fromValue(final String text) {
			for (final AlgortihmNameEnum b : AlgortihmNameEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("algortihmName")
	private AlgortihmNameEnum algortihmName;

	@JsonProperty("algorithmWeights")
	@Valid
	private List<Integer> algorithmWeights;

}
