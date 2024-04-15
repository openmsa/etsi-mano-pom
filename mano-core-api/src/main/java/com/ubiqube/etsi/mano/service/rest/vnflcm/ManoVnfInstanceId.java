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
package com.ubiqube.etsi.mano.service.rest.vnflcm;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoVnfInstanceId {

	private static final Logger LOG = LoggerFactory.getLogger(ManoVnfInstanceId.class);
	@Nonnull
	private final QueryParameters client;

	public ManoVnfInstanceId(final QueryParameters manoClient, final UUID vnfInstanceId) {
		this.client = manoClient;
		manoClient.setQueryType(ApiVersionType.SOL003_VNFLCM);
		manoClient.setObjectId(vnfInstanceId);
		manoClient.setFragment("vnf_instances/{id}");
	}

	public VnfBlueprint instantiate(final VnfInstantiate instantiateVnfRequest) {
		client.setFragment("vnf_instances/{id}/instantiate");
		final BiFunction<HttpGateway, Object, Object> mapper = (x, y) -> x.getVnfInstanceInstantiateRequest(instantiateVnfRequest);
		return (VnfBlueprint) client.createQuery()
				.setWireInClass(mapper)
				.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.post(instantiateVnfRequest);
	}

	public void delete() {
		client.createQuery()
				.delete();
	}

	public @Nullable VnfBlueprint terminate(final CancelModeTypeEnum terminationType, final Integer gracefulTerminationTimeout) {
		try {
			client.setFragment("vnf_instances/{id}/terminate");
			final BiFunction<HttpGateway, Object, Object> request = (httpGateway, x) -> httpGateway.createVnfInstanceTerminate(terminationType, gracefulTerminationTimeout);
			return (VnfBlueprint) client.createQuery()
					.setWireInClass(request)
					.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
					.setOutClass(HttpGateway::mapToVnfBlueprint)
					.post();
		} catch (final RuntimeException e) {
			LOG.warn("", e);
			return null;
		}
	}

	public VnfBlueprint scaleToLevel(final VnfScaleToLevelRequest scaleVnfToLevelRequest) {
		client.setFragment("vnf_instances/{id}/scale_to_level");
		final BiFunction<HttpGateway, Object, Object> mapper = (x, y) -> x.getVnfInstanceScaleToLevelRequest(scaleVnfToLevelRequest);
		return (VnfBlueprint) client.createQuery()
				.setWireInClass(mapper)
				.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.post(scaleVnfToLevelRequest);

	}

	public VnfBlueprint scale(final VnfScaleRequest scaleVnfRequest) {
		client.setFragment("vnf_instances/{id}/scale");
		final BiFunction<HttpGateway, Object, Object> request = (httpGateway, y) -> httpGateway.createVnfInstanceScaleRequest(scaleVnfRequest.getType(), scaleVnfRequest.getAspectId(), scaleVnfRequest.getNumberOfSteps());
		return (VnfBlueprint) client.createQuery()
				.setWireInClass(request)
				.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.post(scaleVnfRequest);
	}

	public VnfBlueprint heal(final VnfHealRequest healVnfRequest) {
		client.setFragment("vnf_instances/{id}/heal");
		final BiFunction<HttpGateway, Object, Object> request = (httpGateway, y) -> httpGateway.createVnfInstanceHealRequest(healVnfRequest);
		return (VnfBlueprint) client.createQuery()
				.setWireInClass(request)
				.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.post(healVnfRequest);
	}

	public VnfBlueprint operate(final VnfOperateRequest operateVnfRequest) {
		client.setFragment("vnf_instances/{id}/operate");
		final BiFunction<HttpGateway, Object, Object> mapper = (x, y) -> x.getVnfInstanceOperateRequest(operateVnfRequest);
		return (VnfBlueprint) client.createQuery()
				.setWireInClass(mapper)
				.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.post(operateVnfRequest);
	}

	public VnfBlueprint changeExtConn(final ChangeExtVnfConnRequest cevcr) {
		client.setFragment("vnf_instances/{id}/change_ext_conn");
		final BiFunction<HttpGateway, Object, Object> mapper = (x, y) -> x.getVnfInstanceChangeExtConnRequest(cevcr);
		return (VnfBlueprint) client.createQuery()
				.setWireInClass(mapper)
				.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.post(cevcr);
	}

	public @Nullable VnfInstance find() {
		return (VnfInstance) client.createQuery()
				.setWireOutClass(HttpGateway::getVnfInstanceClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.getSingle();
	}

	public VnfBlueprint changeFlavour(final ChangeVnfFlavourData req) {
		client.setFragment("vnf_instances/{id}/change_flavour");
		final BiFunction<HttpGateway, Object, Object> mapper = (x, y) -> x.getVnfInstanceChangeFalvourRequest(req);
		return (VnfBlueprint) client.createQuery()
				.setWireInClass(mapper)
				.setWireOutClass(HttpGateway::getVnfLcmOpOccsClass)
				.setOutClass(HttpGateway::mapToVnfBlueprint)
				.post(req);
	}

	public Object patch(final Map<String, Object> patchData) {
		return client.createQuery()
				.setWireOutClass(HttpGateway::getVnfPackageClass)
				.setOutClass(HttpGateway::mapToVnfPackage)
				.patch(null, patchData);
	}

}
