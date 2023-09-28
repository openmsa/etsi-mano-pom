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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.PkgChecksum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.UploadUriParameters;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.ManoUrlResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.bean.ProviderData;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 *
 *         This class is need in VNFM, when the NFVO onboard a package, we will
 *         receive a notification, then we download the new package, and onboard
 *         it.
 */
@Service
public class VnfPackageOnboardingImpl {

	private static final Logger LOG = LoggerFactory.getLogger(VnfPackageOnboardingImpl.class);

	private final EventManager eventManager;

	private final VnfPackageManager packageManager;

	private final VnfPackageService vnfPackageService;

	private final VnfPackageRepository vnfPackageRepository;

	private final VnfOnboardingMapperService onboardingMapper;

	public VnfPackageOnboardingImpl(final VnfPackageRepository vnfPackageRepository, final EventManager eventManager, final VnfPackageManager packageManager,
			final VnfPackageService vnfPackageService, final VnfOnboardingMapperService onboardingMapper) {
		this.vnfPackageRepository = vnfPackageRepository;
		this.eventManager = eventManager;
		this.packageManager = packageManager;
		this.vnfPackageService = vnfPackageService;
		this.onboardingMapper = onboardingMapper;
	}

	public VnfPackage vnfPackagesVnfPkgIdPackageContentPut(final String vnfPkgId) {
		final ManoResource data = vnfPackageRepository.getBinary(getSafeUUID(vnfPkgId), Constants.REPOSITORY_FILENAME_PACKAGE);
		VnfPackage vnfPpackage = vnfPackageService.findById(getSafeUUID(vnfPkgId));
		// MSA-11833
		ensureNotProcessing(vnfPpackage);
		vnfPpackage = startOnboarding(vnfPpackage);
		return uploadAndFinishOnboarding(vnfPpackage, data);
	}

	public VnfPackage vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(final String vnfPkgId) {
		final VnfPackage vnfPackage = vnfPackageService.findById(getSafeUUID(vnfPkgId));
		startOnboarding(vnfPackage);
		final UploadUriParameters params = vnfPackage.getUploadUriParameters();
		LOG.info("Async. Download of {}", params);
		try (FluxRequestor requestor = new FluxRequestor(vnfPackage.getUploadUriParameters())) {
			final ManoResource data = new ManoUrlResource(0, vnfPackage.getUploadUriParameters().getAddressInformation().toString(), requestor);
			return uploadAndFinishOnboarding(vnfPackage, data);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private VnfPackage uploadAndFinishOnboarding(final VnfPackage vnfPackage, final ManoResource data) {
		VnfPackage ret = vnfPackage;
		try {
			final PackageDescriptor<VnfPackageReader> packageProvider = packageManager.getProviderFor(data);
			mapVnfPackage(vnfPackage, data, packageProvider);
			ret = finishOnboarding(vnfPackage);
			buildChecksum(ret, data);
			eventManager.sendNotification(NotificationEvent.VNF_PKG_ONBOARDING, vnfPackage.getId(), Map.of());
		} catch (final RuntimeException | NoSuchAlgorithmException | IOException e) {
			LOG.error("", e);
			final VnfPackage v2 = vnfPackageService.findById(vnfPackage.getId());
			v2.setOnboardingState(OnboardingStateType.ERROR);
			v2.setOnboardingFailureDetails(new FailureDetails(500, e.getMessage()));
			ret = vnfPackageService.save(v2);
		}
		return ret;
	}

	private VnfPackage buildChecksum(final VnfPackage vnfPackage, final ManoResource data) throws NoSuchAlgorithmException, IOException {
		final DigestInputStream dis = new DigestInputStream(data.getInputStream(), MessageDigest.getInstance(Constants.HASH_ALGORITHM));
		try (InputStream stream = data.getInputStream()) {
			stream.readAllBytes();
			vnfPackage.setChecksum(getChecksum(dis));
		}
		return vnfPackageService.save(vnfPackage);
	}

	public void mapVnfPackage(final VnfPackage vnfPackage, final ManoResource data, final PackageDescriptor<VnfPackageReader> packageProvider) {
		vnfPackage.setPackageProvider(packageProvider.getProviderName());
		try (InputStream stream = data.getInputStream();
				final VnfPackageReader reader = packageProvider.getNewReaderInstance(stream, vnfPackage.getId())) {
			mapVnfPackage(reader, vnfPackage);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private void mapVnfPackage(final VnfPackageReader vnfPackageReader, final VnfPackage vnfPackage) {
		final ProviderData pd = vnfPackageReader.getProviderPadata();
		Optional.ofNullable(vnfPackage.getOverwriteDescId()).ifPresent(pd::setDescriptorId);
		final Optional<VnfPackage> optPackage = getVnfPackage(pd);
		optPackage.ifPresent(x -> {
			throw new GenericException("Package " + x.getVnfdId() + " already onboarded in " + x.getId() + ".");
		});
		onboardingMapper.mapper(vnfPackageReader, vnfPackage, pd);
	}

	private static PkgChecksum getChecksum(final DigestInputStream digest) {
		final byte[] hashbytes = digest.getMessageDigest().digest();
		final String sha3_256hex = bytesToHex(hashbytes);
		final PkgChecksum checksum = new PkgChecksum();

		checksum.setAlgorithm(Constants.HASH_ALGORITHM);
		checksum.setHash(sha3_256hex);
		return checksum;
	}

	private static String bytesToHex(final byte[] hash) {
		final StringBuilder hexString = new StringBuilder();
		for (final byte element : hash) {
			final String hex = Integer.toHexString(0xff & element);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	private VnfPackage finishOnboarding(final VnfPackage vnfPackage) {
		vnfPackage.setOnboardingState(OnboardingStateType.ONBOARDED);
		vnfPackage.setOperationalState(PackageOperationalState.ENABLED);
		vnfPackage.setOnboardingFailureDetails(new FailureDetails());
		return vnfPackageService.save(vnfPackage);
	}

	private VnfPackage startOnboarding(final VnfPackage vnfPackage) {
		vnfPackage.setOnboardingState(OnboardingStateType.PROCESSING);
		vnfPackage.setOnboardingFailureDetails(null);
		return vnfPackageService.save(vnfPackage);
	}

	private Optional<VnfPackage> getVnfPackage(final ProviderData pd) {
		return getVnfPackage(pd.getFlavorId(), pd.getDescriptorId(), pd.getVnfdVersion());
	}

	private Optional<VnfPackage> getVnfPackage(final @Nullable String flavor, final @Nullable String descriptorId, final @Nullable String version) {
		int part = 0;
		if (flavor != null) {
			part++;
		}
		if (descriptorId != null) {
			part++;
		}
		if (version != null) {
			part++;
		}
		switch (part) {
		case 0:
			return Optional.empty();
		case 1:
			return vnfPackageService.findByVnfdId(descriptorId);
		case 2:
			return vnfPackageService.findByVnfdIdAndSoftwareVersion(descriptorId, version);
		case 3:
			return vnfPackageService.findByVnfdIdFlavorIdVnfdVersion(descriptorId, flavor, version);
		default:
			break;
		}
		throw new GenericException("Unknown version " + part);
	}

	// MSA-11833
	public static void ensureNotProcessing(final VnfPackage vnfPackage) {
		if (OnboardingStateType.PROCESSING == vnfPackage.getOnboardingState()) {
			throw new ConflictException("THE_VNF_PACKAGE" + vnfPackage.getId() + " is already stared ONBOARDING..");
		}
	}

}
