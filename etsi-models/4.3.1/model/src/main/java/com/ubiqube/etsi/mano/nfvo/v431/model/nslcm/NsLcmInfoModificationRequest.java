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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents attribute modifications for an \&quot;Individual  NS LCM operation occurrence\&quot; resource, i.e. modifications to a  resource representation based on the \&quot;NsLcmOpOcc\&quot; data structure. NOTE: The \&quot;scheduledTime\&quot; attribute represents the new value of  the \&quot;startTime\&quot; attribute of the \&quot;NsLcmOpOcc\&quot; data structure to  start the NS LCM operation. 
 */
@Schema(description = "This type represents attribute modifications for an \"Individual  NS LCM operation occurrence\" resource, i.e. modifications to a  resource representation based on the \"NsLcmOpOcc\" data structure. NOTE: The \"scheduledTime\" attribute represents the new value of  the \"startTime\" attribute of the \"NsLcmOpOcc\" data structure to  start the NS LCM operation. ")
@Validated


public class NsLcmInfoModificationRequest   {
  @JsonProperty("scheduledTime")
  private OffsetDateTime scheduledTime = null;

  public NsLcmInfoModificationRequest scheduledTime(OffsetDateTime scheduledTime) {
    this.scheduledTime = scheduledTime;
    return this;
  }

  /**
   * Get scheduledTime
   * @return scheduledTime
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public OffsetDateTime getScheduledTime() {
    return scheduledTime;
  }

  public void setScheduledTime(OffsetDateTime scheduledTime) {
    this.scheduledTime = scheduledTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsLcmInfoModificationRequest nsLcmInfoModificationRequest = (NsLcmInfoModificationRequest) o;
    return Objects.equals(this.scheduledTime, nsLcmInfoModificationRequest.scheduledTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scheduledTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsLcmInfoModificationRequest {\n");
    
    sb.append("    scheduledTime: ").append(toIndentedString(scheduledTime)).append("\n");
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
