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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents criteria for logging jobs to collect logged system
 * events of the NFV-MANO functional entity. * NOTE: The set of properties and
 * values for this attribute are assumed to be known to the consumer by means
 * defined outside of the present document.
 */
@Schema(description = "This type represents criteria for logging jobs to collect logged system events of the NFV-MANO functional entity. * NOTE: The set of properties and values for this attribute are assumed to be known to the consumer         by means defined outside of the present document.")
@Validated

public class LoggingJobSystemCriteria {
	@JsonProperty("systemLogs")
	private Map<String, String> systemLogs = null;

	@JsonProperty("severityLevelScheme")
	private String severityLevelScheme = null;

	@JsonProperty("severityLevel")
	private BigDecimal severityLevel = null;

	public LoggingJobSystemCriteria systemLogs(final Map<String, String> systemLogs) {
		this.systemLogs = systemLogs;
		return this;
	}

	/**
	 * Get systemLogs
	 *
	 * @return systemLogs
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Map<String, String> getSystemLogs() {
		return systemLogs;
	}

	public void setSystemLogs(final Map<String, String> systemLogs) {
		this.systemLogs = systemLogs;
	}

	public LoggingJobSystemCriteria severityLevelScheme(final String severityLevelScheme) {
		this.severityLevelScheme = severityLevelScheme;
		return this;
	}

	/**
	 * Identifies a severity level scheme. The default value is \"rfc5424\", which
	 * represents the set of values specified in the clause 6.2.1, table 2 of IETF
	 * RFC 5424. Other values may be used to signal different schemes.
	 *
	 * @return severityLevelScheme
	 **/
	@Schema(description = "Identifies a severity level scheme. The default value is \"rfc5424\", which represents the set of values specified in the clause 6.2.1, table 2 of IETF RFC 5424. Other values may be used to signal different schemes.")

	public String getSeverityLevelScheme() {
		return severityLevelScheme;
	}

	public void setSeverityLevelScheme(final String severityLevelScheme) {
		this.severityLevelScheme = severityLevelScheme;
	}

	public LoggingJobSystemCriteria severityLevel(final BigDecimal severityLevel) {
		this.severityLevel = severityLevel;
		return this;
	}

	/**
	 * The severity level, which determines the severity of the system messages to
	 * collect. The NFV-MANO functional entity shall collect system log messages, as
	 * indicated by the \"systemLogs\" attribute, with severity levels lower (i.e.,
	 * more severe) or equal to the value provided by this present attribute.
	 *
	 * @return severityLevel
	 **/
	@Schema(required = true, description = "The severity level, which determines the severity of the system messages to collect. The NFV-MANO functional entity shall collect system log messages, as indicated by the \"systemLogs\" attribute, with severity levels lower (i.e., more severe) or equal to the value provided by this present attribute.")
	@NotNull

	@Valid
	public BigDecimal getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(final BigDecimal severityLevel) {
		this.severityLevel = severityLevel;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final LoggingJobSystemCriteria loggingJobSystemCriteria = (LoggingJobSystemCriteria) o;
		return Objects.equals(this.systemLogs, loggingJobSystemCriteria.systemLogs) &&
				Objects.equals(this.severityLevelScheme, loggingJobSystemCriteria.severityLevelScheme) &&
				Objects.equals(this.severityLevel, loggingJobSystemCriteria.severityLevel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(systemLogs, severityLevelScheme, severityLevel);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class LoggingJobSystemCriteria {\n");

		sb.append("    systemLogs: ").append(toIndentedString(systemLogs)).append("\n");
		sb.append("    severityLevelScheme: ").append(toIndentedString(severityLevelScheme)).append("\n");
		sb.append("    severityLevel: ").append(toIndentedString(severityLevel)).append("\n");
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
