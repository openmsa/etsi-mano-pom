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
package com.ubiqube.parser.tosca.sol006.ir;

import java.util.List;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class IrArgument implements IrNode {
	/**
	 * An argument composed of multiple concatenated parts.
	 */
	public static final class Concatenation extends IrArgument {
		private final List<Single> parts;

		public Concatenation(final List<Single> parts) {
			this.parts = parts;
		}

		/**
		 * Return the argument parts that need to be concatenated.
		 *
		 * @return Argument parts.
		 */
		public List<? extends Single> parts() {
			return parts;
		}

	}

	public abstract static class Single extends IrArgument {
		private final String string;

		Single(final String string) {
			this.string = string;
		}

		/**
		 * Significant portion of this argument. For unquoted and single-quoted strings
		 * this is the unquoted string literal. For double-quoted strings this is the
		 * unquoted string, after whitespace trimming as defined by RFC6020/RFC7950
		 * section 6.1.3, but before escape substitution.
		 *
		 * @return Significant portion of this argument.
		 */
		public final String string() {
			return string;
		}

		/**
		 * Imprecise check if this argument needs further unescape operation (which is
		 * version-specific) to arrive at the literal string value. This is false for
		 * unquoted and single-quoted strings, which do not support any sort of
		 * escaping. This may be true for double-quoted strings, as they <b>may</b> need
		 * to be further processed in version-dependent ways to arrive at the correct
		 * literal value.
		 *
		 * <p>
		 * This method is allowed to err on the false-positive side -- i.e. it may
		 * report any double-quoted string as needing further processing, even when the
		 * actual content could be determined to not need further processing.
		 *
		 * @return False if the value of {@link #string} can be used as-is.
		 */
		public final boolean needUnescape() {
			return this instanceof DoubleQuoted;
		}

		/**
		 * Imprecise check if this argument needs an additional content check for
		 * compliance. This is false if the string was explicitly quoted and therefore
		 * cannot contain stray single- or double-quotes, or if the content has already
		 * been checked to not contain them.
		 *
		 * <p>
		 * The content check is needed to ascertain RFC7950 compliance, because RFC6020
		 * allows constructs like
		 *
		 * <pre>
		 * abc"def
		 * </pre>
		 *
		 * in unquoted strings, while RFC7950 explicitly forbids them.
		 *
		 * <p>
		 * This method is allowed to err on the false-positive side -- i.e. it may
		 * report any unquoted string as needing this check, even when the actual
		 * content could be determined to not contain quotes.
		 *
		 * @return True if this argument requires a version-specific check for quote
		 *         content.
		 */
		public final boolean needQuoteCheck() {
			return this instanceof Unquoted;
		}

		/**
		 * Imprecise check if this argument complies with the {@code identifier} YANG
		 * specification.
		 *
		 * <p>
		 * This method is allowed to err on the false-negative side -- i.e. it may
		 * report any string as not being compliant with {@code identifier}, even when
		 * the actual content could be determined to be compliant.
		 *
		 * @return True if this argument is known to be directly usable in contexts
		 *         where YANG requires the use of
		 */
		public final boolean isValidIdentifier() {
			return this instanceof Identifier;
		}

		@Override
		public String toString() {
			return string;
		}

	}

	public static final class DoubleQuoted extends Single {
		public DoubleQuoted(final String string) {
			super(string);
		}
	}

	public static final class SingleQuoted extends Single {
		public static final SingleQuoted EMPTY = new SingleQuoted("");

		public SingleQuoted(final String string) {
			super(string);
		}

	}

	public static final class Identifier extends Single {
		public Identifier(final String string) {
			super(string);
		}
	}

	public static final class Unquoted extends Single {
		public Unquoted(final String string) {
			super(string);
		}
	}

}
