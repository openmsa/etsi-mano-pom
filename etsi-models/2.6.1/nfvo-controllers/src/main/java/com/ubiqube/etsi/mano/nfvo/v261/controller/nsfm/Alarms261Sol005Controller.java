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
package com.ubiqube.etsi.mano.nfvo.v261.controller.nsfm;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.common.v261.model.Link;
import com.ubiqube.etsi.mano.controller.nsfm.NsAlarmFrontController;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.Alarm;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.AlarmLinks;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.AlarmModifications;

import jakarta.validation.Valid;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@RestController
public class Alarms261Sol005Controller implements Alarms261Sol005Api {
	private final NsAlarmFrontController nsAlarmFrontController;

	public Alarms261Sol005Controller(final NsAlarmFrontController nsAlarmFrontController) {
		this.nsAlarmFrontController = nsAlarmFrontController;
	}

	@Override
	public ResponseEntity<Alarm> alarmsAlarmIdGet(final String alarmId) {
		return nsAlarmFrontController.findById(getSafeUUID(alarmId), Alarm.class, Alarms261Sol005Controller::makeLinks);
	}

	@Override
	public ResponseEntity<AlarmModifications> alarmsAlarmIdPatch(@Valid final AlarmModifications body, final String alarmId) {
		return nsAlarmFrontController.patch(alarmId, body, AlarmModifications.class);
	}

	@Override
	public ResponseEntity<List<Alarm>> alarmsGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return nsAlarmFrontController.search(requestParams, nextpageOpaqueMarker, Alarm.class, Alarms261Sol005Controller::makeLinks);
	}

	private static void makeLinks(final Alarm alarm) {
		final AlarmLinks links = new AlarmLinks();
		final Link self = new Link();
		self.setHref(linkTo(methodOn(Alarms261Sol005Api.class).alarmsAlarmIdGet(alarm.getId())).withSelfRel().getHref());
		links.setSelf(self);
		alarm.setLinks(links);
	}
}
