package com.ubiqube.etsi.mano.service.ejb;

import java.util.Properties;

import com.ubiqube.etsi.mano.service.Configuration;

public interface EjbNamingConvention {

	String getEjbName(String appName, String moduleName, String beanName, Class viewName);

	Properties getConnectionProperties(Configuration configuration);
}
