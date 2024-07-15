package com.ubiqube.etsi.mano.service.vim;

import java.util.List;
import java.util.Objects;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.ClusterMachine;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.service.sys.SysImage;
import com.ubiqube.etsi.mano.vim.dto.Flavor;

public class CnfInformationVerifier {
	private final Vim vim;
	private final VimConnectionInformation vci;

	private CnfInformationVerifier(final Vim vim, final VimConnectionInformation vci) {
		this.vim = vim;
		this.vci = vci;
	}

	public static CnfInformationVerifier of(final Vim vim, final VimConnectionInformation vci) {
		return new CnfInformationVerifier(vim, vci);
	}

	public void verifyCnf() {
		final CnfInformations cnfInfo = vci.getCnfInfo();
		if (null == cnfInfo) {
			return;
		}
		checkNetwork(cnfInfo);
		final List<Flavor> lst = vim.getFlavorList(vci);

		final ClusterMachine master = cnfInfo.getMaster();
		verifyMachine(master, lst);
		final ClusterMachine worker = cnfInfo.getWorker();
		verifyMachine(worker, lst);
	}

	private void checkNetwork(final CnfInformations cnfInfo) {
		final String netName = cnfInfo.getExtNetwork();
		final String netId = cnfInfo.getExtNetworkId();
		if ((null == netName) && (null == netId)) {
			throw new VimException("You must have an external network name or ID.");
		}
		if (null != netName) {
			final List<NetworkObject> res = vim.network(vci).search(NetowrkSearchField.NAME, List.of(netName));
			if (res.size() != 1) {
				throw new VimException("External network must be unique but was " + res.size());
			}
			final NetworkObject no = res.get(0);
			cnfInfo.setExtNetworkId(no.id());
		} else {
			final List<NetworkObject> res = vim.network(vci).search(NetowrkSearchField.ID, List.of(netId));
			if (res.size() != 1) {
				throw new VimException("External network must be unique but was " + res.size());
			}
			final NetworkObject no = res.get(0);
			cnfInfo.setExtNetwork(no.name());
		}
	}

	private void verifyMachine(final ClusterMachine machine, final List<Flavor> lst) {
		final String flav = machine.getFlavor();
		final String flavId = machine.getFlavorId();
		if ((null == flav) && (null == flavId)) {
			throw new VimException("You must have a flavor or a flavorId");
		}
		if (null != flav) {
			final Flavor f = findFlavorByName(lst, flav);
			machine.setFlavorId(f.getId());
		} else {
			final Flavor f = findFlavorById(lst, flavId);
			machine.setFlavor(f.getName());
		}
		if (machine.getMinNumberInstance() < 1) {
			throw new VimException("Number of instance must be at least 1");
		}
		final SysImage res = vim.storage(vci).getImagesInformations(machine.getImage());
		Objects.requireNonNull(res, () -> "Unable to find image: " + machine.getImage());
	}

	private static Flavor findFlavorById(final List<Flavor> lst, final String flav) {
		return lst.stream().filter(x -> x.getId().equals(flav))
				.findFirst()
				.orElseThrow(() -> new VimException("Unable to find flavor: " + flav));
	}

	private static Flavor findFlavorByName(final List<Flavor> lst, final String flav) {
		return lst.stream().filter(x -> x.getName().equals(flav))
				.findFirst()
				.orElseThrow(() -> new VimException("Unable to find flavor: " + flav));
	}
}
