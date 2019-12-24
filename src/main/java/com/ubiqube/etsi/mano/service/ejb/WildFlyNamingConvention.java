package com.ubiqube.etsi.mano.service.ejb;

import java.util.Properties;

import javax.naming.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.service.Configuration;

public class WildFlyNamingConvention implements EjbNamingConvention {

	private static final Logger LOG = LoggerFactory.getLogger(WildFlyNamingConvention.class);

	@Override
	public String getEjbName(final String appName, final String moduleName, final String beanName, final Class viewName) {
		final StringBuilder sb = new StringBuilder();
		sb.append(appName).append('/');
		sb.append(moduleName).append('/');
		sb.append('/');
		sb.append(beanName).append('!').append(viewName.getName());
		return sb.toString();
	}

	@Override
	public Properties getConnectionProperties(final Configuration _configuration) {
		final Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put(Context.PROVIDER_URL, _configuration.get("remote.ejb.url"));
		props.put(Context.SECURITY_PRINCIPAL, _configuration.build("remote.ejb.user").withDefault("ncroot").build());
		props.put(Context.SECURITY_CREDENTIALS, _configuration.build("remote.ejb.password").withDefault("ubiqube").build());

		props.put("jboss.naming.client.ejb.context", true);
		LOG.debug("Properties: {}", props);
		return props;
	}

}
