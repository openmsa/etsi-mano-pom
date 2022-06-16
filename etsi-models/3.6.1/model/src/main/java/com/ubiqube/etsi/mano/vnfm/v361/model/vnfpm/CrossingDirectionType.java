package com.ubiqube.etsi.mano.vnfm.v361.model.vnfpm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets CrossingDirectionType
 */
public enum CrossingDirectionType {
  UP("UP"),
    DOWN("DOWN");

  private String value;

  CrossingDirectionType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static CrossingDirectionType fromValue(String text) {
    for (CrossingDirectionType b : CrossingDirectionType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
