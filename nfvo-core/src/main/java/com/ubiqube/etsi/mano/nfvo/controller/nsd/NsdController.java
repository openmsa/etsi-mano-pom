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
package com.ubiqube.etsi.mano.nfvo.controller.nsd;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.repository.ManoResource;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public interface NsdController {

	<U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<NsdPackage, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, Class<?> frontClass);

	void nsDescriptorsNsdInfoIdDelete(@NotNull UUID id);

	NsdPackage nsDescriptorsNsdInfoIdGet(@NotNull UUID id);

	ManoResource nsDescriptorsNsdInfoIdNsdContentGet(@NotNull UUID id);

	void nsDescriptorsNsdInfoIdNsdContentPut(@NotNull UUID id, @NotNull InputStream is);

	NsdPackage nsDescriptorsNsdInfoIdPatch(@NotNull UUID id, String body, @Nullable String ifMatch);

	NsdPackage nsDescriptorsPost(Map<String, String> userDefinedData);

}