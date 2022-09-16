package com.ubiqube.parser.tosca;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallActivity  {
	
	String workflow;
	
	String operation;
	
	private Map<String, ActionInputValue> inputs;

}
