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
package com.ubiqube.etsi.mano.dao.mano.sol009.logm;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
@Entity
public class LogReport {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull
	@OneToOne
	private ManoManagedObjectReference objectInstanceId;

	/**
	 * The trigger for the compilation of the log file. Permitted values: -
	 * ON_DEMAND: created based on explicit request by a client. - AUTOMATIC:
	 * created according to the logging job compilation configuration.
	 */
	public enum CompilationTriggerEnum {
		ON_DEMAND("ON_DEMAND"),

		AUTOMATIC("AUTOMATIC");

		private String value;

		CompilationTriggerEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static CompilationTriggerEnum fromValue(final String text) {
			for (final CompilationTriggerEnum b : CompilationTriggerEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@NotNull
	private CompilationTriggerEnum compilationTrigger;

	@NotNull
	private OffsetDateTime readyTime;

	private OffsetDateTime expiryTime;

	private Integer fileSize;

	@NotNull
	private String fileFormat;

	@NotNull
	private LogReportFileLocationInfo fileLocationInfo;

	@NotNull
	private LogReportSecurityAndIntegrityInfo securityAndIntegrityInfo;

}
