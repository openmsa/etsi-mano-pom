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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The enumeration OperationalStateEnumType defines values representing the operational state of an NFV-MANO functional application type of managed entity. The OperationalStateEnumType shall comply with the provisions:   - STARTED The managed entity is operational.   - STOPPED The managed entity is not operational.   - STOPPING The managed entity is in the transition to stop.   - STARTING The managed entity is in the transition to start and become operational.   - RESTARTING The managed entity is in the transition to stop and start again. 
 */
public enum OperationalStateEnumType {
  STARTED("STARTED"),
    STOPPED("STOPPED"),
    STOPPING("STOPPING"),
    STARTING("STARTING"),
    RESTARTING("RESTARTING");

  private String value;

  OperationalStateEnumType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OperationalStateEnumType fromValue(String text) {
    for (OperationalStateEnumType b : OperationalStateEnumType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
