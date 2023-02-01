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
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LoggingJobConfigSecurityConfLogFileEncryption;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LoggingJobConfigSecurityConfLogTransferSecurity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Configuration about the security aspects of the logging job.
 */
@Schema(description = "Configuration about the security aspects of the logging job.")
@Validated


public class LoggingJobConfigSecurityConf   {
  @JsonProperty("logFileEncryption")
  private LoggingJobConfigSecurityConfLogFileEncryption logFileEncryption = null;

  @JsonProperty("logTransferSecurity")
  private LoggingJobConfigSecurityConfLogTransferSecurity logTransferSecurity = null;

  public LoggingJobConfigSecurityConf logFileEncryption(LoggingJobConfigSecurityConfLogFileEncryption logFileEncryption) {
    this.logFileEncryption = logFileEncryption;
    return this;
  }

  /**
   * Get logFileEncryption
   * @return logFileEncryption
   **/
  @Schema(description = "")
  
    @Valid
    public LoggingJobConfigSecurityConfLogFileEncryption getLogFileEncryption() {
    return logFileEncryption;
  }

  public void setLogFileEncryption(LoggingJobConfigSecurityConfLogFileEncryption logFileEncryption) {
    this.logFileEncryption = logFileEncryption;
  }

  public LoggingJobConfigSecurityConf logTransferSecurity(LoggingJobConfigSecurityConfLogTransferSecurity logTransferSecurity) {
    this.logTransferSecurity = logTransferSecurity;
    return this;
  }

  /**
   * Get logTransferSecurity
   * @return logTransferSecurity
   **/
  @Schema(description = "")
  
    @Valid
    public LoggingJobConfigSecurityConfLogTransferSecurity getLogTransferSecurity() {
    return logTransferSecurity;
  }

  public void setLogTransferSecurity(LoggingJobConfigSecurityConfLogTransferSecurity logTransferSecurity) {
    this.logTransferSecurity = logTransferSecurity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoggingJobConfigSecurityConf loggingJobConfigSecurityConf = (LoggingJobConfigSecurityConf) o;
    return Objects.equals(this.logFileEncryption, loggingJobConfigSecurityConf.logFileEncryption) &&
        Objects.equals(this.logTransferSecurity, loggingJobConfigSecurityConf.logTransferSecurity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logFileEncryption, logTransferSecurity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoggingJobConfigSecurityConf {\n");
    
    sb.append("    logFileEncryption: ").append(toIndentedString(logFileEncryption)).append("\n");
    sb.append("    logTransferSecurity: ").append(toIndentedString(logTransferSecurity)).append("\n");
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
