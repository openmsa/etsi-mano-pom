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
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.Link;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * LoggingJobLogReports
 */
@Validated


public class LoggingJobLogReports   {
  @JsonProperty("logReportId")
  private String logReportId = null;

  @JsonProperty("logReportLoc")
  private Link logReportLoc = null;

  public LoggingJobLogReports logReportId(String logReportId) {
    this.logReportId = logReportId;
    return this;
  }

  /**
   * An identifier with the intention of being globally unique. 
   * @return logReportId
   **/
  @Schema(required = true, description = "An identifier with the intention of being globally unique. ")
      @NotNull

    public String getLogReportId() {
    return logReportId;
  }

  public void setLogReportId(String logReportId) {
    this.logReportId = logReportId;
  }

  public LoggingJobLogReports logReportLoc(Link logReportLoc) {
    this.logReportLoc = logReportLoc;
    return this;
  }

  /**
   * Get logReportLoc
   * @return logReportLoc
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Link getLogReportLoc() {
    return logReportLoc;
  }

  public void setLogReportLoc(Link logReportLoc) {
    this.logReportLoc = logReportLoc;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoggingJobLogReports loggingJobLogReports = (LoggingJobLogReports) o;
    return Objects.equals(this.logReportId, loggingJobLogReports.logReportId) &&
        Objects.equals(this.logReportLoc, loggingJobLogReports.logReportLoc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logReportId, logReportLoc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoggingJobLogReports {\n");
    
    sb.append("    logReportId: ").append(toIndentedString(logReportId)).append("\n");
    sb.append("    logReportLoc: ").append(toIndentedString(logReportLoc)).append("\n");
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
