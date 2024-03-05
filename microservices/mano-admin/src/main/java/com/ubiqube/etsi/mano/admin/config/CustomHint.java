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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.admin.config;

import java.lang.reflect.Method;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.web.context.WebServerInitializedEvent;

import com.ubiqube.etsi.mano.admin.AdminException;

import de.codecentric.boot.admin.client.registration.DefaultApplicationFactory;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.domain.values.Tags;
import de.codecentric.boot.admin.server.services.AbstractEventHandler;
import de.codecentric.boot.admin.server.services.StatusUpdateTrigger;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.Cache;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.Palette;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.PollTimer;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.UiTheme;
import de.codecentric.boot.admin.server.ui.config.CssColorUtils;
import de.codecentric.boot.admin.server.ui.web.UiController;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.InstanceResponse;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class CustomHint implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(final @Nonnull RuntimeHints hints, final @Nullable ClassLoader classLoader) {
		hints.resources().registerPattern("META-INF/spring-boot-admin-server-ui/*");
		hints.reflection().registerType(UiController.Settings.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(UiTheme.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Palette.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Cache.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(PollTimer.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(CssColorUtils.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(SnapshottingInstanceRepository.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(AbstractEventHandler.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(StatusUpdateTrigger.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		// hints.reflection().registerType(AdminController.class,
		// MemberCategory.INVOKE_DECLARED_METHODS,
		// MemberCategory.INVOKE_DECLARED_CONSTRUCTORS)
		hints.reflection().registerType(BuildVersion.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Instance.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Registration.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(StatusInfo.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(InstanceId.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(InstanceEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Endpoints.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Endpoint.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Tags.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(InstanceResponse.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Application.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		Method method;
		try {
			method = DefaultApplicationFactory.class.getMethod("onWebServerInitialized", WebServerInitializedEvent.class);
		} catch (final NoSuchMethodException | SecurityException e) {
			throw new AdminException(e);
		}
		hints.reflection().registerMethod(method, ExecutableMode.INVOKE);
		hints.reflection().registerType(TypeReference.of("org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler$SupplierCsrfToken"), MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(TypeReference.of("de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy$Response"), MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(TypeReference.of("de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy$Response$EndpointRef"), MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
	}

}
