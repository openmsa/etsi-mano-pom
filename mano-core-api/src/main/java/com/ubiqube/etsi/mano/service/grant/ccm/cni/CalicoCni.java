package com.ubiqube.etsi.mano.service.grant.ccm.cni;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class CalicoCni implements CniInstaller {

	@Override
	public String getType() {
		return "calico";
	}

	@Override
	public List<String> getK8sDocuments(final String version) {
		final String resourcePath = "/cni/calico/v%S".formatted(version);
		try (final InputStream in = this.getClass().getResourceAsStream(resourcePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			in.transferTo(baos);
			return List.of(baos.toString());
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

}
