package com.ubiqube.etsi.mano.service.grant.ccm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class OpenstackCcm implements CcmInstaller {

	@Override
	public String getType() {
		return "openstack";
	}

	private static String getVersionedResource(final String version, final String filename) {
		return "/ccm/openstack/v%s/%s".formatted(version, filename);
	}

	@Override
	public List<String> getK8sDocuments(final String version) {
		final String resourcePath = getVersionedResource(version, "filelist.txt");
		try (final InputStream in = this.getClass().getResourceAsStream(resourcePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			in.transferTo(baos);
			return loadFiles(version, baos.toString());
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private List<String> loadFiles(final String version, final String string) {
		final String[] lines = string.split(System.getProperty("line.separator"));
		return Arrays.stream(lines).map(x -> {
			final String resourcePath = getVersionedResource(version, x);
			return loadDoc(resourcePath);
		}).toList();
	}

	private String loadDoc(final String resourcePath) {
		try (final InputStream in = this.getClass().getResourceAsStream(resourcePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Objects.requireNonNull(in, () -> "Could not load: " + resourcePath);
			in.transferTo(baos);
			return baos.toString();
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

}
