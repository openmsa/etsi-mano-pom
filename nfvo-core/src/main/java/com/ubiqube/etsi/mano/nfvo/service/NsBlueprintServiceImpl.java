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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.service.SearchableService;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsBlueprintServiceImpl implements NsBlueprintService {

	private final NsBlueprintJpa nsBlueprintJpa;

	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	private final SearchableService searchableService;

	public NsBlueprintServiceImpl(final NsBlueprintJpa nsBlueprintJpa, final NsLiveInstanceJpa nsLiveInstanceJpa, final SearchableService searchableService) {
		this.nsBlueprintJpa = nsBlueprintJpa;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.searchableService = searchableService;
	}

	@Override
	public int getNumberOfLiveVl(final NsdInstance nsInstance, final NsVirtualLink x) {
		final List<NsLiveInstance> res = nsLiveInstanceJpa.findByVnfInstanceAndTaskVlIsNotNull(nsInstance, x.getToscaName());
		return res.size();
	}

	@Override
	public NsBlueprint findById(final UUID blueprintId) {
		return nsBlueprintJpa.findById(blueprintId).orElseThrow(() -> new NotFoundException("Could not find Ns Lcm: " + blueprintId));
	}

	@Override
	public NsBlueprint save(final NsBlueprint nsBlueprint) {
		return nsBlueprintJpa.save(nsBlueprint);
	}

	@Override
	public Blueprint<NsTask, NsdInstance> updateState(final NsBlueprint plan, final OperationStatusType processing) {
		plan.setOperationStatus(processing);
		return save(plan);
	}

	@Override
	public List<NsLiveInstance> findByNsdInstanceAndClass(final NsdInstance ret, final Class<?> simpleName) {
		return nsLiveInstanceJpa.findByNsdInstanceAndClass(ret, simpleName);
	}

	@Override
	public long countByNsInstance(final NsdInstance ret) {
		return nsLiveInstanceJpa.countByNsInstance(ret);
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<NsBlueprint, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, final Class<?> frontClass) {
		return searchableService.search(NsBlueprint.class, requestParams, mapper, excludeDefaults, mandatoryFields, makeLink, List.of(), frontClass);
	}

}
