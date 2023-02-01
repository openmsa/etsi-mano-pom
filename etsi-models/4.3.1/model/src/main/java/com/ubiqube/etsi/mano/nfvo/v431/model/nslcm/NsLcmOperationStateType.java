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
 * Value | Description ------|------------ PROCESSING | The NS LCM operation is currently in execution. COMPLETED | The NS LCM operation has been completed successfully. PARTIALLY_COMPLETED | The NS LCM operation has been partially completed with accepTable errors. FAILED_TEMP | The NS LCM operation has failed and execution has stopped, but the execution of the operation is not considered to be closed. FAILED | The NS LCM operation has failed and it cannot be retried or rolled back, as it is determined that such action will not succeed. OLLING_BACK | The NS LCM operation is currently being rolled back. ROLLED_BACK | The NS LCM operation has been successfully rolled back, i.e. The state of the NS prior to the original operation invocation has been restored as closely as possible. 
 */
public enum NsLcmOperationStateType {
  PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED"),
    PARTIALLY_COMPLETED("PARTIALLY_COMPLETED"),
    FAILED_TEMP("FAILED_TEMP"),
    FAILED("FAILED"),
    ROLLING_BACK("ROLLING_BACK"),
    ROLLED_BACK("ROLLED_BACK");

  private String value;

  NsLcmOperationStateType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static NsLcmOperationStateType fromValue(String text) {
    for (NsLcmOperationStateType b : NsLcmOperationStateType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
