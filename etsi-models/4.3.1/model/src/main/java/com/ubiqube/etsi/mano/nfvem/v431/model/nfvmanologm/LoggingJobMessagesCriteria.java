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
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LoggingJobMessagesCriteriaMatchingPatterns;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents criteria for logging jobs to collect logged messages on NFV-MANO service interfaces. * NOTE: If a matching pattern is present, at least one of the \&quot;srcIpAddress\&quot;, \&quot;dstIpAddress\&quot;, \&quot;requestMethod\&quot;,         \&quot;requestUriPattern\&quot; or \&quot;responseCodes\&quot; shall be provided.
 */
@Schema(description = "This type represents criteria for logging jobs to collect logged messages on NFV-MANO service interfaces. * NOTE: If a matching pattern is present, at least one of the \"srcIpAddress\", \"dstIpAddress\", \"requestMethod\",         \"requestUriPattern\" or \"responseCodes\" shall be provided.")
@Validated


public class LoggingJobMessagesCriteria   {
  /**
   * The direction of the interface messages to match. Permitted values: - IN: input messages into the interface. - OUT: output messages from the interface. - ALL: both input and output messages into/from the interface.
   */
  public enum DirectionEnum {
    IN("IN"),
    
    OUT("OUT"),
    
    ALL("ALL");

    private String value;

    DirectionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DirectionEnum fromValue(String text) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("direction")
  private DirectionEnum direction = null;

  @JsonProperty("matchingPatterns")
  @Valid
  private List<LoggingJobMessagesCriteriaMatchingPatterns> matchingPatterns = null;

  public LoggingJobMessagesCriteria direction(DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  /**
   * The direction of the interface messages to match. Permitted values: - IN: input messages into the interface. - OUT: output messages from the interface. - ALL: both input and output messages into/from the interface.
   * @return direction
   **/
  @Schema(required = true, description = "The direction of the interface messages to match. Permitted values: - IN: input messages into the interface. - OUT: output messages from the interface. - ALL: both input and output messages into/from the interface.")
      @NotNull

    public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  public LoggingJobMessagesCriteria matchingPatterns(List<LoggingJobMessagesCriteriaMatchingPatterns> matchingPatterns) {
    this.matchingPatterns = matchingPatterns;
    return this;
  }

  public LoggingJobMessagesCriteria addMatchingPatternsItem(LoggingJobMessagesCriteriaMatchingPatterns matchingPatternsItem) {
    if (this.matchingPatterns == null) {
      this.matchingPatterns = new ArrayList<>();
    }
    this.matchingPatterns.add(matchingPatternsItem);
    return this;
  }

  /**
   * Patterns to be matched in the interface message. If provided, only messages that match all the values provided in the sub-attributes shall be logged. An API consumer can provide more than one \"matchingPattern\" if combinations of patterns are to be considered to match diverse sets of interface messages. See note.
   * @return matchingPatterns
   **/
  @Schema(description = "Patterns to be matched in the interface message. If provided, only messages that match all the values provided in the sub-attributes shall be logged. An API consumer can provide more than one \"matchingPattern\" if combinations of patterns are to be considered to match diverse sets of interface messages. See note.")
      @Valid
    public List<LoggingJobMessagesCriteriaMatchingPatterns> getMatchingPatterns() {
    return matchingPatterns;
  }

  public void setMatchingPatterns(List<LoggingJobMessagesCriteriaMatchingPatterns> matchingPatterns) {
    this.matchingPatterns = matchingPatterns;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoggingJobMessagesCriteria loggingJobMessagesCriteria = (LoggingJobMessagesCriteria) o;
    return Objects.equals(this.direction, loggingJobMessagesCriteria.direction) &&
        Objects.equals(this.matchingPatterns, loggingJobMessagesCriteria.matchingPatterns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(direction, matchingPatterns);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoggingJobMessagesCriteria {\n");
    
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    matchingPatterns: ").append(toIndentedString(matchingPatterns)).append("\n");
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
