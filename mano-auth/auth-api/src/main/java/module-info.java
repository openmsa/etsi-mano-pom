/**
 * Copyright (C) 2019-2023 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 */
module com.ubiqube.etsi.mano.auth {
	exports com.ubiqube.etsi.mano.auth;
	exports com.ubiqube.etsi.mano.auth.config;

	//
	requires transitive com.ubiqube.etsi.mano.config.properties;
	//
	requires lombok;
	requires org.slf4j;
	requires io.swagger.v3.oas.models;
	requires jakarta.annotation;
	// Spring
	requires transitive spring.context;
	requires spring.core;
	requires spring.web;
	requires spring.boot.autoconfigure;
	requires transitive spring.security.core;
	requires transitive spring.security.config;
	requires spring.security.web;
	// Jakarta
	requires transitive jakarta.servlet;
	//
	requires transitive com.fasterxml.jackson.databind;

}