package com.ubiqube.parser.tosca.deserializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubiqube.parser.tosca.ActionInputValue;

public class ActionInputDeserializer extends StdDeserializer<ActionInputValue> {
	
	/** Serial. */
	private static final long serialVersionUID = 1L;

	public ActionInputDeserializer() {
		this(null);
	}

	protected ActionInputDeserializer(final Class<?> vc) {
		super(vc);
	}
	
	@Override
	public ActionInputValue deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
		Object value = p.getCodec().readTree(p);
		if(value instanceof ObjectNode) {
			value = ((ObjectNode)value).get("value");
		}
		String val = String.valueOf(value).replaceAll("\"", "");
		return new ActionInputValue(val);
	}
}
