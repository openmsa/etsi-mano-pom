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
package com.ubiqube.etsi.mano.repository;

import java.io.InputStream;
import java.util.UUID;

import jakarta.annotation.Nullable;

public interface BinaryRepository {

	void storeObject(UUID id, String filename, Object object);

	void storeBinary(UUID id, String filename, InputStream stream);

	ManoResource getBinary(UUID id, String filename);

	ManoResource getBinary(UUID id, String filename, int min, @Nullable Long max);

	void delete(UUID id, String filename);

	void delete(UUID id);
}
