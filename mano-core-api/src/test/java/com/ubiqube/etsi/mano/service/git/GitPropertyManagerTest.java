package com.ubiqube.etsi.mano.service.git;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GitPropertyManagerTest {
	@Mock
	private GitVersionExtractor gitVersionExtractor;

	private GitPropertyManager createService() {
		return new GitPropertyManager(gitVersionExtractor);
	}

	@Test
	void testGetAllProperties() {
		final GitPropertyManager srv = createService();
		final List<GitVersion> res = srv.getAllProperties();
		assertNotNull(res);
	}

	@Test
	void testGetLocalProperties() {
		final GitPropertyManager srv = createService();
		final GitVersion res = srv.getLocalPRoperty();
		assertNull(res);
	}
}
