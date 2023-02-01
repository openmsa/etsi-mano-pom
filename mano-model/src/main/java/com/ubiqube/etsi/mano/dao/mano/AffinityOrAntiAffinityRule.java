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
package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class AffinityOrAntiAffinityRule implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	private List<String> vnfdId = null;

	@JsonProperty("vnfProfileId")
	@Valid
	private List<String> vnfProfileId = null;

	@JsonProperty("vnfInstanceId")
	@Valid
	private List<String> vnfInstanceId = null;

	/**
	 * The type of the constraint. Permitted values: AFFINITY ANTI_AFFINITY.
	 */
	public enum AffinityOrAntiAffiintyEnum {
		AFFINITY("AFFINITY"),

		ANTI_AFFINITY("ANTI_AFFINITY");

		private String value;

		AffinityOrAntiAffiintyEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static AffinityOrAntiAffiintyEnum fromValue(final String text) {
			for (final AffinityOrAntiAffiintyEnum b : AffinityOrAntiAffiintyEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("affinityOrAntiAffiinty")
	private AffinityOrAntiAffiintyEnum affinityOrAntiAffiinty = null;

	/**
	 * Specifies the scope of the rule where the placement constraint applies.
	 * Permitted values: NFVI_POP ZONE ZONE_GROUP NFVI_NODE.
	 */
	public enum ScopeEnum {
		NFVI_POP("NFVI_POP"),

		ZONE("ZONE"),

		ZONE_GROUP("ZONE_GROUP"),

		NFVI_NODE("NFVI_NODE");

		private String value;

		ScopeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ScopeEnum fromValue(final String text) {
			for (final ScopeEnum b : ScopeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("scope")
	private ScopeEnum scope = null;

}
