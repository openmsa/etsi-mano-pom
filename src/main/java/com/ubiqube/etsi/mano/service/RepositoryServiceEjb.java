package com.ubiqube.etsi.mano.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.netcelo.ses.entities.actor.Actor;
import com.ubiqube.api.commons.id.ManagerId;
import com.ubiqube.api.entities.repository.RepositoryElement;
import com.ubiqube.api.entities.repository.RepositoryElement.RepositoryElementType;
import com.ubiqube.api.entities.repository.RepositoryElementUpload;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.exception.configuration.ConfigurationException;
import com.ubiqube.api.exception.repository.RepositoryException;
import com.ubiqube.api.interfaces.repository.RepositoryService;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.JndiWrapper;

/**
 * Implementation of a Device service thru remote EJB call. NOTE it's just a
 * delegate of the interface, feel free to regenerate for correcting arguments.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class RepositoryServiceEjb implements RepositoryService {
	protected static final String NCROOT = "ncroot";
	protected static final String MANO = "MANO";
	protected static final String PROCESS_BASE_PATH = "Process";
	protected static final String PROCESS_NFVO_BASE_PATH = PROCESS_BASE_PATH + "/NFVO";
	protected static final String PROCESS_VNF_VNF_PCKGM_BASE_PATH = PROCESS_NFVO_BASE_PATH + "/VNF_PCKGM";
	protected static final String DATAFILE_BASE_PATH = "Datafiles";
	protected static final String NVFO_DATAFILE_BASE_PATH = "Datafiles/NFVO";
	protected static final String REPOSITORY_NVFO_DATAFILE_BASE_PATH = "Datafiles/NFVO/vnf_packages";
	protected static final String REPOSITORY_SUBSCRIPTION_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/subscriptions";
	protected static final String REPOSITORY_NSD_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/nsd";
	protected static final String REPOSITORY_VNF_INSTANCE_DATAFILE_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/vnf_instances";
	protected static final String REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/ns_instances";
	protected static final String REPOSITORY_LCM_OP_OCCS_DATAFILE_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/lcm-op-occs";
	/** EJB Instance. */
	private RepositoryService repositoryService;

	/**
	 * Constructor.
	 */
	public RepositoryServiceEjb(final JndiWrapper _jndiWrapper) {
		try {
			repositoryService = (RepositoryService) _jndiWrapper.lookup("ubi-jentreprise/RepositoryManagerBean/remote-com.ubiqube.api.interfaces.repository.RepositoryService");
			init();
		} catch (final ServiceException e) {
			throw new GenericException(e);
		}
	}

	/**
	 * MSA related stuff. TODO move this method elsewhere.
	 *
	 * @throws ServiceException
	 */
	private void init() throws ServiceException {
		if (!repositoryService.exists(PROCESS_BASE_PATH)) {
			repositoryService.addDirectory(PROCESS_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(PROCESS_NFVO_BASE_PATH)) {
			repositoryService.addDirectory(PROCESS_NFVO_BASE_PATH, "", MANO, NCROOT);
		}

		if (!repositoryService.exists(PROCESS_VNF_VNF_PCKGM_BASE_PATH)) {
			repositoryService.addDirectory(PROCESS_VNF_VNF_PCKGM_BASE_PATH, "", MANO, NCROOT);
		}

		if (!repositoryService.exists(DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(NVFO_DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(NVFO_DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_NVFO_DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_NVFO_DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_SUBSCRIPTION_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_SUBSCRIPTION_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_NSD_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_NSD_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_VNF_INSTANCE_DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_VNF_INSTANCE_DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_LCM_OP_OCCS_DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_LCM_OP_OCCS_DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
	}

	@Override
	public void addCustomer(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.addCustomer(_arg0, _arg1);
	}

	@Override
	public void addDirectory(final String _arg0, final String _arg1, final String _arg2, final String _arg3) throws ServiceException {
		repositoryService.addDirectory(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public void addFile(final String _arg0, final String _arg1, final String _arg2, final byte[] _arg3, final String _arg4) throws ServiceException {
		repositoryService.addFile(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public void addFile(final String _arg0, final String _arg1, final String _arg2, final String _arg3, final String _arg4) throws ServiceException {
		repositoryService.addFile(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public void addLargeFileContent(final String _arg0, final String _arg1, final String _arg2, final byte[] _arg3, final String _arg4) throws ServiceException {
		repositoryService.addLargeFileContent(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public void addManufacturer(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.addManufacturer(_arg0, _arg1);
	}

	@Override
	public void addModel(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.addModel(_arg0, _arg1);
	}

	@Override
	public void addOperator(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.addOperator(_arg0, _arg1);
	}

	@Override
	public boolean addRepositoryElementContent(final RepositoryElement _arg0, final byte[] _arg1, final String _arg2) throws RepositoryException {
		return repositoryService.addRepositoryElementContent(_arg0, _arg1, _arg2);
	}

	@Override
	public void addRepositoryElementContent(final RepositoryElementUpload _arg0, final String _arg1, final String _arg2) throws RepositoryException {
		repositoryService.addRepositoryElementContent(_arg0, _arg1, _arg2);
	}

	@Override
	public void copyRepositoryDirContent(final RepositoryElement _arg0, final RepositoryElement _arg1, final String _arg2) throws RepositoryException {
		repositoryService.copyRepositoryDirContent(_arg0, _arg1, _arg2);
	}

	@Override
	public void copyRepositoryElementContent(final RepositoryElement _arg0, final String _arg1, final String _arg2) throws RepositoryException {
		repositoryService.copyRepositoryElementContent(_arg0, _arg1, _arg2);
	}

	@Override
	public void deleteRepositoryElement(final RepositoryElement _arg0, final String _arg1) throws RepositoryException {
		repositoryService.deleteRepositoryElement(_arg0, _arg1);
	}

	@Override
	public List<String> doSearch(final String _arg0, final String _arg1) throws ServiceException {
		return repositoryService.doSearch(_arg0, _arg1);
	}

	@Override
	public boolean exists(final String _arg0) throws ServiceException {
		return repositoryService.exists(_arg0);
	}

	@Override
	public String getAbsolutePath(final String _arg0) throws RepositoryException {
		return repositoryService.getAbsolutePath(_arg0);
	}

	@Override
	public List<String> getAvailableRepositories() throws RepositoryException {
		return repositoryService.getAvailableRepositories();
	}

	@Override
	public List<RepositoryElement> getChildren(final String _arg0, final boolean _arg1, final String _arg2) throws RepositoryException {
		return repositoryService.getChildren(_arg0, _arg1, _arg2);
	}

	@Override
	public List<RepositoryElement> getChildren(final String _arg0, final boolean _arg1) throws RepositoryException {
		return repositoryService.getChildren(_arg0, _arg1);
	}

	@Override
	public List<RepositoryElementType> getChildrenType(final Actor _arg0, final RepositoryElement _arg1) throws RepositoryException {
		return repositoryService.getChildrenType(_arg0, _arg1);
	}

	@Override
	public String getConfigurationRoot() {
		return repositoryService.getConfigurationRoot();
	}

	@Override
	public RepositoryElement getElement(final String _arg0) throws RepositoryException {
		return repositoryService.getElement(_arg0);
	}

	@Override
	public String getElementAbsoluteURI(final RepositoryElement _arg0) throws RepositoryException {
		return repositoryService.getElementAbsoluteURI(_arg0);
	}

	@Override
	public String getNextUploadName(final RepositoryElement _arg0, final String _arg1, final String _arg2) throws RepositoryException {
		return repositoryService.getNextUploadName(_arg0, _arg1, _arg2);
	}

	@Override
	public byte[] getRepositoryElementContent(final RepositoryElement _arg0) throws RepositoryException {
		return repositoryService.getRepositoryElementContent(_arg0);
	}

	@Override
	public String[] getRepositoryKeysByCustomization(final String _arg0) throws ServiceException {
		return repositoryService.getRepositoryKeysByCustomization(_arg0);
	}

	@Override
	public String getRepositoryName(final RepositoryElement _arg0) throws RepositoryException {
		return repositoryService.getRepositoryName(_arg0);
	}

	@Override
	public byte[] getSVNFileContent(final String _arg0) throws RepositoryException {
		return repositoryService.getSVNFileContent(_arg0);
	}

	@Override
	public List<String> getUnallowedEntityList(final long _arg0, final String _arg1) throws ConfigurationException {
		return repositoryService.getUnallowedEntityList(_arg0, _arg1);
	}

	@Override
	public String getUploadURIFromFileURI(final String _arg0) {
		return repositoryService.getUploadURIFromFileURI(_arg0);
	}

	@Override
	public List<String> getUsage(final long _arg0, final String _arg1) throws ConfigurationException {
		return repositoryService.getUsage(_arg0, _arg1);
	}

	@Override
	public boolean hasModificationRights(final Actor _arg0, final String _arg1) throws ConfigurationException {
		return repositoryService.hasModificationRights(_arg0, _arg1);
	}

	@Override
	public boolean isBinary(final String _arg0) throws RepositoryException {
		return repositoryService.isBinary(_arg0);
	}

	@Override
	public boolean isReadOnly(final RepositoryElement _arg0) throws RepositoryException {
		return repositoryService.isReadOnly(_arg0);
	}

	@Override
	public boolean modifyRepositoryElementContent(final ManagerId _arg0, final RepositoryElement _arg1, final byte[] _arg2, final String _arg3) throws RepositoryException {
		return repositoryService.modifyRepositoryElementContent(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public void modifyRepositoryElementContent(final ManagerId _arg0, final RepositoryElementUpload _arg1, final String _arg2, final String _arg3) throws RepositoryException {
		repositoryService.modifyRepositoryElementContent(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public Properties readPropertiesFile(final String _arg0) throws ServiceException, FileNotFoundException, IOException {
		return repositoryService.readPropertiesFile(_arg0);
	}

	@Override
	public void removeCustomer(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.removeCustomer(_arg0, _arg1);
	}

	@Override
	public void removeDirectory(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.removeDirectory(_arg0, _arg1);
	}

	@Override
	public void removeFile(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.removeFile(_arg0, _arg1);
	}

	@Override
	public void removeManufacturer(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.removeManufacturer(_arg0, _arg1);
	}

	@Override
	public void removeModel(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.removeModel(_arg0, _arg1);
	}

	@Override
	public void removeOperator(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.removeOperator(_arg0, _arg1);
	}

	@Override
	public void saveRepositoryElementMetadata(final RepositoryElement _arg0) throws RepositoryException {
		repositoryService.saveRepositoryElementMetadata(_arg0);
	}

	@Override
	public void updateComment(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.updateComment(_arg0, _arg1);
	}

	@Override
	public void updateFileContent(final String _arg0, final byte[] _arg1, final String _arg2) throws ServiceException {
		repositoryService.updateFileContent(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateFileContent(final String _arg0, final String _arg1, final String _arg2) throws ServiceException {
		repositoryService.updateFileContent(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateLargeFileContent(final String _arg0, final byte[] _arg1, final String _arg2) throws ServiceException {
		repositoryService.updateLargeFileContent(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateTag(final String _arg0, final String _arg1) throws ServiceException {
		repositoryService.updateTag(_arg0, _arg1);
	}

	@Override
	public void writePropertiesFile(final String _arg0, final Properties _arg1) throws ServiceException, IOException {
		repositoryService.writePropertiesFile(_arg0, _arg1);
	}

	@Override
	public StringBuffer yangFilecompilation(final String _arg0) {
		return repositoryService.yangFilecompilation(_arg0);
	}
}
