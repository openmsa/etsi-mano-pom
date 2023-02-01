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
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Specifies the condition under which the producer will report to the consumer about the compiled log data.
 */
@Schema(description = "Specifies the condition under which the producer will report to the consumer about the compiled log data.")
@Validated


public class LoggingJobConfigReportingCondition   {
  /**
   * Specifies the type of reporting condition. Permitted values: - REPORTING_ON_COMPILATION: the producer shall notify the consumer once the compilation of the                 collected logging data into a file is completed and a new log report is available. - NO_REPORTING: no reporting is requested (the consumer can query the logging jobs to know about the                 availability of new log reports).
   */
  public enum ReportingTypeEnum {
    REPORTING_ON_COMPILATION("REPORTING_ON_COMPILATION"),
    
    NO_REPORTING("NO_REPORTING");

    private String value;

    ReportingTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ReportingTypeEnum fromValue(String text) {
      for (ReportingTypeEnum b : ReportingTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("reportingType")
  private ReportingTypeEnum reportingType = null;

  @JsonProperty("minimumReportingPeriod")
  private Integer minimumReportingPeriod = null;

  public LoggingJobConfigReportingCondition reportingType(ReportingTypeEnum reportingType) {
    this.reportingType = reportingType;
    return this;
  }

  /**
   * Specifies the type of reporting condition. Permitted values: - REPORTING_ON_COMPILATION: the producer shall notify the consumer once the compilation of the                 collected logging data into a file is completed and a new log report is available. - NO_REPORTING: no reporting is requested (the consumer can query the logging jobs to know about the                 availability of new log reports).
   * @return reportingType
   **/
  @Schema(required = true, description = "Specifies the type of reporting condition. Permitted values: - REPORTING_ON_COMPILATION: the producer shall notify the consumer once the compilation of the                 collected logging data into a file is completed and a new log report is available. - NO_REPORTING: no reporting is requested (the consumer can query the logging jobs to know about the                 availability of new log reports).")
      @NotNull

    public ReportingTypeEnum getReportingType() {
    return reportingType;
  }

  public void setReportingType(ReportingTypeEnum reportingType) {
    this.reportingType = reportingType;
  }

  public LoggingJobConfigReportingCondition minimumReportingPeriod(Integer minimumReportingPeriod) {
    this.minimumReportingPeriod = minimumReportingPeriod;
    return this;
  }

  /**
   * Specifies the minimum periodicity at which the producer will report to the consumer about the collected log information, in seconds. See note 1.
   * @return minimumReportingPeriod
   **/
  @Schema(description = "Specifies the minimum periodicity at which the producer will report to the consumer about the collected log information, in seconds. See note 1.")
  
    public Integer getMinimumReportingPeriod() {
    return minimumReportingPeriod;
  }

  public void setMinimumReportingPeriod(Integer minimumReportingPeriod) {
    this.minimumReportingPeriod = minimumReportingPeriod;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoggingJobConfigReportingCondition loggingJobConfigReportingCondition = (LoggingJobConfigReportingCondition) o;
    return Objects.equals(this.reportingType, loggingJobConfigReportingCondition.reportingType) &&
        Objects.equals(this.minimumReportingPeriod, loggingJobConfigReportingCondition.minimumReportingPeriod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportingType, minimumReportingPeriod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoggingJobConfigReportingCondition {\n");
    
    sb.append("    reportingType: ").append(toIndentedString(reportingType)).append("\n");
    sb.append("    minimumReportingPeriod: ").append(toIndentedString(minimumReportingPeriod)).append("\n");
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
