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
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import java.util.function.Function;

import org.apache.commons.io.input.BoundedInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.ubiqube.etsi.mano.dao.mano.vim.Checksum;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.DownloadResult;
import com.ubiqube.etsi.mano.service.rest.ExceptionHandler;
import com.ubiqube.etsi.mano.service.utils.MultiHashInputStream;
import com.ubiqube.etsi.mano.service.vim.VimException;

import io.micrometer.context.ContextExecutorService;
import jakarta.annotation.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.netty.http.client.HttpClient;

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

	public void doDownload(final List<SoftwareImage> sws, final UUID vnfPkgId) {
		final ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		final ExecutorService executor = ContextExecutorService.wrap(tpe);
		final CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
		final List<Future<String>> all = new ArrayList<>();
		sws.forEach(x -> {
			final Future<String> res = completionService.submit(() -> doDownload(x, vnfPkgId));
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

	public DownloadResult doDownload(final UUID vnfPkgId, final String url, final String target) {
		final ExceptionHandler eh = new ExceptionHandler();
		final WebClient webclient = createWebClient();
		try (final PipedOutputStream osPipe = new PipedOutputStream();
				final PipedInputStream isPipeIn = new PipedInputStream(osPipe);
				final BoundedInputStream count = new BoundedInputStream(isPipeIn);
				MultiHashInputStream mhis = new MultiHashInputStream(count);) {

			final Flux<DataBuffer> wc = webclient
					.get()
					.uri(url)
					.accept(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL)
					.retrieve()
					.onRawStatus(i -> i != 200, exepctionFunction(osPipe))
					.bodyToFlux(DataBuffer.class);

			DataBufferUtils.write(wc, osPipe)
					.doFinally(onComplete(osPipe))
					.onErrorResume(Throwable.class, e -> {
						eh.setE(e);
						eh.setMessage(e.getMessage());
						return Mono.error(e);
					})
					.subscribe(DataBufferUtils.releaseConsumer());
			if (eh.getMessage() != null) {
				throw new GenericException(eh.getE());
			}
			packageRepository.storeBinary(vnfPkgId, target, mhis);
			return new DownloadResult(mhis.getMd5(), mhis.getSha256(), mhis.getSha512(), count.getCount());
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private String doDownload(final SoftwareImage si, final UUID vnfPkgId) {
		LOG.info("Downloading: {}", si.getImagePath());
		si.setNfvoPath(UUID.randomUUID().toString());
		final String imgUrl = Objects.requireNonNull(si.getImagePath(), "Software image url is null, ID: " + si.getId());
		final DownloadResult hash = doDownload(vnfPkgId, imgUrl, si.getNfvoPath());
		final Checksum chk = si.getChecksum();
		chk.setMd5(hash.md5String());
		chk.setSha256(hash.sha256String());
		chk.setSha512(hash.sha512String());
		if (si.getSize() == null) {
			si.setSize(hash.count());
		} else if (si.getSize().equals(hash.count())) {
			throw new GenericException("File size for [" + si.getImagePath() + "] doesn't match the given size: " + si.getSize() + ", but found: " + hash.count());
		}
		return "OK";
	}

	private static Consumer<SignalType> onComplete(final PipedOutputStream osPipe) {
		return s -> closePipe(osPipe);
	}

	private static WebClient createWebClient() {
		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
				.build();
	}

	private static Function<ClientResponse, Mono<? extends Throwable>> exepctionFunction(final PipedOutputStream osPipe) {
		return response -> {
			closePipe(osPipe);
			throw new GenericException("An error occured." + response.statusCode());
		};
	}

	private static void closePipe(final OutputStream osPipe) {
		try (osPipe) {
			//
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}
}
