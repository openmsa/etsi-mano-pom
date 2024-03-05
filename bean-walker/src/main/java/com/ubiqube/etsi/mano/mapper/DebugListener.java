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
package com.ubiqube.etsi.mano.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;

public class DebugListener implements BeanListener {
	private static final Logger LOG = LoggerFactory.getLogger(DebugListener.class);

	@Override
	public void addProperty(final @Nullable Object source) {
		LOG.debug("addProperty: {}", source);
	}

	@Override
	public void startList(final String name) {
		LOG.debug("startList: {}", name);
	}

	@Override
	public void endList() {
		LOG.debug("endList");
	}

	@Override
	public void listElementStart(final int i) {
		LOG.debug("listElementStart {}", i);

	}

	@Override
	public void complexStart(final String name) {
		LOG.debug("complexStart {}", name);
	}

	@Override
	public void complexEnd() {
		LOG.debug("complexEnd");
	}

	@Override
	public void listElementEnd() {
		LOG.debug("listElementEnd");
	}

	@Override
	public void startMap(final String name) {
		LOG.debug("startMap {}", name);
	}

	@Override
	public void mapStartEntry(final String key) {
		LOG.debug("mapStartEntry {}", key);
	}

	@Override
	public void mapEndEntry(final String key) {
		LOG.debug("mapEndEntry {}", key);
	}

	@Override
	public void endMap(final String name) {
		LOG.debug("endMap {}", name);
	}

}
