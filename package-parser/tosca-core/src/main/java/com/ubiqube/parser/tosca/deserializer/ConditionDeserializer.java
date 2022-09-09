package com.ubiqube.parser.tosca.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubiqube.parser.tosca.ConditionListDefintion;

public class ConditionDeserializer extends StdDeserializer<ConditionListDefintion> {
	
	/** Serial. */
	private static final long serialVersionUID = 1L;

	public ConditionDeserializer() {
		this(null);
	}

	protected ConditionDeserializer(final Class<?> vc) {
		super(vc);
	}
	
	@Override
	public ConditionListDefintion deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
		final ObjectNode value = p.getCodec().readTree(p);
		return new ConditionListDefintion(p.getCodec().treeToValue(value, Map.class));
	}

}
