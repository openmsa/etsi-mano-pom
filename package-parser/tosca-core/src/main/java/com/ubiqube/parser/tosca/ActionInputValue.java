package com.ubiqube.parser.tosca;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ubiqube.parser.tosca.deserializer.ActionInputDeserializer;
import com.ubiqube.parser.tosca.deserializer.ActivityDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize(using = ActionInputDeserializer.class)
public class ActionInputValue {

	private String value;
	
	public ActionInputValue(String value) {
		this.value = value;
	}
	
	
}
