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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.juju.cli;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;
import com.ubiqube.etsi.mano.service.juju.entities.JujuMetadata;

import jakarta.validation.constraints.NotNull;

@HttpExchange(url = "/juju", accept = "application/json", contentType = "application/json")
public interface JujuRemoteService {

	@GetExchange("/cloud")
	ResponseEntity<String> clouds();

	@PostExchange("/cloud")
	ResponseEntity<String> addCloud(@RequestBody @NotNull final JujuCloud cloud);

	@DeleteExchange("/cloud/{cloudname}")
	ResponseEntity<String> removeCloud(@PathVariable("cloudname") @NotNull final String cloudname);

	@GetExchange("/credential")
	ResponseEntity<String> credentials();

	@PostExchange("/credential")
	ResponseEntity<String> addCredential(@RequestBody @NotNull final JujuCloud cloud);

	@PutExchange("/credential")
	ResponseEntity<String> updateCredential(@RequestBody @NotNull final JujuCloud cloud);

	@DeleteExchange("/credential/{cloudname}/{name}")
	ResponseEntity<String> removeCredential(@PathVariable("cloudname") @NotNull final String cloudname, @PathVariable("name") final String name);

//	@PostExchange("/metadata")
//	public ResponseEntity<String> genMetadata(@RequestParam("path") @NotNull final String path,
//			@RequestParam("imageId") @NotNull final String imageId, @RequestParam("osSeries") @NotNull final String osSeries,
//			@RequestParam("region") @NotNull final String region, @RequestParam("osAuthUrl") @NotNull final String osAuthUrl);

	@PostExchange("/metadata")
	ResponseEntity<String> genMetadata(@RequestBody @NotNull final JujuMetadata meta);

//	@PostExchange("/controller")
//	public ResponseEntity<String> addController(@RequestParam("imageId") @NotNull final String imageId,
//			@RequestParam("osSeries") @NotNull final String osSeries, @RequestParam("constraints") @NotNull final String constraints,
//			@RequestParam("cloudname") @NotNull final String cloudname, @RequestParam("controllername") @NotNull final String controllername,
//			@RequestParam("region") @NotNull final String region);

	@PostExchange("/controller/{cloudname}")
	ResponseEntity<String> addController(@PathVariable("cloudname") @NotNull final String cloudname, @RequestBody @NotNull final JujuMetadata controller);

	@GetExchange("/controller")
	ResponseEntity<String> controllers();

	@DeleteExchange("/controller/{controllername}")
	ResponseEntity<String> removeController(@PathVariable("controllername") @NotNull final String controllername);

	@PostExchange("/model/{name}")
	ResponseEntity<String> addModel(@PathVariable("name") @NotNull final String name);

	@GetExchange("/model")
	ResponseEntity<String> model();

	@DeleteExchange("/model/{name}")
	ResponseEntity<String> removeModel(@PathVariable("name") @NotNull final String name);

	@PostExchange("/application/{charm}/{name}")
	ResponseEntity<String> deployApp(@PathVariable("charm") @NotNull final String charm, @PathVariable("name") @NotNull final String name);

	@GetExchange("/application/{name}")
	ResponseEntity<String> application(@PathVariable("name") @NotNull final String name);

	@DeleteExchange("/application/{name}")
	ResponseEntity<String> removeApplication(@PathVariable("name") @NotNull final String name);

	@GetExchange("/status")
	ResponseEntity<String> status();

}
