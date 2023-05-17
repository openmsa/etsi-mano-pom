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
package com.ubiqube.etsi.mano.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Configuration
@SuppressWarnings("static-method")
public class SwaggerDocumentationConfig {

	@Bean
	OpenAPI openApiMain() {
		final OpenAPI openApi = new OpenAPI();
		openApi.info(new Info().title("Ubiqube mano-alarm API")
				.description("Ubiqube mano alarming API")
				.contact(createContact())
				.license(new License().name("Ubiqube copyright notice").url("https://github.com/openmsa/etsi-mano-java/raw/master/LICENSE"))
				.version("1.0.0-impl:ubiqube.org:NFV_OpenAPI:1"));
		return openApi;
	}

	private Contact createContact() {
		final Contact contact = new Contact();
		contact.email("mano-dev@ubiqube.com").name("mano-dev").url("http://ubiqube.com/");
		return contact;
	}

	@Bean
	GroupedOpenApi customImplementationMain() {
		return GroupedOpenApi.builder()
				.group("main")
				.packagesToScan("com.ubiqube.etsi.mano")
				.build();
	}

}
