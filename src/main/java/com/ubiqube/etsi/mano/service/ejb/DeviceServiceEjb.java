package com.ubiqube.etsi.mano.service.ejb;

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
import com.netcelo.ses.entities.device.Sd.DeviceNature;
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
import com.ubiqube.api.exception.DatabaseServiceException;
import com.ubiqube.api.exception.DatabaseSystemException;
import com.ubiqube.api.exception.DuplicateReferenceException;
import com.ubiqube.api.exception.IllegalRebootDateException;
import com.ubiqube.api.exception.IllegalSdRouteCreationException;
import com.ubiqube.api.exception.ObjectNotFoundException;
import com.ubiqube.api.exception.ProvisioningException;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.device.DeviceService;
import com.ubiqube.api.secEngine.result.SecEngineResult;
import com.ubiqube.api.ws.entities.device.TemplateManagedDevice;
import com.ubiqube.common.ftp.FtpAccount;

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
	private final DeviceService deviceService;

	/**
	 * Constructor.
	 */
	public DeviceServiceEjb(final EjbProvider ejbn) {
		deviceService = ejbn.getEjbService("DeviceBean", DeviceService.class);
	}

	@Override
	public void applyConfToOneDevice(final ManagerId _arg0, final DeviceId _arg1) throws DataBaseFailureException, ServiceException {
		deviceService.applyConfToOneDevice(_arg0, _arg1);
	}

	@Override
	public void applyConfToOneDeviceByManagerReference(final String _arg0, final DeviceId _arg1) throws DataBaseFailureException, ServiceException {
		deviceService.applyConfToOneDeviceByManagerReference(_arg0, _arg1);
	}

	@Override
	public void attachDefaultRepositoryFiles(final Customer _arg0, final Site _arg1, final String _arg2) throws ServiceException {
		deviceService.attachDefaultRepositoryFiles(_arg0, _arg1, _arg2);
	}

	@Override
	public UpdateStatus checkAllUpdate(final DeviceId _arg0, final String _arg1) throws ServiceException {
		return deviceService.checkAllUpdate(_arg0, _arg1);
	}

	@Override
	public UpdateStatus checkAsynchronousVerbStatus(final DeviceId _arg0, final String _arg1) throws ServiceException {
		return deviceService.checkAsynchronousVerbStatus(_arg0, _arg1);
	}

	@Override
	public int countHADevice(final CustomerId _arg0) throws ServiceException {
		return deviceService.countHADevice(_arg0);
	}

	@Override
	public Site createDevice(final long _arg0, final Site _arg1, final String _arg2) throws ServiceException {
		return deviceService.createDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public Site createDevice(final String _arg0, final Site _arg1, final String _arg2) throws ServiceException {
		return deviceService.createDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public Site createManagedDevice(final String _arg0, final String _arg1, final String _arg2, final String _arg3, final Contact _arg4, final String _arg5, final String _arg6, final String _arg7, final boolean _arg8, final boolean _arg9, final boolean _arg10, final boolean _arg11, final boolean _arg12, final int _arg13, final int _arg14, final EthernetInterface _arg15, final EthernetInterface _arg16, final SdRoute[] _arg17) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.createManagedDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11, _arg12, _arg13, _arg14, _arg15, _arg16, _arg17);
	}

	@Override
	public Site createManagedDeviceWithManagerReference(final String _arg0, final String _arg1, final String _arg2, final String _arg3, final com.ubiqube.api.ws.entities.contact.Contact _arg4, final String _arg5, final String _arg6, final String _arg7, final boolean _arg8, final boolean _arg9, final boolean _arg10, final boolean _arg11, final boolean _arg12, final int _arg13, final int _arg14, final com.ubiqube.api.ws.entities.device.EthernetInterface _arg15, final com.ubiqube.api.ws.entities.device.EthernetInterface _arg16, final com.ubiqube.api.ws.entities.device.SdRoute[] _arg17) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.createManagedDeviceWithManagerReference(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11, _arg12, _arg13, _arg14, _arg15, _arg16, _arg17);
	}

	@Override
	public DeviceId createMonitoringDevice(final String _arg0, final String _arg1, final String _arg2, final String _arg3, final Contact _arg4, final int _arg5, final int _arg6, final EthernetInterface _arg7) throws ServiceException {
		return deviceService.createMonitoringDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
	}

	@Override
	public Site createTemplateManagedDevice(final long _arg0, final String _arg1, final String _arg2, final int _arg3, final int _arg4, final String _arg5, final String _arg6, final String _arg7, final boolean _arg8, final boolean _arg9, final boolean _arg10, final boolean _arg11, final boolean _arg12, final String _arg13, final String _arg14) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.createTemplateManagedDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11, _arg12, _arg13, _arg14);
	}

	@Override
	public void deleteDevice(final DeviceId _arg0, final boolean _arg1, final String _arg2) throws ServiceException {
		deviceService.deleteDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public void deleteDevice(final DeviceId _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.deleteDevice(_arg0, _arg1);
	}

	@Override
	public void deleteDevice(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.deleteDevice(_arg0, _arg1);
	}

	@Override
	public void deleteDevice(final String _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.deleteDevice(_arg0, _arg1);
	}

	@Override
	public void doArchivedLogExtractionByDeviceId(final DeviceId _arg0, final List<String> _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doArchivedLogExtractionByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doCancelRebootByDeviceId(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doCancelRebootByDeviceId(_arg0, _arg1);
	}

	@Override
	public JSONObject doCheckProvisioningByDeviceId(final DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.doCheckProvisioningByDeviceId(_arg0);
	}

	@Override
	public void doDeleteFileonRouterByDeviceId(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doDeleteFileonRouterByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doDeleteTARFileonRouterByDeviceId(final long _arg0, final String _arg1) throws ServiceException {
		deviceService.doDeleteTARFileonRouterByDeviceId(_arg0, _arg1);
	}

	@Override
	public SecEngineResult doExecuteCommandByDeviceId(final long _arg0, final ManagerId _arg1, final String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public SecEngineResult doExecuteCommandByDeviceReference(final String _arg0, final String _arg1, final String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandByDeviceReference(_arg0, _arg1, _arg2);
	}

	@Override
	public SecEngineResult doExecuteCommandOnAddonByDeviceId(final long _arg0, final ManagerId _arg1, final String _arg2, final String _arg3) throws ServiceException {
		return deviceService.doExecuteCommandOnAddonByDeviceId(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public SecEngineResult doExecuteCommandOnAddonByDeviceReference(final String _arg0, final String _arg1, final String _arg2, final String _arg3) throws ServiceException {
		return deviceService.doExecuteCommandOnAddonByDeviceReference(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public SecEngineResult doExecuteCommandOnCueByDeviceId(final long _arg0, final ManagerId _arg1, final String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandOnCueByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public SecEngineResult doExecuteCommandOnCueByDeviceReference(final String _arg0, final String _arg1, final String _arg2) throws ServiceException {
		return deviceService.doExecuteCommandOnCueByDeviceReference(_arg0, _arg1, _arg2);
	}

	@Override
	public void doFirmwareUpdateByDeviceId(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doFirmwareUpdateByDeviceId(_arg0, _arg1);
	}

	@Override
	public String doGenTemplate(final long _arg0, final String _arg1, final String _arg2) throws ServiceException, ObjectNotFoundException {
		return deviceService.doGenTemplate(_arg0, _arg1, _arg2);
	}

	@Override
	public void doImmediateReboot(final long _arg0) throws ServiceException, ObjectNotFoundException, IllegalRebootDateException {
		deviceService.doImmediateReboot(_arg0);
	}

	@Override
	public void doInitialConnectionByDeviceId(final long _arg0, final String _arg1) throws ServiceException {
		deviceService.doInitialConnectionByDeviceId(_arg0, _arg1);
	}

	@Override
	public JsonGenericStatus doPing(final String _arg0) throws ServiceException {
		return deviceService.doPing(_arg0);
	}

	@Override
	public void doProvisioning(final String _arg0, final String _arg1) throws ServiceException, ProvisioningException {
		deviceService.doProvisioning(_arg0, _arg1);
	}

	@Override
	public void doProvisioningByAddress(final String _arg0, final String _arg1, final String _arg2) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningByAddress(_arg0, _arg1, _arg2);
	}

	@Override
	public void doProvisioningByDeviceId(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doProvisioningByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doProvisioningById(final DeviceId _arg0, final long _arg1) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningById(_arg0, _arg1);
	}

	@Override
	public void doProvisioningByIdAndOtherParams(final DeviceId _arg0, final long _arg1, final String _arg2, final String _arg3, final String _arg4, final String _arg5) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningByIdAndOtherParams(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
	}

	@Override
	public void doProvisioningByIdByAddress(final DeviceId _arg0, final long _arg1, final String _arg2) throws ServiceException, ProvisioningException {
		deviceService.doProvisioningByIdByAddress(_arg0, _arg1, _arg2);
	}

	@Override
	public void doPushConfigToAddonByDeviceId(final long _arg0, final String _arg1, final String _arg2) throws ServiceException {
		deviceService.doPushConfigToAddonByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public void doPushConfigToDeviceByDeviceId(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doPushConfigToDeviceByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doPushConfigToStartupByDeviceId(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		deviceService.doPushConfigToStartupByDeviceId(_arg0, _arg1);
	}

	@Override
	public void doScheduleRebootByDeviceId(final long _arg0, final Date _arg1, final String _arg2) throws ServiceException, ObjectNotFoundException, IllegalRebootDateException {
		deviceService.doScheduleRebootByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public void doSendFilesToCueByDeviceId(final long _arg0) throws ServiceException, ObjectNotFoundException {
		deviceService.doSendFilesToCueByDeviceId(_arg0);
	}

	@Override
	public void doSendFilesToCueByDeviceReference(final String _arg0) throws ServiceException, ObjectNotFoundException {
		deviceService.doSendFilesToCueByDeviceReference(_arg0);
	}

	@Override
	public SecEngineResult doUpdateConfiguration(final long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.doUpdateConfiguration(_arg0);
	}

	@Override
	public SecEngineResult doUpdateConfiguration(final String _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.doUpdateConfiguration(_arg0);
	}

	@Override
	public boolean exist(final long _arg0) throws ServiceException {
		return deviceService.exist(_arg0);
	}

	@Override
	public boolean exist(final String _arg0) throws ServiceException {
		return deviceService.exist(_arg0);
	}

	@Override
	public DeviceId findDeviceIdByReference(final String _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.findDeviceIdByReference(_arg0);
	}

	@Override
	public String findSitePingStatus(final long _arg0) throws EntitesException {
		return deviceService.findSitePingStatus(_arg0);
	}

	@Override
	public void forcePurgeOfArchivedLogs(final DeviceId _arg0) throws ServiceException {
		deviceService.forcePurgeOfArchivedLogs(_arg0);
	}

	@Override
	public Actor getActorByUbiID(final String _arg0) throws ServiceException {
		return deviceService.getActorByUbiID(_arg0);
	}

	@Override
	public UpdateStatus getArchivedLogExtractionStatusByDeviceId(final DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getArchivedLogExtractionStatusByDeviceId(_arg0);
	}

	@Override
	public boolean getAutomaticalUpdate(final long _arg0) throws ServiceException {
		return deviceService.getAutomaticalUpdate(_arg0);
	}

	@Override
	public Map<Long, Manufacturer> getAvailableManufacturers() throws ServiceException {
		return deviceService.getAvailableManufacturers();
	}

	@Override
	public Map<Long, Model> getAvailableModels(final Flag _arg0, final boolean _arg1) throws ServiceException {
		return deviceService.getAvailableModels(_arg0, _arg1);
	}

	@Override
	public Map<Long, Model> getAvailableModels(final long _arg0) throws ServiceException {
		return deviceService.getAvailableModels(_arg0);
	}

	@Override
	public Map<Long, Model> getAvailableModels(final Manufacturer _arg0) throws ServiceException {
		return deviceService.getAvailableModels(_arg0);
	}

	@Override
	public Map<Long, Model> getAvailableModelsEx(final long _arg0) throws ServiceException {
		return deviceService.getAvailableModelsEx(_arg0);
	}

	@Override
	public CustomerId getCustomerId(final long _arg0) throws ServiceException {
		return deviceService.getCustomerId(_arg0);
	}

	@Override
	public void getDataFilesByDeviceId(final long _arg0, final String _arg1, final String _arg2, final String _arg3) throws ServiceException {
		deviceService.getDataFilesByDeviceId(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public List<String> getDefaultRepositoryFilesToAttach(final Site _arg0) throws ServiceException {
		return deviceService.getDefaultRepositoryFilesToAttach(_arg0);
	}

	@Override
	public UpdateStatus getDeleteFileonRouterStatusByDeviceId(final long _arg0) throws ServiceException {
		return deviceService.getDeleteFileonRouterStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getDeleteTARFileonRouterStatusByDeviceId(final long _arg0) throws ServiceException {
		return deviceService.getDeleteTARFileonRouterStatusByDeviceId(_arg0);
	}

	@Override
	public String getDeviceFieldByFieldName(final long _arg0, final String _arg1) throws ServiceException {
		return deviceService.getDeviceFieldByFieldName(_arg0, _arg1);
	}

	@Override
	public DeviceId getDeviceId(final long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getDeviceId(_arg0);
	}

	@Override
	public DeviceId getDeviceId(final String _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getDeviceId(_arg0);
	}

	@Override
	public long getDeviceModel(final DeviceId _arg0) throws ServiceException {
		return deviceService.getDeviceModel(_arg0);
	}

	@Override
	public SimpleDevice getDeviceModeleAndManId(final DeviceId _arg0) throws ServiceException {
		return deviceService.getDeviceModeleAndManId(_arg0);
	}

	@Override
	public String getDeviceReference(final DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.getDeviceReference(_arg0);
	}

	@Override
	public String getDeviceReference(final long _arg0) throws ServiceException {
		return deviceService.getDeviceReference(_arg0);
	}

	@Override
	public String getExternalReference(final long _arg0) throws ServiceException {
		return deviceService.getExternalReference(_arg0);
	}

	@Override
	public UpdateStatus getFirmwareUpdateStatusByDeviceId(final long _arg0) throws ServiceException {
		return deviceService.getFirmwareUpdateStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getGetDataFilesStatusByDeviceId(final long _arg0) throws ServiceException {
		return deviceService.getGetDataFilesStatusByDeviceId(_arg0);
	}

	@Override
	public DeviceId getHAPeer(final long _arg0) throws ServiceException {
		return deviceService.getHAPeer(_arg0);
	}

	@Override
	public int getHAType(final long _arg0) throws ServiceException {
		return deviceService.getHAType(_arg0);
	}

	@Override
	public String getHostname(final long _arg0) throws ServiceException {
		return deviceService.getHostname(_arg0);
	}

	@Override
	public DeviceId getIdByExternalReference(final String _arg0) throws ServiceException {
		return deviceService.getIdByExternalReference(_arg0);
	}

	@Override
	public List<LightSite> getLightSiteByCustomer(final Customer _arg0, final Feature _arg1) throws ServiceException {
		return deviceService.getLightSiteByCustomer(_arg0, _arg1);
	}

	@Override
	public int getLogRetentionPeriod(final long _arg0) throws ServiceException {
		return deviceService.getLogRetentionPeriod(_arg0);
	}

	@Override
	public boolean getManageCertificate(final long _arg0) throws ServiceException {
		return deviceService.getManageCertificate(_arg0);
	}

	@Override
	public IpAddress getManagementIpAddress(final long _arg0) throws ServiceException {
		return deviceService.getManagementIpAddress(_arg0);
	}

	@Override
	public Manufacturer getManufacturerFromId(final long _arg0) throws ServiceException {
		return deviceService.getManufacturerFromId(_arg0);
	}

	@Override
	public long getManufacturerId(final String _arg0) throws ServiceException {
		return deviceService.getManufacturerId(_arg0);
	}

	@Override
	public Map<Long, Manufacturer> getManufacturers() throws ServiceException {
		return deviceService.getManufacturers();
	}

	@Override
	public Model getModelFromId(final long _arg0, final long _arg1) throws ServiceException {
		return deviceService.getModelFromId(_arg0, _arg1);
	}

	@Override
	public String getOperatorPrefix(final long _arg0) throws ServiceException {
		return deviceService.getOperatorPrefix(_arg0);
	}

	@Override
	public Set<ProductPartNumber> getPPNlist(final long _arg0) throws ServiceException {
		return deviceService.getPPNlist(_arg0);
	}

	@Override
	public Set<ProductPartNumber> getPPNlistByCustomer(final long _arg0) throws ServiceException {
		return deviceService.getPPNlistByCustomer(_arg0);
	}

	@Override
	public ProvisioningStatus getProvisioningStatus(final String _arg0) throws ServiceException {
		return deviceService.getProvisioningStatus(_arg0);
	}

	@Override
	public ProvisioningStatus getProvisioningStatusById(final long _arg0) throws ServiceException {
		return deviceService.getProvisioningStatusById(_arg0);
	}

	@Override
	public UpdateStatus getPushConfigToAddonStatusByDeviceId(final long _arg0, final String _arg1) throws ServiceException {
		return deviceService.getPushConfigToAddonStatusByDeviceId(_arg0, _arg1);
	}

	@Override
	public UpdateStatus getPushConfigToDeviceStatusByDeviceId(final long _arg0) throws ServiceException {
		return deviceService.getPushConfigToDeviceStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getPushConfigToStartupStatusByDeviceId(final long _arg0) throws ServiceException {
		return deviceService.getPushConfigToStartupStatusByDeviceId(_arg0);
	}

	@Override
	public UpdateStatus getRebootStatus(final long _arg0) throws ServiceException {
		return deviceService.getRebootStatus(_arg0);
	}

	@Override
	public FileInfo getRouterFileInfoByDeviceId(final long _arg0, final String _arg1) throws ServiceException, ObjectNotFoundException {
		return deviceService.getRouterFileInfoByDeviceId(_arg0, _arg1);
	}

	@Override
	public SdType getSdType(final int _arg0, final int _arg1) throws IllegalArgumentException, ServiceException {
		return deviceService.getSdType(_arg0, _arg1);
	}

	@Override
	public SdType getSdType(final String _arg0, final String _arg1) throws ServiceException {
		return deviceService.getSdType(_arg0, _arg1);
	}

	@Override
	public UpdateStatus getSendDataFilesStatusByDeviceId(final long _arg0) throws ServiceException {
		return deviceService.getSendDataFilesStatusByDeviceId(_arg0);
	}

	@Override
	public String getSerialNumber(final long _arg0) throws ServiceException {
		return deviceService.getSerialNumber(_arg0);
	}

	@Override
	public boolean getSilverMonitoring(final long _arg0) throws ServiceException {
		return deviceService.getSilverMonitoring(_arg0);
	}

	@Override
	public DeviceFeatures getSiteFeature(final long _arg0, final long _arg1) throws ServiceException, IllegalArgumentException {
		return deviceService.getSiteFeature(_arg0, _arg1);
	}

	@Override
	public GeoCoordinate getSiteGeoLocalization(final long _arg0) throws ServiceException {
		return deviceService.getSiteGeoLocalization(_arg0);
	}

	@Override
	public String getSnmpCommunity(final long _arg0) throws ServiceException {
		return deviceService.getSnmpCommunity(_arg0);
	}

	@Override
	public SecEngineResult getStagingConfiguration(final String _arg0, final String _arg1) throws ServiceException {
		return deviceService.getStagingConfiguration(_arg0, _arg1);
	}

	@Override
	public SecEngineResult getStagingConfigurationById(final DeviceId _arg0, final ManagerId _arg1) throws ServiceException {
		return deviceService.getStagingConfigurationById(_arg0, _arg1);
	}

	@Override
	public String getStagingModel(final long _arg0) throws ServiceException {
		return deviceService.getStagingModel(_arg0);
	}

	@Override
	public UpdateStatus getUpdateConfigurationStatus(final long _arg0) throws ServiceException {
		return deviceService.getUpdateConfigurationStatus(_arg0);
	}

	@Override
	public UpdateStatus getUpdateConfigurationStatus(final String _arg0) throws ServiceException {
		return deviceService.getUpdateConfigurationStatus(_arg0);
	}

	@Override
	public SecEngineResult getUpdateStatus(final DeviceId _arg0, final String _arg1) throws ServiceException {
		return deviceService.getUpdateStatus(_arg0, _arg1);
	}

	@Override
	public SecEngineResult getUpdateStatus(final String _arg0, final String _arg1) throws ServiceException {
		return deviceService.getUpdateStatus(_arg0, _arg1);
	}

	@Override
	public boolean isDevice(final DeviceId _arg0) {
		return deviceService.isDevice(_arg0);
	}

	@Override
	public boolean isModelAvailableForWizard(final long _arg0, final long _arg1) throws ServiceException {
		return deviceService.isModelAvailableForWizard(_arg0, _arg1);
	}

	@Override
	public List<String> listArchivedLogFilesByDeviceId(final DeviceId _arg0, final Date _arg1, final Date _arg2) throws ServiceException, ObjectNotFoundException {
		return deviceService.listArchivedLogFilesByDeviceId(_arg0, _arg1, _arg2);
	}

	@Override
	public List<Site> listDeviceByManager(final long _arg0) throws ServiceException, DataBaseFailureException {
		return deviceService.listDeviceByManager(_arg0);
	}

	@Override
	public List<DeviceFeatures> listDeviceFeaturesByCustomer(final long _arg0) throws ServiceException {
		return deviceService.listDeviceFeaturesByCustomer(_arg0);
	}

	@Override
	public List<Site> listDevicePingStatusByCustomer(final long _arg0) throws ServiceException {
		return deviceService.listDevicePingStatusByCustomer(_arg0);
	}

	@Override
	public List<Site> listDevicePingStatusByGes(final long _arg0) throws ServiceException {
		return deviceService.listDevicePingStatusByGes(_arg0);
	}

	@Override
	public SimpleDevice[] listDevicesByEntityAndManufacturer(final String _arg0, final int _arg1, final boolean _arg2) throws DatabaseSystemException, ServiceException {
		return deviceService.listDevicesByEntityAndManufacturer(_arg0, _arg1, _arg2);
	}

	@Override
	public SimpleDevice[] listDevicesByEntityAndModels(final String _arg0, final List<Model> _arg1) throws DatabaseSystemException, ServiceException {
		return deviceService.listDevicesByEntityAndModels(_arg0, _arg1);
	}

	@Override
	public List<String> listFilesFromDevice(final long _arg0, final String _arg1, final SortFileType _arg2) throws ServiceException {
		return deviceService.listFilesFromDevice(_arg0, _arg1, _arg2);
	}

	@Override
	public List<String> listFilesFromDevice(final long _arg0) throws ServiceException {
		return deviceService.listFilesFromDevice(_arg0);
	}

	@Override
	public List<String> listFilesFromDeviceIncludingAttachedProfile(final long _arg0, final String _arg1, final SortFileType _arg2) throws ServiceException {
		return deviceService.listFilesFromDeviceIncludingAttachedProfile(_arg0, _arg1, _arg2);
	}

	@Override
	public List<DeviceFeatures> listProfileAndDeviceFeatureByCustomerId(final long _arg0) throws IllegalArgumentException, ServiceException {
		return deviceService.listProfileAndDeviceFeatureByCustomerId(_arg0);
	}

	@Override
	public List<Site> listSitesByCustomer(final Customer _arg0) throws ServiceException {
		return deviceService.listSitesByCustomer(_arg0);
	}

	@Override
	public boolean manufacturerExists(final long _arg0) throws ServiceException {
		return deviceService.manufacturerExists(_arg0);
	}

	@Override
	public void markAsProvisionedByDeviceId(final long _arg0) throws ServiceException {
		deviceService.markAsProvisionedByDeviceId(_arg0);
	}

	@Override
	public void markAsProvisionedByDeviceReference(final String _arg0) throws ServiceException {
		deviceService.markAsProvisionedByDeviceReference(_arg0);
	}

	@Override
	public boolean modelExists(final long _arg0, final long _arg1) throws ServiceException {
		return deviceService.modelExists(_arg0, _arg1);
	}

	@Override
	public byte[] processScriptTemplate(final ManagerId _arg0, final DeviceId _arg1, final String _arg2) throws ServiceException {
		return deviceService.processScriptTemplate(_arg0, _arg1, _arg2);
	}

	@Override
	public FileBasedConfiguration readConfDeviceXml(final long _arg0) throws ServiceException {
		return deviceService.readConfDeviceXml(_arg0);
	}

	@Override
	public FileBasedConfiguration readConfDeviceXml(final ManagerId _arg0, final DeviceId _arg1) throws ServiceException {
		return deviceService.readConfDeviceXml(_arg0, _arg1);
	}

	@Override
	public Site readDevice(final DeviceId _arg0) throws ServiceException {
		return deviceService.readDevice(_arg0);
	}

	@Override
	public Site readDevice(final long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.readDevice(_arg0);
	}

	@Override
	public Site readDeviceAndCheckCredentialsInSMS(final long _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.readDeviceAndCheckCredentialsInSMS(_arg0);
	}

	@Override
	public Site readDeviceByReference(final String _arg0) throws ServiceException {
		return deviceService.readDeviceByReference(_arg0);
	}

	@Override
	public Interface readInterface(final long _arg0, final char _arg1) throws ServiceException {
		return deviceService.readInterface(_arg0, _arg1);
	}

	@Override
	public GeoCoordinate readLatitudeAndLongitude(final DeviceId _arg0) throws ServiceException, ObjectNotFoundException {
		return deviceService.readLatitudeAndLongitude(_arg0);
	}

	@Override
	public String readSdAdminPassword(final long _arg0) throws ServiceException {
		return deviceService.readSdAdminPassword(_arg0);
	}

	@Override
	public Address readSiteAddress(final long _arg0) throws ServiceException {
		return deviceService.readSiteAddress(_arg0);
	}

	@Override
	public HashMap<String, String> readSiteByExternalReference(final String _arg0) throws ServiceException {
		return deviceService.readSiteByExternalReference(_arg0);
	}

	@Override
	public void saveConfDeviceXml(final ManagerId _arg0, final DeviceId _arg1, final FileBasedConfiguration _arg2) throws ServiceException {
		deviceService.saveConfDeviceXml(_arg0, _arg1, _arg2);
	}

	@Override
	public void sendDataFiles(final long _arg0) throws ServiceException, ObjectNotFoundException {
		deviceService.sendDataFiles(_arg0);
	}

	@Override
	public void setAutomaticalUpdate(final long _arg0, final boolean _arg1, final String _arg2) throws ServiceException {
		deviceService.setAutomaticalUpdate(_arg0, _arg1, _arg2);
	}

	@Override
	public void setAutomaticalUpdateByExternalReference(final String _arg0, final boolean _arg1, final String _arg2) throws ServiceException {
		deviceService.setAutomaticalUpdateByExternalReference(_arg0, _arg1, _arg2);
	}

	@Override
	public boolean setDHCPpublicToDevice(final String _arg0, final DeviceId _arg1, final IpAddress _arg2, final IpAddress _arg3, final IpAddress _arg4, final IpAddress _arg5, final IpAddress _arg6, final IpAddress _arg7, final String _arg8) throws ServiceException {
		return deviceService.setDHCPpublicToDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
	}

	@Override
	public boolean setDHCPpublicToDevice(final String _arg0, final long _arg1, final IpAddress _arg2, final IpAddress _arg3, final IpAddress _arg4, final IpAddress _arg5, final IpAddress _arg6, final IpAddress _arg7, final String _arg8) throws ServiceException {
		return deviceService.setDHCPpublicToDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
	}

	@Override
	public void setDeviceFieldByFieldName(final long _arg0, final String _arg1, final String _arg2, final String _arg3) throws ServiceException {
		deviceService.setDeviceFieldByFieldName(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public void setExtendedAtrtibutes(final long _arg0, final Object _arg1, final Object _arg2) throws ServiceException, DataBaseFailureException {
		deviceService.setExtendedAtrtibutes(_arg0, _arg1, _arg2);
	}

	@Override
	public void setHostname(final long _arg0, final String _arg1, final String _arg2) throws ServiceException {
		deviceService.setHostname(_arg0, _arg1, _arg2);
	}

	@Override
	public void setLogRetentionPeriod(final long _arg0, final int _arg1, final String _arg2) throws ServiceException {
		deviceService.setLogRetentionPeriod(_arg0, _arg1, _arg2);
	}

	@Override
	public void setManageCertificate(final long _arg0, final boolean _arg1, final String _arg2) throws ServiceException {
		deviceService.setManageCertificate(_arg0, _arg1, _arg2);
	}

	@Override
	public void setSerialNumber(final long _arg0, final String _arg1, final String _arg2) throws ServiceException {
		deviceService.setSerialNumber(_arg0, _arg1, _arg2);
	}

	@Override
	public void setSilverMonitoring(final long _arg0, final boolean _arg1, final String _arg2) throws ServiceException {
		deviceService.setSilverMonitoring(_arg0, _arg1, _arg2);
	}

	@Override
	public void setSiteGeoLocalization(final long _arg0, final GeoCoordinate _arg1) throws ServiceException {
		deviceService.setSiteGeoLocalization(_arg0, _arg1);
	}

	@Override
	public void setSnmpCommunity(final long _arg0, final String _arg1) throws ServiceException {
		deviceService.setSnmpCommunity(_arg0, _arg1);
	}

	@Override
	public void setStagingModel(final long _arg0, final String _arg1, final String _arg2) throws ServiceException {
		deviceService.setStagingModel(_arg0, _arg1, _arg2);
	}

	@Override
	public String storeStagingConfigurationToFtpServer(final String _arg0, final String _arg1, final String _arg2, final String _arg3, final FtpAccount _arg4) throws ServiceException {
		return deviceService.storeStagingConfigurationToFtpServer(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public boolean testHostnameIsUsed(final String _arg0, final String _arg1, final String _arg2) throws ServiceException {
		return deviceService.testHostnameIsUsed(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateAddress(final long _arg0, final String _arg1, final String _arg2, final String _arg3, final String _arg4, final String _arg5, final String _arg6) throws ServiceException, ObjectNotFoundException {
		deviceService.updateAddress(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
	}

	@Override
	public Site updateDevice(final Site _arg0, final String _arg1) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		return deviceService.updateDevice(_arg0, _arg1);
	}

	@Override
	public void updateDeviceCredentials(final long _arg0, final String _arg1, final String _arg2, final String _arg3, final String _arg4) throws ServiceException, ObjectNotFoundException {
		deviceService.updateDeviceCredentials(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public void updateDeviceExternalReference(final long _arg0, final String _arg1, final String _arg2) throws ServiceException {
		deviceService.updateDeviceExternalReference(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateDeviceManagementIpAddress(final long _arg0, final String _arg1, final String _arg2, final String _arg3) throws ServiceException {
		deviceService.updateDeviceManagementIpAddress(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public DeviceId updateDeviceManagementPort(final long _arg0, final int _arg1, final String _arg2) throws ServiceException {
		return deviceService.updateDeviceManagementPort(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateDeviceName(final long _arg0, final String _arg1, final String _arg2) throws ServiceException {
		deviceService.updateDeviceName(_arg0, _arg1, _arg2);
	}

	@Override
	public void updateHACluster(final long _arg0, final int _arg1, final long _arg2, final int _arg3) throws ServiceException {
		deviceService.updateHACluster(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public void updateLatitudeAndLongitude(final long _arg0, final double _arg1, final double _arg2) throws ServiceException, ObjectNotFoundException {
		deviceService.updateLatitudeAndLongitude(_arg0, _arg1, _arg2);
	}

	@Override
	public DeviceId updateNAT(final long _arg0, final boolean _arg1, final String _arg2) throws ServiceException {
		return deviceService.updateNAT(_arg0, _arg1, _arg2);
	}

	@Override
	public DeviceId updateNATRouterAddress(final long _arg0, final String _arg1, final String _arg2) throws ServiceException, ObjectNotFoundException {
		return deviceService.updateNATRouterAddress(_arg0, _arg1, _arg2);
	}

	@Override
	public void applyConfigurationToManagedEntity(final long arg0, final String arg1) throws ServiceException {
		//
	}

	@Override
	public TemplateManagedDevice convertSiteToTemplateManagedDevice(final Site arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public int countManagedEntitiesByStatusWithOptionalFilters(final long arg0, final String arg1, final String arg2, final long arg3, final String arg4, final int arg5) throws DatabaseServiceException {
		//
		return 0;
	}

	@Override
	public Site createTemplateManagedDevice(final String arg0, final long arg1, final String arg2, final String arg3, final int arg4, final int arg5, final String arg6, final String arg7, final String arg8, final boolean arg9, final boolean arg10, final boolean arg11, final boolean arg12, final boolean arg13, final String arg14, final String arg15) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		//
		return null;
	}

	@Override
	public Site createTemplateManagedDevice(final String arg0, final long arg1, final String arg2, final String arg3, final int arg4, final int arg5, final String arg6, final String arg7, final String arg8, final boolean arg9, final boolean arg10, final boolean arg11, final boolean arg12, final boolean arg13, final String arg14, final String arg15, final int arg16) throws ServiceException, DuplicateReferenceException, IllegalSdRouteCreationException {
		//
		return null;
	}

	@Override
	public void doFirmwareUpdateByDeviceId(final long arg0, final String arg1, final long arg2) throws ServiceException, ObjectNotFoundException {
		//

	}

	@Override
	public SecEngineResult doUpdateConfiguration(final long arg0, final long arg1) throws ServiceException, ObjectNotFoundException {
		//
		return null;
	}

	@Override
	public List<Long> getDevicesByNature(final DeviceNature arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<Long> getDevicesByNature(final String arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<String, Object> getManagedEntityStatusReport(final long arg0, final long arg1, final long arg2, final String arg3, final int arg4, final int arg5, final String arg6) throws ServiceException {
		//
		return null;
	}

	@Override
	public int getManagedEntityTotalCount(final long arg0, final Long arg1, final Long arg2) throws DatabaseServiceException, ObjectNotFoundException {
		//
		return 0;
	}

	@Override
	public List<Asset> listDeviceAssetDetailsByCustomer(final long arg0, final String arg1, final String arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<Site> listDeviceByManager(final long arg0, final boolean arg1, final String arg2, final String arg3) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<Site> listDevicePingStatusByManagerOptionalCustomerTenantFilter(final long arg0, final long arg1, final String arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<Site> listDevicePingStatusByTenant(final long arg0, final long arg1) throws DatabaseServiceException {
		//
		return null;
	}

	@Override
	public void markAsProvisionedByDeviceId(final long arg0, final long arg1) throws ServiceException {
		//

	}

	@Override
	public SecEngineResult pushConfigurationToManagedEntity(final DeviceId arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public DeviceNature readDeviceNature(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public void sendDataFiles(final long arg0, final long arg1) throws ServiceException, ObjectNotFoundException {
		//

	}

	@Override
	public void updateDeviceNature(final long arg0, final DeviceNature arg1) throws ServiceException {
		//

	}

	@Override
	public void updateDeviceNature(final long arg0, final String arg1) throws ServiceException {
		//

	}

	@Override
	public TemplateManagedDevice updateManagedDevice(final String arg0, final long arg1, final TemplateManagedDevice arg2) throws ServiceException {
		//
		return null;
	}

}
