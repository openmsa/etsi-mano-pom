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
package com.ubiqube.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.mapper.AttrHolder;
import com.ubiqube.etsi.mano.mapper.BeanWalker;
import com.ubiqube.etsi.mano.mapper.BeanWalkerException;
import com.ubiqube.etsi.mano.mapper.CollectHashMapListener;
import com.ubiqube.etsi.mano.mapper.CollectNonNullListener;
import com.ubiqube.etsi.mano.mapper.DebugListener;
import com.ubiqube.etsi.mano.mapper.JsonWalker;
import com.ubiqube.etsi.mano.mapper.QueryFilterListener;
import com.ubiqube.etsi.mano.mapper.QueryFilterListener.ListRecord;
import com.ubiqube.etsi.mano.mapper.SpelWriter;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;
import com.ubiqube.mapper.objects.PackageOperationalStateType;
import com.ubiqube.mapper.objects.PkgmNotificationsFilter;
import com.ubiqube.mapper.objects.PkgmNotificationsFilter.NotificationTypesEnum;
import com.ubiqube.mapper.objects.PkgmNotificationsFilterVnfProductsFromProviders;
import com.ubiqube.mapper.objects.PkgmSubscription;
import com.ubiqube.mapper.objects.PkgmSubscriptionLinks;
import com.ubiqube.mapper.objects.VnfPkgInfo;

import ma.glasnost.orika.impl.DefaultMapperFactory;

@SuppressWarnings("static-method")
class BeanWalkerTest {

	private final DefaultMapperFactory mapperFactory;

	public BeanWalkerTest() {
		mapperFactory = new DefaultMapperFactory.Builder().build();
	}

	@Test
	void testName() {
		final PkgmSubscription subsJson = createSubscription();
		final BeanWalker bw = new BeanWalker();
		final CollectNonNullListener beanListener = new CollectNonNullListener();
		bw.walk(subsJson, beanListener);
		final List<AttrHolder> attrs = beanListener.getAttrs();
		final SpelWriter sw = new SpelWriter(mapperFactory.getMapperFacade());
		final List<FilterAttributes> swRes = sw.getFilterAttrs(attrs);

		assertEquals(4, swRes.size());
		FilterAttributes swElem = swRes.get(0);
		assertEquals("callbackUri", swElem.getAttribute());
		assertEquals("http://callbackUri/", swElem.getValue());
		swElem = swRes.get(1);
		assertEquals("filter.notificationTypes[0]", swElem.getAttribute());
		assertEquals("VnfPackageChangeNotification", swElem.getValue());
		swElem = swRes.get(2);
		assertEquals("filter.vnfProductsFromProviders[0].operationalState[0]", swElem.getAttribute());
		assertEquals("DISABLED", swElem.getValue());
		swElem = swRes.get(3);
		assertEquals("id", swElem.getAttribute());
		assertEquals("96feacab-2765-4927-9bf5-883f6b566f36", swElem.getValue());
	}

	private static PkgmSubscription createSubscription() {
		final PkgmSubscription subsJson = new PkgmSubscription();
		subsJson.setCallbackUri("http://callbackUri/");
		final PkgmNotificationsFilter filter = new PkgmNotificationsFilter();
		filter.setNotificationTypes(Arrays.asList(NotificationTypesEnum.VnfPackageChangeNotification));
		final List<PkgmNotificationsFilterVnfProductsFromProviders> vnfProductsFromProviders = new ArrayList<>();
		final PkgmNotificationsFilterVnfProductsFromProviders subProv = new PkgmNotificationsFilterVnfProductsFromProviders();
		subProv.addOperationalStateItem(PackageOperationalStateType.DISABLED);
		vnfProductsFromProviders.add(subProv);
		filter.setVnfProductsFromProviders(vnfProductsFromProviders);
		subsJson.setFilter(filter);
		subsJson.setId(UUID.fromString("96feacab-2765-4927-9bf5-883f6b566f36").toString());
		final PkgmSubscriptionLinks links = new PkgmSubscriptionLinks();
		subsJson.setLinks(links);
		return subsJson;
	}

	@Test
	void testHashMap() {
		final VnfPkgInfo subsJson = new VnfPkgInfo();
		final Map<String, String> userDefinedData = new HashMap<>();
		userDefinedData.put("test", "value");
		subsJson.setUserDefinedData(userDefinedData);
		final BeanWalker bw = new BeanWalker();
		final CollectNonNullListener beanListener = new CollectNonNullListener();
		bw.walk(subsJson, beanListener);
		final List<AttrHolder> attrs = beanListener.getAttrs();
		final SpelWriter sw = new SpelWriter(mapperFactory.getMapperFacade());
		final List<FilterAttributes> swRes = sw.getFilterAttrs(attrs);
		assertEquals(1, swRes.size());
		final FilterAttributes sw0 = swRes.get(0);
		assertEquals("userDefinedData[test]", sw0.getAttribute());
	}

	@Test
	void testDebugWalker() {
		final PkgmSubscription subsJson = createSubscription();

		final BeanWalker bw = new BeanWalker();
		final DebugListener beanListener = new DebugListener();
		bw.walk(subsJson, beanListener);
		assertTrue(true);
	}

	@Test
	void testCollectHashMapListener() {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonWalker jw = new JsonWalker(mapper);
		final PkgmSubscription subs = createSubscription();
		final CollectHashMapListener beanListener = new CollectHashMapListener(subs.getClass());
		final String patch = """
				{
					"callbackUri": "http://",
					"filter": {
						"vnfdId": "vnfdId",
						"notificationTypes": ["VnfPackageChangeNotification"],
						"vnfProductsFromProviders": [
							{
								"vnfProvider": "vnfProvider"
							}
						]
					},
					"userData": {
						"key": "lav"
					}
				}
				""";
		jw.walk(patch, beanListener);
		final List<AttrHolder> attrs = beanListener.getAttrs();
		assertNotNull(attrs);
	}

	@Test
	void testBadArgument() {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonWalker jw = new JsonWalker(mapper);
		final PkgmSubscription subs = createSubscription();
		final CollectHashMapListener beanListener = new CollectHashMapListener(subs.getClass());
		final String patch = """
				{
					"bad": "value"
				}
				""";
		assertThrows(BeanWalkerException.class, () -> jw.walk(patch, beanListener));
	}

	@Test
	void testQueryFilterListener() {
		final QueryFilterListener qfl = new QueryFilterListener();
		final PkgmSubscription subsJson = createSubscription();
		final BeanWalker bw = new BeanWalker();
		bw.walk(subsJson, qfl);
		final List<ListRecord> res = qfl.getResults();
		assertNotNull(res);
		res.toString();
		final ListRecord rr = res.get(0);
		rr.toString();
		rr.getChild();
		rr.getList();
		rr.getName();
		assertTrue(true);
	}
}
