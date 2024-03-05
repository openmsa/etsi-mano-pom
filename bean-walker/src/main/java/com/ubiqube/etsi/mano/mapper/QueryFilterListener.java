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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class QueryFilterListener implements BeanListener {
	private ListRecord lr;
	@Nonnull
	private final Deque<ListRecord> queue = new ArrayDeque<>();
	@Nonnull
	private final List<ListRecord> results = new ArrayList<>();

	@Override
	public void addProperty(final @Nullable Object source) {
		if (lr == null) {
			throw new IllegalArgumentException("");
		}
		if (null != source) {
			lr.getList().add(source);
		}
	}

	@Override
	public void startList(final String name) {
		if (lr != null) {
			queue.push(lr);
		}
		lr = new ListRecord(name);
	}

	@Override
	public void endList() {
		if (queue.isEmpty()) {
			if (!lr.getList().isEmpty()) {
				results.add(lr);
			}
			lr = null;
		} else {
			final ListRecord tmp = lr;
			lr = queue.pop();
			lr.addChild(Objects.requireNonNull(tmp));
		}
	}

	@Override
	public void listElementStart(final int i) {
		// Nothing
	}

	@Override
	public void complexStart(final String name) {
		//
		startList(name);
	}

	@Override
	public void complexEnd() {
		//
		endList();
	}

	@Override
	public void listElementEnd() {
		//
	}

	@Override
	public void startMap(final String name) {
		throw new IllegalArgumentException("");

	}

	@Override
	public void mapStartEntry(final String key) {
		throw new IllegalArgumentException("");

	}

	@Override
	public void mapEndEntry(final String key) {
		throw new IllegalArgumentException("");
	}

	@Override
	public void endMap(final String name) {
		throw new IllegalArgumentException("");
	}

	public List<ListRecord> getResults() {
		return results;
	}

	public class ListRecord {
		private final String name;
		@Nonnull
		private final List<Object> list;
		@Nonnull
		private final List<ListRecord> child;

		public ListRecord(final String name) {
			this(name, new ArrayList<>());
		}

		public ListRecord(final String name, final @Nonnull List<Object> list) {
			this.name = name;
			this.list = list;
			child = new ArrayList<>();
		}

		public List<ListRecord> getChild() {
			return child;
		}

		public void addChild(final ListRecord childIn) {
			this.child.add(childIn);
		}

		public @Nullable String getName() {
			return name;
		}

		public List<Object> getList() {
			return list;
		}

		@Override
		public String toString() {
			return "ListRecord [name=" + name + ", list=" + list + ", child=" + child + "]";
		}
	}
}
