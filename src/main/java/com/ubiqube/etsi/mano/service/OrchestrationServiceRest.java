package com.ubiqube.etsi.mano.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Service
public class OrchestrationServiceRest implements OrchestrationService {

	private final UbiRest rest;

	public OrchestrationServiceRest(final UbiRest _rest) {
		this.rest = _rest;
	}

	@Override
	public ProcessInstance scheduleServiceImmediateMode(final String ubiqubeId, final long serviceId, final String serviceName, final String processName, final Map<String, String> varsMap) throws ServiceException {
		final URI uri = rest.uriBuilder()
				.pathSegment("orchestration/v1/scheduleServiceImmediateMode")
				.queryParam("ubiqubeId", ubiqubeId)
				.queryParam("serviceId", serviceId)
				.queryParam("serviceName", serviceName)
				.queryParam("processName", processName)
				.build()
				.toUri();
		return rest.post(uri, varsMap, ProcessInstance.class);
	}

	@Override
	public ProcessInstance getProcessInstance(final long processId) {
		final Map<String, String> vars = new HashMap<>();
		vars.put("processId", Long.toString(processId));
		final URI uri = rest.uriBuilder()
				.pathSegment("orchestration/process/instance/{processId}")
				.buildAndExpand(vars)
				.toUri();
		return rest.get(uri, ProcessInstance.class);
	}

	@Override
	public void archiveServiceInstance(final long arg0) throws ServiceException {
		//

	}

	@Override
	public void attachServices(final UbiqubeId arg0, final String[] arg1, final String arg2) throws ServiceException {
		//

	}

	@Override
	public ServiceId checkService(final String arg0, final long arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public ServiceId checkServiceReference(final UbiqubeId arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public ServiceId checkUbiqubeId(final String arg0, final long arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public int countArchivedServices(final UbiqubeId arg0, final String arg1) throws ServiceException {
		//
		return 0;
	}

	@Override
	public List<DashBoardProcessCount> countLastProcesses(final UbiqubeId arg0, final int arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public long createProcessInstance(final long arg0, final String arg1, final String arg2, final long arg3) throws ServiceException {
		//
		return 0;
	}

	@Override
	public ServiceId createServiceInstance(final UbiqubeId arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public ServiceId createServiceInstance(final UbiqubeId arg0, final String arg1, final String arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public void deleteAllArchivedServiceInstance(final UbiqubeId arg0, final String arg1) throws ServiceException {
		//

	}

	@Override
	public void deleteAllServiceVariablesLikeName(final long arg0, final String arg1) {
		//

	}

	@Override
	public void deleteProcessScheduling(final OrchestrationScheduling arg0) throws ServiceException {
		//

	}

	@Override
	public void deleteServiceInstance(final long arg0) throws ServiceException {
		//

	}

	@Override
	public void deleteServiceVariable(final long arg0, final String arg1) {
		//

	}

	@Override
	public void detachServices(final UbiqubeId arg0, final String[] arg1, final String arg2) throws ServiceException {
		//

	}

	@Override
	public List<String> extractProcessVariables(final ProcessDefinition arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<String, List<Map<String, String>>> extractProcessVariablesWithTypeFromTask(final ProcessDefinition arg0) throws IllegalStateException, IOException {
		//
		return null;
	}

	@Override
	public List<Map<String, String>> extractVariablesFromTaskWithType(final TaskDefinition arg0) throws IllegalStateException, IOException {
		//
		return null;
	}

	@Override
	public String formatProcessDefinitionToFile(final ProcessDefinition arg0) {
		//
		return null;
	}

	@Override
	public ProcessInstance getProcessInstanceBySchedHandle(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public String getServiceDisplayName(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public UbiqubeId getServiceInstanceUbiqubeId(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public String getServiceVariable(final long arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<String, String> getServiceVariablesLike(final long arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public UbiqubeId getUbiqubeId(final String arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public void insertServiceVariable(final long arg0, final String arg1, final String arg2) {
		//

	}

	@Override
	public Map<Long, String> listArchivedServiceFieldValues(final UbiqubeId arg0, final String arg1, final String arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ServiceId> listArchivedServiceIds(final UbiqubeId arg0, final String arg1, final long arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<String> listAvailServiceDefinitions(final UbiqubeId arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ProcessInstance> listLastProcesses(final UbiqubeId arg0, final long arg1, final long arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<String, String> listMergedServiceVariables(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<OrchestrationScheduling> listProcessActivity(final UbiqubeId arg0, final List<String> arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ProcessInstance> listProcessInstance(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ProcessInstance> listProcessInstance(final long arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ProcessInstance> listProcessInstance(final UbiqubeId arg0, final String arg1, final String arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ProcessInstance> listProcessInstanceWithoutSteps(final long arg0, final List<String> arg1, final long arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ProcessInstance> listProcessInstanceWithoutSteps(final UbiqubeId arg0, final String arg1, final List<String> arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<String, Map<SecEngineProcessStatus, Integer>> listServiceByStatus(final UbiqubeId arg0, final int arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ServiceId> listServiceInstance(final UbiqubeId arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<String, String> listServiceVariables(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<ServiceId, Map<String, String>> listServiceVariables(final UbiqubeId arg0, final String arg1, final long arg2, final int arg3) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<ServiceId, String> listSingleServiceVariable(final UbiqubeId arg0, final String arg1, final String arg2, final String arg3, final String arg4) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<OrchestrationTaskExec> listTaskActivity(final UbiqubeId arg0, final List<String> arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public List<ServiceId> listUnarchivedServiceInstances(final UbiqubeId arg0, final int arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<ServiceId, ProcessStatus> listUnarchivedServiceStatus(final UbiqubeId arg0, final String arg1, final List<String> arg2) throws ServiceException {
		//
		return null;
	}

	@Override
	public Map<Long, Long> listUnqueuedProcessByServiceId(final UbiqubeId arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public OrchestrationDefinition readOrchestrationDefinition(final String arg0, final boolean arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public ProcessDefinition readProcessDefinitionByUri(final String arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public OrchestrationScheduling readScheduling(final long arg0) throws ServiceException {
		//
		return null;
	}

	@Override
	public ServiceId readServiceIdByReference(final String arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public String readServiceVariable(final long arg0, final String arg1) throws DatabaseSystemException, ObjectNotFoundException {
		//
		return null;
	}

	@Override
	public void replaceServiceVariables(final long arg0, final Map<String, String> arg1) throws ServiceException {
		//

	}

	@Override
	public void reschedule(final OrchestrationScheduling arg0) throws ServiceException {
		//

	}

	@Override
	public void saveOrchestrationDefinition(final ManagerId arg0, final String arg1, final OrchestrationDefinition arg2, final String arg3) throws ServiceException {
		//

	}

	@Override
	public ProcessInstance scheduleImmediateModeByReference(final String arg0, final String arg1, final String arg2, final String arg3, final Map<String, String> arg4) throws ServiceException {
		//
		return null;
	}

	@Override
	public OrchestrationScheduling scheduleProcess(final OrchestrationScheduling arg0, final String arg1) throws ServiceException {
		//
		return null;
	}

	@Override
	public void unArchiveServiceInstance(final long arg0) throws ServiceException {
		//

	}

	@Override
	public void updateProcessScriptDetails(final long arg0, final String arg1, final long arg2, final long arg3) throws ServiceException {
		//

	}

	@Override
	public void updateServiceInstanceReference(final long arg0, final String arg1) throws ServiceException {
		//

	}

	@Override
	public void updateServiceVariable(final long arg0, final String arg1, final String arg2) throws DatabaseSystemException {
		//

	}
}
