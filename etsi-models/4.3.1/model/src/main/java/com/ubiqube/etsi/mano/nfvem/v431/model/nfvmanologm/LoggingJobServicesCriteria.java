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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents criteria for logging jobs to collect logged messages about processes pertaining to NFV-MANO services. * NOTE: In the present version of the present document, only one attribute, i.e.         \&quot;logGarbageCollection\&quot;, is available.
 */
@Schema(description = "This type represents criteria for logging jobs to collect logged messages about processes pertaining to NFV-MANO services. * NOTE: In the present version of the present document, only one attribute, i.e.         \"logGarbageCollection\", is available.")
@Validated


public class LoggingJobServicesCriteria   {
  @JsonProperty("logGarbageCollection")
  private Boolean logGarbageCollection = null;

  public LoggingJobServicesCriteria logGarbageCollection(Boolean logGarbageCollection) {
    this.logGarbageCollection = logGarbageCollection;
    return this;
  }

  /**
   * Indicates to collect logged information about garbage collection processes associated to NFV-MANO services. See note.
   * @return logGarbageCollection
   **/
  @Schema(description = "Indicates to collect logged information about garbage collection processes associated to NFV-MANO services. See note.")
  
    public Boolean isLogGarbageCollection() {
    return logGarbageCollection;
  }

  public void setLogGarbageCollection(Boolean logGarbageCollection) {
    this.logGarbageCollection = logGarbageCollection;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoggingJobServicesCriteria loggingJobServicesCriteria = (LoggingJobServicesCriteria) o;
    return Objects.equals(this.logGarbageCollection, loggingJobServicesCriteria.logGarbageCollection);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logGarbageCollection);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoggingJobServicesCriteria {\n");
    
    sb.append("    logGarbageCollection: ").append(toIndentedString(logGarbageCollection)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
