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
package com.ubiqube.etsi.mano.service.event.jms;

public class Constants {

	private Constants() {
		//
	}

	public static final String QUEUE_NOTIFICATION = "${spring.application.name:none}.system.notifications";

	public static final String QUEUE_VNFM_ACTIONS = "${spring.application.name:none}.system.actions.vnfm";

	public static final String QUEUE_NFVO_ACTIONS = "${spring.application.name:none}.system.actions.nfvo";

	public static final String QUEUE_GRANT = "${spring.application.name:none}.system.actions.grants";

	public static final String QUEUE_COMMON_ACTION = "${spring.application.name:none}.system.actions.common";

	public static final String QUEUE_NOTIFICATION_SENDER = "${spring.application.name:none}.system.notifications.sender";
}
