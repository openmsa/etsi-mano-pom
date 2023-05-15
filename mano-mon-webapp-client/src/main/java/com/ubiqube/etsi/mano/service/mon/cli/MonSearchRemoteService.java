package com.ubiqube.etsi.mano.service.mon.cli;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

import jakarta.annotation.Nonnull;

/**
 *
 * @author Olivier Vignaud
 *
 */
@HttpExchange(url = "/search", accept = "application/json", contentType = "application/json")
public interface MonSearchRemoteService {

	@GetExchange
	ResponseEntity<Void> search(@Nonnull @RequestParam MultiValueMap<String, String> requestParams);

	@GetExchange("/{objectId}/{key}")
	ResponseEntity<List<MonitoringDataSlim>> findByObjectIdAndKey(@PathVariable("objectId") @Nonnull final String objectId, @PathVariable @Nonnull String key);
}
