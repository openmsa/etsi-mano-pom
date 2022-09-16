package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

public class ActivityListDefinition extends Root {
	@JsonProperty("activity")
	private Object activity;

	public Object getActivity() {
		return activity;
	}

	public void setActivity(Object activity) {
		this.activity = activity;
	}
   
	
}
