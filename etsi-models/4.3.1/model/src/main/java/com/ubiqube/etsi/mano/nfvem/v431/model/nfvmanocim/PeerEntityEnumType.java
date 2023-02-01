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
 * The enumeration PeerEntityEnumType defines the permitted values to represent peer functional entities. it shall complains with the provisions : - NFVO The peer functional entity is an NFVO. - VNFM The peer functional entity is a VNFM. - VIM   The peer functional entity is a VIM. - WIM   The peer functional entity is a WIM. - CISM   The peer functional entity is a CISM.  - EM   The peer functional entity is an EM. - OSS   The peer functional entity is an OSS/BSS. 
 */
public enum PeerEntityEnumType {
  NFVO("NFVO"),
    VNFM("VNFM"),
    VIM("VIM"),
    WIM("WIM"),
    CISM("CISM"),
    EM("EM"),
    OSS("OSS");

  private String value;

  PeerEntityEnumType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static PeerEntityEnumType fromValue(String text) {
    for (PeerEntityEnumType b : PeerEntityEnumType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
