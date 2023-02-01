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
 * VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms
 */
@Validated

public class VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms {
	@JsonProperty("algorithm")
	private String algorithm = null;

	@JsonProperty("keyLengths")
	@Valid
	private List<String> keyLengths = new ArrayList<>();

	@JsonProperty("additionalAlgParams")
	private Map<String, String> additionalAlgParams = null;

	public VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms algorithm(final String algorithm) {
		this.algorithm = algorithm;
		return this;
	}

	/**
	 * The name of the algorithm. Permitted values are: \"RSA\", \"DSA\", \"ECDSA\".
	 *
	 * @return algorithm
	 **/
	@Schema(required = true, description = "The name of the algorithm. Permitted values are: \"RSA\", \"DSA\", \"ECDSA\". ")
	@NotNull

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}

	public VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms keyLengths(final List<String> keyLengths) {
		this.keyLengths = keyLengths;
		return this;
	}

	public VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms addKeyLengthsItem(final String keyLengthsItem) {
		this.keyLengths.add(keyLengthsItem);
		return this;
	}

	/**
	 * List of supported key lengths of the algorithm. The key length indicates the
	 * number of bits, such as “256”, “512”, etc. See note.
	 *
	 * @return keyLengths
	 **/
	@Schema(required = true, description = "List of supported key lengths of the algorithm. The key length indicates the number of bits, such as “256”, “512”, etc. See note. ")
	@NotNull

	public List<String> getKeyLengths() {
		return keyLengths;
	}

	public void setKeyLengths(final List<String> keyLengths) {
		this.keyLengths = keyLengths;
	}

	public VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms additionalAlgParams(final Map<String, String> additionalAlgParams) {
		this.additionalAlgParams = additionalAlgParams;
		return this;
	}

	/**
	 * Get additionalAlgParams
	 *
	 * @return additionalAlgParams
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getAdditionalAlgParams() {
		return additionalAlgParams;
	}

	public void setAdditionalAlgParams(final Map<String, String> additionalAlgParams) {
		this.additionalAlgParams = additionalAlgParams;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms vimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms = (VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms) o;
		return Objects.equals(this.algorithm, vimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms.algorithm) &&
				Objects.equals(this.keyLengths, vimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms.keyLengths) &&
				Objects.equals(this.additionalAlgParams, vimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms.additionalAlgParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(algorithm, keyLengths, additionalAlgParams);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VimSpecificInfoImageSignatureVerificationSignatureVerificationAlgorithms {\n");

		sb.append("    algorithm: ").append(toIndentedString(algorithm)).append("\n");
		sb.append("    keyLengths: ").append(toIndentedString(keyLengths)).append("\n");
		sb.append("    additionalAlgParams: ").append(toIndentedString(additionalAlgParams)).append("\n");
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
