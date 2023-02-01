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
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents information attributes specific to a VIM entity, and
 * that can be relevant to more than one NFV MANO service offered by a VIM
 * entity. It shall comply with the provisions defined in table 5.6.3.6-1. The
 * present document version does not specify the support for \&quot;maximum
 * number of virtualised resources (attribute
 * \&quot;maxVirtualResourceNum\&quot;)\&quot; as specified in clause 7.2.9.2 of
 * ETSI GS NFV IFA 031 [1]. NOTE: Recommendations on appropriate algorithms and
 * key lengths are given e.g., in NIST Special Publication 800-57 Part 3
 * Revision 1 [i.26] or in documents related to local legislation.
 */
@Schema(description = "This type represents information attributes specific to a VIM entity, and that can be relevant to more than one NFV MANO service offered by a VIM entity. It shall comply with the provisions defined in table 5.6.3.6-1. The present document version does not specify the support for \"maximum number of virtualised resources (attribute \"maxVirtualResourceNum\")\" as specified in clause 7.2.9.2 of ETSI GS NFV IFA 031 [1]. NOTE: Recommendations on appropriate algorithms and key lengths are given e.g., in NIST Special Publication 800-57 Part 3 Revision 1 [i.26] or in documents related to local legislation. ")
@Validated

public class VimSpecificInfo {
	@JsonProperty("imageContainerFormats")
	@Valid
	private List<String> imageContainerFormats = new ArrayList<>();

	@JsonProperty("imageDiskFormats")
	@Valid
	private List<String> imageDiskFormats = new ArrayList<>();

	@JsonProperty("imageSignatureVerification")
	private VimSpecificInfoImageSignatureVerification imageSignatureVerification = null;

	@JsonProperty("imageDigestAlgorithms")
	@Valid
	private List<VimSpecificInfoImageDigestAlgorithms> imageDigestAlgorithms = new ArrayList<>();

	public VimSpecificInfo imageContainerFormats(final List<String> imageContainerFormats) {
		this.imageContainerFormats = imageContainerFormats;
		return this;
	}

	public VimSpecificInfo addImageContainerFormatsItem(final String imageContainerFormatsItem) {
		this.imageContainerFormats.add(imageContainerFormatsItem);
		return this;
	}

	/**
	 * List of supported image container formats. Container format indicates whether
	 * a software image is in a file that also contains metadata about the actual
	 * software. Valid values are all values for \"container_format\" as defined in
	 * ETSI GS NFV-SOL 001 [16] and all values for \"container-format\" as defined
	 * in ETSI GS NFV-SOL 006 [i.17].
	 *
	 * @return imageContainerFormats
	 **/
	@Schema(required = true, description = "List of supported image container formats. Container format indicates whether a software image is in a file that also contains metadata about the actual software. Valid values are all values for \"container_format\" as defined in ETSI GS NFV-SOL 001 [16] and all values for \"container-format\" as defined in ETSI GS NFV-SOL 006 [i.17]. ")
	@NotNull

	public List<String> getImageContainerFormats() {
		return imageContainerFormats;
	}

	public void setImageContainerFormats(final List<String> imageContainerFormats) {
		this.imageContainerFormats = imageContainerFormats;
	}

	public VimSpecificInfo imageDiskFormats(final List<String> imageDiskFormats) {
		this.imageDiskFormats = imageDiskFormats;
		return this;
	}

	public VimSpecificInfo addImageDiskFormatsItem(final String imageDiskFormatsItem) {
		this.imageDiskFormats.add(imageDiskFormatsItem);
		return this;
	}

	/**
	 * List of supported image disk formats. The Disk format of a software image is
	 * the format of the underlying disk image. Valid values are all values for
	 * \"disk_format\" as defined in ETSI GS NFV-SOL 001 [16] and all values of
	 * \"disk-format\" as defined in ETSI GS NFV-SOL 006 [i.17].
	 *
	 * @return imageDiskFormats
	 **/
	@Schema(required = true, description = "List of supported image disk formats. The Disk format of a software image is the format of the underlying disk image. Valid values are all values for \"disk_format\" as defined in ETSI GS NFV-SOL 001 [16] and all values of \"disk-format\" as defined in ETSI GS NFV-SOL 006 [i.17]. ")
	@NotNull

	public List<String> getImageDiskFormats() {
		return imageDiskFormats;
	}

	public void setImageDiskFormats(final List<String> imageDiskFormats) {
		this.imageDiskFormats = imageDiskFormats;
	}

	public VimSpecificInfo imageSignatureVerification(final VimSpecificInfoImageSignatureVerification imageSignatureVerification) {
		this.imageSignatureVerification = imageSignatureVerification;
		return this;
	}

	/**
	 * Get imageSignatureVerification
	 *
	 * @return imageSignatureVerification
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public VimSpecificInfoImageSignatureVerification getImageSignatureVerification() {
		return imageSignatureVerification;
	}

	public void setImageSignatureVerification(final VimSpecificInfoImageSignatureVerification imageSignatureVerification) {
		this.imageSignatureVerification = imageSignatureVerification;
	}

	public VimSpecificInfo imageDigestAlgorithms(final List<VimSpecificInfoImageDigestAlgorithms> imageDigestAlgorithms) {
		this.imageDigestAlgorithms = imageDigestAlgorithms;
		return this;
	}

	public VimSpecificInfo addImageDigestAlgorithmsItem(final VimSpecificInfoImageDigestAlgorithms imageDigestAlgorithmsItem) {
		this.imageDigestAlgorithms.add(imageDigestAlgorithmsItem);
		return this;
	}

	/**
	 * List of supported digest algorithms that can be used for digital signatures.
	 *
	 * @return imageDigestAlgorithms
	 **/
	@Schema(required = true, description = "List of supported digest algorithms that can be used for digital signatures. ")
	@NotNull
	@Valid
	public List<VimSpecificInfoImageDigestAlgorithms> getImageDigestAlgorithms() {
		return imageDigestAlgorithms;
	}

	public void setImageDigestAlgorithms(final List<VimSpecificInfoImageDigestAlgorithms> imageDigestAlgorithms) {
		this.imageDigestAlgorithms = imageDigestAlgorithms;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VimSpecificInfo vimSpecificInfo = (VimSpecificInfo) o;
		return Objects.equals(this.imageContainerFormats, vimSpecificInfo.imageContainerFormats) &&
				Objects.equals(this.imageDiskFormats, vimSpecificInfo.imageDiskFormats) &&
				Objects.equals(this.imageSignatureVerification, vimSpecificInfo.imageSignatureVerification) &&
				Objects.equals(this.imageDigestAlgorithms, vimSpecificInfo.imageDigestAlgorithms);
	}

	@Override
	public int hashCode() {
		return Objects.hash(imageContainerFormats, imageDiskFormats, imageSignatureVerification, imageDigestAlgorithms);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VimSpecificInfo {\n");

		sb.append("    imageContainerFormats: ").append(toIndentedString(imageContainerFormats)).append("\n");
		sb.append("    imageDiskFormats: ").append(toIndentedString(imageDiskFormats)).append("\n");
		sb.append("    imageSignatureVerification: ").append(toIndentedString(imageSignatureVerification)).append("\n");
		sb.append("    imageDigestAlgorithms: ").append(toIndentedString(imageDigestAlgorithms)).append("\n");
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
