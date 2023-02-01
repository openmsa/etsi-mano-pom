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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Information about the supported mechanisms, algorithms, and protocols for
 * verifying the signature of software images.
 */
@Schema(description = "Information about the supported mechanisms, algorithms, and protocols for verifying the signature of software images. ")
@Validated

public class VimSpecificInfoImageSignatureVerification {
	@JsonProperty("additionalVerificationCapabilities")
	private Map<String, String> additionalVerificationCapabilities = null;

	@JsonProperty("signatureVerificationAlgorithms")
	@Valid
	private List<VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms> signatureVerificationAlgorithms = new ArrayList<>();

	public VimSpecificInfoImageSignatureVerification additionalVerificationCapabilities(final Map<String, String> additionalVerificationCapabilities) {
		this.additionalVerificationCapabilities = additionalVerificationCapabilities;
		return this;
	}

	/**
	 * Get additionalVerificationCapabilities
	 *
	 * @return additionalVerificationCapabilities
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getAdditionalVerificationCapabilities() {
		return additionalVerificationCapabilities;
	}

	public void setAdditionalVerificationCapabilities(final Map<String, String> additionalVerificationCapabilities) {
		this.additionalVerificationCapabilities = additionalVerificationCapabilities;
	}

	public VimSpecificInfoImageSignatureVerification signatureVerificationAlgorithms(final List<VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms> signatureVerificationAlgorithms) {
		this.signatureVerificationAlgorithms = signatureVerificationAlgorithms;
		return this;
	}

	public VimSpecificInfoImageSignatureVerification addSignatureVerificationAlgorithmsItem(final VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms signatureVerificationAlgorithmsItem) {
		this.signatureVerificationAlgorithms.add(signatureVerificationAlgorithmsItem);
		return this;
	}

	/**
	 * List of algorithms for verifying the signature of software images that are
	 * supported by the VIM.
	 *
	 * @return signatureVerificationAlgorithms
	 **/
	@Schema(required = true, description = "List of algorithms for verifying the signature of software images that are supported by the VIM. ")
	@NotNull
	@Valid
	public List<VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms> getSignatureVerificationAlgorithms() {
		return signatureVerificationAlgorithms;
	}

	public void setSignatureVerificationAlgorithms(final List<VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms> signatureVerificationAlgorithms) {
		this.signatureVerificationAlgorithms = signatureVerificationAlgorithms;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VimSpecificInfoImageSignatureVerification vimSpecificInfoImageSignatureVerification = (VimSpecificInfoImageSignatureVerification) o;
		return Objects.equals(this.additionalVerificationCapabilities, vimSpecificInfoImageSignatureVerification.additionalVerificationCapabilities) &&
				Objects.equals(this.signatureVerificationAlgorithms, vimSpecificInfoImageSignatureVerification.signatureVerificationAlgorithms);
	}

	@Override
	public int hashCode() {
		return Objects.hash(additionalVerificationCapabilities, signatureVerificationAlgorithms);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VimSpecificInfoImageSignatureVerification {\n");

		sb.append("    additionalVerificationCapabilities: ").append(toIndentedString(additionalVerificationCapabilities)).append("\n");
		sb.append("    signatureVerificationAlgorithms: ").append(toIndentedString(signatureVerificationAlgorithms)).append("\n");
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
