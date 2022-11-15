package com.ubiqube.etsi.mano.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;

import lombok.Builder;
import lombok.Data;

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
		for (int i = 0; i < indent; i++) {
			sb.append("    ");
		}
		return sb.toString();
	}

	private static Object format(final Duration d) {
		return String.format("%02d:%02d:%02d:%03d %d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart(), d.toNanosPart());
	}

	@Data
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
