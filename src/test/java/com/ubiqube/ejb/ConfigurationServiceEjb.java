package com.ubiqube.ejb;

import java.util.List;

import com.ubiqube.api.commons.id.ProfileId;
import com.ubiqube.api.entities.configuration.FileBasedConfiguration;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.exception.configuration.ConfigurationException;
import com.ubiqube.api.interfaces.configuration.ConfigurationService;
import com.ubiqube.api.ws.interfaces.configuration.template.ConfigurationTemplateWSInterface.TemplateOrder;
import com.ubiqube.etsi.mano.service.ejb.JndiWrapper;

public class ConfigurationServiceEjb implements ConfigurationService {
	private final ConfigurationService configurationService;

	public ConfigurationServiceEjb(final JndiWrapper _jndiWrapper) {
		configurationService = (ConfigurationService) _jndiWrapper.lookup("ubi-jentreprise/ConfigurationManagerBean/remote-com.ubiqube.api.interfaces.configuration.ConfigurationService");
	}

	@Override
	public void attachDeviceToProfile(final long arg0, final long arg1, final String arg2) throws ServiceException {
		configurationService.attachDeviceToProfile(arg0, arg1, arg2);
	}

	@Override
	public void attachFiles(final String[] arg0, final TemplateOrder arg1, final FileBasedConfiguration arg2, final String arg3) throws ServiceException, ConfigurationException {
		configurationService.attachFiles(arg0, arg1, arg2, arg3);
	}

	@Override
	public void attachFilesToDevice(final long arg0, final String[] arg1, final TemplateOrder arg2, final String arg3) throws ServiceException {
		configurationService.attachFilesToDevice(arg0, arg1, arg2, arg3);
	}

	@Override
	public void attachFilesToProfile(final long arg0, final String[] arg1, final TemplateOrder arg2, final String arg3) throws ServiceException {
		configurationService.attachFilesToProfile(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean attachFreeFileToDevice(final Long arg0, final String arg1, final String arg2) throws ServiceException {
		return configurationService.attachFreeFileToDevice(arg0, arg1, arg2);
	}

	@Override
	public ProfileId createConfigurationProfile(final long arg0, final String arg1, final String arg2, final String arg3) throws ServiceException {
		return configurationService.createConfigurationProfile(arg0, arg1, arg2, arg3);
	}

	@Override
	public void deleteConfigurationElement(final FileBasedConfiguration arg0, final String arg1) throws ConfigurationException {
		configurationService.deleteConfigurationElement(arg0, arg1);
	}

	@Override
	public void detachDeviceFromProfile(final long arg0, final long arg1, final String arg2) throws ServiceException {
		configurationService.detachDeviceFromProfile(arg0, arg1, arg2);
	}

	@Override
	public void detachFiles(final long arg0, final String[] arg1, final FileBasedConfiguration arg2, final String arg3) throws ServiceException, ConfigurationException {
		configurationService.detachFiles(arg0, arg1, arg2, arg3);
	}

	@Override
	public void detachFilesFromDevice(final long arg0, final String[] arg1, final String arg2) throws ServiceException {
		configurationService.detachFilesFromDevice(arg0, arg1, arg2);
	}

	@Override
	public void detachFilesFromProfile(final long arg0, final String[] arg1, final String arg2) throws ServiceException {
		configurationService.detachFilesFromProfile(arg0, arg1, arg2);
	}

	@Override
	public FileBasedConfiguration getConfigurationElement(final String arg0) throws ConfigurationException {
		return configurationService.getConfigurationElement(arg0);
	}

	@Override
	public boolean isConfigurationElement(final String arg0) throws ConfigurationException {
		return configurationService.isConfigurationElement(arg0);
	}

	@Override
	public boolean isElementIsUsedInConfigurationElements(final String arg0) throws ConfigurationException {
		return configurationService.isElementIsUsedInConfigurationElements(arg0);
	}

	@Override
	public List<FileBasedConfiguration> listConfigurationElements() throws ConfigurationException {
		return configurationService.listConfigurationElements();
	}

	@Override
	public List<String> listConfigurationFilesByDeviceId(final long arg0) throws ServiceException {
		return configurationService.listConfigurationFilesByDeviceId(arg0);
	}

	@Override
	public List<String> listFilesByProfileId(final long arg0) throws ServiceException {
		return configurationService.listFilesByProfileId(arg0);
	}

	@Override
	public void reinitAttachmentCache() throws ServiceException {
		configurationService.reinitAttachmentCache();
	}

	@Override
	public void saveConfigurationElement(final FileBasedConfiguration arg0, final String arg1) throws ConfigurationException {
		configurationService.saveConfigurationElement(arg0, arg1);
	}

	@Override
	public void updateConfigurationElement(final FileBasedConfiguration arg0, final String arg1) throws ConfigurationException {
		configurationService.updateConfigurationElement(arg0, arg1);
	}

	@Override
	public List<String> whereElementIsUsedInConfigurationElements(final String arg0) throws ConfigurationException {
		return configurationService.whereElementIsUsedInConfigurationElements(arg0);
	}

}
