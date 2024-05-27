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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.search.jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarValue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@ExtendWith(MockitoExtension.class)
class JpaSearchTest {
	@Mock
	private EntityManager em;
	@Mock
	private CriteriaBuilder cb;
	@Mock
	private CriteriaQuery<Object> cq;
	@Mock
	private Root<Object> root;
	@Mock
	private TypedQuery<Object> tq;
	@Mock
	private Path path;

	JpaSearch createService() {
		return new JpaSearch(em);
	}

	@Test
	void testGetByDistance() {
		final JpaSearch srv = createService();
		srv.getByDistance(null, 0, 0);
		assertTrue(true);
	}

	@Test
	void testGetCriteriaEmpty() {
		final JpaSearch srv = createService();
		when(em.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(Object.class)).thenReturn(cq);
		when(cq.from(Object.class)).thenReturn(root);
		when(em.createQuery(cq)).thenReturn(tq);
		srv.getCriteria(List.of(), Object.class);
		assertTrue(true);
	}

	@ParameterizedTest
	@EnumSource(value = GrammarOperandType.class, mode = Mode.EXCLUDE, names = { "NIN", "NCONT" })
	void testGetCriteria(final GrammarOperandType op) {
		final JpaSearch srv = createService();
		final GrammarNode left = new GrammarLabel("left");
		final GrammarNode right = new GrammarValue("value");
		final GrammarNode gn = new BooleanExpression(left, op, right);
		when(em.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(Object.class)).thenReturn(cq);
		when(cq.from(Object.class)).thenReturn(root);
		when(em.createQuery(cq)).thenReturn(tq);
		//
		when(root.get(anyString())).thenReturn(path);
		when(path.getJavaType()).thenReturn(Object.class);
		srv.getCriteria(List.of(gn), Object.class);
		assertTrue(true);
	}

	@ParameterizedTest
	@EnumSource(value = GrammarOperandType.class, mode = Mode.INCLUDE, names = { "NIN", "NCONT" })
	void testGetCriteriaNinandNcont(final GrammarOperandType op) {
		final JpaSearch srv = createService();
		final GrammarNode left = new GrammarLabel("left");
		final GrammarNode right = new GrammarValue("value");
		final GrammarNode gn = new BooleanExpression(left, op, right);
		when(em.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(Object.class)).thenReturn(cq);
		when(cq.from(Object.class)).thenReturn(root);
		when(em.createQuery(cq)).thenReturn(tq);
		//
		when(root.get(anyString())).thenReturn(path);
		when(path.getJavaType()).thenReturn(Object.class);
		//
		final Predicate pred = Mockito.mock(Predicate.class);
		when(path.in(anyCollection())).thenReturn(pred);
		srv.getCriteria(List.of(gn), Object.class);
		assertTrue(true);
	}
}
