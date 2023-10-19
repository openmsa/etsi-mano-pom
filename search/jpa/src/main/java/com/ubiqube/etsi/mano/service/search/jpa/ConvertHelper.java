package com.ubiqube.etsi.mano.service.search.jpa;

import java.util.UUID;

import com.ubiqube.etsi.mano.grammar.GrammarException;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.service.search.SearchException;

public class ConvertHelper {

	@SuppressWarnings("unchecked")
	public static Comparable convertComparable(final GrammarValue gv, final Class<?> clazz) {
		if (clazz.isEnum()) {
			try {
				return Enum.valueOf((Class<? extends Enum>) clazz, gv.getAsString());
			} catch (final IllegalArgumentException e) {
				throw new GrammarException("Unable to find correct value for " + clazz.getSimpleName() + " field and value: " + gv.getAsString());
			}
		}
		if (clazz.isAssignableFrom(String.class)) {
			return gv.getAsString();
		}
		if (clazz.isAssignableFrom(Long.class)) {
			return Long.valueOf(gv.getAsString());
		}
		if (clazz.isAssignableFrom(Integer.class)) {
			return Integer.valueOf(gv.getAsString());
		}
		if (clazz.isAssignableFrom(UUID.class)) {
			return UUID.fromString(gv.getAsString());
		}
		throw new SearchException("Unknown class: " + clazz.getCanonicalName());
	}
}
