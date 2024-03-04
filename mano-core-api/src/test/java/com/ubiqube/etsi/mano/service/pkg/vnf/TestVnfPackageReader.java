/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
/**
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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.ScalingAspect;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCp;
import com.ubiqube.etsi.mano.dao.mano.repo.Repository;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.service.pkg.bean.AffinityRuleAdapater;
import com.ubiqube.etsi.mano.service.pkg.bean.ProviderData;
import com.ubiqube.etsi.mano.service.pkg.bean.SecurityGroupAdapter;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.InstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInitialDelta;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduScalingAspectDeltas;

public class TestVnfPackageReader implements VnfPackageReader {

	private List<InstantiationLevels> instatiationLevels = List.of();
	private List<VduInstantiationLevels> vduInstantiationLevels = List.of();
	private List<VduScalingAspectDeltas> vduScalingAspectDeltas = List.of();
	private List<VduInitialDelta> vduInitialDelta = List.of();
	private List<String> vnfdFiles = List.of();
	private Set<SecurityGroupAdapter> securityGroup = Set.of();
	private Set<AffinityRuleAdapater> affinityRules = Set.of();
	private String manifestContent;

	@Override
	public void close() throws IOException {
		//
	}

	@Override
	public ProviderData getProviderPadata() {
		return new ProviderData();
	}

	@Override
	public Set<AdditionalArtifact> getAdditionalArtefacts(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<VnfCompute> getVnfComputeNodes(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<VnfStorage> getVnfStorages(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<VnfVl> getVnfVirtualLinks(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<VnfLinkPort> getVnfVduCp(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<VnfExtCp> getVnfExtCp(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<ScalingAspect> getScalingAspects(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public List<InstantiationLevels> getInstatiationLevels(final Map<String, String> parameters) {
		return instatiationLevels;
	}

	public void setInstatiationLevels(final List<InstantiationLevels> instatiationLevels) {
		this.instatiationLevels = instatiationLevels;
	}

	@Override
	public List<VduInstantiationLevels> getVduInstantiationLevels(final Map<String, String> parameters) {
		return vduInstantiationLevels;
	}

	public void setVduInstantiationLevels(final List<VduInstantiationLevels> vduInstantiationLevels) {
		this.vduInstantiationLevels = vduInstantiationLevels;
	}

	@Override
	public List<VduInitialDelta> getVduInitialDelta(final Map<String, String> parameters) {
		return vduInitialDelta;
	}

	public void setVduInitialDelta(final List<VduInitialDelta> vduInitialDelta) {
		this.vduInitialDelta = vduInitialDelta;
	}

	@Override
	public List<VduScalingAspectDeltas> getVduScalingAspectDeltas(final Map<String, String> parameters) {
		return vduScalingAspectDeltas;
	}

	public void setVduScalingAspectDeltas(final List<VduScalingAspectDeltas> vduScalingAspectDeltas) {
		this.vduScalingAspectDeltas = vduScalingAspectDeltas;
	}

	@Override
	public Set<AffinityRuleAdapater> getAffinityRules(final Map<String, String> userDefinedData) {
		return affinityRules;
	}

	public void setAffinityRules(final Set<AffinityRuleAdapater> affinityRules) {
		this.affinityRules = affinityRules;
	}

	@Override
	public Set<SecurityGroupAdapter> getSecurityGroups(final Map<String, String> userData) {
		return securityGroup;
	}

	public void setSecurityGroup(final Set<SecurityGroupAdapter> securityGroup) {
		this.securityGroup = securityGroup;
	}

	@Override
	public List<String> getImports() {
		return List.of();
	}

	@Override
	public String getManifestContent() {
		return manifestContent;
	}

	public void setManifestContent(final String manifestContent) {
		this.manifestContent = manifestContent;
	}

	@Override
	public byte[] getFileContent(final String fileName) {
		return "".getBytes();
	}

	@Override
	public Set<OsContainer> getOsContainer(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<OsContainerDeployableUnit> getOsContainerDeployableUnit(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<VirtualCp> getVirtualCp(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<VnfIndicator> getVnfIndicator(final Map<String, String> parameters) {
		return Set.of();
	}

	@Override
	public Set<McIops> getMciops(final Map<String, String> userDefinedData) {
		return Set.of();
	}

	@Override
	public InputStream getFileInputStream(final String path) {
		return null;
	}

	@Override
	public List<String> getVnfdFiles(final boolean includeSignatures) {
		return vnfdFiles;
	}

	public void setVnfdFiles(final List<String> vnfdFiles) {
		this.vnfdFiles = vnfdFiles;
	}

	@Override
	public Set<Repository> getRepositories() {
		return Set.of();
	}

}
