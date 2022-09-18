package com.ubiqube.parser.tosca;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallActivity {

	private String workflow;

	private String operation;

	private Map<String, ActionInputValue> inputs;

}
