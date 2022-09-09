package com.ubiqube.parser.tosca;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.ubiqube.parser.tosca.deserializer.ConditionDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize(using = ConditionDeserializer.class)
public class ConditionListDefintion {
	
	private Map<String, List<Object>> conditionAttributes;

	public ConditionListDefintion(Map<String, List<Object>> conditionAttributes) {
		this.conditionAttributes = conditionAttributes;
	}

}
