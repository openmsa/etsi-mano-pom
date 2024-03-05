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
module com.ubiqube.etsi.mano.search.hsearch {
	exports com.ubiqube.etsi.mano.config;
	exports com.ubiqube.etsi.mano.service.search.hibernate;

	requires com.ubiqube.etsi.mano.grammar;
	requires com.ubiqube.etsi.mano.search.api;
	requires org.slf4j;
	requires spring.context;
	requires jakarta.persistence;
	requires jakarta.transaction;
	requires org.hibernate.search.mapper.orm;
}