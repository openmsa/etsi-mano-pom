package com.ubiqube.etsi.mano.dao.mano.v2;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AffectedVipCp implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String cpInstanceId;

	private String cpdId;

	private String vnfdId;

	/**
	 * Signals the type of change. Permitted values: - ADDED - REMOVED - MODIFIED
	 */
	public enum ChangeTypeEnum {
		ADDED("ADDED"),

		REMOVED("REMOVED"),

		MODIFIED("MODIFIED");

		private final String value;

		ChangeTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		public static ChangeTypeEnum fromValue(final String text) {
			for (final ChangeTypeEnum b : ChangeTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	private ChangeTypeEnum changeType;

}
