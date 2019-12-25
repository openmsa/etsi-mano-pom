package com.ubiqube.etsi.mano.service.ejb;

import java.util.Properties;

import javax.naming.Context;

import com.ubiqube.etsi.mano.service.Configuration;

public class JbossNamingConvention implements EjbNamingConvention {

	// ubi-jentreprise/DeviceBean/remote-com.ubiqube.api.interfaces.device.DeviceService
	@Override
	public String getEjbName(final String appName, final String moduleName, final String beanName, final Class<?> viewName) {
		final StringBuilder sb = new StringBuilder();
		sb.append(appName).append('/');
		sb.append(beanName).append('/');
		sb.append("remote-").append(viewName.getName());
		return sb.toString();
	}

	@Override
	public Properties getConnectionProperties(final Configuration _configuration) {
		final Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, _configuration.get("remote.ejb.url"));
		return props;
	}

}
