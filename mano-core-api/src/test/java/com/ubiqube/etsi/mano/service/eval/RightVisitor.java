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
package com.ubiqube.etsi.mano.service.eval;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.SimpleNodeReturn;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;

import jakarta.annotation.Nullable;

public class RightVisitor extends SimpleNodeReturn<Void> {

	@Override
	public Node visit(@Nullable final GenericCondition gc, @Nullable final Void arg) {
		Objects.requireNonNull(gc);
		assertNotNull(gc.getLeft());
		gc.getLeft().accept(this, arg);
		assertNotNull(gc.getRight());
		gc.getRight().accept(this, arg);
		return super.visit(gc, arg);
	}

}
