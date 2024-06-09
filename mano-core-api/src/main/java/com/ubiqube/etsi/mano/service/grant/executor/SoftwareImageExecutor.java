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
package com.ubiqube.etsi.mano.service.grant.executor;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VimSoftwareImageEntity;
import com.ubiqube.etsi.mano.dao.mano.vim.ImageServiceAware;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.service.event.images.SoftwareImageService;
import com.ubiqube.etsi.mano.service.grant.GrantSupport;
import com.ubiqube.etsi.mano.service.vim.Vim;

@Service
public class SoftwareImageExecutor {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(SoftwareImageExecutor.class);
	private final GrantSupport grantSupport;
	private final SoftwareImageService imageService;

	public SoftwareImageExecutor(final SoftwareImageService imageService, final GrantSupport grantSupport) {
		this.grantSupport = grantSupport;
		this.imageService = imageService;
	}

	public Set<VimSoftwareImageEntity> getSoftwareImageSafe(final VimConnectionInformation vimInfo, final Vim vim, final GrantResponse grants) {
		try {
			return getSoftwareImage(vimInfo, vim, grants);
		} catch (final RuntimeException e) {
			LOG.error("getImage error", e);
		}
		return new HashSet<>();
	}

	private Set<VimSoftwareImageEntity> getSoftwareImage(final VimConnectionInformation vimInfo, final Vim vim, final GrantResponse grants) {
		final UUID vnfPkgId = grantSupport.convertVnfdToId(grants.getVnfdId());
		final List<SoftwareImage> vimImgs = imageService.getFullDetailImageList(vimInfo);
		final Set<VimSoftwareImageEntity> ret = new LinkedHashSet<>(getImage(grantSupport.getVnfCompute(grants.getId()), vimImgs, vimInfo, vim, vnfPkgId));
		final Set<VnfStorage> storage = grantSupport.getVnfStorage(grants.getId());
		ret.addAll(getImage(storage, vimImgs, vimInfo, vim, vnfPkgId));
		return ret;
	}

	private Set<VimSoftwareImageEntity> getImage(final Set<? extends ImageServiceAware> storage, final List<SoftwareImage> vimImgs, final VimConnectionInformation vimInfo, final Vim vim, final UUID vnfPkgId) {
		return storage.stream()
				.map(ImageServiceAware::getSoftwareImage)
				.filter(Objects::nonNull)
				.map(x -> {
					final SoftwareImage newImg = imageService.getImage(vimImgs, x, vimInfo, vnfPkgId);
					return mapSoftwareImage(newImg, x.getName(), vimInfo, vim);
				})
				.collect(Collectors.toSet());
	}

	private static VimSoftwareImageEntity mapSoftwareImage(final SoftwareImage softwareImage, final String vduId, final VimConnectionInformation vimInfo, final Vim vim) {
		final VimSoftwareImageEntity vsie = new VimSoftwareImageEntity();
		vsie.setVimSoftwareImageId(softwareImage.getVimId());
		vsie.setVnfdSoftwareImageId(vduId);
		vsie.setVimConnectionId(vimInfo.getVimId());
		vsie.setResourceProviderId(vim.getType());
		return vsie;
	}

}
