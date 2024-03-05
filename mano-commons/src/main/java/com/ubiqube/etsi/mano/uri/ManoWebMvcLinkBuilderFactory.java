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
package com.ubiqube.etsi.mano.uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.hateoas.server.core.MethodParameters;
import org.springframework.hateoas.server.core.SpringAffordanceBuilder;
import org.springframework.hateoas.server.core.UriMapping;
import org.springframework.hateoas.server.core.WebHandler;
import org.springframework.hateoas.server.mvc.UriComponentsContributor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletContext;

/**
 *
 * @author olivier
 *
 */
public class ManoWebMvcLinkBuilderFactory {

	private static final ConversionService FALLBACK_CONVERSION_SERVICE = new DefaultFormattingConversionService();
	private final List<UriComponentsContributor> uriComponentsContributors = new ArrayList<>();

	public ManoWebMvcLinkBuilder linkTo(final Object invocationValue) {

		final Function<UriMapping, UriComponentsBuilder> builderFactory = ManoUriComponentsBuilder::forMapping;

		return WebHandler.linkTo(invocationValue, ManoWebMvcLinkBuilder::new, (builder, invocation) -> {

			final String[] primaryParams = SpringAffordanceBuilder.DISCOVERER.getParams(invocation.getMethod());

			if (primaryParams.length > 0) {

				final ParamsRequestCondition paramsRequestCondition = new ParamsRequestCondition(primaryParams);

				for (final NameValueExpression<String> expression : paramsRequestCondition.getExpressions()) {

					if (expression.isNegated()) {
						continue;
					}

					final String value = expression.getValue();

					if (value == null) {
						continue;
					}

					builder.queryParam(expression.getName(), value);
				}
			}

			final MethodParameters parameters = MethodParameters.of(invocation.getMethod());
			final Iterator<Object> parameterValues = Arrays.asList(invocation.getArguments()).iterator();

			for (final MethodParameter parameter : parameters.getParameters()) {

				final Object parameterValue = parameterValues.next();

				for (final UriComponentsContributor contributor : uriComponentsContributors) {
					if (contributor.supportsParameter(parameter)) {
						contributor.enhance(builder, parameter, parameterValue);
					}
				}
			}

			return builder;

		}, builderFactory, getConversionService());
	}

	private static Supplier<ConversionService> getConversionService() {

		return () -> {

			final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

			if (!(attributes instanceof ServletRequestAttributes)) {
				return FALLBACK_CONVERSION_SERVICE;
			}

			final ServletContext servletContext = ((ServletRequestAttributes) attributes).getRequest().getServletContext();
			final WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);

			return (context == null) || !context.containsBean("mvcConversionService")
					? FALLBACK_CONVERSION_SERVICE
					: context.getBean("mvcConversionService", ConversionService.class);
		};
	}

}
