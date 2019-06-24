package com.ubiqube.etsi.mano.repository;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class JndiWrapper {
	private final InitialContext context;

	public JndiWrapper() {
		final Hashtable<String, String> props = new Hashtable<String, String>();

		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, "jnp://10.11.1.102:9199");
		// create the InitialContext
		try {
			context = new javax.naming.InitialContext(props);
		} catch (final NamingException e) {
			throw new GenericException(e);
		}
	}

	public Object lookup(String url) {
		try {
			return context.lookup(url);
		} catch (final NamingException e) {
			throw new GenericException(e);
		}
	}
}
