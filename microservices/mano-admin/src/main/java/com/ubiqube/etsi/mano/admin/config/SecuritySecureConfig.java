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

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ubiqube.etsi.mano.admin.AdminException;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@SuppressWarnings("static-method")
public class SecuritySecureConfig {
	private final AdminServerProperties adminServer;

	public SecuritySecureConfig(final AdminServerProperties adminServer) {
		this.adminServer = adminServer;
	}

	@Bean
	SecurityFilterChain configure(final HttpSecurity http) {
		final SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

		try {
			http.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));
			http.csrf(CsrfConfigurer::disable);
			http.authorizeHttpRequests(
					authorizeRequests -> authorizeRequests.requestMatchers(this.adminServer.path("/assets/**")).permitAll()
							.requestMatchers(this.adminServer.path("/actuator/**")).permitAll()
							.requestMatchers(this.adminServer.path("/login")).permitAll().anyRequest().authenticated())
					.formLogin(
							formLogin -> formLogin.loginPage(this.adminServer.path("/login")).successHandler(successHandler).and())
					.logout(logout -> logout.logoutUrl(this.adminServer.path("/logout"))).httpBasic(Customizer.withDefaults())
					.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
							.ignoringRequestMatchers(
									new AntPathRequestMatcher(this.adminServer.path("/instances"),
											HttpMethod.POST.toString()),
									new AntPathRequestMatcher(this.adminServer.path("/instances/*"),
											HttpMethod.DELETE.toString()),
									new AntPathRequestMatcher(this.adminServer.path("/actuator/**"))))
					.rememberMe(rememberMe -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1_209_600));
			return http.build();
		} catch (final Exception e) {
			throw new AdminException(e);
		}
	}

	@Bean
	UserDetailsService users() {
		final UserDetails admin = User.builder()
				.username("admin")
				.password("{noop}admin")
				.roles("USER", "ADMIN")
				.build();
		return new InMemoryUserDetailsManager(admin);
	}

}
