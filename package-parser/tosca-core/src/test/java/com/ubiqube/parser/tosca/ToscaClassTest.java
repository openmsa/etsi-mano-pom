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
package com.ubiqube.parser.tosca;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ToscaClassTest {

	@Test
	void test() {
		final ToscaClass t = new ToscaClass();
		t.setAttributes(Map.of());
		t.setMimeType("mime");
		t.setFileExt(List.of());
		final RequirementDefinition req = new RequirementDefinition();
		t.setRequirements(req);
		t.setInterfaces(Map.of());
		t.setCapabilities(Map.of());
		t.setArtifacts(Map.of());
		t.setValidTargetTypes(List.of());
		assertNotNull(t.toString());
		t.getArtifacts();
		t.getAttributes();
		t.getCapabilities();
		t.getDerivedFrom();
		t.getDescription();
		t.getFileExt();
		t.getInterfaces();
		t.getMetadata();
		t.getMimeType();
		t.getProperties();
		t.getRequirements();
		t.getValidTargetTypes();
		t.getVersion();
	}

}
