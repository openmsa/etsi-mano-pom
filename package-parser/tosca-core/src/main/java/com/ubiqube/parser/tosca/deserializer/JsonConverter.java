package com.ubiqube.parser.tosca.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.ubiqube.parser.tosca.ParseException;

public class JsonConverter extends StdConverter<Object, String> {
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convert(final Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new ParseException(e);
		}
	}
}