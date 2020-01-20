package com.ubiqube.etsi.mano.service.ejb;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.Configuration;

@Service
public class EjbProvider {

	private static final Logger LOG = LoggerFactory.getLogger(EjbProvider.class);
	private EjbNamingConvention ejbNamingConvention;
	private final String appName;
	private final String moduleName;
	private final Configuration configuration;
	private InitialContext context;

	public EjbProvider(final Configuration _configuration) {
		configuration = _configuration;
		final String url = _configuration.build("remote.ejb.url").withDefault("no EJB URL ?").build();
		final boolean isWildfly = url.startsWith("http-remoting://");
		appName = _configuration.build("remote.ejb.appname").withDefault("ubi-jentreprise").build();
		moduleName = _configuration.build("remote.ejb.modulename").withDefault("ubi-api-ejb").build();
		LOG.info("EJB wildfly: {}, appName: {}, moduleName: {}", isWildfly, appName, moduleName);
		if (isWildfly) {
			ejbNamingConvention = new WildFlyNamingConvention();
		} else {
			ejbNamingConvention = new JbossNamingConvention();
		}
		final Properties props = ejbNamingConvention.getConnectionProperties(configuration);
		try {
			context = new javax.naming.InitialContext(props);
		} catch (final NamingException e) {
			throw new GenericException(e);
		}
	}

	public <T> T getEjbService(final String beanName, final Class<T> viewName) {
		try {
			final String ejbUrl = getEjbName(beanName, viewName);
			LOG.info("EJB URL: {}", ejbUrl);
			return (T) context.lookup(ejbUrl);
		} catch (final NamingException e) {
			throw new GenericException(e);
		}

	}

	private String getEjbName(final String beanName, final Class<?> viewName) {
		return ejbNamingConvention.getEjbName(appName, moduleName, beanName, viewName);
	}

}
