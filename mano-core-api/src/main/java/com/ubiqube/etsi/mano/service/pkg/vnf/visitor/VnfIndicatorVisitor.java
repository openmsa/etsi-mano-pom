package com.ubiqube.etsi.mano.service.pkg.vnf.visitor;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardVisitor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;


@Service
public class VnfIndicatorVisitor  implements OnboardVisitor {
	
	@Override
	public void visit(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final Map<String, String> userData) {
		final Set<VnfIndicator> vnfIndicators = vnfPackageReader.getVnfIndicator(vnfPackage.getUserDefinedData());
		vnfPackage.setVnfIndicator(vnfIndicators);
	}
	
}
