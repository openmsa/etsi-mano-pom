package com.ubiqube.parser.tosca.deserializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubiqube.parser.tosca.ConditionListDefintion;
import com.ubiqube.parser.tosca.ConditionValue;

public class ConditionValueDeserializer extends StdDeserializer<ConditionValue> {
	
	/** Serial. */
	private static final long serialVersionUID = 1L;

	public ConditionValueDeserializer() {
		this(null);
	}

	protected ConditionValueDeserializer(final Class<?> vc) {
		super(vc);
	}
	
	@Override
	public ConditionValue deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
		final ObjectNode value = p.getCodec().readTree(p);
		return new ConditionValue("");
	}

}
