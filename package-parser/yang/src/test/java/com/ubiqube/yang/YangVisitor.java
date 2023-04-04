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
package com.ubiqube.yang;

import com.ubiqube.etsi.mano.yang.YangStatementParser.ArgumentContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.FileContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.KeywordContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.StatementContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.UnquotedStringContext;
import com.ubiqube.etsi.mano.yang.YangStatementParserBaseVisitor;

public class YangVisitor extends YangStatementParserBaseVisitor<Object> {

	@Override
	public Object visitFile(final FileContext ctx) {
		System.out.println("visitFile: " + ctx.getChildCount());
		return super.visitFile(ctx);
	}

	@Override
	public Object visitStatement(final StatementContext ctx) {
		System.out.println("statement: ");
		return super.visitStatement(ctx);
	}

	@Override
	public Object visitKeyword(final KeywordContext ctx) {
		System.out.println("keyword: " + ctx.getText());
		return super.visitKeyword(ctx);
	}

	@Override
	public Object visitArgument(final ArgumentContext ctx) {
		System.out.println("argument: " + ctx.getText() + " c= " + ctx.getChildCount());
		return super.visitArgument(ctx);
	}

	@Override
	public Object visitUnquotedString(final UnquotedStringContext ctx) {
		System.out.println("unquoted");
		return super.visitUnquotedString(ctx);
	}

}
