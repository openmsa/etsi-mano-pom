/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.pm.ObjectType;
import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceReport;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJobCriteria;
import com.ubiqube.etsi.mano.dao.mano.pm.ResolvedObjectId;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.MetricGroupService;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.mon.MonitoringManager;
import com.ubiqube.etsi.mano.vnfm.service.PmJobsService;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfmPmControllerImpl implements VnfmPmController {
	private final PmJobsService pmJobsJpa;
	private final SearchableService searchableService;
	private final MetricGroupService metricGroupService;
	private final SubscriptionService subscriptionService;
	private final ObjectMapper objectMapper;
	private final VnfInstanceGatewayService vnfInstanceGatewayService;
	private final MonitoringManager monitoringManager;
	@Nullable
	private final URI frontendUrl;

	public VnfmPmControllerImpl(final PmJobsService pmJobsJpa, final SearchableService searchableService, final MetricGroupService metricGroupService,
			final SubscriptionService subscriptionService, final com.ubiqube.etsi.mano.config.properties.ManoProperties props,
			final VnfInstanceGatewayService vnfInstanceGatewayService, final MonitoringManager monitoringManager) {
		this.pmJobsJpa = pmJobsJpa;
		this.searchableService = searchableService;
		this.metricGroupService = metricGroupService;
		this.subscriptionService = subscriptionService;
		this.vnfInstanceGatewayService = vnfInstanceGatewayService;
		this.monitoringManager = monitoringManager;
		this.frontendUrl = props.getFrontendUrl();
		objectMapper = new ObjectMapper();
	}

	@Override
	public void delete(final UUID id) {
		final PmJob pmJob = findById(id);
		monitoringManager.delete(pmJob);
		pmJobsJpa.deleteById(id);
	}

	@Override
	public PmJob findById(final UUID id) {
		return pmJobsJpa.findById(id).orElseThrow(() -> new NotFoundException("Could not find PM Job: " + id));
	}

	@Override
	public PerformanceReport findReport(final UUID fromString, final UUID fromString2) {
		throw new UnsupportedOperationException();
	}

	@Transactional
	@Override
	public PmJob save(final PmJob res) {
		ensureCriteria(res);
		final List<VnfInstance> insts = checkInstanceIds(res);
		setVim(res, insts);
		resolvSubObjectsId(res, insts);
		final PmJob localRes = pmJobsJpa.save(res);
		createSubscriptionIfNeeded(localRes);
		monitoringManager.create(localRes);
		return pmJobsJpa.save(localRes);
	}

	private static void setVim(final PmJob res, final List<VnfInstance> insts) {
		final List<VimConnectionInformation> vims = insts.stream().flatMap(x -> x.getVimConnectionInfo().stream())
				.distinct()
				.toList();
		if (vims.size() != 1) {
			throw new GenericException("Bad number of Vim: " + vims.size());
		}
		res.setVimConnectionInformation(vims.getFirst());
	}

	/**
	 * In 2.7.1+ We have to check and create the subscription. On error 422
	 *
	 * @param A PM job with an Id.
	 */
	private void createSubscriptionIfNeeded(final PmJob res) {
		if (null != res.getCallbackUri()) {
			return;
		}
		// XXX: It is more complex, need my URL.
		final String cond = createCondition(res.getId());
		final Subscription subscription = Subscription.builder()
				.authentication(copy(res.getAuthentication()))
				.callbackUri(frontendUrl)
				.subscriptionType(SubscriptionType.VNFPM)
				.nodeFilter(cond)
				.build();
		final Subscription subscr = subscriptionService.save(subscription);
		res.setSubscriptionRemoteId(subscr.getId());
	}

	private List<VnfInstance> checkInstanceIds(final PmJob res) {
		final List<String> objects = res.getObjectInstanceIds();
		if (objects.isEmpty()) {
			throw new NotFoundException("ObjectInstanceIds is mandatory.");
		}

		// Checking metrics.
		return res.getObjectInstanceIds().stream()
				.map(x -> vnfInstanceGatewayService.findById(getSafeUUID(x)))
				.toList();
	}

	private static AuthentificationInformations copy(final AuthentificationInformations authentication) {
		return AuthentificationInformations.builder()
				.authParamBasic(authentication.getAuthParamBasic())
				.authParamOauth2(authentication.getAuthParamOauth2())
				.authTlsCert(authentication.getAuthTlsCert())
				.authType(new ArrayList<>(authentication.getAuthType()))
				.build();
	}

	private String createCondition(final UUID id) {
		final LabelExpression name = new LabelExpression("id");
		final TestValueExpr value = new TestValueExpr(id.toString());
		final GenericCondition root = new GenericCondition(name, Operator.EQUAL, value);
		try {
			return objectMapper.writeValueAsString(root);
		} catch (final JsonProcessingException e) {
			throw new GenericException(e);
		}
	}

	private void ensureCriteria(final PmJob res) {
		final PmJobCriteria criteria = res.getCriteria();
		criteria.getPerformanceMetric();
		final List<String> expandedCriteria = criteria.getPerformanceMetricGroup().stream()
				.flatMap(x -> metricGroupService.findByGroupName(x).stream())
				.distinct()
				.toList();
		criteria.getPerformanceMetric().addAll(expandedCriteria);
	}

	private static void resolvSubObjectsId(final PmJob res, final List<VnfInstance> insts) {
		final List<String> subObjects = Objects.requireNonNull(res.getSubObjectInstanceIds(), "Pm job doesn't contain any subObjects.");
		if (subObjects.isEmpty()) {
			throw new GenericException("Pm job should have a subObject.");
		}
		final Set<ResolvedObjectId> resolved = subObjects
				.stream()
				.map(x -> findVnfName(x, insts))
				.collect(Collectors.toSet());
		res.setResolvedSubObjectInstanceIds(resolved);
	}

	private static ResolvedObjectId findVnfName(final String str, final List<VnfInstance> insts) {
		final List<ResolvedObjectId> osDu = insts.stream()
				.map(VnfInstance::getVnfPkg)
				.flatMap(x -> x.getOsContainerDeployableUnits().stream())
				.filter(x -> x.getName().equals(str))
				.map(x -> new ResolvedObjectId(null, str, ObjectType.OS_CONTAINER, x.getId()))
				.toList();
		final List<ResolvedObjectId> ret = new ArrayList<>(osDu);
		final List<ResolvedObjectId> vnfComp = insts.stream()
				.map(VnfInstance::getVnfPkg)
				.flatMap(x -> x.getVnfCompute().stream())
				.filter(x -> x.getToscaName().equals(str))
				.map(x -> new ResolvedObjectId(null, str, ObjectType.COMPUTE, x.getId()))
				.toList();
		ret.addAll(vnfComp);
		final List<ResolvedObjectId> vnfExtCp = insts.stream()
				.map(VnfInstance::getVnfPkg)
				.flatMap(x -> x.getVnfExtCp().stream())
				.filter(x -> x.getToscaName().equals(str))
				.map(x -> new ResolvedObjectId(null, str, ObjectType.EXT_CP, x.getId()))
				.toList();
		ret.addAll(vnfExtCp);
		final List<ResolvedObjectId> vnfVl = insts.stream()
				.map(VnfInstance::getVnfPkg)
				.flatMap(x -> x.getVnfVl().stream())
				.filter(x -> x.getToscaName().equals(str))
				.map(x -> new ResolvedObjectId(null, str, ObjectType.VL, x.getId()))
				.toList();
		ret.addAll(vnfVl);
		if (ret.size() > 1) {
			throw new GenericException("More than one object correspond to the given metric [" + str + " ] => " + ret);
		}
		if (ret.isEmpty()) {
			throw new GenericException("Could not find metric: " + str);
		}
		return ret.getFirst();
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<PmJob, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, final Class<?> frontClass) {
		return searchableService.search(PmJob.class, requestParams, mapper, excludeDefaults, mandatoryFields, makeLink, List.of(), frontClass);
	}

}
