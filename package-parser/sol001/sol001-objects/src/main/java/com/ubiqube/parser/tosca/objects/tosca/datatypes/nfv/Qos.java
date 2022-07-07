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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.scalar.Time;
import java.lang.Float;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes QoS data for a given VL used in a VNF deployment flavour
 */
public class Qos extends Root {
	/**
	 * Specifies the maximum latency
	 */
	@Valid
	@NotNull
	@JsonProperty("latency")
	private Time latency;

	/**
	 * Specifies the maximum packet loss ratio
	 */
	@Valid
	@JsonProperty("packet_loss_ratio")
	@Min(0)
	@Max(1)
	private Float packetLossRatio;

	/**
	 * Specifies the maximum jitter
	 */
	@Valid
	@NotNull
	@JsonProperty("packet_delay_variation")
	private Time packetDelayVariation;

	@NotNull
	public Time getLatency() {
		return this.latency;
	}

	public void setLatency(@NotNull final Time latency) {
		this.latency = latency;
	}

	public Float getPacketLossRatio() {
		return this.packetLossRatio;
	}

	public void setPacketLossRatio(final Float packetLossRatio) {
		this.packetLossRatio = packetLossRatio;
	}

	@NotNull
	public Time getPacketDelayVariation() {
		return this.packetDelayVariation;
	}

	public void setPacketDelayVariation(@NotNull final Time packetDelayVariation) {
		this.packetDelayVariation = packetDelayVariation;
	}
}
