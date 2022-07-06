package com.ubiqube.etsi.mano.vnfm.v361.model.vnfind;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnfind.NotificationLink;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Links for this resource. 
 */
@Schema(description = "Links for this resource. ")
@Validated


public class SupportedIndicatorsChangeNotificationLinks   {
  @JsonProperty("vnfInstance")
  private NotificationLink vnfInstance = null;

  @JsonProperty("subscription")
  private NotificationLink subscription = null;

  public SupportedIndicatorsChangeNotificationLinks vnfInstance(NotificationLink vnfInstance) {
    this.vnfInstance = vnfInstance;
    return this;
  }

  /**
   * Get vnfInstance
   * @return vnfInstance
   **/
  @Schema(description = "")
  
    @Valid
    public NotificationLink getVnfInstance() {
    return vnfInstance;
  }

  public void setVnfInstance(NotificationLink vnfInstance) {
    this.vnfInstance = vnfInstance;
  }

  public SupportedIndicatorsChangeNotificationLinks subscription(NotificationLink subscription) {
    this.subscription = subscription;
    return this;
  }

  /**
   * Get subscription
   * @return subscription
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public NotificationLink getSubscription() {
    return subscription;
  }

  public void setSubscription(NotificationLink subscription) {
    this.subscription = subscription;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SupportedIndicatorsChangeNotificationLinks supportedIndicatorsChangeNotificationLinks = (SupportedIndicatorsChangeNotificationLinks) o;
    return Objects.equals(this.vnfInstance, supportedIndicatorsChangeNotificationLinks.vnfInstance) &&
        Objects.equals(this.subscription, supportedIndicatorsChangeNotificationLinks.subscription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfInstance, subscription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SupportedIndicatorsChangeNotificationLinks {\n");
    
    sb.append("    vnfInstance: ").append(toIndentedString(vnfInstance)).append("\n");
    sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
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