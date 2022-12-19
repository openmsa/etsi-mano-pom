package com.ubiqube.etsi.mano.dao.mano.v2;

public enum ProtocolEnum {
	TCP("TCP"),

	UDP("UDP"),

	SCTP("SCTP");

	private String value;

	ProtocolEnum(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static ProtocolEnum fromValue(final String text) {
		for (final ProtocolEnum b : ProtocolEnum.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

}
