/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.parser.tosca.generator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public final class ClassUtils {
	private final static Pattern PACKAGE_PATTERN = Pattern.compile("(?<package>.*)(?=\\.)\\.(?<clazz>.*)");

	private ClassUtils() {
		// Nothing.
	}

	public static String getClassName(final String key) {
		if (key.lastIndexOf('.') == -1) {
			return key;
		}
		final int pi = key.lastIndexOf('.');
		return key.substring(pi + 1);
	}

	public static String getPackage(final String key) {
		if (key.lastIndexOf('.') == -1) {
			return null;
		}
		final int pi = key.lastIndexOf('.');
		final String p = key.substring(0, pi).toLowerCase();
		return p.replaceFirst("\\.abstract", ".Abstract");
	}

	public static String toscaToJava(final String derivedFrom) {
		final Matcher m = PACKAGE_PATTERN.matcher(derivedFrom);
		if (m.matches()) {
			return m.group("package").toLowerCase(Locale.ROOT) + "." + m.group("clazz");
		}
		return derivedFrom;
	}

}
