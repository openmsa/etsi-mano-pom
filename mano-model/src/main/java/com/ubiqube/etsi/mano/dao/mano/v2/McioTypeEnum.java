package com.ubiqube.etsi.mano.dao.mano.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum McioTypeEnum {
	DEPLOYMENT("Deployment"),

	STATEFULSET("Statefulset");

	private final String value;

	McioTypeEnum(final String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static McioTypeEnum fromValue(final String text) {
		for (final McioTypeEnum b : McioTypeEnum.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
