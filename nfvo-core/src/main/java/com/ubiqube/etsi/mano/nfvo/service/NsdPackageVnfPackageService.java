package com.ubiqube.etsi.mano.nfvo.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdPackageVnfPackageJpa;

@Service
public class NsdPackageVnfPackageService {

	private final NsdPackageVnfPackageJpa nsdPackageVnfPackageJpa;

	public NsdPackageVnfPackageService(final NsdPackageVnfPackageJpa nsdPackageVnfPackageJpa) {
		this.nsdPackageVnfPackageJpa = nsdPackageVnfPackageJpa;
	}

	public Set<NsdPackageVnfPackage> findByNsdPackage(final NsdPackage nsdPackage) {
		return nsdPackageVnfPackageJpa.findByNsdPackage(nsdPackage);
	}

	public Set<NsdPackageVnfPackage> findByVnfdId(final String vnfdId) {
		return nsdPackageVnfPackageJpa.findByVnfdId(vnfdId);
	}
}
