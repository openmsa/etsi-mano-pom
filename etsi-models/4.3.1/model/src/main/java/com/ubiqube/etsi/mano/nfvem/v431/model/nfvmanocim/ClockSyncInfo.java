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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents parameters for connecting to an NTP server. * NOTE:
 * Either ipAddress or hostname shall be set, but not both at the same time.
 */
@Schema(description = "This type represents parameters for connecting to an NTP server. * NOTE: Either ipAddress or hostname shall be set, but not both at the same time. ")
@Validated

public class ClockSyncInfo {
	/**
	 * Type of clock synchronization. Permitted values: - NTP: For Network Time
	 * Protocol (NTP) based clock synchronization. - OTHER: For other types of clock
	 * synchronization.
	 */
	public enum TypeEnum {
		NTP("NTP"),

		OTHER("OTHER");

		private final String value;

		TypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static TypeEnum fromValue(final String text) {
			for (final TypeEnum b : TypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("type")
	private TypeEnum type = null;

	@JsonProperty("ntpServerInfo")
	private ClockSyncInfoNtpServerInfo ntpServerInfo = null;

	@JsonProperty("otherClockSyncParams")
	private Map<String, String> otherClockSyncParams = null;

	public ClockSyncInfo type(final TypeEnum type) {
		this.type = type;
		return this;
	}

	/**
	 * Type of clock synchronization. Permitted values: - NTP: For Network Time
	 * Protocol (NTP) based clock synchronization. - OTHER: For other types of clock
	 * synchronization.
	 *
	 * @return type
	 **/
	@Schema(required = true, description = "Type of clock synchronization. Permitted values:   - NTP: For Network Time Protocol (NTP) based clock synchronization.   - OTHER: For other types of clock synchronization. ")
	@NotNull

	public TypeEnum getType() {
		return type;
	}

	public void setType(final TypeEnum type) {
		this.type = type;
	}

	public ClockSyncInfo ntpServerInfo(final ClockSyncInfoNtpServerInfo ntpServerInfo) {
		this.ntpServerInfo = ntpServerInfo;
		return this;
	}

	/**
	 * Get ntpServerInfo
	 *
	 * @return ntpServerInfo
	 **/
	@Schema(description = "")

	@Valid
	public ClockSyncInfoNtpServerInfo getNtpServerInfo() {
		return ntpServerInfo;
	}

	public void setNtpServerInfo(final ClockSyncInfoNtpServerInfo ntpServerInfo) {
		this.ntpServerInfo = ntpServerInfo;
	}

	public ClockSyncInfo otherClockSyncParams(final Map<String, String> otherClockSyncParams) {
		this.otherClockSyncParams = otherClockSyncParams;
		return this;
	}

	/**
	 * Get otherClockSyncParams
	 *
	 * @return otherClockSyncParams
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getOtherClockSyncParams() {
		return otherClockSyncParams;
	}

	public void setOtherClockSyncParams(final Map<String, String> otherClockSyncParams) {
		this.otherClockSyncParams = otherClockSyncParams;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ClockSyncInfo clockSyncInfo = (ClockSyncInfo) o;
		return Objects.equals(this.type, clockSyncInfo.type) &&
				Objects.equals(this.ntpServerInfo, clockSyncInfo.ntpServerInfo) &&
				Objects.equals(this.otherClockSyncParams, clockSyncInfo.otherClockSyncParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, ntpServerInfo, otherClockSyncParams);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ClockSyncInfo {\n");

		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    ntpServerInfo: ").append(toIndentedString(ntpServerInfo)).append("\n");
		sb.append("    otherClockSyncParams: ").append(toIndentedString(otherClockSyncParams)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
