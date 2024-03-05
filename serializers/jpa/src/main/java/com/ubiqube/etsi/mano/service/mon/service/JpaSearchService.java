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
package com.ubiqube.etsi.mano.service.mon.service;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.mon.MonGenericException;
import com.ubiqube.etsi.mano.mon.api.SearchApi;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataProjection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class JpaSearchService implements SearchApi {
	private final EntityManager em;
	private final MonitoringDataJpa monitoringDataJpa;

	public JpaSearchService(final MonitoringDataJpa monitoringDataJpa, final EntityManager em) {
		this.monitoringDataJpa = monitoringDataJpa;
		this.em = em;
	}

	@Override
	public List<MonitoringDataSlim> findByObjectIdAndKey(final String masterJobId, final String key) {
		final List<MonitoringDataProjection> ress = monitoringDataJpa.getLastMetrics(masterJobId, key);
		if (ress.isEmpty()) {
			throw new MonGenericException("Unable to find metric: " + masterJobId + "/" + key);
		}
		return ress.stream().map(JpaSearchService::convert).toList();
	}

	private static MonitoringDataSlim convert(final MonitoringDataProjection mdp) {
		return new TelemetryMetricsResult(mdp.getMasterJobId(), mdp.getResourceId(), mdp.getKey(), mdp.getValue(), mdp.getText(), mdp.getTime().toInstant().atOffset(ZoneOffset.UTC), true);
	}

	@Override
	public List<MonitoringDataSlim> search(final MultiValueMap<String, String> params) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<MonitoringData> cq = cb.createQuery(MonitoringData.class);
		final Root<MonitoringData> itemRoot = cq.from(MonitoringData.class);
		final List<Predicate> p2 = new ArrayList<>();
		for (final Map.Entry<String, List<String>> entry : params.entrySet()) {
			final String key = entry.getKey();
			final List<Predicate> preds = entry.getValue().stream()
					.map(x -> cb.equal(itemRoot.get(key), x))
					.toList();
			if (!preds.isEmpty()) {
				final Predicate pp = cb.or(preds.toArray(new Predicate[0]));
				p2.add(pp);
			}
		}
		Predicate finalPred = null;
		if (!p2.isEmpty()) {
			finalPred = cb.and(p2.toArray(new Predicate[0]));
		}
		cq.where(finalPred);
		return em.createQuery(cq).getResultList().stream()
				.map(JpaSearchService::convert)
				.toList();
	}

	private static MonitoringDataSlim convert(final MonitoringData x) {
		final TelemetryMetricsResult m = new TelemetryMetricsResult();
		m.setKey(x.getKey());
		m.setMasterJobId(x.getMasterJobId());
		m.setResourceId(x.getResourceId());
		m.setText(x.getText());
		m.setTime(x.getTime());
		m.setValue(x.getValue());
		return m;
	}
}
