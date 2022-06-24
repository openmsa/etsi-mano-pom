package com.ubiqube.etsi.mano.dao.mano.nsd.upd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author olivier
 *
 */
public enum StopType {
	FORCEFUL("FORCEFUL"),
	GRACEFUL("GRACEFUL");

	private String value;

	StopType(final String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static StopType fromValue(final String text) {
		for (final StopType b : StopType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
