package com.ubiqube.etsi.mano.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ubiqube.api.commons.id.ManagerId;
import com.ubiqube.api.commons.id.UbiqubeId;
import com.ubiqube.api.entities.orchestration.DashBoardProcessCount;
import com.ubiqube.api.entities.orchestration.OrchestrationScheduling;
import com.ubiqube.api.entities.orchestration.OrchestrationTaskExec;
import com.ubiqube.api.entities.orchestration.ProcessDefinition;
import com.ubiqube.api.entities.orchestration.ProcessInstance;
import com.ubiqube.api.entities.orchestration.ProcessStatus;
import com.ubiqube.api.entities.orchestration.ServiceId;
import com.ubiqube.api.entities.orchestration.TaskDefinition;
import com.ubiqube.api.entities.orchestration.definition.OrchestrationDefinition;
import com.ubiqube.api.exception.DatabaseSystemException;
import com.ubiqube.api.exception.ObjectNotFoundException;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.orchestration.OrchestrationService;
import com.ubiqube.api.secEngine.result.SecEngineProcessStatus;
import com.ubiqube.etsi.mano.repository.JndiWrapper;

/**
 * Implementation of a Orchestration service thru remote EJB call. NOTE it's
 * just a delegate of the interface, feel free to regenerate for correcting
 * arguments.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class OrchestrationServiceEjb implements OrchestrationService {
	/** EJB Proxy. */
	private final OrchestrationService orchestrationService;

	/**
	 * Constructor.
	 */
	@Inject
	public OrchestrationServiceEjb(JndiWrapper _jndiWrapper) {
		orchestrationService = (OrchestrationService) _jndiWrapper.lookup("ubi-jentreprise/OrchestrationBean/remote-com.ubiqube.api.interfaces.orchestration.OrchestrationService");
	}

	@Override
	public void archiveServiceInstance(long _arg0) throws ServiceException {
		orchestrationService.archiveServiceInstance(_arg0);
	}

	@Override
	public void attachServices(UbiqubeId _arg0, String[] _arg1, String _arg2) throws ServiceException {
		orchestrationService.attachServices(_arg0, _arg1, _arg2);
	}

	@Override
	public ServiceId checkService(String _arg0, long _arg1) throws ServiceException {
		return orchestrationService.checkService(_arg0, _arg1);
	}

	@Override
	public ServiceId checkServiceReference(UbiqubeId _arg0, String _arg1) throws ServiceException {
		return orchestrationService.checkServiceReference(_arg0, _arg1);
	}

	@Override
	public ServiceId checkUbiqubeId(String _arg0, long _arg1) throws ServiceException {
		return orchestrationService.checkUbiqubeId(_arg0, _arg1);
	}

	@Override
	public int countArchivedServices(UbiqubeId _arg0, String _arg1) throws ServiceException {
		return orchestrationService.countArchivedServices(_arg0, _arg1);
	}

	@Override
	public List<DashBoardProcessCount> countLastProcesses(UbiqubeId _arg0, int _arg1) throws ServiceException {
		return orchestrationService.countLastProcesses(_arg0, _arg1);
	}

	@Override
	public long createProcessInstance(long _arg0, String _arg1, String _arg2, long _arg3) throws ServiceException {
		return orchestrationService.createProcessInstance(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public ServiceId createServiceInstance(UbiqubeId _arg0, String _arg1, String _arg2) throws ServiceException {
		return orchestrationService.createServiceInstance(_arg0, _arg1, _arg2);
	}

	@Override
	public ServiceId createServiceInstance(UbiqubeId _arg0, String _arg1) throws ServiceException {
		return orchestrationService.createServiceInstance(_arg0, _arg1);
	}

	@Override
	public void deleteAllArchivedServiceInstance(UbiqubeId _arg0, String _arg1) throws ServiceException {
		orchestrationService.deleteAllArchivedServiceInstance(_arg0, _arg1);
	}

	@Override
	public void deleteAllServiceVariablesLikeName(long _arg0, String _arg1) {
		orchestrationService.deleteAllServiceVariablesLikeName(_arg0, _arg1);
	}

	@Override
	public void deleteProcessScheduling(OrchestrationScheduling _arg0) throws ServiceException {
		orchestrationService.deleteProcessScheduling(_arg0);
	}

	@Override
	public void deleteServiceInstance(long _arg0) throws ServiceException {
		orchestrationService.deleteServiceInstance(_arg0);
	}

	@Override
	public void deleteServiceVariable(long _arg0, String _arg1) {
		orchestrationService.deleteServiceVariable(_arg0, _arg1);
	}

	@Override
	public void detachServices(UbiqubeId _arg0, String[] _arg1, String _arg2) throws ServiceException {
		orchestrationService.detachServices(_arg0, _arg1, _arg2);
	}

	@Override
	public List<String> extractProcessVariables(ProcessDefinition _arg0) throws ServiceException {
		return orchestrationService.extractProcessVariables(_arg0);
	}

	@Override
	public Map<String, List<Map<String, String>>> extractProcessVariablesWithTypeFromTask(ProcessDefinition _arg0) throws IllegalStateException, IOException {
		return orchestrationService.extractProcessVariablesWithTypeFromTask(_arg0);
	}

	@Override
	public List<Map<String, String>> extractVariablesFromTaskWithType(TaskDefinition _arg0) throws IllegalStateException, IOException {
		return orchestrationService.extractVariablesFromTaskWithType(_arg0);
	}

	@Override
	public String formatProcessDefinitionToFile(ProcessDefinition _arg0) {
		return orchestrationService.formatProcessDefinitionToFile(_arg0);
	}

	@Override
	public ProcessInstance getProcessInstance(long _arg0) throws ServiceException {
		return orchestrationService.getProcessInstance(_arg0);
	}

	@Override
	public ProcessInstance getProcessInstanceBySchedHandle(long _arg0) throws ServiceException {
		return orchestrationService.getProcessInstanceBySchedHandle(_arg0);
	}

	@Override
	public String getServiceDisplayName(long _arg0) throws ServiceException {
		return orchestrationService.getServiceDisplayName(_arg0);
	}

	@Override
	public UbiqubeId getServiceInstanceUbiqubeId(long _arg0) throws ServiceException {
		return orchestrationService.getServiceInstanceUbiqubeId(_arg0);
	}

	@Override
	public String getServiceVariable(long _arg0, String _arg1) throws ServiceException {
		return orchestrationService.getServiceVariable(_arg0, _arg1);
	}

	@Override
	public Map<String, String> getServiceVariablesLike(long _arg0, String _arg1) throws ServiceException {
		return orchestrationService.getServiceVariablesLike(_arg0, _arg1);
	}

	@Override
	public UbiqubeId getUbiqubeId(String _arg0) throws ServiceException {
		return orchestrationService.getUbiqubeId(_arg0);
	}

	@Override
	public void insertServiceVariable(long _arg0, String _arg1, String _arg2) {
		orchestrationService.insertServiceVariable(_arg0, _arg1, _arg2);
	}

	@Override
	public Map<Long, String> listArchivedServiceFieldValues(UbiqubeId _arg0, String _arg1, String _arg2) throws ServiceException {
		return orchestrationService.listArchivedServiceFieldValues(_arg0, _arg1, _arg2);
	}

	@Override
	public List<ServiceId> listArchivedServiceIds(UbiqubeId _arg0, String _arg1, long _arg2) throws ServiceException {
		return orchestrationService.listArchivedServiceIds(_arg0, _arg1, _arg2);
	}

	@Override
	public List<String> listAvailServiceDefinitions(UbiqubeId _arg0) throws ServiceException {
		return orchestrationService.listAvailServiceDefinitions(_arg0);
	}

	@Override
	public List<ProcessInstance> listLastProcesses(UbiqubeId _arg0, long _arg1, long _arg2) throws ServiceException {
		return orchestrationService.listLastProcesses(_arg0, _arg1, _arg2);
	}

	@Override
	public Map<String, String> listMergedServiceVariables(long _arg0) throws ServiceException {
		return orchestrationService.listMergedServiceVariables(_arg0);
	}

	@Override
	public List<OrchestrationScheduling> listProcessActivity(UbiqubeId _arg0, List<String> _arg1) throws ServiceException {
		return orchestrationService.listProcessActivity(_arg0, _arg1);
	}

	@Override
	public List<ProcessInstance> listProcessInstance(long _arg0, String _arg1) throws ServiceException {
		return orchestrationService.listProcessInstance(_arg0, _arg1);
	}

	@Override
	public List<ProcessInstance> listProcessInstance(long _arg0) throws ServiceException {
		return orchestrationService.listProcessInstance(_arg0);
	}

	@Override
	public List<ProcessInstance> listProcessInstance(UbiqubeId _arg0, String _arg1, String _arg2) throws ServiceException {
		return orchestrationService.listProcessInstance(_arg0, _arg1, _arg2);
	}

	@Override
	public List<ProcessInstance> listProcessInstanceWithoutSteps(long _arg0, List<String> _arg1, long _arg2) throws ServiceException {
		return orchestrationService.listProcessInstanceWithoutSteps(_arg0, _arg1, _arg2);
	}

	@Override
	public List<ProcessInstance> listProcessInstanceWithoutSteps(UbiqubeId _arg0, String _arg1, List<String> _arg2) throws ServiceException {
		return orchestrationService.listProcessInstanceWithoutSteps(_arg0, _arg1, _arg2);
	}

	@Override
	public Map<String, Map<SecEngineProcessStatus, Integer>> listServiceByStatus(UbiqubeId _arg0, int _arg1) throws ServiceException {
		return orchestrationService.listServiceByStatus(_arg0, _arg1);
	}

	@Override
	public List<ServiceId> listServiceInstance(UbiqubeId _arg0) throws ServiceException {
		return orchestrationService.listServiceInstance(_arg0);
	}

	@Override
	public Map<String, String> listServiceVariables(long _arg0) throws ServiceException {
		return orchestrationService.listServiceVariables(_arg0);
	}

	@Override
	public Map<ServiceId, Map<String, String>> listServiceVariables(UbiqubeId _arg0, String _arg1, long _arg2, int _arg3) throws ServiceException {
		return orchestrationService.listServiceVariables(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public Map<ServiceId, String> listSingleServiceVariable(UbiqubeId _arg0, String _arg1, String _arg2, String _arg3, String _arg4) throws ServiceException {
		return orchestrationService.listSingleServiceVariable(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public List<OrchestrationTaskExec> listTaskActivity(UbiqubeId _arg0, List<String> _arg1) throws ServiceException {
		return orchestrationService.listTaskActivity(_arg0, _arg1);
	}

	@Override
	public List<ServiceId> listUnarchivedServiceInstances(UbiqubeId _arg0, int _arg1) throws ServiceException {
		return orchestrationService.listUnarchivedServiceInstances(_arg0, _arg1);
	}

	@Override
	public Map<ServiceId, ProcessStatus> listUnarchivedServiceStatus(UbiqubeId _arg0, String _arg1, List<String> _arg2) throws ServiceException {
		return orchestrationService.listUnarchivedServiceStatus(_arg0, _arg1, _arg2);
	}

	@Override
	public Map<Long, Long> listUnqueuedProcessByServiceId(UbiqubeId _arg0) throws ServiceException {
		return orchestrationService.listUnqueuedProcessByServiceId(_arg0);
	}

	@Override
	public OrchestrationDefinition readOrchestrationDefinition(String _arg0, boolean _arg1) throws ServiceException {
		return orchestrationService.readOrchestrationDefinition(_arg0, _arg1);
	}

	@Override
	public ProcessDefinition readProcessDefinitionByUri(String _arg0) throws ServiceException {
		return orchestrationService.readProcessDefinitionByUri(_arg0);
	}

	@Override
	public OrchestrationScheduling readScheduling(long _arg0) throws ServiceException {
		return orchestrationService.readScheduling(_arg0);
	}

	@Override
	public ServiceId readServiceIdByReference(String _arg0, String _arg1) throws ServiceException {
		return orchestrationService.readServiceIdByReference(_arg0, _arg1);
	}

	@Override
	public String readServiceVariable(long _arg0, String _arg1) throws DatabaseSystemException, ObjectNotFoundException {
		return orchestrationService.readServiceVariable(_arg0, _arg1);
	}

	@Override
	public void replaceServiceVariables(long _arg0, Map<String, String> _arg1) throws ServiceException {
		orchestrationService.replaceServiceVariables(_arg0, _arg1);
	}

	@Override
	public void reschedule(OrchestrationScheduling _arg0) throws ServiceException {
		orchestrationService.reschedule(_arg0);
	}

	@Override
	public void saveOrchestrationDefinition(ManagerId _arg0, String _arg1, OrchestrationDefinition _arg2, String _arg3) throws ServiceException {
		orchestrationService.saveOrchestrationDefinition(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public ProcessInstance scheduleImmediateModeByReference(String _arg0, String _arg1, String _arg2, String _arg3, Map<String, String> _arg4) throws ServiceException {
		return orchestrationService.scheduleImmediateModeByReference(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public OrchestrationScheduling scheduleProcess(OrchestrationScheduling _arg0, String _arg1) throws ServiceException {
		return orchestrationService.scheduleProcess(_arg0, _arg1);
	}

	@Override
	public ProcessInstance scheduleServiceImmediateMode(String _arg0, long _arg1, String _arg2, String _arg3, Map<String, String> _arg4) throws ServiceException {
		return orchestrationService.scheduleServiceImmediateMode(_arg0, _arg1, _arg2, _arg3, _arg4);
	}

	@Override
	public void unArchiveServiceInstance(long _arg0) throws ServiceException {
		orchestrationService.unArchiveServiceInstance(_arg0);
	}

	@Override
	public void updateProcessScriptDetails(long _arg0, String _arg1, long _arg2, long _arg3) throws ServiceException {
		orchestrationService.updateProcessScriptDetails(_arg0, _arg1, _arg2, _arg3);
	}

	@Override
	public void updateServiceInstanceReference(long _arg0, String _arg1) throws ServiceException {
		orchestrationService.updateServiceInstanceReference(_arg0, _arg1);
	}

	@Override
	public void updateServiceVariable(long _arg0, String _arg1, String _arg2) throws DatabaseSystemException {
		orchestrationService.updateServiceVariable(_arg0, _arg1, _arg2);
	}
}
