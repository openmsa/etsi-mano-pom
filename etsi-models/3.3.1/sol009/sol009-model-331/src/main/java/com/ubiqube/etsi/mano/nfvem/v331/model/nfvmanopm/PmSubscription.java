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
package com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm.PmNotificationsFilter;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm.PmSubscriptionCallbackUri;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents a subscription.  
 */
@Schema(description = "This type represents a subscription.  ")
@Validated


public class PmSubscription   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("filter")
  private PmNotificationsFilter filter = null;

  @JsonProperty("callbackUri")
  private PmSubscriptionCallbackUri callbackUri = null;

  public PmSubscription id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PmSubscription filter(PmNotificationsFilter filter) {
    this.filter = filter;
    return this;
  }

  /**
   * Get filter
   * @return filter
   **/
  @Schema(description = "")
  
    @Valid
    public PmNotificationsFilter getFilter() {
    return filter;
  }

  public void setFilter(PmNotificationsFilter filter) {
    this.filter = filter;
  }

  public PmSubscription callbackUri(PmSubscriptionCallbackUri callbackUri) {
    this.callbackUri = callbackUri;
    return this;
  }

  /**
   * Get callbackUri
   * @return callbackUri
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PmSubscriptionCallbackUri getCallbackUri() {
    return callbackUri;
  }

  public void setCallbackUri(PmSubscriptionCallbackUri callbackUri) {
    this.callbackUri = callbackUri;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PmSubscription pmSubscription = (PmSubscription) o;
    return Objects.equals(this.id, pmSubscription.id) &&
        Objects.equals(this.filter, pmSubscription.filter) &&
        Objects.equals(this.callbackUri, pmSubscription.callbackUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, filter, callbackUri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PmSubscription {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
    sb.append("    callbackUri: ").append(toIndentedString(callbackUri)).append("\n");
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
