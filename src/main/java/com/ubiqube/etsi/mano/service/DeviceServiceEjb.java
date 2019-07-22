package com.ubiqube.etsi.mano.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.netcelo.commun.database.exceptions.DataBaseFailureException;
import com.netcelo.commun.entites.EntitesException;
import com.netcelo.commun.util.IpAddress;
import com.netcelo.ses.entities.actor.Actor;
import com.netcelo.ses.entities.asset.Asset;
import com.netcelo.ses.entities.device.DeviceFeatures;
import com.netcelo.ses.entities.device.EthernetInterface;
import com.netcelo.ses.entities.device.Interface;
import com.netcelo.ses.entities.device.LightSite;
import com.netcelo.ses.entities.device.Manufacturer;
import com.netcelo.ses.entities.device.Model;
import com.netcelo.ses.entities.device.Model.Flag;
import com.netcelo.ses.entities.device.ProductPartNumber;
import com.netcelo.ses.entities.device.SdRoute;
import com.netcelo.ses.entities.device.SdType;
import com.netcelo.ses.entities.device.Site;
import com.netcelo.ses.entities.device.SiteConst.Feature;
import com.netcelo.ses.entities.misc.Address;
import com.netcelo.ses.entities.misc.Contact;
import com.netcelo.ses.entities.misc.GeoCoordinate;
import com.netcelo.ses.entities.user.Customer;
import com.ubiqube.api.commons.FileInfo;
import com.ubiqube.api.commons.JsonGenericStatus;
import com.ubiqube.api.commons.ProvisioningStatus;
import com.ubiqube.api.commons.UpdateStatus;
import com.ubiqube.api.commons.id.CustomerId;
import com.ubiqube.api.commons.id.DeviceId;
import com.ubiqube.api.commons.id.ManagerId;
import com.ubiqube.api.entities.configuration.FileBasedConfiguration;
import com.ubiqube.api.entities.configuration.FileBasedConfiguration.SortFileType;
import com.ubiqube.api.entities.device.SimpleDevice;
import com.ubiqube.api.exception.DatabaseSystemException;
import com.ubiqube.api.exception.DuplicateReferenceException;
import com.ubiqube.api.exception.IllegalRebootDateException;
import com.ubiqube.api.exception.IllegalSdRouteCreationException;
import com.ubiqube.api.exception.ObjectNotFoundException;
import com.ubiqube.api.exception.ProvisioningException;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.device.DeviceService;
import com.ubiqube.api.secEngine.result.SecEngineResult;
import com.ubiqube.common.ftp.FtpAccount;
import com.ubiqube.etsi.mano.repository.JndiWrapper;

import net.sf.json.JSONObject;

/**
 * Implementation of a Device service thru remote EJB call. NOTE it's just a
 * delegate of the interface, feel free to regenerate for correcting arguments.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class DeviceServiceEjb implements DeviceService {
	/** EJB proxy. */
	DeviceService deviceService;

	/**
	 * Constructor.
	 */
	public DeviceServiceEjb(JndiWrapper _jndiWrapper) {
		deviceService = (DeviceService) _jndiWrapper.lookup("ubi-jentreprise/DeviceBean/remote-com.ubiqube.api.interfaces.device.DeviceService");
	}

	@Override
	public void applyConfToOneDevice(ManagerId _arg0, DeviceId _arg1) throws DataBaseFailureException, ServiceException {
		deviceService.applyConfToOneDevice(_arg0, _arg1);
	}

	@Override
	public void applyConfToOneDeviceByManagerReference(String _arg0, DeviceId _arg1) throws DataBaseFailureException, ServiceException {
		deviceService.applyConfToOneDeviceByManagerReference(_arg0, _arg1);
	}

	@Override
	public void attachDefaultRepositoryFiles(Customer _arg0, Site _arg1, String _arg2) throws ServiceException {
		deviceService.attachDefaultRepositoryFiles(_arg0, _arg1, _arg2);
	}

	@Override
	public UpdateStatus checkAllUpdate(DeviceId _arg0, String _arg1) throws ServiceException {
		return deviceService.checkAllUpdate(_arg0, _arg1);
	}

	@Override
	public UpdateStatus checkAsynchronousVerbStatus(DeviceId _arg0, String _arg1) throws ServiceException {
		return deviceService.checkAsynchronousVerbStatus(_arg0, _arg1);
	}

	@Override
	public int countHADevice(CustomerId _arg0) throws ServiceException {
		return deviceService.countHADevice(_arg0);
	}

	@Override
	public Site createDevice(long _arg0, Site _arg1, String _arg2) throws ServiceException {
		return deviceService.createDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public Site createDevice(String _arg0, Site _arg1, String _arg2) throws ServiceException {
		return deviceService.createDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public Site createManagedDevice(String _arg0, String _arg1, String _arg2, String _arg3, Contact _arg4, String _arg5, String _arg6, String _arg7, boolean _arg8, boolean _arg9, boolean _arg10, boolean _arg11, boolean _arg12, int _arg13, int _arg14, EthernetInterface _arg15, EthernetInterface _arg16, SdRoute[] _arg17) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.createManagedDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11, _arg12, _arg13, _arg14, _arg15, _arg16, _arg17);
	}

	@Override
	public Site createManagedDeviceWithManagerReference(String _arg0, String _arg1, String _arg2, String _arg3, com.ubiqube.api.ws.entities.contact.Contact _arg4, String _arg5, String _arg6, String _arg7, boolean _arg8, boolean _arg9, boolean _arg10, boolean _arg11, boolean _arg12, int _arg13, int _arg14, com.ubiqube.api.ws.entities.device.EthernetInterface _arg15, com.ubiqube.api.ws.entities.device.EthernetInterface _arg16, com.ubiqube.api.ws.entities.device.SdRoute[] _arg17) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.createManagedDeviceWithManagerReference(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11, _arg12, _arg13, _arg14, _arg15, _arg16, _arg17);
	}

	@Override
	public DeviceId createMonitoringDevice(String _arg0, String _arg1, String _arg2, String _arg3, Contact _arg4, int _arg5, int _arg6, EthernetInterface _arg7) throws ServiceException {
		return deviceService.createMonitoringDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
	}

	@Override
	public Site createTemplateManagedDevice(long _arg0, String _arg1, String _arg2, int _arg3, int _arg4, String _arg5, String _arg6, String _arg7, boolean _arg8, boolean _arg9, boolean _arg10, boolean _arg11, boolean _arg12, String _arg13, String _arg14) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.createTemplateManagedDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11, _arg12, _arg13, _arg14);
	}

	@Override
	public void deleteDevice(DeviceId _arg0, boolean _arg1, String _arg2) throws ServiceException {
		deviceService.deleteDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public void deleteDevice(DeviceId _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.deleteDevice(_arg0, _arg1);
	}

	@Override
	public void deleteDevice(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.deleteDevice(_arg0, _arg1);
	}

	@Override
	public void deleteDevice(String _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.deleteDevice(_arg0, _arg1);
	}

	@Override
	public void doArchivedLogExtractionByDeviceId(DeviceId _arg0, List<String> _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doArchivedLogExtractionByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doCancelRebootByDeviceId(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doCancelRebootByDeviceId(_arg0, _arg1);
	}

	@Override
	public JSONObject doCheckProvisioningByDeviceId(DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.doCheckProvisioningByDeviceId(_arg0);
	}

	@Override
	public void doDeleteFileonRouterByDeviceId(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doDeleteFileonRouterByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doDeleteTARFileonRouterByDeviceId(long _arg0, String _arg1) throws ServiceException {
		deviceService.doDeleteTARFileonRouterByDeviceId(_arg0, _arg1);
	}

	@Override
	public SecEngineResult doExecuteCommandByDeviceId(long _arg0, ManagerId _arg1, String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public SecEngineResult doExecuteCommandByDeviceReference(String _arg0, String _arg1, String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandByDeviceReference(_arg0, _arg1, _arg2);
	}

	@Override
	public SecEngineResult doExecuteCommandOnAddonByDeviceId(long _arg0, ManagerId _arg1, String _arg2, String _arg3) throws ServiceException {
		return deviceService.doExecuteCommandOnAddonByDeviceId(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public SecEngineResult doExecuteCommandOnAddonByDeviceReference(String _arg0, String _arg1, String _arg2, String _arg3) throws ServiceException {
		return deviceService.doExecuteCommandOnAddonByDeviceReference(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public SecEngineResult doExecuteCommandOnCueByDeviceId(long _arg0, ManagerId _arg1, String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandOnCueByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public SecEngineResult doExecuteCommandOnCueByDeviceReference(String _arg0, String _arg1, String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandOnCueByDeviceReference(_arg0, _arg1, _arg2);
	}

	@Override
	public void doFirmwareUpdateByDeviceId(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doFirmwareUpdateByDeviceId(_arg0, _arg1);
	}

	@Override
	public String doGenTemplate(long _arg0, String _arg1, String _arg2) throws ServiceException, ObjectNotFoundException {
		return deviceService.doGenTemplate(_arg0, _arg1, _arg2);
	}

	@Override
	public void doImmediateReboot(long _arg0) throws ServiceException, ObjectNotFoundException, IllegalRebootDateException {
		deviceService.doImmediateReboot(_arg0);
	}

	@Override
	public void doInitialConnectionByDeviceId(long _arg0, String _arg1) throws ServiceException {
		deviceService.doInitialConnectionByDeviceId(_arg0, _arg1);
	}

	@Override
	public JsonGenericStatus doPing(String _arg0) throws ServiceException {
		return deviceService.doPing(_arg0);
	}

	@Override
	public void doProvisioning(String _arg0, String _arg1) throws ServiceException, ProvisioningException {
		deviceService.doProvisioning(_arg0, _arg1);
	}

	@Override
	public void doProvisioningByAddress(String _arg0, String _arg1, String _arg2) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningByAddress(_arg0, _arg1, _arg2);
	}

	@Override
	public void doProvisioningByDeviceId(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doProvisioningByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doProvisioningById(DeviceId _arg0, long _arg1) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningById(_arg0, _arg1);
	}

	@Override
	public void doProvisioningByIdAndOtherParams(DeviceId _arg0, long _arg1, String _arg2, String _arg3, String _arg4, String _arg5) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningByIdAndOtherParams(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
	}

	@Override
	public void doProvisioningByIdByAddress(DeviceId _arg0, long _arg1, String _arg2) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningByIdByAddress(_arg0, _arg1, _arg2);
	}

	@Override
	public void doPushConfigToAddonByDeviceId(long _arg0, String _arg1, String _arg2) throws ServiceException {
		deviceService.doPushConfigToAddonByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public void doPushConfigToDeviceByDeviceId(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doPushConfigToDeviceByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doPushConfigToStartupByDeviceId(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doPushConfigToStartupByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doScheduleRebootByDeviceId(long _arg0, Date _arg1, String _arg2) throws ServiceException, ObjectNotFoundException, IllegalRebootDateException {
		deviceService.doScheduleRebootByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public void doSendFilesToCueByDeviceId(long _arg0) throws ServiceException, ObjectNotFoundException {
		deviceService.doSendFilesToCueByDeviceId(_arg0);
	}

	@Override
	public void doSendFilesToCueByDeviceReference(String _arg0) throws ServiceException, ObjectNotFoundException {
		deviceService.doSendFilesToCueByDeviceReference(_arg0);
	}

	@Override
	public SecEngineResult doUpdateConfiguration(long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.doUpdateConfiguration(_arg0);
	}

	@Override
	public SecEngineResult doUpdateConfiguration(String _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.doUpdateConfiguration(_arg0);
	}

	@Override
	public boolean exist(long _arg0) throws ServiceException {
		return deviceService.exist(_arg0);
	}

	@Override
	public boolean exist(String _arg0) throws ServiceException {
		return deviceService.exist(_arg0);
	}

	@Override
	public DeviceId findDeviceIdByReference(String _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.findDeviceIdByReference(_arg0);
	}

	@Override
	public String findSitePingStatus(long _arg0) throws EntitesException {
		return deviceService.findSitePingStatus(_arg0);
	}

	@Override
	public void forcePurgeOfArchivedLogs(DeviceId _arg0) throws ServiceException {
		deviceService.forcePurgeOfArchivedLogs(_arg0);
	}

	@Override
	public Actor getActorByUbiID(String _arg0) throws ServiceException {
		return deviceService.getActorByUbiID(_arg0);
	}

	@Override
	public UpdateStatus getArchivedLogExtractionStatusByDeviceId(DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getArchivedLogExtractionStatusByDeviceId(_arg0);
	}

	@Override
	public boolean getAutomaticalUpdate(long _arg0) throws ServiceException {
		return deviceService.getAutomaticalUpdate(_arg0);
	}

	@Override
	public Map<Long, Manufacturer> getAvailableManufacturers() throws ServiceException {
		return deviceService.getAvailableManufacturers();
	}

	@Override
	public Map<Long, Model> getAvailableModels(Flag _arg0, boolean _arg1) throws ServiceException {
		return deviceService.getAvailableModels(_arg0, _arg1);
	}

	@Override
	public Map<Long, Model> getAvailableModels(long _arg0) throws ServiceException {
		return deviceService.getAvailableModels(_arg0);
	}

	@Override
	public Map<Long, Model> getAvailableModels(Manufacturer _arg0) throws ServiceException {
		return deviceService.getAvailableModels(_arg0);
	}

	@Override
	public Map<Long, Model> getAvailableModelsEx(long _arg0) throws ServiceException {
		return deviceService.getAvailableModelsEx(_arg0);
	}

	@Override
	public CustomerId getCustomerId(long _arg0) throws ServiceException {
		return deviceService.getCustomerId(_arg0);
	}

	@Override
	public void getDataFilesByDeviceId(long _arg0, String _arg1, String _arg2, String _arg3) throws ServiceException {
		deviceService.getDataFilesByDeviceId(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public List<String> getDefaultRepositoryFilesToAttach(Site _arg0) throws ServiceException {
		return deviceService.getDefaultRepositoryFilesToAttach(_arg0);
	}

	@Override
	public UpdateStatus getDeleteFileonRouterStatusByDeviceId(long _arg0) throws ServiceException {
		return deviceService.getDeleteFileonRouterStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getDeleteTARFileonRouterStatusByDeviceId(long _arg0) throws ServiceException {
		return deviceService.getDeleteTARFileonRouterStatusByDeviceId(_arg0);
	}

	@Override
	public String getDeviceFieldByFieldName(long _arg0, String _arg1) throws ServiceException {
		return deviceService.getDeviceFieldByFieldName(_arg0, _arg1);
	}

	@Override
	public DeviceId getDeviceId(long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getDeviceId(_arg0);
	}

	@Override
	public DeviceId getDeviceId(String _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getDeviceId(_arg0);
	}

	@Override
	public long getDeviceModel(DeviceId _arg0) throws ServiceException {
		return deviceService.getDeviceModel(_arg0);
	}

	@Override
	public SimpleDevice getDeviceModeleAndManId(DeviceId _arg0) throws ServiceException {
		return deviceService.getDeviceModeleAndManId(_arg0);
	}

	@Override
	public String getDeviceReference(DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getDeviceReference(_arg0);
	}

	@Override
	public String getDeviceReference(long _arg0) throws ServiceException {
		return deviceService.getDeviceReference(_arg0);
	}

	@Override
	public String getExternalReference(long _arg0) throws ServiceException {
		return deviceService.getExternalReference(_arg0);
	}

	@Override
	public UpdateStatus getFirmwareUpdateStatusByDeviceId(long _arg0) throws ServiceException {
		return deviceService.getFirmwareUpdateStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getGetDataFilesStatusByDeviceId(long _arg0) throws ServiceException {
		return deviceService.getGetDataFilesStatusByDeviceId(_arg0);
	}

	@Override
	public DeviceId getHAPeer(long _arg0) throws ServiceException {
		return deviceService.getHAPeer(_arg0);
	}

	@Override
	public int getHAType(long _arg0) throws ServiceException {
		return deviceService.getHAType(_arg0);
	}

	@Override
	public String getHostname(long _arg0) throws ServiceException {
		return deviceService.getHostname(_arg0);
	}

	@Override
	public DeviceId getIdByExternalReference(String _arg0) throws ServiceException {
		return deviceService.getIdByExternalReference(_arg0);
	}

	@Override
	public List<LightSite> getLightSiteByCustomer(Customer _arg0, Feature _arg1) throws ServiceException {
		return deviceService.getLightSiteByCustomer(_arg0, _arg1);
	}

	@Override
	public int getLogRetentionPeriod(long _arg0) throws ServiceException {
		return deviceService.getLogRetentionPeriod(_arg0);
	}

	@Override
	public boolean getManageCertificate(long _arg0) throws ServiceException {
		return deviceService.getManageCertificate(_arg0);
	}

	@Override
	public IpAddress getManagementIpAddress(long _arg0) throws ServiceException {
		return deviceService.getManagementIpAddress(_arg0);
	}

	@Override
	public Manufacturer getManufacturerFromId(long _arg0) throws ServiceException {
		return deviceService.getManufacturerFromId(_arg0);
	}

	@Override
	public long getManufacturerId(String _arg0) throws ServiceException {
		return deviceService.getManufacturerId(_arg0);
	}

	@Override
	public Map<Long, Manufacturer> getManufacturers() throws ServiceException {
		return deviceService.getManufacturers();
	}

	@Override
	public Model getModelFromId(long _arg0, long _arg1) throws ServiceException {
		return deviceService.getModelFromId(_arg0, _arg1);
	}

	@Override
	public String getOperatorPrefix(long _arg0) throws ServiceException {
		return deviceService.getOperatorPrefix(_arg0);
	}

	@Override
	public Set<ProductPartNumber> getPPNlist(long _arg0) throws ServiceException {
		return deviceService.getPPNlist(_arg0);
	}

	@Override
	public Set<ProductPartNumber> getPPNlistByCustomer(long _arg0) throws ServiceException {
		return deviceService.getPPNlistByCustomer(_arg0);
	}

	@Override
	public ProvisioningStatus getProvisioningStatus(String _arg0) throws ServiceException {
		return deviceService.getProvisioningStatus(_arg0);
	}

	@Override
	public ProvisioningStatus getProvisioningStatusById(long _arg0) throws ServiceException {
		return deviceService.getProvisioningStatusById(_arg0);
	}

	@Override
	public UpdateStatus getPushConfigToAddonStatusByDeviceId(long _arg0, String _arg1) throws ServiceException {
		return deviceService.getPushConfigToAddonStatusByDeviceId(_arg0, _arg1);
	}

	@Override
	public UpdateStatus getPushConfigToDeviceStatusByDeviceId(long _arg0) throws ServiceException {
		return deviceService.getPushConfigToDeviceStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getPushConfigToStartupStatusByDeviceId(long _arg0) throws ServiceException {
		return deviceService.getPushConfigToStartupStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getRebootStatus(long _arg0) throws ServiceException {
		return deviceService.getRebootStatus(_arg0);
	}

	@Override
	public FileInfo getRouterFileInfoByDeviceId(long _arg0, String _arg1) throws ServiceException, ObjectNotFoundException {
		return deviceService.getRouterFileInfoByDeviceId(_arg0, _arg1);
	}

	@Override
	public SdType getSdType(int _arg0, int _arg1) throws IllegalArgumentException, ServiceException {
		return deviceService.getSdType(_arg0, _arg1);
	}

	@Override
	public SdType getSdType(String _arg0, String _arg1) throws ServiceException {
		return deviceService.getSdType(_arg0, _arg1);
	}

	@Override
	public UpdateStatus getSendDataFilesStatusByDeviceId(long _arg0) throws ServiceException {
		return deviceService.getSendDataFilesStatusByDeviceId(_arg0);
	}

	@Override
	public String getSerialNumber(long _arg0) throws ServiceException {
		return deviceService.getSerialNumber(_arg0);
	}

	@Override
	public boolean getSilverMonitoring(long _arg0) throws ServiceException {
		return deviceService.getSilverMonitoring(_arg0);
	}

	@Override
	public DeviceFeatures getSiteFeature(long _arg0, long _arg1) throws ServiceException, IllegalArgumentException {
		return deviceService.getSiteFeature(_arg0, _arg1);
	}

	@Override
	public GeoCoordinate getSiteGeoLocalization(long _arg0) throws ServiceException {
		return deviceService.getSiteGeoLocalization(_arg0);
	}

	@Override
	public String getSnmpCommunity(long _arg0) throws ServiceException {
		return deviceService.getSnmpCommunity(_arg0);
	}

	@Override
	public SecEngineResult getStagingConfiguration(String _arg0, String _arg1) throws ServiceException {
		return deviceService.getStagingConfiguration(_arg0, _arg1);
	}

	@Override
	public SecEngineResult getStagingConfigurationById(DeviceId _arg0, ManagerId _arg1) throws ServiceException {
		return deviceService.getStagingConfigurationById(_arg0, _arg1);
	}

	@Override
	public String getStagingModel(long _arg0) throws ServiceException {
		return deviceService.getStagingModel(_arg0);
	}

	@Override
	public UpdateStatus getUpdateConfigurationStatus(long _arg0) throws ServiceException {
		return deviceService.getUpdateConfigurationStatus(_arg0);
	}

	@Override
	public UpdateStatus getUpdateConfigurationStatus(String _arg0) throws ServiceException {
		return deviceService.getUpdateConfigurationStatus(_arg0);
	}

	@Override
	public SecEngineResult getUpdateStatus(DeviceId _arg0, String _arg1) throws ServiceException {
		return deviceService.getUpdateStatus(_arg0, _arg1);
	}

	@Override
	public SecEngineResult getUpdateStatus(String _arg0, String _arg1) throws ServiceException {
		return deviceService.getUpdateStatus(_arg0, _arg1);
	}

	@Override
	public boolean isDevice(DeviceId _arg0) {
		return deviceService.isDevice(_arg0);
	}

	@Override
	public boolean isModelAvailableForWizard(long _arg0, long _arg1) throws ServiceException {
		return deviceService.isModelAvailableForWizard(_arg0, _arg1);
	}

	@Override
	public List<String> listArchivedLogFilesByDeviceId(DeviceId _arg0, Date _arg1, Date _arg2) throws ServiceException, ObjectNotFoundException {
		return deviceService.listArchivedLogFilesByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public List<Asset> listDeviceAssetDetailsByCustomer(long _arg0, String _arg1) throws ServiceException {
		return deviceService.listDeviceAssetDetailsByCustomer(_arg0, _arg1);
	}

	@Override
	public List<Site> listDeviceByManager(long _arg0, String _arg1, String _arg2) throws ServiceException {
		return deviceService.listDeviceByManager(_arg0, _arg1, _arg2);
	}

	@Override
	public List<Site> listDeviceByManager(long _arg0) throws ServiceException, DataBaseFailureException {
		return deviceService.listDeviceByManager(_arg0);
	}

	@Override
	public List<DeviceFeatures> listDeviceFeaturesByCustomer(long _arg0) throws ServiceException {
		return deviceService.listDeviceFeaturesByCustomer(_arg0);
	}

	@Override
	public List<Site> listDevicePingStatusByCustomer(long _arg0) throws ServiceException {
		return deviceService.listDevicePingStatusByCustomer(_arg0);
	}

	@Override
	public List<Site> listDevicePingStatusByGes(long _arg0) throws ServiceException {
		return deviceService.listDevicePingStatusByGes(_arg0);
	}

	@Override
	public SimpleDevice[] listDevicesByEntityAndManufacturer(String _arg0, int _arg1, boolean _arg2) throws DatabaseSystemException, ServiceException {
		return deviceService.listDevicesByEntityAndManufacturer(_arg0, _arg1, _arg2);
	}

	@Override
	public SimpleDevice[] listDevicesByEntityAndModels(String _arg0, List<Model> _arg1) throws DatabaseSystemException, ServiceException {
		return deviceService.listDevicesByEntityAndModels(_arg0, _arg1);
	}

	@Override
	public List<String> listFilesFromDevice(long _arg0, String _arg1, SortFileType _arg2) throws ServiceException {
		return deviceService.listFilesFromDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public List<String> listFilesFromDevice(long _arg0) throws ServiceException {
		return deviceService.listFilesFromDevice(_arg0);
	}

	@Override
	public List<String> listFilesFromDeviceIncludingAttachedProfile(long _arg0, String _arg1, SortFileType _arg2) throws ServiceException {
		return deviceService.listFilesFromDeviceIncludingAttachedProfile(_arg0, _arg1, _arg2);
	}

	@Override
	public List<DeviceFeatures> listProfileAndDeviceFeatureByCustomerId(long _arg0) throws IllegalArgumentException, ServiceException {
		return deviceService.listProfileAndDeviceFeatureByCustomerId(_arg0);
	}

	@Override
	public List<Site> listSitesByCustomer(Customer _arg0) throws ServiceException {
		return deviceService.listSitesByCustomer(_arg0);
	}

	@Override
	public boolean manufacturerExists(long _arg0) throws ServiceException {
		return deviceService.manufacturerExists(_arg0);
	}

	@Override
	public void markAsProvisionedByDeviceId(long _arg0) throws ServiceException {
		deviceService.markAsProvisionedByDeviceId(_arg0);
	}

	@Override
	public void markAsProvisionedByDeviceReference(String _arg0) throws ServiceException {
		deviceService.markAsProvisionedByDeviceReference(_arg0);
	}

	@Override
	public boolean modelExists(long _arg0, long _arg1) throws ServiceException {
		return deviceService.modelExists(_arg0, _arg1);
	}

	@Override
	public byte[] processScriptTemplate(ManagerId _arg0, DeviceId _arg1, String _arg2) throws ServiceException {
		return deviceService.processScriptTemplate(_arg0, _arg1, _arg2);
	}

	@Override
	public FileBasedConfiguration readConfDeviceXml(long _arg0) throws ServiceException {
		return deviceService.readConfDeviceXml(_arg0);
	}

	@Override
	public FileBasedConfiguration readConfDeviceXml(ManagerId _arg0, DeviceId _arg1) throws ServiceException {
		return deviceService.readConfDeviceXml(_arg0, _arg1);
	}

	@Override
	public Site readDevice(DeviceId _arg0) throws ServiceException {
		return deviceService.readDevice(_arg0);
	}

	@Override
	public Site readDevice(long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.readDevice(_arg0);
	}

	@Override
	public Site readDeviceAndCheckCredentialsInSMS(long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.readDeviceAndCheckCredentialsInSMS(_arg0);
	}

	@Override
	public Site readDeviceByReference(String _arg0) throws ServiceException {
		return deviceService.readDeviceByReference(_arg0);
	}

	@Override
	public Interface readInterface(long _arg0, char _arg1) throws ServiceException {
		return deviceService.readInterface(_arg0, _arg1);
	}

	@Override
	public GeoCoordinate readLatitudeAndLongitude(DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.readLatitudeAndLongitude(_arg0);
	}

	@Override
	public String readSdAdminPassword(long _arg0) throws ServiceException {
		return deviceService.readSdAdminPassword(_arg0);
	}

	@Override
	public Address readSiteAddress(long _arg0) throws ServiceException {
		return deviceService.readSiteAddress(_arg0);
	}

	@Override
	public HashMap<String, String> readSiteByExternalReference(String _arg0) throws ServiceException {
		return deviceService.readSiteByExternalReference(_arg0);
	}

	@Override
	public void saveConfDeviceXml(ManagerId _arg0, DeviceId _arg1, FileBasedConfiguration _arg2) throws ServiceException {
		deviceService.saveConfDeviceXml(_arg0, _arg1, _arg2);
	}

	@Override
	public void sendDataFiles(long _arg0) throws ServiceException, ObjectNotFoundException {
		deviceService.sendDataFiles(_arg0);
	}

	@Override
	public void setAutomaticalUpdate(long _arg0, boolean _arg1, String _arg2) throws ServiceException {
		deviceService.setAutomaticalUpdate(_arg0, _arg1, _arg2);
	}

	@Override
	public void setAutomaticalUpdateByExternalReference(String _arg0, boolean _arg1, String _arg2) throws ServiceException {
		deviceService.setAutomaticalUpdateByExternalReference(_arg0, _arg1, _arg2);
	}

	@Override
	public boolean setDHCPpublicToDevice(String _arg0, DeviceId _arg1, IpAddress _arg2, IpAddress _arg3, IpAddress _arg4, IpAddress _arg5, IpAddress _arg6, IpAddress _arg7, String _arg8) throws ServiceException {
		return deviceService.setDHCPpublicToDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
	}

	@Override
	public boolean setDHCPpublicToDevice(String _arg0, long _arg1, IpAddress _arg2, IpAddress _arg3, IpAddress _arg4, IpAddress _arg5, IpAddress _arg6, IpAddress _arg7, String _arg8) throws ServiceException {
		return deviceService.setDHCPpublicToDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
	}

	@Override
	public void setDeviceFieldByFieldName(long _arg0, String _arg1, String _arg2, String _arg3) throws ServiceException {
		deviceService.setDeviceFieldByFieldName(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public void setExtendedAtrtibutes(long _arg0, Object _arg1, Object _arg2) throws ServiceException, DataBaseFailureException {
		deviceService.setExtendedAtrtibutes(_arg0, _arg1, _arg2);
	}

	@Override
	public void setHostname(long _arg0, String _arg1, String _arg2) throws ServiceException {
		deviceService.setHostname(_arg0, _arg1, _arg2);
	}

	@Override
	public void setLogRetentionPeriod(long _arg0, int _arg1, String _arg2) throws ServiceException {
		deviceService.setLogRetentionPeriod(_arg0, _arg1, _arg2);
	}

	@Override
	public void setManageCertificate(long _arg0, boolean _arg1, String _arg2) throws ServiceException {
		deviceService.setManageCertificate(_arg0, _arg1, _arg2);
	}

	@Override
	public void setSerialNumber(long _arg0, String _arg1, String _arg2) throws ServiceException {
		deviceService.setSerialNumber(_arg0, _arg1, _arg2);
	}

	@Override
	public void setSilverMonitoring(long _arg0, boolean _arg1, String _arg2) throws ServiceException {
		deviceService.setSilverMonitoring(_arg0, _arg1, _arg2);
	}

	@Override
	public void setSiteGeoLocalization(long _arg0, GeoCoordinate _arg1) throws ServiceException {
		deviceService.setSiteGeoLocalization(_arg0, _arg1);
	}

	@Override
	public void setSnmpCommunity(long _arg0, String _arg1) throws ServiceException {
		deviceService.setSnmpCommunity(_arg0, _arg1);
	}

	@Override
	public void setStagingModel(long _arg0, String _arg1, String _arg2) throws ServiceException {
		deviceService.setStagingModel(_arg0, _arg1, _arg2);
	}

	@Override
	public String storeStagingConfigurationToFtpServer(String _arg0, String _arg1, String _arg2, String _arg3, FtpAccount _arg4) throws ServiceException {
		return deviceService.storeStagingConfigurationToFtpServer(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public boolean testHostnameIsUsed(String _arg0, String _arg1, String _arg2) throws ServiceException {
		return deviceService.testHostnameIsUsed(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateAddress(long _arg0, String _arg1, String _arg2, String _arg3, String _arg4, String _arg5, String _arg6) throws ServiceException, ObjectNotFoundException {
		deviceService.updateAddress(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
	}

	@Override
	public Site updateDevice(Site _arg0, String _arg1) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.updateDevice(_arg0, _arg1);
	}

	@Override
	public void updateDeviceCredentials(long _arg0, String _arg1, String _arg2, String _arg3, String _arg4) throws ServiceException, ObjectNotFoundException {
		deviceService.updateDeviceCredentials(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public void updateDeviceExternalReference(long _arg0, String _arg1, String _arg2) throws ServiceException {
		deviceService.updateDeviceExternalReference(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateDeviceManagementIpAddress(long _arg0, String _arg1, String _arg2, String _arg3) throws ServiceException {
		deviceService.updateDeviceManagementIpAddress(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public DeviceId updateDeviceManagementPort(long _arg0, int _arg1, String _arg2) throws ServiceException {
		return deviceService.updateDeviceManagementPort(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateDeviceName(long _arg0, String _arg1, String _arg2) throws ServiceException {
		deviceService.updateDeviceName(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateHACluster(long _arg0, int _arg1, long _arg2, int _arg3) throws ServiceException {
		deviceService.updateHACluster(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public void updateLatitudeAndLongitude(long _arg0, double _arg1, double _arg2) throws ServiceException, ObjectNotFoundException {
		deviceService.updateLatitudeAndLongitude(_arg0, _arg1, _arg2);
	}

	@Override
	public DeviceId updateNAT(long _arg0, boolean _arg1, String _arg2) throws ServiceException {
		return deviceService.updateNAT(_arg0, _arg1, _arg2);
	}

	@Override
	public DeviceId updateNATRouterAddress(long _arg0, String _arg1, String _arg2) throws ServiceException, ObjectNotFoundException {
		return deviceService.updateNATRouterAddress(_arg0, _arg1, _arg2);
	}

}
