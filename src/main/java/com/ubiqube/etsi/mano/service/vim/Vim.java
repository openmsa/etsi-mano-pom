package com.ubiqube.etsi.mano.service.vim;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.jgrapht.ListenableGraph;

import com.ubiqube.etsi.mano.dao.mano.GrantInformation;
import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VlProtocolData;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.service.graph.vnfm.ConnectivityEdge;
import com.ubiqube.etsi.mano.service.graph.vnfm.UnitOfWork;

public interface Vim {

	String onVnfInstanceTerminate(Map<String, String> userData);

	String onVnfInstantiate(GrantInformation grantInformation, VnfPackage vnfPackage);

	String onNsInstantiate(UUID nsdId, Map<String, Object> userData);

	String onNsInstanceTerminate(String processId, Map<String, Object> userData);

	@Nonnull
	VimStatus waitForCompletion(String processId, int seconds);

	void allocateResources(VimConnectionInformation vimConnectionInformation, GrantInformation grantInformation);

	void freeResources(VimConnectionInformation vimConnectionInformation, GrantInformation grantInformation);

	String getType();

	@Nonnull
	VimImage getImagesInformations(VimConnectionInformation vimConnectionInformation, String name);

	String createNetwork(final VimConnectionInformation vimConnectionInformation, final VlProtocolData vl, String name);

	void refineExecutionPlan(@Nonnull final ListenableGraph<UnitOfWork, ConnectivityEdge> g);

	Optional<SoftwareImage> getSwImageMatching(VimConnectionInformation vimConnectionInformation, SoftwareImage img);

	SoftwareImage uploadSoftwareImage(VimConnectionInformation vimConnectionInformation, SoftwareImage img);

	@Nonnull
	String getOrCreateFlavor(VimConnectionInformation vimConnectionInformation, String name, int numVcpu, long virtualMemorySize, long disk);

	String createStorage(VimConnectionInformation vimConnectionInformation, VnfStorage vnfStorage, final String aliasName);

	String createCompute(VimConnectionInformation vimConnectionInformation, String instanceName, String flavorId, String imageId, List<String> networks, List<String> storages);

	String createObjectStorage(final VimConnectionInformation vimConnectionInformation, final VnfStorage vnfStorage);

	List<String> getZoneAvailableList(VimConnectionInformation vimConnectionInformation);

	void deleteCompute(VimConnectionInformation vimConnectionInformation, String resourceId);

	void deleteVirtualLink(VimConnectionInformation vimConnectionInformation, String resourceId);

	void deleteStorage(VimConnectionInformation vimConnectionInformation, String resourceId);

	void deleteObjectStorage(VimConnectionInformation vimConnectionInformation, String resourceId);

	List<ServerGroup> getServerGroup(final VimConnectionInformation vimConnectionInformation);

	String createRouter(final VimConnectionInformation vimConnectionInformation, final String name, final String internalNetworkId, final String externalNetworkId);

	void deleteRouter(VimConnectionInformation vimConnectionInformation, String resourceId);
}