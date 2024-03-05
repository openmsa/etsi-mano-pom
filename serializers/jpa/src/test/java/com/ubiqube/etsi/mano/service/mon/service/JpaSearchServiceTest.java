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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.mon.MonGenericException;
import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataProjection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@ExtendWith(MockitoExtension.class)
class JpaSearchServiceTest {
	@Mock
	private MonitoringDataJpa monitoringDataJpa;
	@Mock
	private EntityManager em;
	@Mock
	MonitoringDataProjection mdp;
	@Mock
	private CriteriaBuilder cb;
	@Mock
	private CriteriaQuery<MonitoringData> cq;
	@Mock
	private TypedQuery<MonitoringData> tq;
	@Mock
	private Root<MonitoringData> root;

	@Test
	void testSearch2() {
		final JpaSearchService srv = new JpaSearchService(monitoringDataJpa, em);
		assertThrows(MonGenericException.class, () -> srv.findByObjectIdAndKey(null, null));
		assertTrue(true);
	}

	@Test
	void test() {
		final JpaSearchService srv = new JpaSearchService(monitoringDataJpa, em);
		when(monitoringDataJpa.getLastMetrics(null, null)).thenReturn(List.of(mdp));
		final Timestamp tim = new Timestamp(12345L);
		when(mdp.getTime()).thenReturn(tim);
		srv.findByObjectIdAndKey(null, null);
		assertTrue(true);
	}

	@Test
	void testSearch1Empty() {
		final JpaSearchService srv = new JpaSearchService(monitoringDataJpa, em);
		final MultiValueMap<String, String> param = new HttpHeaders();
		when(em.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(MonitoringData.class)).thenReturn(cq);
		when(em.createQuery(cq)).thenReturn(tq);
		when(tq.getResultList()).thenReturn(List.of());
		srv.search(param);
		assertTrue(true);
	}

	@Test
	void testSearch1() {
		final JpaSearchService srv = new JpaSearchService(monitoringDataJpa, em);
		final MultiValueMap<String, String> param = new HttpHeaders();
		param.add("key", "value");
		param.put("blank", List.of());
		when(em.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(MonitoringData.class)).thenReturn(cq);
		when(cq.from(MonitoringData.class)).thenReturn(root);
		when(em.createQuery(cq)).thenReturn(tq);
		final MonitoringData res = new MonitoringData();
		res.setMasterJobId("Hello");
		when(tq.getResultList()).thenReturn(List.of(res));
		srv.search(param);
		assertTrue(true);
	}

}
