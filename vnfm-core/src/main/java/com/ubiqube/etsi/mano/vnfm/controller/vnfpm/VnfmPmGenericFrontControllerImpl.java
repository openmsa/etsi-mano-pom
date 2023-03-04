/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import static com.ubiqube.etsi.mano.Constants.VNFPMJOB_SEARCH_DEFAULT_EXCLUDE_FIELDS;
import static com.ubiqube.etsi.mano.Constants.VNFPMJOB_SEARCH_MANDATORY_FIELDS;
import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJobCriteria;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.MetricGroupService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.vnfm.fc.vnfpm.VnfmPmGenericFrontController;

import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfmPmGenericFrontControllerImpl implements VnfmPmGenericFrontController {
	private final VnfmPmController vnfmPmController;
	private final VnfInstanceGatewayService vnfInstanceGatewayService;
	private final MapperFacade mapper;
	private final MetricGroupService metricGroupService;
	private final SubscriptionService subscriptionService;
	private final ObjectMapper objectMapper;

	public VnfmPmGenericFrontControllerImpl(final VnfmPmController vnfmPmController, final VnfInstanceGatewayService vnfInstanceGatewayService, final MapperFacade mapper,
			final MetricGroupService metricGroupService, final SubscriptionService subscriptionService) {
		this.vnfmPmController = vnfmPmController;
		this.vnfInstanceGatewayService = vnfInstanceGatewayService;
		this.mapper = mapper;
		this.metricGroupService = metricGroupService;
		this.subscriptionService = subscriptionService;
		objectMapper = new ObjectMapper();
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final Consumer<U> makeLink) {
		return vnfmPmController.search(requestParams, clazz, VNFPMJOB_SEARCH_DEFAULT_EXCLUDE_FIELDS, VNFPMJOB_SEARCH_MANDATORY_FIELDS, makeLink);
	}

	@Override
	public ResponseEntity<Void> deleteById(final UUID pmJobId) {
		vnfmPmController.delete(pmJobId);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final UUID pmJobIdn, final Class<U> clazz, final Consumer<U> makeLinks) {
		final com.ubiqube.etsi.mano.dao.mano.pm.PmJob pmJob = vnfmPmController.findById(pmJobIdn);
		final U ret = mapper.map(pmJob, clazz);
		makeLinks.accept(ret);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@Override
	public <U> ResponseEntity<U> findReportById(final String pmJobId, final String reportId, final Class<U> clazz) {
		final com.ubiqube.etsi.mano.dao.mano.pm.PerformanceReport pm = vnfmPmController.findReport(getSafeUUID(pmJobId), getSafeUUID(reportId));
		return ResponseEntity.ok(mapper.map(pm, clazz));
	}

	@Override
	public <U> ResponseEntity<U> pmJobsPost(@Valid final Object createPmJobRequest, final Class<U> clazz, final Consumer<U> makeLinks, final Function<U, String> getSelfLink) {
		com.ubiqube.etsi.mano.dao.mano.pm.PmJob res = mapper.map(createPmJobRequest, com.ubiqube.etsi.mano.dao.mano.pm.PmJob.class);
		ensureCriteria(res);
		final List<VnfInstance> insts = checkInstanceIds(res);
		setVim(res, insts);
		res = vnfmPmController.save(res);
		createSubscriptionIfNeeded(res);
		final U obj = mapper.map(res, clazz);
		makeLinks.accept(obj);
		final String link = getSelfLink.apply(obj);
		return ResponseEntity.created(URI.create(link)).body(obj);
	}

	private static void setVim(final PmJob res, final List<VnfInstance> insts) {
		final List<VimConnectionInformation> vims = insts.stream().flatMap(x -> x.getVimConnectionInfo().stream())
				.distinct()
				.toList();
		if (vims.size() != 1) {
			throw new GenericException("Bad number of Vim: " + vims.size());
		}
		res.setVimConnectionInformation(vims.get(0));
	}

	/**
	 * In 2.7.1+ We have to check and create the subscription. On error 422
	 *
	 * @param A PM job with an Id.
	 */
	private void createSubscriptionIfNeeded(final PmJob res) {
		if (null == res.getCallbackUri()) {
			return;
		}
		final String cond = createCondition(res.getId());
		final Subscription subscription = Subscription.builder()
				.authentication(res.getSubscription())
				.callbackUri(res.getCallbackUri().toString())
				.subscriptionType(SubscriptionType.VNFPM)
				.nodeFilter(cond)
				.build();
		subscriptionService.save(subscription, String.class, SubscriptionType.VNFPM);
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

	@Override
	public <U> ResponseEntity<U> pmJobsPmJobIdPatch(final UUID pmJobId, final Object pmJobModifications) {
		// XXX
		throw new UnsupportedOperationException();
	}
}
