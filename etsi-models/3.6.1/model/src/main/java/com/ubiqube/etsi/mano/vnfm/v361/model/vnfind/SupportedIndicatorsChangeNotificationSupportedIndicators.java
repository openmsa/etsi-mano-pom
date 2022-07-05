package com.ubiqube.etsi.mano.vnfm.v361.model.vnfind;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SupportedIndicatorsChangeNotificationSupportedIndicators
 */
@Validated


public class SupportedIndicatorsChangeNotificationSupportedIndicators   {
  @JsonProperty("vnfIndicatorId")
  private String vnfIndicatorId = null;

  @JsonProperty("name")
  private String name = null;

  public SupportedIndicatorsChangeNotificationSupportedIndicators vnfIndicatorId(String vnfIndicatorId) {
    this.vnfIndicatorId = vnfIndicatorId;
    return this;
  }

  /**
   * Get vnfIndicatorId
   * @return vnfIndicatorId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getVnfIndicatorId() {
    return vnfIndicatorId;
  }

  public void setVnfIndicatorId(String vnfIndicatorId) {
    this.vnfIndicatorId = vnfIndicatorId;
  }

  public SupportedIndicatorsChangeNotificationSupportedIndicators name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Human readable name of the VNF indicator. Shall be present if defined in the VNFD. See note. 
   * @return name
   **/
  @Schema(description = "Human readable name of the VNF indicator. Shall be present if defined in the VNFD. See note. ")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SupportedIndicatorsChangeNotificationSupportedIndicators supportedIndicatorsChangeNotificationSupportedIndicators = (SupportedIndicatorsChangeNotificationSupportedIndicators) o;
    return Objects.equals(this.vnfIndicatorId, supportedIndicatorsChangeNotificationSupportedIndicators.vnfIndicatorId) &&
        Objects.equals(this.name, supportedIndicatorsChangeNotificationSupportedIndicators.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfIndicatorId, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SupportedIndicatorsChangeNotificationSupportedIndicators {\n");
    
    sb.append("    vnfIndicatorId: ").append(toIndentedString(vnfIndicatorId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
