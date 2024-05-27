/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.pkg.vnf;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.input.BoundedInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.ExternalArtifactsAccessConfigArtifact;
import com.ubiqube.etsi.mano.dao.mano.pkg.ParamsBasicCredentials;
import com.ubiqube.etsi.mano.dao.mano.pkg.ParamsOauth2ClientCredentials;
import com.ubiqube.etsi.mano.dao.mano.repo.Repository;
import com.ubiqube.etsi.mano.dao.mano.repo.ToscaRepository;
import com.ubiqube.etsi.mano.dao.mano.vim.Checksum;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.auth.model.ServerConnection;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.utils.MultiHashInputStream;
import com.ubiqube.etsi.mano.service.vim.VimException;

import io.micrometer.context.ContextExecutorService;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class DownloaderService {

	private static final Logger LOG = LoggerFactory.getLogger(DownloaderService.class);

	private final VnfPackageRepository packageRepository;

	public DownloaderService(final VnfPackageRepository packageRepository) {
		this.packageRepository = packageRepository;
	}

	public void doDownload(final List<SoftwareImage> sws, final VnfPackage vnfPackage) {
		final ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		final ExecutorService executor = ContextExecutorService.wrap(tpe);
		final CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
		final List<Future<String>> all = new ArrayList<>();
		sws.forEach(x -> {
			final Future<String> res = completionService.submit(() -> doDownload(x, vnfPackage));
			all.add(res);
		});
		final Throwable ex = waitForCompletion(completionService, all);
		tpe.shutdown();
		try {
			tpe.awaitTermination(5, TimeUnit.MINUTES);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new VimException(e);
		}
		if (null != ex) {
			throw new VimException(ex);
		}
	}

	private static @Nullable Throwable waitForCompletion(final CompletionService<String> completionService, final List<Future<String>> all) {
		int received = 0;
		while (received < all.size()) {
			try {
				final Future<?> resultFuture = completionService.take();
				resultFuture.get();
				received++;
			} catch (final InterruptedException e) {
				LOG.info("", e);
				Thread.currentThread().interrupt();
				return e;
			} catch (final ExecutionException e) {
				return e.getCause();
			}
		}
		return null;
	}

	private String doDownload(final SoftwareImage si, final VnfPackage vnfPackage) {
		final UUID vnfPkgId = vnfPackage.getId();
		final String filename = getFilename(si);
		LOG.info("Downloading: {} {}", filename, si.getImagePath());
		si.setNfvoPath(filename);
		final String imgUrl = Objects.requireNonNull(si.getImagePath(), "Software image url is null, ID: " + si.getId());
		final String path = Constants.REPOSITORY_FOLDER_ARTIFACTS + "/" + si.getNfvoPath();
		final FluxRest cli = getWebClient(si, vnfPackage);
		final Consumer<InputStream> func = is -> {
			try (final PipedOutputStream osPipe = new PipedOutputStream();
					final PipedInputStream isPipeIn = new PipedInputStream(osPipe);
					final BoundedInputStream count = new BoundedInputStream(is);
					MultiHashInputStream mhis = new MultiHashInputStream(count);) {
				packageRepository.storeBinary(vnfPkgId, path, mhis);
				setChecksum(si, mhis);
				setSize(si, count);
			} catch (final IOException e) {
				throw new GenericException(e);
			}
		};
		cli.getWebClient();
		cli.doDownload(imgUrl, func, null);
		si.setImagePath(filename);
		return "OK";
	}

	private static void setSize(final SoftwareImage si, final BoundedInputStream count) {
		if (si.getSize() == null) {
			si.setSize(count.getCount());
		} else if (si.getSize().equals(count.getCount())) {
			throw new GenericException("File size for [" + si.getImagePath() + "] doesn't match the given size: " + si.getSize() + ", but found: " + count.getCount());
		}
	}

	private static void setChecksum(final SoftwareImage si, final MultiHashInputStream mhis) {
		final Checksum chk = new Checksum();
		chk.setAlgorithm("SHA-1");
		chk.setHash(mhis.getSha1());
		chk.setMd5(mhis.getMd5());
		chk.setSha256(mhis.getSha256());
		chk.setSha512(mhis.getSha512());
		si.setChecksum(chk);
	}

	@Nullable
	private static AuthentificationInformations getAuthFromEa(final SoftwareImage si, final VnfPackage vnfPackage) {
		final Optional<ExternalArtifactsAccessConfigArtifact> optCred = vnfPackage.getExternalArtifactsAccessConfig().getArtifact().stream()
				.filter(x -> x.getArtifactUri().equals(si.getImagePath()))
				.findFirst();
		if (optCred.isEmpty()) {
			return null;
		}
		final ExternalArtifactsAccessConfigArtifact cred = optCred.get();
		Optional.ofNullable(cred.getOverrideUri()).ifPresent(si::setImageUri);
		if (cred.getAuthType() == AuthType.BASIC) {
			final ParamsBasicCredentials basic = cred.getParamsBasicCredentials();
			final AuthParamBasic authBasic = AuthParamBasic.builder()
					.userName(basic.getUsername())
					.password(basic.getPassword())
					.build();
			return AuthentificationInformations.builder()
					.authParamBasic(authBasic)
					.authType(List.of(AuthType.BASIC))
					.build();
		}
		if (cred.getAuthType() == AuthType.OAUTH2_CLIENT_CREDENTIALS) {
			final ParamsOauth2ClientCredentials oAuth2 = cred.getParamsOauth2ClientCredentials();
			final AuthParamOauth2 authOauth2 = AuthParamOauth2.builder()
					.clientId(oAuth2.getClientId())
					.clientSecret(oAuth2.getClientPassword())
					.grantType(OAuth2GrantType.CLIENT_CREDENTIAL)
					.tokenEndpoint(oAuth2.getTokenEndpoint())
					.build();
			return AuthentificationInformations.builder()
					.authParamOauth2(authOauth2)
					.authType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS))
					.build();
		}
		throw new GenericException("Unsupported Auth: " + cred.getAuthType());
	}

	private static FluxRest getWebClient(final SoftwareImage si, final VnfPackage vnfPackage) {
		final AuthentificationInformations auth = getCredential(si, vnfPackage);
		final ServerConnection srv = ServerConnection.serverBuilder()
				.url(URI.create(si.getImagePath()))
				.authentification(auth)
				.build();
		return new FluxRest(srv);
	}

	@Nullable
	private static AuthentificationInformations getCredential(final SoftwareImage si, final VnfPackage vnfPackage) {
		final AuthentificationInformations auth = getAuthFromEa(si, vnfPackage);
		if (auth != null) {
			return auth;
		}
		return getAuthFromRepo(si, vnfPackage);
	}

	@Nullable
	private static AuthentificationInformations getAuthFromRepo(final SoftwareImage si, final VnfPackage vnfPackage) {
		if (si.getRepository() == null) {
			return null;
		}
		final Repository repo = vnfPackage.getRepositories().stream()
				.filter(x -> x.getName().equals(si.getRepository()))
				.findFirst()
				.orElseThrow(() -> new GenericException("Unable to find: " + si.getRepository()));
		if (repo instanceof final ToscaRepository tr) {
			if ("http".equals(tr.getProtocol()) && "basic_auth".equals(tr.getTokenType())) {
				final AuthParamBasic authBasic = AuthParamBasic.builder()
						.userName(tr.getUsername())
						.password(tr.getToken())
						.build();
				return AuthentificationInformations.builder()
						.authParamBasic(authBasic)
						.authType(List.of(AuthType.BASIC))
						.build();
			}
			if ("oauth2".equals(tr.getProtocol()) && "bearer".equals(tr.getTokenType())) {
				final AuthParamOauth2 authOauth2 = AuthParamOauth2.builder()
						.clientId(tr.getUsername())
						.clientSecret(tr.getToken())
						.grantType(OAuth2GrantType.CLIENT_CREDENTIAL)
						.tokenEndpoint(URI.create(tr.getUrl()))
						.build();
				return AuthentificationInformations.builder()
						.authParamOauth2(authOauth2)
						.authType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS))
						.build();
			}
			throw new GenericException("Unkown protocol/token combinaison: " + tr.getProtocol() + "/" + tr.getTokenType());
		}
		throw new GenericException("Unkown class Type: " + repo.getClass());
	}

	private static String getFilename(final SoftwareImage si) {
		final URI uri = URI.create(si.getImagePath());
		return FilenameUtils.getName(uri.getPath());
	}
}
