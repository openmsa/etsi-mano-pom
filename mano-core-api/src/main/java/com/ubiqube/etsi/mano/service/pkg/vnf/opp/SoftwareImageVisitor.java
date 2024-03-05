/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.service.pkg.vnf.opp;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.service.pkg.vnf.DownloaderService;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardingPostProcessorVisitor;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class SoftwareImageVisitor implements OnboardingPostProcessorVisitor {

	private static final Logger LOG = LoggerFactory.getLogger(SoftwareImageVisitor.class);

	private final DownloaderService downloaderService;

	public SoftwareImageVisitor(final DownloaderService downloaderService) {
		this.downloaderService = downloaderService;
	}

	@Override
	public void visit(final VnfPackage vnfPackage) {
		final Map<ImageKey, SoftwareImage> mvnf = vnfPackage.getVnfCompute().stream().map(VnfCompute::getSoftwareImage).filter(Objects::nonNull).collect(Collectors.toMap(ImageKey::new, x -> x, (k1, k2) -> k1));
		final Map<ImageKey, SoftwareImage> msto = vnfPackage.getVnfStorage().stream().map(VnfStorage::getSoftwareImage).filter(Objects::nonNull).collect(Collectors.toMap(ImageKey::new, x -> x, (k1, k2) -> k1));
		final Map<ImageKey, SoftwareImage> osco = vnfPackage.getOsContainer().stream().map(OsContainer::getArtifacts)
				.filter(Objects::nonNull)
				.flatMap(x -> x.entrySet().stream())
				.map(Entry::getValue)
				.collect(Collectors.toMap(ImageKey::new, x -> x, (k1, k2) -> k1));
		final Map<ImageKey, SoftwareImage> mciop = vnfPackage.getMciops().stream().map(McIops::getArtifacts).filter(Objects::nonNull)
				.flatMap(x -> x.entrySet().stream())
				.map(Entry::getValue)
				.collect(Collectors.toMap(ImageKey::new, x -> x, (k1, k2) -> k1));
		final HashMap<ImageKey, SoftwareImage> mall = new HashMap<>(mvnf);
		mall.putAll(msto);
		mall.putAll(osco);
		mall.putAll(mciop);
		final List<SoftwareImage> toUpload = mall.entrySet().stream().map(Entry::getValue).filter(x -> isRemote(x.getImagePath())).toList();
		downloaderService.doDownload(toUpload, vnfPackage.getId());
		vnfPackage.setSoftwareImages(mall.entrySet().stream().map(Entry::getValue).collect(Collectors.toSet()));
	}

	private static boolean isRemote(final String p) {
		try {
			final URI uri = URI.create(p);
			return uri.getScheme() != null;
		} catch (final RuntimeException e) {
			LOG.trace("", e);
		}
		return false;
	}

	private static class ImageKey {
		private final SoftwareImage si;

		public ImageKey(final SoftwareImage si) {
			this.si = si;
		}

		@Override
		public int hashCode() {
			if ((si.getChecksum() != null) && (null != si.getChecksum().getHash())) {
				return Objects.hash(si.getChecksum().getHash());
			}
			return Objects.hash(si.getName());
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if ((obj == null) || !(obj instanceof final ImageKey ik)) {
				return false;
			}
			final SoftwareImage si2 = ik.si;
			if ((si2.getChecksum() == null) || (si.getChecksum() == null)) {
				return si.getName().equals(si2.getName());
			}
			return Objects.equals(si.getChecksum().getHash(), si2.getChecksum().getHash());
		}
	}

}
