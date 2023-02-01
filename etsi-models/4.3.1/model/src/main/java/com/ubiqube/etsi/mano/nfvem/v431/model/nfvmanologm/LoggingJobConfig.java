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
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LoggingJobConfigReportingCondition;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LoggingJobConfigSecurityConf;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents configuration data for a logging job. It shall comply with the provisions defined in table 8.6.3.6-1. NOTE: The present document version does not specify the support for \&quot;log compilation and reporting based on events\&quot; as specified in clause 6.6.2.2 of ETSI GS NFV-IFA 031. * NOTE 1: The minimumReportingPeriod is used to throttle possible flooding of reports by providing a lower             limit on the gap between two log availability notification to be emitted by the same logging job.   NOTE 2: More than one logCompilingCondition is possible to provide a combination of different conditions             for the compilation. This covers use cases when the compilation of the log can be based on, e.g.             a timer value and a size value, whichever first condition is met first. For instance, compile a             log every 3 600 seconds, or whenever during the 3 600 seconds period the log grows in             size to 1 000 000 bytes.   NOTE 3: The value is indicative, as the actual size of the compiled log files might not match the provided             compileBySizeValue due to possible deviations in the tracking of the size of the log data by the             API producer, and the size of individual logged data entries when these are collected.
 */
@Schema(description = "This type represents configuration data for a logging job. It shall comply with the provisions defined in table 8.6.3.6-1. NOTE: The present document version does not specify the support for \"log compilation and reporting based on events\" as specified in clause 6.6.2.2 of ETSI GS NFV-IFA 031. * NOTE 1: The minimumReportingPeriod is used to throttle possible flooding of reports by providing a lower             limit on the gap between two log availability notification to be emitted by the same logging job.   NOTE 2: More than one logCompilingCondition is possible to provide a combination of different conditions             for the compilation. This covers use cases when the compilation of the log can be based on, e.g.             a timer value and a size value, whichever first condition is met first. For instance, compile a             log every 3 600 seconds, or whenever during the 3 600 seconds period the log grows in             size to 1 000 000 bytes.   NOTE 3: The value is indicative, as the actual size of the compiled log files might not match the provided             compileBySizeValue due to possible deviations in the tracking of the size of the log data by the             API producer, and the size of individual logged data entries when these are collected.")
@Validated


public class LoggingJobConfig   {
  @JsonProperty("startTime")
  private OffsetDateTime startTime = null;

  @JsonProperty("endTime")
  private OffsetDateTime endTime = null;

  @JsonProperty("reportingCondition")
  private LoggingJobConfigReportingCondition reportingCondition = null;

  @JsonProperty("compileBySizeValue")
  private Integer compileBySizeValue = null;

  @JsonProperty("compileByTimerValue")
  private Integer compileByTimerValue = null;

  @JsonProperty("securityConf")
  private LoggingJobConfigSecurityConf securityConf = null;

  public LoggingJobConfig startTime(OffsetDateTime startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Date-time stamp. Representation: String formatted according to IETF RFC 3339. 
   * @return startTime
   **/
  @Schema(description = "Date-time stamp. Representation: String formatted according to IETF RFC 3339. ")
  
    @Valid
    public OffsetDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  public LoggingJobConfig endTime(OffsetDateTime endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Date-time stamp. Representation: String formatted according to IETF RFC 3339. 
   * @return endTime
   **/
  @Schema(description = "Date-time stamp. Representation: String formatted according to IETF RFC 3339. ")
  
    @Valid
    public OffsetDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(OffsetDateTime endTime) {
    this.endTime = endTime;
  }

  public LoggingJobConfig reportingCondition(LoggingJobConfigReportingCondition reportingCondition) {
    this.reportingCondition = reportingCondition;
    return this;
  }

  /**
   * Get reportingCondition
   * @return reportingCondition
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LoggingJobConfigReportingCondition getReportingCondition() {
    return reportingCondition;
  }

  public void setReportingCondition(LoggingJobConfigReportingCondition reportingCondition) {
    this.reportingCondition = reportingCondition;
  }

  public LoggingJobConfig compileBySizeValue(Integer compileBySizeValue) {
    this.compileBySizeValue = compileBySizeValue;
    return this;
  }

  /**
   * An indicative size threshold for compiling the collected log data, in bytes. It is used when the compilation is based on the size of the collected log data. If not present, a default value as specified with the \"defaultLogCompileBySizeValue\" configuration in the \"ManoEntityConfigurableParams\" shall be used. See note 2 and note 3.
   * @return compileBySizeValue
   **/
  @Schema(description = "An indicative size threshold for compiling the collected log data, in bytes. It is used when the compilation is based on the size of the collected log data. If not present, a default value as specified with the \"defaultLogCompileBySizeValue\" configuration in the \"ManoEntityConfigurableParams\" shall be used. See note 2 and note 3.")
  
    public Integer getCompileBySizeValue() {
    return compileBySizeValue;
  }

  public void setCompileBySizeValue(Integer compileBySizeValue) {
    this.compileBySizeValue = compileBySizeValue;
  }

  public LoggingJobConfig compileByTimerValue(Integer compileByTimerValue) {
    this.compileByTimerValue = compileByTimerValue;
    return this;
  }

  /**
   * The periodicity threshold for compiling the filtered log, in seconds. It is used when the compilation is based on a timer (e.g., every 24 hours). If not present, a default value as specified with the \"defaultLogCompileByTimerValue\" configuration in the \"ManoEntityConfigurableParams\" shall be used. See note 2.
   * @return compileByTimerValue
   **/
  @Schema(description = "The periodicity threshold for compiling the filtered log, in seconds. It is used when the compilation is based on a timer (e.g., every 24 hours). If not present, a default value as specified with the \"defaultLogCompileByTimerValue\" configuration in the \"ManoEntityConfigurableParams\" shall be used. See note 2.")
  
    public Integer getCompileByTimerValue() {
    return compileByTimerValue;
  }

  public void setCompileByTimerValue(Integer compileByTimerValue) {
    this.compileByTimerValue = compileByTimerValue;
  }

  public LoggingJobConfig securityConf(LoggingJobConfigSecurityConf securityConf) {
    this.securityConf = securityConf;
    return this;
  }

  /**
   * Get securityConf
   * @return securityConf
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LoggingJobConfigSecurityConf getSecurityConf() {
    return securityConf;
  }

  public void setSecurityConf(LoggingJobConfigSecurityConf securityConf) {
    this.securityConf = securityConf;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoggingJobConfig loggingJobConfig = (LoggingJobConfig) o;
    return Objects.equals(this.startTime, loggingJobConfig.startTime) &&
        Objects.equals(this.endTime, loggingJobConfig.endTime) &&
        Objects.equals(this.reportingCondition, loggingJobConfig.reportingCondition) &&
        Objects.equals(this.compileBySizeValue, loggingJobConfig.compileBySizeValue) &&
        Objects.equals(this.compileByTimerValue, loggingJobConfig.compileByTimerValue) &&
        Objects.equals(this.securityConf, loggingJobConfig.securityConf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, endTime, reportingCondition, compileBySizeValue, compileByTimerValue, securityConf);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoggingJobConfig {\n");
    
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    reportingCondition: ").append(toIndentedString(reportingCondition)).append("\n");
    sb.append("    compileBySizeValue: ").append(toIndentedString(compileBySizeValue)).append("\n");
    sb.append("    compileByTimerValue: ").append(toIndentedString(compileByTimerValue)).append("\n");
    sb.append("    securityConf: ").append(toIndentedString(securityConf)).append("\n");
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
