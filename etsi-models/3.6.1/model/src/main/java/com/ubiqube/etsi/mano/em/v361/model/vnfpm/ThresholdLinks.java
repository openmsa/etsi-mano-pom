package com.ubiqube.etsi.mano.em.v361.model.vnfpm;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.Link;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links for this resource.
 */
@Schema(description = "Links for this resource. ")
@Validated

public class ThresholdLinks {
	@JsonProperty("self")
	private Link self = null;

	@JsonProperty("object")
	private Link object = null;

	public ThresholdLinks self(final Link self) {
		this.self = self;
		return this;
	}

	/**
	 * Get self
	 *
	 * @return self
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getSelf() {
		return self;
	}

	public void setSelf(final Link self) {
		this.self = self;
	}

	public ThresholdLinks object(final Link object) {
		this.object = object;
		return this;
	}

	/**
	 * Get object
	 *
	 * @return object
	 **/
	@Schema(description = "")

	@Valid
	public Link getObject() {
		return object;
	}

	public void setObject(final Link object) {
		this.object = object;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final ThresholdLinks thresholdLinks = (ThresholdLinks) o;
		return Objects.equals(this.self, thresholdLinks.self) &&
				Objects.equals(this.object, thresholdLinks.object);
	}

	@Override
	public int hashCode() {
		return Objects.hash(self, object);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ThresholdLinks {\n");

		sb.append("    self: ").append(toIndentedString(self)).append("\n");
		sb.append("    object: ").append(toIndentedString(object)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
