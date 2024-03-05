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

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.server.core.DummyInvocationUtils;
import org.springframework.hateoas.server.core.SpringAffordanceBuilder;
import org.springframework.hateoas.server.core.TemplateVariableAwareLinkBuilderSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author olivier
 *
 */
public class ManoWebMvcLinkBuilder extends TemplateVariableAwareLinkBuilderSupport<ManoWebMvcLinkBuilder> {
	private static final String CONTROLLER_MUST_NOT_BE_NULL = "Controller must not be null!";
	private static final String METHOD_MUST_NOT_BE_NULL = "Method must not be null!";
	private static final String PARAMETERS_MUST_NOT_BE_NULL = "Parameters must not be null!";
	private static final ManoWebMvcLinkBuilderFactory FACTORY = new ManoWebMvcLinkBuilderFactory();
	private static final DefaultUriBuilderFactory URI_FACTORY = new DefaultUriBuilderFactory();
	private static final Pattern SOL_REGEXP = Pattern.compile("^\\/sol00[0-9]\\/");
	private final UriComponents components;

	/**
	 * Creates a new {@link WebMvcLinkBuilder} using the given
	 * {@link UriComponentsBuilder}.
	 *
	 * @param builder must not be {@literal null}.
	 */
	ManoWebMvcLinkBuilder(final UriComponents components) {
		this(components, TemplateVariables.NONE, Collections.emptyList());
	}

	protected ManoWebMvcLinkBuilder(final UriComponents components, final TemplateVariables variables, final List<Affordance> affordances) {
		super(components, variables, affordances);
		this.components = components;
	}

	@Override
	protected ManoWebMvcLinkBuilder createNewInstance(final UriComponents uriComponents, final List<Affordance> affordances, final TemplateVariables variables) {
		return new ManoWebMvcLinkBuilder(uriComponents, variables, affordances);
	}

	@Override
	protected ManoWebMvcLinkBuilder getThis() {
		return this;
	}

	/**
	 * Wrapper for {@link DummyInvocationUtils#methodOn(Class, Object...)} to be
	 * available in case you work with static imports of
	 * {@link ManoWebMvcLinkBuilder}.
	 *
	 * @param controller must not be {@literal null}.
	 * @param parameters parameters to extend template variables in the type level
	 *                   mapping.
	 * @return The object.
	 */
	public static <T> T methodOn(final Class<T> controller, final Object... parameters) {
		return DummyInvocationUtils.methodOn(controller, parameters);
	}

	/**
	 * Creates a new {@link ManoWebMvcLinkBuilder} with a base of the mapping
	 * annotated to the given controller class.
	 *
	 * @param controller the class to discover the annotation on, must not be
	 *                   {@literal null}.
	 * @return Mano Mvc Link.
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Class<?> controller) {
		return linkTo(controller, new Object[0]);
	}

	/**
	 * Creates a new {@link ManoWebMvcLinkBuilder} with a base of the mapping
	 * annotated to the given controller class. The additional parameters are used
	 * to fill up potentially available path variables in the class scop request
	 * mapping.
	 *
	 * @param controller the class to discover the annotation on, must not be
	 *                   {@literal null}.
	 * @param parameters additional parameters to bind to the URI template declared
	 *                   in the annotation, must not be {@literal null}.
	 * @return Mano MVC Link.
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Class<?> controller, final Object... parameters) {

		Assert.notNull(controller, CONTROLLER_MUST_NOT_BE_NULL);
		Assert.notNull(parameters, PARAMETERS_MUST_NOT_BE_NULL);

		Assert.notNull(controller, CONTROLLER_MUST_NOT_BE_NULL);
		Assert.notNull(parameters, PARAMETERS_MUST_NOT_BE_NULL);

		final var mapping = SpringAffordanceBuilder.DISCOVERER.getMapping(controller);
		final var defaulted = mapping == null ? "/" : mapping;

		final var uri = URI_FACTORY.expand(defaulted, parameters);
		final var uriComponents = UriComponentsBuilder.fromUri(uri).build();

		// return new
		// ManoWebMvcLinkBuilder(UriComponentsBuilderFactory.getComponents()).slash(uriComponents,
		// true);

		return new ManoWebMvcLinkBuilder(ManoUriComponentsBuilder.getComponents()).slash(uriComponents, true);
	}

	/**
	 * Creates a new {@link ManoWebMvcLinkBuilder} with a base of the mapping
	 * annotated to the given controller class. Parameter map is used to fill up
	 * potentially available path variables in the class scope request mapping.
	 *
	 * @param controller the class to discover the annotation on, must not be
	 *                   {@literal null}.
	 * @param parameters additional parameters to bind to the URI template declared
	 *                   in the annotation, must not be {@literal null}.
	 * @return Mano MVC link.
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Class<?> controller, final Map<String, ?> parameters) {

		Assert.notNull(controller, CONTROLLER_MUST_NOT_BE_NULL);
		Assert.notNull(parameters, PARAMETERS_MUST_NOT_BE_NULL);

		final var mapping = SpringAffordanceBuilder.DISCOVERER.getMapping(controller);
		final var defaulted = mapping == null ? "/" : mapping;

		final var uri = URI_FACTORY.expand(defaulted, parameters);
		final var uriComponents = UriComponentsBuilder.fromUri(uri).build();

		return new ManoWebMvcLinkBuilder(ManoUriComponentsBuilder.getComponents()).slash(uriComponents, true);
	}

	/*
	 * @see org.springframework.hateoas.MethodLinkBuilderFactory#linkTo(Method)
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Method method) {

		Assert.notNull(method, METHOD_MUST_NOT_BE_NULL);

		return linkTo(method.getDeclaringClass(), method, new Object[method.getParameterTypes().length]);
	}

	/*
	 * @see org.springframework.hateoas.MethodLinkBuilderFactory#linkTo(Method,
	 * Object...)
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Method method, final Object... parameters) {

		Assert.notNull(method, METHOD_MUST_NOT_BE_NULL);
		Assert.notNull(parameters, PARAMETERS_MUST_NOT_BE_NULL);

		return linkTo(method.getDeclaringClass(), method, parameters);
	}

	/*
	 * @see org.springframework.hateoas.MethodLinkBuilderFactory#linkTo(Class<?>,
	 * Method)
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Class<?> controller, final Method method) {

		Assert.notNull(controller, "Controller type must not be null!");
		Assert.notNull(method, METHOD_MUST_NOT_BE_NULL);

		return linkTo(controller, method, new Object[method.getParameterTypes().length]);
	}

	/*
	 * @see org.springframework.hateoas.MethodLinkBuilderFactory#linkTo(Class<?>,
	 * Method, Object...)
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Class<?> controller, final Method method, final Object... parameters) {

		Assert.notNull(controller, "Controller type must not be null!");
		Assert.notNull(method, METHOD_MUST_NOT_BE_NULL);
		Assert.notNull(parameters, PARAMETERS_MUST_NOT_BE_NULL);

		final int expected = method.getParameterTypes().length;
		final int given = parameters.length;

		Assert.isTrue(expected == given,
				() -> String.format("Incorrect number of parameter values given. Expected %s, got %s!", expected, given));

		return linkTo(DummyInvocationUtils.getLastInvocationAware(controller, method, parameters));
	}

	/**
	 * Creates a {@link ManoWebMvcLinkBuilder} pointing to a controller method. Hand
	 * in a dummy method invocation result you can create via
	 * {@link #methodOn(Class, Object...)} or
	 * {@link DummyInvocationUtils#methodOn(Class, Object...)}.
	 *
	 * <pre>
	 * &#64;RequestMapping("/customers")
	 * class CustomerController {
	 *
	 *   &#64;RequestMapping("/{id}/addresses")
	 *   HttpEntity&lt;Addresses&gt; showAddresses(@PathVariable Long id) { … }
	 * }
	 *
	 * Link link = linkTo(methodOn(CustomerController.class).showAddresses(2L)).withRel("addresses");
	 * </pre>
	 *
	 * The resulting {@link Link} instance will point to
	 * {@code /customers/2/addresses} and have a rel of {@code addresses}. For more
	 * details on the method invocation constraints, see
	 * {@link DummyInvocationUtils#methodOn(Class, Object...)}.
	 *
	 * @param invocationValue
	 * @return Mano MVC Link.
	 */
	public static ManoWebMvcLinkBuilder linkTo(final Object invocationValue) {
		return FACTORY.linkTo(invocationValue);
	}

	@Override
	public String toString() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			final String tmp = components.toString();
			return SOL_REGEXP.matcher(tmp).replaceAll("/");
		}
		return super.toString();
	}
}
