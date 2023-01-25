/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PackageOperationalStateType;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PackageUsageStateType;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilter;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilter.NotificationTypesEnum;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilterVersions;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilterVnfProducts;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilterVnfProductsFromProviders;
import com.ubiqube.etsi.mano.mapper.BeanWalker;
import com.ubiqube.etsi.mano.mapper.QueryFilterListener;
import com.ubiqube.etsi.mano.mapper.QueryFilterListener.ListRecord;
import com.ubiqube.etsi.mano.service.cond.BooleanOperatorEnum;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;
import com.ubiqube.etsi.mano.service.cond.visitor.BooleanListExprRemoverVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.PrintVisitor;

class QueryFilterTest {

	private final BeanWalker beanWalker;

	public QueryFilterTest() {
		beanWalker = new BeanWalker();
	}

	@Test
	void testName() throws Exception {
		final PkgmNotificationsFilter pkgmFilter = new PkgmNotificationsFilter();
		final List<NotificationTypesEnum> nts = List.of(NotificationTypesEnum.VnfPackageChangeNotification, NotificationTypesEnum.VnfPackageOnboardingNotification);
		pkgmFilter.setNotificationTypes(nts);
		final List<PkgmNotificationsFilterVnfProductsFromProviders> vpfps = List.of(createFilterProvider001());
		pkgmFilter.setVnfProductsFromProviders(vpfps);
		final QueryFilterListener beanListener = new QueryFilterListener();
		beanWalker.walk(pkgmFilter, beanListener);
		final List<ListRecord> res = beanListener.getResults();
		//
		final BooleanExpression node = toManoCondition(res);

		final BooleanListExprRemoverVisitor visitor = new BooleanListExprRemoverVisitor();
		final Node nn = node.accept(visitor, null);
		//
		final PrintVisitor pv = new PrintVisitor();
		final String deb = nn.accept(pv, 0);
		System.out.println(deb);
		final ObjectMapper om = new ObjectMapper();
		final String str = om.writeValueAsString(nn);
		System.out.println(str);
		assertTrue(true);
	}

	@Test
	void testVnfInd() throws Exception {
		final VnfIndicatorNotificationsFilter vnfInd = new VnfIndicatorNotificationsFilter();
		final QueryFilterListener beanListener = new QueryFilterListener();
		beanWalker.walk(vnfInd, beanListener);
		final List<ListRecord> res = beanListener.getResults();
		//
		final BooleanExpression node = toManoCondition(res);

		final BooleanListExprRemoverVisitor visitor = new BooleanListExprRemoverVisitor();
		final Node nn = node.accept(visitor, null);
		//
		final ObjectMapper om = new ObjectMapper();
		final String str = om.writeValueAsString(nn);
		System.out.println(str);
		assertTrue(true);
	}

	@Test
	void testNullProperties() {
		final VnfIndicatorNotificationsFilter vnfInd = new VnfIndicatorNotificationsFilter();
		final PkgmNotificationsFilter pkgmFilter = new PkgmNotificationsFilter();
		final QueryFilterListener beanListener = new QueryFilterListener();
		beanWalker.walk(vnfInd, beanListener);
		final List<ListRecord> res = beanListener.getResults();
		assertNotNull(res);
		final BooleanExpression node = toManoCondition(res);
	}

	private static BooleanExpression toManoCondition(final List<ListRecord> lrs) {
		final List<BooleanExpression> res = new ArrayList<>();
		lrs.forEach(x -> {
			if (x.getChild() == null) {
				res.add(createSimpleEqual(x));
			} else {
				res.addAll(handleTree(x));
			}
		});
		return new BooleanListExpr(BooleanOperatorEnum.AND, res);
	}

	private static List<BooleanExpression> handleTree(final ListRecord lr) {
		final ArrayList<BooleanExpression> ret = new ArrayList<>();
		if (!lr.getList().isEmpty()) {
			ret.add(createSimpleEqual(lr));
		}
		ret.add(toManoCondition(lr.getChild()));
		return ret;
	}

	private static BooleanListExpr createSimpleEqual(final ListRecord lr) {
		final List<? extends BooleanExpression> lst = lr.getList().stream()
				.map(x -> new GenericCondition(LabelExpression.of(lr.getName()), Operator.EQUAL, convert(x)))
				.toList();
		return new BooleanListExpr(BooleanOperatorEnum.OR, (List<BooleanExpression>) lst);
	}

	private static Node convert(final Object obj) {
		if (obj instanceof final Enum<?> e) {
			return new TestValueExpr(e.toString());
		}
		if (obj instanceof final String s) {
			return new TestValueExpr(s);
		}
		if (obj instanceof final Double d) {
			return new NumberValueExpr(d);
		}
		if (obj instanceof final Boolean b) {
			return new BooleanValueExpr(b);
		}
		throw new IllegalArgumentException("Could not convert: " + obj.getClass());
	}

	PkgmNotificationsFilterVnfProductsFromProviders createFilterProvider001() {
		final PkgmNotificationsFilterVnfProductsFromProviders pnfvpfp = new PkgmNotificationsFilterVnfProductsFromProviders();
		pnfvpfp.setOperationalState(List.of(PackageOperationalStateType.DISABLED));
		pnfvpfp.setUsageState(List.of(PackageUsageStateType.IN_USE));
		pnfvpfp.setVnfdId(List.of("vnfdId#1", "VnfdId#2"));
		pnfvpfp.setVnfPkgId(List.of("vnfPkgId#1", "vnfPkgId#2"));
		final List<PkgmNotificationsFilterVnfProducts> vnfProducts = List.of(createVnfProduct1());
		pnfvpfp.setVnfProducts(vnfProducts);
		pnfvpfp.setVnfProvider("provider");
		return pnfvpfp;
	}

	private PkgmNotificationsFilterVnfProducts createVnfProduct1() {
		final PkgmNotificationsFilterVnfProducts ret = new PkgmNotificationsFilterVnfProducts();
		ret.setVnfProductName("productname");
		ret.setVersions(List.of(createVersion1()));
		return ret;
	}

	private PkgmNotificationsFilterVersions createVersion1() {
		final PkgmNotificationsFilterVersions ret = new PkgmNotificationsFilterVersions();
		ret.setVnfdVersions(List.of("0.0.1", "0.0.2"));
		ret.setVnfSoftwareVersion("sw");
		return ret;
	}
}
