/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.event.elect;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.config.properties.ManoElectionProperties;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.exception.GenericException;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import io.micrometer.context.ContextExecutorService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Service
public class GroovyElection implements VimElection {

	private static final Logger LOG = LoggerFactory.getLogger(GroovyElection.class);

	private final ManoElectionProperties properties;

	public GroovyElection(final ManoElectionProperties properties) {
		this.properties = properties;
	}

	@Override
	public @Nullable VimConnectionInformation doElection(final List<VimConnectionInformation> inVims, final @Nullable GrantResponse grant, final Set<VnfCompute> vnfcs, final Set<VnfStorage> storages) {
		final ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		final ExecutorService executor = ContextExecutorService.wrap(tpe);
		final CompletionService<VoteResponse> completionService = new ExecutorCompletionService<>(executor);
		final List<Path> scripts = findScript();
		final List<String> list = scripts.stream().map(Path::toString).toList();
		final int numberOfVim = inVims.size();
		inVims.forEach(x -> completionService.submit(() -> executueScripts(list, x)));
		int received = 0;
		final List<VimConnectionInformation> vims = new ArrayList<>();
		while (received < numberOfVim) {
			try {
				final Future<VoteResponse> resultFuture = completionService.take();
				final VoteResponse result = resultFuture.get();
				if (result.getVoteStatus() == VoteStatus.GRANTED) {
					vims.add(result.getVimConnectionInformation());
				}
				received++;
			} catch (final RuntimeException | ExecutionException e) {
				LOG.error("", e);
				executor.shutdownNow();
				return null;
			} catch (final InterruptedException e) {
				LOG.error("", e);
				executor.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
		executor.shutdown();
		if (vims.isEmpty()) {
			return null;
		}
		return vims.get(0);
	}

	List<Path> findScript() {
		final String scriPtath = properties.getScriptPath();
		final Path path = Paths.get(scriPtath);
		if (!path.toFile().exists()) {
			return new ArrayList<>();
		}
		try (Stream<Path> stream = Files.walk(path)) {
			return stream.filter(x -> x.toFile().isFile())
					.filter(x -> x.getFileName().toString().endsWith("elect"))
					.toList();
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private static VoteResponse executueScripts(final List<String> list, final VimConnectionInformation vimConnectionInformation) {
		LOG.info("Running Groovy script against {}", vimConnectionInformation.getVimId());
		final VoteResponse ret = new VoteResponse(VoteStatus.GRANTED, vimConnectionInformation);
		for (final String string : list) {
			final VoteStatus res = executueScript(string, vimConnectionInformation);
			if (res == VoteStatus.DENIED) {
				ret.setVoteStatus(VoteStatus.DENIED);
				return ret;
			}
		}
		return ret;
	}

	static VoteStatus executueScript(final String script, final VimConnectionInformation vimConnectionInformation) {
		final Binding binding = new Binding();
		binding.setVariable("vimConnectionInformation", vimConnectionInformation);
		final GroovyShell shell = new GroovyShell(binding);
		try {
			return (VoteStatus) shell.evaluate(new File(script));
		} catch (CompilationFailedException | IOException e) {
			throw new GenericException(e);
		}
	}

	@Setter
	@Getter
	@AllArgsConstructor
	static class VoteResponse {
		private VoteStatus voteStatus;

		private VimConnectionInformation vimConnectionInformation;
	}
}
