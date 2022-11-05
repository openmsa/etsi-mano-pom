package com.ubiqube.etsi.mano.notification.impl;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String args[]) {
		String condition = "[{\"utilization_vnf_indicator\":[{\"greater_or_equal\":60.0}]},{\"call_proc_scale_level\":[{\"less_than\":3}]}]";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj;
		try {
			actualObj = mapper.readTree(condition);
			for (JsonNode jsonNode : actualObj) {
				Map<String, Object> conditions = mapper.convertValue(jsonNode,
						new TypeReference<Map<String, Object>>() {
						});
				for(Map.Entry<String, Object> c : conditions.entrySet()) {
					String indicatorName = c.getKey();
					List<Map<String, Object>> conList = (List<Map<String, Object>>) c.getValue();
					for(Map<String, Object> m : conList) {
						for(Map.Entry<String, Object> r : m.entrySet()) {
							String conditionOperator = r.getKey();
							String conditionValue = r.getValue().toString();
						}
					}
				}
				System.out.println();
			}
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
