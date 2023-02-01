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
package com.ubiqube.etsi.mano.dao.mano.sol009.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
@Entity
public class WimSpecificInfo {
	// Added.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@OneToOne
	private MscsNums maxMscsNums;

	@OneToOne
	private MscsNums maxMsncNum;

	/**
	 * List of protocols of particular layers used to realize an MSCS that are
	 * supported by the WIM. Permitted values: - EVPN_BGP_MPLS: L2 MSCS realized by
	 * BGP MPLS-based Ethernet VPN (EVPN) as specified in IETF RFC 7432. -
	 * EVPN_VPWS: L2 MSCS realized by EVPN Virtual Private Wire Service (VPWS) as
	 * specified in IETF RFC 8214. - VPLS_BGP: L2 MSCS realized by Virtual Private
	 * LAN Service (VPLS) using BGP as specified in IETF RFC 4761 and IETF RFC. -
	 * VPLS_LDP_L2TP: L2 MSCS realized by VPLS using Label Distribution Protocol
	 * (LDP) Layer 2 Tunnelling Protocol (L2TP) as specified in IETF RFC 4762 and
	 * IETF RFC 6074. - VPWS_LDP_L2TP: L2 MSCS realized by VPWS using LDP L2TP as
	 * specified in IETF RFC 6074. - BGP_IP_VPN: L3 MSCS realized by BGP/MPLS based
	 * IP VPN as specified in IETF RFC 4364.
	 */
	public enum MscsLayerProtocolSupportEnum {
		EVPN_BGP_MPLS("EVPN_BGP_MPLS"),

		EVPN_VPWS("EVPN_VPWS"),

		VPLS_BGP("VPLS_BGP"),

		VPLS_LDP_L2TP("VPLS_LDP_L2TP"),

		VPWS_LDP_L2TP("VPWS_LDP_L2TP"),

		BGP_IP_VPN("BGP_IP_VPN");

		private final String value;

		MscsLayerProtocolSupportEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static MscsLayerProtocolSupportEnum fromValue(final String text) {
			for (final MscsLayerProtocolSupportEnum b : MscsLayerProtocolSupportEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	private MscsLayerProtocolSupportEnum mscsLayerProtocolSupport;

}
