package com.ubiqube.parser.tosca;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ubiqube.parser.tosca.deserializer.ConditionDeserializer;
import com.ubiqube.parser.tosca.deserializer.ConditionValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize(using = ConditionValueDeserializer.class)
public class ConditionValue {
	
	String value;
	
	public ConditionValue(String value) {
		this.value = value;
	}
	
}
