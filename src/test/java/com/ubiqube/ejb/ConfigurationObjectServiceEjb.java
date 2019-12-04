package com.ubiqube.ejb;

import java.util.List;
import java.util.Map;

import com.ubiqube.api.entities.configuration.object.ConfigurationObject;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.configuration.object.ConfigurationObjectService;
import com.ubiqube.etsi.mano.repository.JndiWrapper;

public class ConfigurationObjectServiceEjb implements ConfigurationObjectService {

	private final ConfigurationObjectService configurationObjectService;

	public ConfigurationObjectServiceEjb(JndiWrapper _jndiWrapper) {
		configurationObjectService = (ConfigurationObjectService) _jndiWrapper.lookup("ubi-jentreprise/ConfigurationObjectBean/remote-com.ubiqube.api.interfaces.configuration.object.ConfigurationObjectService");
	}

	@Override
	public void clearObject(long arg0, String arg1) throws ServiceException {
		configurationObjectService.clearObject(arg0, arg1);
	}

	@Override
	public void createConfigObject(long arg0, String arg1, Map<String, String> arg2) throws ServiceException {
		configurationObjectService.createConfigObject(arg0, arg1, arg2);
	}

	@Override
	public List<ConfigurationObject> listConfigurationObjectVariable(long arg0) throws ServiceException {
		return configurationObjectService.listConfigurationObjectVariable(arg0);
	}

	@Override
	public Map<String, String> retrieveConfigurationObjects(long arg0) throws ServiceException {
		return configurationObjectService.retrieveConfigurationObjects(arg0);
	}

}
