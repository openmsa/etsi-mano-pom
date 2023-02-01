/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.v431.model.nslcm;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The enumeration FeasibilityCheckReserveType defines the permitted values to represent the feasibility check and reserve options applicable for the NS LCM operations. It shall comply with the provisions  defined in table 6.5.4.14-1. 
 */
public enum FeasibilityCheckReserveType {
  NO_FEASIBILITY_CHECK("NO_FEASIBILITY_CHECK"),
    FEASIBILITY_CHECK_ONLY("FEASIBILITY_CHECK_ONLY"),
    FEASIBILITY_CHECK_WITH_OPERATION("FEASIBILITY_CHECK_WITH_OPERATION"),
    FEASIBILITY_CHECK_WITH_RESERVATION_AND_OPERATION("FEASIBILITY_CHECK_WITH_RESERVATION_AND_OPERATION");

  private String value;

  FeasibilityCheckReserveType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static FeasibilityCheckReserveType fromValue(String text) {
    for (FeasibilityCheckReserveType b : FeasibilityCheckReserveType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
