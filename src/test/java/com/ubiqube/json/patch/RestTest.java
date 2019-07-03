package com.ubiqube.json.patch;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ubiqube.api.commons.id.DeviceId;

public class RestTest {

	public void testName() throws Exception {

		// RestTemplateBuilder
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic bmNyb290OnViaXF1YmU=");
		final HttpEntity<String> request = new HttpEntity<>(httpHeaders);
		final DeviceId quote = restTemplate.getForObject("http://ncroot:ubiqube@localhost:8380/ubi-api-rest/device/v1/id/1234",
				DeviceId.class);
		final ResponseEntity<DeviceId> response = restTemplate.exchange("http://warm64:8380/ubi-api-rest/device/v1/id/1234",
				HttpMethod.GET, request, DeviceId.class);
		final DeviceId deviceId = response.getBody();
		System.out.println(deviceId);
	}
}
