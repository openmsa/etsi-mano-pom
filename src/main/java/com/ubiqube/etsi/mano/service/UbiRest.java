package com.ubiqube.etsi.mano.service;

import java.net.URI;
import java.util.Base64;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UbiRest {

	private static final Logger LOG = LoggerFactory.getLogger(UbiRest.class);

	private final String url;

	private final RestTemplate restTemplate;

	public UbiRest(final Configuration conf) {
		restTemplate = new RestTemplate();
		url = conf.get("remote.http.url");
		LOG.info("MSA REST client against {}", url);
	}

	public <T> T get(final URI uri, final Class<T> clazz) {
		return call(uri, HttpMethod.GET, clazz);
	}

	public <T> T post(final URI uri, final Class<T> clazz) {
		return call(uri, HttpMethod.POST, clazz);
	}

	public <T> T post(final URI uri, final Object body, final Class<T> clazz) {
		return call(uri, HttpMethod.POST, body, clazz);
	}

	public <T> T delete(final URI uri, final Class<T> clazz) {
		return call(uri, HttpMethod.DELETE, clazz);
	}

	public <T> T call(final URI uri, final HttpMethod method, final Class<T> clazz) {
		final HttpEntity<String> request = new HttpEntity<>(getHttpHeaders());
		return _call(uri, method, request, clazz);
	}

	public <T> T call(final URI uri, final HttpMethod method, final Object body, final Class<T> clazz) {
		final HttpEntity<Object> request = new HttpEntity<>(body, getHttpHeaders());
		return _call(uri, method, request, clazz);
	}

	public UriComponentsBuilder uriBuilder() {
		return UriComponentsBuilder.fromHttpUrl(url);
	}

	private <T, Tbody> T _call(final URI uri, final HttpMethod method, final HttpEntity<Tbody> request, final Class<T> clazz) {
		final ResponseEntity<T> resp = restTemplate.exchange(uri, method, request, clazz);
		return resp.getBody();
	}

	private static HttpHeaders getHttpHeaders() {
		final HttpHeaders httpHeaders = new HttpHeaders();

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user;
		if (null == authentication) {
			user = new User("ncroot", "ubiqube", Collections.EMPTY_LIST);
		} else {
			user = (UserDetails) authentication.getPrincipal();
		}
		final String basic = user.getUsername() + ":" + user.getPassword();
		httpHeaders.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(basic.getBytes()));
		return httpHeaders;
	}
}
