/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.parser.tosca.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
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
		if (value instanceof final ObjectNode on) {
			value = on.get("value");
		}
		final String val = String.valueOf(value).replace("\"", "");
		return new ActionInputValue(val);
	}
}
