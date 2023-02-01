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
public class NfpRule implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	@JsonProperty("etherDestinationAddress")
	private String etherDestinationAddress;

	@JsonProperty("etherSourceAddress")
	private String etherSourceAddress;

	/**
	 * Indicates the protocol carried over the Ethernet layer. Permitted values: -
	 * IPV4 - IPV6 See note.
	 */
	public enum EtherTypeEnum {
		IPV4("IPV4"),

		IPV6("IPV6");

		private final String value;

		EtherTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static EtherTypeEnum fromValue(final String text) {
			for (final EtherTypeEnum b : EtherTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("etherType")
	private EtherTypeEnum etherType;

	@JsonProperty("vlanTag")
	@Valid
	private List<String> vlanTag;

	/**
	 * Indicates the L4 protocol, For IPv4 this corresponds to the field called
	 * \"Protocol\" to identify the next level protocol. For IPv6 this corresponds
	 * to the field is called the \"Next Header\" field. Permitted values: Any
	 * keyword defined in the IANA protocol registry, e.g.: TCP UDP ICMP See note.
	 */
	public enum ProtocolEnum {
		TCP("TCP"),

		UDP("UDP"),

		ICMP("ICMP");

		private final String value;

		ProtocolEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ProtocolEnum fromValue(final String text) {
			for (final ProtocolEnum b : ProtocolEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("protocol")
	private ProtocolEnum protocol;

	@JsonProperty("dscp")
	private String dscp;

	@JsonProperty("sourcePortRange")
	private PortRange sourcePortRange;

	@JsonProperty("destinationPortRange")
	private PortRange destinationPortRange;

	@JsonProperty("sourceIpAddressPrefix")
	private String sourceIpAddressPrefix;

	@JsonProperty("destinationIpAddressPrefix")
	private String destinationIpAddressPrefix;

	@JsonProperty("extendedCriteria")
	@Valid
	private List<Mask> extendedCriteria;

}
