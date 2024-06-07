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
package com.ubiqube.etsi.mano.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class StopWatch {

	private final Instant start;

	private final Deque<Metric> stack = new ArrayDeque<>();

	private final Logger log;

	public StopWatch(final Logger log) {
		this.start = Instant.now();
		this.log = log;
	}

	public static StopWatch create(final Logger log) {
		return new StopWatch(log);
	}

	public void start(final String string) {
		final Metric m = Metric.builder()
				.start(Instant.now())
				.label(string)
				.indent(stack.size())
				.build();
		stack.push(m);
	}

	public void stop() {
		final Metric m = stack.pop();
		m.setStop(Instant.now());
		final Duration d2 = Duration.between(m.getStart(), m.getStop());
		log.debug("{}{} {}", ident(m.getIndent()), m.getLabel(), format(d2));
	}

	public void log() {
		final Duration d = Duration.between(start, Instant.now());
		log.debug("Global time {}", format(d));
	}

	private static String ident(final int indent) {
		final StringBuilder sb = new StringBuilder();
        sb.append("    ".repeat(Math.max(0, indent)));
		return sb.toString();
	}

	private static Object format(final Duration d) {
		return String.format("%02d:%02d:%02d:%03d %d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart(), d.toNanosPart());
	}

	@Getter
	@Setter
	@Builder
	private static class Metric {
		private Instant start;
		private Instant stop;
		private String label;

		private int indent;

		@Override
		public String toString() {
			return label + ": " + Duration.between(start, stop);
		}
	}
}
