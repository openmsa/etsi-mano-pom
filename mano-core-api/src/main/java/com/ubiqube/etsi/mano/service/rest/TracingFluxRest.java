/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.service.rest;

import org.springframework.context.ConfigurableApplicationContext;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;

public class TracingFluxRest extends FluxRest {

	public TracingFluxRest(final Servers server, final ConfigurableApplicationContext configurableApplicationContext) {
		super(server);
		// applyTracing(httpClient, configurableApplicationContext);
		// webBuilder.filters(addTraceExchangeFilterFunctionIfNotPresent(configurableApplicationContext));
	}

//	private static HttpClient applyTracing(final HttpClient httpClient, final ConfigurableApplicationContext springContext) {
//		final HttpClientBeanPostProcessor post = new HttpClientBeanPostProcessor(springContext);
//		return (HttpClient) post.postProcessAfterInitialization(httpClient, "");
//	}
//
//	private static Consumer<List<ExchangeFilterFunction>> addTraceExchangeFilterFunctionIfNotPresent(final ConfigurableApplicationContext configurableApplicationContext) {
//		return functions -> {
//			final boolean noneMatch = noneMatchTraceExchangeFunction(functions);
//			if (noneMatch) {
//				functions.add(TraceExchangeFilterFunction.create(configurableApplicationContext));
//			}
//		};
//	}
//
//	private static boolean noneMatchTraceExchangeFunction(final List<ExchangeFilterFunction> functions) {
//		for (final ExchangeFilterFunction function : functions) {
//			if (function instanceof TraceExchangeFilterFunction) {
//				return false;
//			}
//		}
//		return true;
//	}

}
