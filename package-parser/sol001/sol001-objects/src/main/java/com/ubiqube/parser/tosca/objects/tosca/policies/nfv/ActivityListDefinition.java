package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

public class ActivityListDefinition extends Root {
	@JsonProperty("activity")
	private String activity;

	public String getActivity() {
		return activity;
	}

	public void setActivity(final String activity) {
		this.activity = activity;
	}

}
