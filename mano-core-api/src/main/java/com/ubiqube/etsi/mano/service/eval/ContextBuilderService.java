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
package com.ubiqube.etsi.mano.service.eval;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsdPackageDb;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.cond.BasicContext;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.utils.ReflectionUtils;

@Service
public class ContextBuilderService {
	private final VnfPackageService vnfPackageService;
	private final NsdPackageDb nsdPackageDb;
	private final VnfInstanceJpa vnfInstanceJpa;

	public ContextBuilderService(final VnfPackageService vnfPackageService, final NsdPackageDb nsdPackageDb, final VnfInstanceJpa vnfInstanceJpa) {
		this.vnfPackageService = vnfPackageService;
		this.nsdPackageDb = nsdPackageDb;
		this.vnfInstanceJpa = vnfInstanceJpa;
	}

	public Context build(final SubscriptionType type, final UUID id, final String eventName) {
		final Object res = switch (type) {
		case VNF -> vnfPackageService.findById(id);
		case VNFLCM -> vnfInstanceJpa.findById(id);
		case NSD -> nsdPackageDb.get(id);
		case NSLCM -> vnfPackageService.findById(id);
		case NSDVNF -> vnfPackageService.findById(id);
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
		Objects.requireNonNull(res, "No result for " + type + '#' + id);
		return buildContext(res, eventName);
	}

	public static Context build(final Map<String, String> map, final String eventName) {
		final Map<String, Object> m2 = new LinkedHashMap<>(map);
		m2.put("notificationTypes", eventName);
		return new BasicContext(m2);
	}

	private static Context buildContext(final Object res, final String eventName) {
		final Map<String, Object> ctx = new HashMap<>();
		BeanInfo cls;
		try {
			cls = Introspector.getBeanInfo(res.getClass());
		} catch (final IntrospectionException e) {
			throw new GenericException(e);
		}
		final PropertyDescriptor[] clspd = cls.getPropertyDescriptors();
		for (final PropertyDescriptor pd : clspd) {
			final Method rm = Objects.requireNonNull(pd.getReadMethod());
			final Object oRes = ReflectionUtils.invoke(rm, res);
			if (null != oRes) {
				ctx.put(pd.getName(), oRes);
			}
		}
		ctx.put("notificationTypes", eventName);
		return new BasicContext(ctx);
	}
}
