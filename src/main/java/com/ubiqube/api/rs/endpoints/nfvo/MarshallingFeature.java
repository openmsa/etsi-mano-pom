package com.ubiqube.api.rs.endpoints.nfvo;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.api.ejb.nfvo.JacksonJsonProviderAtRest;

public class MarshallingFeature implements Feature {
	private final static Logger LOG = LoggerFactory.getLogger(MarshallingFeature.class);

	@Override
	public boolean configure(FeatureContext _context) {
		_context.register(JacksonJsonProviderAtRest.class, MessageBodyReader.class, MessageBodyWriter.class);
		LOG.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FEATURE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return true;
	}

}
