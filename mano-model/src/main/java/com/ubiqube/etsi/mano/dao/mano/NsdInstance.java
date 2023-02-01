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
package com.ubiqube.etsi.mano.dao.mano;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.ubiqube.etsi.mano.dao.mano.dto.ParamsForNestedNsd;
import com.ubiqube.etsi.mano.dao.mano.nfvo.NsVnfInstance;
import com.ubiqube.etsi.mano.dao.mano.nfvo.ParamsForVnf;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.wan.WanConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Entity
@Indexed
public class NsdInstance extends Instance {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@FullTextField
	private String nsInstanceName = null;

	@FullTextField
	private String nsInstanceDescription = null;

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private NsdPackage nsdInfo = null;

	private String nsInstantiationLevelId;

	private String instanceFlavourId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn
	private List<NsVnfInstance> vnfInstance = null;

	// XXX Add pnfInfo
	// XXX Add virtualLinkInfo
	// XXX Add vnffgInfo
	// XXX Add sapInfo

	@OneToMany(fetch = FetchType.LAZY)
	private List<NsdInstance> nestedNsInstance = null;

	// XXX Add additionalAffinityOrAntiAffinityRule

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "nsInstance")
	private Set<NsBlueprint> blueprint;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Set<VnfInstanceData> vnfInstanceData;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Set<NestedNsInstanceData> nestedNsInstanceData;

	@ElementCollection
	private Map<String, String> additionalParamsForNs;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Set<ParamsForNestedNsd> additionalParamForNestedNs;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Set<ParamsForVnf> additionalParamsForVnf;
	// 3.3.1
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<WanConnectionInformation> wanConnectionInfo;
	// 3.3.1
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vnfSnapshotInfoIds;
	// 3.5.1
	private BigDecimal priority;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<NsdVnfPackageCopy> vnfPkgIds;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VnffgDescriptor> vnffgs;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<VnffgDescriptor> vnffgInfo;

	@Transient
	private List<AffinityOrAntiAffinityRule> additionalAffinityOrAntiAffinityRule;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<NsMonitoringParameter> monitoringParameter;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<NsScaleInfo> nsScaleStatus;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SapInfo> sapInfo;

	public void addNestedNsInstance(final NsdInstance nsIn) {
		if (null == nestedNsInstance) {
			nestedNsInstance = new ArrayList<>();
		}
		nestedNsInstance.add(nsIn);
	}

}
