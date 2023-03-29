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
module com.ubiqube.etsi.mano.config.docker {
	exports com.ubiqube.etsi.mano.config.docker;

	requires org.slf4j;
	requires jakarta.annotation;
	requires spring.core;
	requires spring.jcl;
	requires spring.cloud.context;
}