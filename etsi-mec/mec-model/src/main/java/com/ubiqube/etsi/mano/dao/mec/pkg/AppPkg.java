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
package com.ubiqube.etsi.mano.dao.mec.pkg;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.ubiqube.etsi.mano.dao.mano.Audit;
import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.Auditable;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageBase;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.PackageUsageState;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.common.Checksum;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Setter
@Getter
@Entity
@Table(schema = "mec_meo")
@EntityListeners(AuditListener.class)
@Indexed
public class AppPkg implements PackageBase, Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@FullTextField
	private UUID id;

	@FullTextField
	private String appDId;

	@FullTextField
	private String appSoftwareVersion;

	@Embedded
	private Checksum checksum;

	// AppPkgSWImageInfo

	// additionalArtifacts

	@Enumerated(EnumType.STRING)
	private OnboardingStateType onboardingState = null;

	@Enumerated(EnumType.STRING)
	private PackageOperationalState operationalState = null;

	@Enumerated(EnumType.STRING)
	private PackageUsageState usageState = null;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@CollectionTable(schema = "mec_meo")
	private Map<String, String> userDefinedData = null;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn
	private Set<DNSRuleDescriptor> appDNSRule;

	@FullTextField
	private String appDVersion;

	@FullTextField
	private String appDescription;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn
	private Set<AppExternalCpd> appExtCpd;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<VnfVl> vnfVl = new LinkedHashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<VnfLinkPort> vnfLinkPort = new LinkedHashSet<>();

	@ElementCollection
	@CollectionTable(schema = "mec_meo")
	private Set<FeatureDependency> appFeatureOptional;

	@ElementCollection
	@CollectionTable(schema = "mec_meo")
	private Set<FeatureDependency> appFeatureRequired;

	@FullTextField
	private String appInfoName;

	@Embedded
	private LatencyDescriptor appLatency;

	@FullTextField
	private String appName;

	@FullTextField
	private String appProvider;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<ServiceDependency> appServiceOptional = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn
	private Set<ServiceDescriptor> appServiceProduced = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<ServiceDependency> appServiceRequired = new HashSet<>();

	@FullTextField
	private String appSoftVersion;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn
	private Set<TrafficRuleDescriptor> appTrafficRule = new HashSet<>();

	@FullTextField
	private String changeAppInstanceStateOpConfig;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> mecVersion = new HashSet<>();

	@FullTextField
	private String terminateAppInstanceOpConfig;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn
	private Set<TransportDependency> transportDependencies = new HashSet<>();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private VnfCompute virtualComputeDescriptor;

	@ElementCollection
	@CollectionTable(schema = "mec_meo")
	private Set<String> virtualStorageDescriptor = new HashSet<>();

	@Embedded
	private Audit audit;

}
