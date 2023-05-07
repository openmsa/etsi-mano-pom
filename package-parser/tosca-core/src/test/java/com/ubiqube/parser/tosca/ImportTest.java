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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ubiqube.parser.tosca.scripting.ScriptingValue;
import com.ubiqube.parser.tosca.scripting.SimpleString;
import com.ubiqube.parser.tosca.workflow.ImplementationDefinition;
import com.ubiqube.parser.tosca.workflow.PreConditionDefinition;
import com.ubiqube.parser.tosca.workflow.StepDefinition;
import com.ubiqube.parser.tosca.workflow.WorkflowDefinition;

class ImportTest {

	@Test
	void test() {
		TestBean.testClass(Import.class);
	}

	@Test
	void testArtifact() {
		TestBean.testClass(Artifact.class);
	}

	@Test
	void testToscaBaseEntity() {
		TestBean.testClass(ToscaBaseEntity.class);
	}

	@Test
	void testNodeTemplate() {
		TestBean.testClass(NodeTemplate.class);
	}

	@Test
	void testCallActivityInlineDefinition() {
		TestBean.testClass(CallActivityInlineDefinition.class);
	}

	@Test
	void testToscaClassHolder() {
		TestBean.testClass(ToscaClassHolder.class);
	}

	@Test
	void testCallActivityDelegateDefinition() {
		TestBean.testClass(CallActivityDelegateDefinition.class);
	}

	@Test
	void testCallActivityOperationDefinition() {
		TestBean.testClass(CallActivityOperationDefinition.class);
	}

	@Test
	void testInputBean() {
		TestBean.testClass(InputBean.class);
	}

	@Test
	void testInterfaceDefinition() {
		TestBean.testClass(InterfaceDefinition.class);
	}

	@Test
	void testMetaProperties() {
		TestBean.testClass(MetaProperties.class);
	}

	@Test
	void testCapabilityDefinition() {
		TestBean.testClass(CapabilityDefinition.class);
	}

	@Test
	void testCapabilityMapping() {
		TestBean.testClass(CapabilityMapping.class);
	}

	@Test
	void testTriggerDefinition() {
		TestBean.testClass(TriggerDefinition.class);
	}

	@Test
	void testRepositoryDefinition() {
		TestBean.testClass(RepositoryDefinition.class);
		TestBean.testClass(RepositoryDefinition.Credential.class);
	}

	@Test
	void testActionDefinition() {
		TestBean.testClass(ActionDefinition.class);
	}

	@ParameterizedTest
	@ValueSource(classes = {
			EntrySchema.class,
			GroupType.class,
			Requirement.class,
			DataType.class,
			InterfaceMapping.class,
			NotificationDefnition.class,
			NotificationType.class,
			OperationType.class,
			PolicyType.class,
			InterfaceType.class,
			SubstitutionMapping.class,
			OperationDefinition.class,
			ActionInputValue.class,
			OperationImplementationDefinition.class,
			RelationshipType.class,
			AttributeMapping.class,
			CapabilityTypes.class,
			CondictionClause.class,
			PolicyDefinition.class,
			EventFilter.class,
			RequirementMapping.class,
			RequirementDefinition.class,
			TimeInterval.class,
			NodeFilter.class,
			GroupDefinition.class,
			StepDefinition.class,
			WorkflowDefinition.class,
			PreConditionDefinition.class,
			ImplementationDefinition.class,
			ScriptingValue.class,
			SimpleString.class
	})
	void testEntrySchema(final Class<?> cls) {
		TestBean.testClass(cls);
	}
}
