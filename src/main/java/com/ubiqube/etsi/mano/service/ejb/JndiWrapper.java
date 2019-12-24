package com.ubiqube.etsi.mano.service.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.Configuration;

public class JndiWrapper {
	private final InitialContext context;

	public JndiWrapper(final Configuration _configuration) {
		final Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, _configuration.get("remote.ejb.url"));
		// create the InitialContext
		try {
			context = new javax.naming.InitialContext(props);
		} catch (final NamingException e) {
			throw new GenericException(e);
		}
	}

	public Object lookup(final String url) {
		try {
			return context.lookup(url);
		} catch (final NamingException e) {
			throw new GenericException(e);
		}
	}
}
