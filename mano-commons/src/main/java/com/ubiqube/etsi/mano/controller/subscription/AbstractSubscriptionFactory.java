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
package com.ubiqube.etsi.mano.controller.subscription;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ubiqube.etsi.mano.exception.GenericException;

import jakarta.annotation.Nonnull;

/**
 * @author olivier
 */
public abstract class AbstractSubscriptionFactory {
	@Nonnull
	private final Map<ApiAndType, SubscriptionLinkable> subscriptions;

	protected AbstractSubscriptionFactory(final List<? extends SubscriptionLinkable> subs) {
		this.subscriptions = subs.stream()
				.collect(Collectors.toMap(SubscriptionLinkable::getApiAndType, x -> x));
	}

	public Map<ApiAndType, SubscriptionLinkable> getSubscriptions() {
		return subscriptions;
	}

	public String createSubscriptionLink(final ApiAndType at, final String id) {
		final SubscriptionLinkable res = getSubscriptions().get(at);
		if (null == res) {
			throw new GenericException("Unable to find subscription API for " + at);
		}
		return res.makeSelfLink(id);
	}
}
