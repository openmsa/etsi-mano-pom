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
package com.ubiqube.etsi.mano.service.grant.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;
import com.ubiqube.etsi.mano.service.sys.ServerGroup;

@Service
public class ServerGroupExecutor {

	/**
	 * XXX Check Vim caps. ZoneGroup are not available on all vims.
	 *
	 * @param grants
	 * @return callable.
	 */
	public Callable<ZoneGroupInformation> getServerGroup(final GrantResponse grants) {
		return () -> {
			final List<ServerGroup> sg = new ArrayList<>();
			final ServerGroup serverGroup = new ServerGroup("1", "az", "az");
			sg.add(serverGroup);
			final Set<String> sgList = sg.stream().map(ServerGroup::getId).collect(Collectors.toSet());
			final ZoneGroupInformation zgi = new ZoneGroupInformation();
			zgi.setZoneId(sgList);
			grants.setZoneGroups(Collections.singleton(zgi));
			return zgi;
		};
	}

}
