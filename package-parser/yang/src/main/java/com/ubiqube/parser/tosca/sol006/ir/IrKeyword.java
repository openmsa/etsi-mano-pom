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

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public abstract class IrKeyword implements IrNode {
	public static final class Qualified extends IrKeyword {
		private final String prefix;

		public Qualified(final String prefix, final String localName) {
			super(localName);
			this.prefix = prefix;
		}

		@Override
		public String prefix() {
			return prefix;
		}

		@Override
		public String asStringDeclaration() {
			return prefix + ':' + identifier();
		}

		@Override
		public String toString() {
			return "Qualified [prefix=" + prefix + ", identifier()=" + identifier() + "]";
		}

	}

	public static final class Unqualified extends IrKeyword {
		public Unqualified(final String localName) {
			super(localName);
		}

		@Override
		public String prefix() {
			return null;
		}

		@Override
		public String asStringDeclaration() {
			return identifier();
		}

		@Override
		public String toString() {
			return "Unqualified [prefix()=" + prefix() + ", identifier()=" + identifier() + "]";
		}

	}

	private final String identifier;

	IrKeyword(final String localName) {
		this.identifier = localName;
	}

	/**
	 * This keyword's 'identifier' part. This corresponds to what the RFCs refer to
	 * as {@code YANG keyword} or as {@code language extension keyword}.
	 *
	 * <p>
	 * Note the returned string is guaranteed to conform to rules of
	 * {@code identifier} ABNF and therefore is directly usable as a
	 * {@code localName} in an {@link AbstractQName}.
	 *
	 * @return This keyword's identifier part.
	 */
	public final String identifier() {
		return identifier;
	}

	/**
	 * This keyword's 'prefix' part. This corresponds to {@code prefix identifier}.
	 * For {@code YANG keyword}s this is null. For language extension references
	 * this is the non-null prefix which references the YANG module defining the
	 * language extension.
	 *
	 * <p>
	 * Note the returned string, if non-null, is guaranteed to conform to rules of
	 * {@code identifier} ABNF and therefore is directly usable as a
	 * {@code localName} in an {@link AbstractQName}.
	 *
	 * @return This keyword's prefix, or null if this keyword references a YANG
	 *         keyword.
	 */
	public abstract String prefix();

	/**
	 * Helper method to re-create the string which was used to declared this
	 * keyword.
	 *
	 * @return Declaration string.
	 */
	public abstract String asStringDeclaration();

}
