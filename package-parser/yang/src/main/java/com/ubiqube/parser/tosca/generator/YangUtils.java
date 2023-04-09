/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.parser.tosca.generator;

import java.util.List;
import java.util.function.Supplier;

import com.ubiqube.parser.tosca.sol006.ir.IrArgument;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Concatenation;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Single;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword.Unqualified;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;
import com.ubiqube.parser.tosca.sol006.statement.Statement;

/**
 *
 * @author Olivier Vignaud
 *
 */
public class YangUtils {

	private YangUtils() {
		//
	}

	public static String getRevisionDateString(final IrStatement importStatement) {
		String revisionDateStr = null;
		for (final IrStatement substatement : importStatement.getStatements()) {
			if (isBuiltin(substatement, "revision-date")) {
				revisionDateStr = argumentToString(substatement.getArgument());
			}
		}
		return revisionDateStr;
	}

	public static String argumentToString(final IrArgument arg) {
		if (arg instanceof final Single s) {
			// Need to unescape.
			return s.string();
		}
		final Concatenation c = (Concatenation) arg;
		return concatStrings(c.parts());
	}

	public static String concatStrings(final List<? extends Single> parts) {
		final StringBuilder sb = new StringBuilder();
		for (final Single part : parts) {
			final String str = part.string();
			sb.append(str);
		}
		return sb.toString();
	}

	public static boolean isBuiltin(final IrStatement stmt, final String localName) {
		final IrKeyword keyword = stmt.getKeyword();
		return (keyword instanceof Unqualified) && localName.equals(keyword.identifier());
	}

	public static <U extends Statement> void genericHandle(final IrStatement x, final Supplier<U> supp, final List<U> lst) {
		final U n = supp.get();
		n.load(x);
		lst.add(n);
	}

	public static void handleListable(final IrArgument arg, final List<String> lst) {
		lst.add(argumentToString(arg));
	}

	public static <U extends Statement> U genericHandleSingle(final IrStatement x, final Supplier<U> supp) {
		final U n = supp.get();
		n.load(x);
		return n;
	}

}
