package com.ubiqube.api.rs.endpoints.nfvo;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.CommonProperties;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JacksonFeature implements Feature {

	@Override
	public boolean configure(FeatureContext _context) {
		final String disableMoxy = CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
				+ _context.getConfiguration().getRuntimeType().name().toLowerCase();
		_context.property(disableMoxy, true);

		_context.register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
		return true;
	}

}
