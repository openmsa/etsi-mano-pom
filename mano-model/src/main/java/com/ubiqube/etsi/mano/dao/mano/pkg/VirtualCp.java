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
package com.ubiqube.etsi.mano.dao.mano.pkg;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Entity
@Getter
@Setter
public class VirtualCp extends ConnectionPoint implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AdditionalServiceData> additionalServiceData;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> targetRef;

	private String virtualLinkRef;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return (prime * result) + Objects.hash(additionalServiceData, id, targetRef, virtualLinkRef);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		final VirtualCp other = (VirtualCp) obj;
		return Objects.equals(additionalServiceData, other.additionalServiceData) && Objects.equals(id, other.id) && Objects.equals(targetRef, other.targetRef) && Objects.equals(virtualLinkRef, other.virtualLinkRef);
	}

}
