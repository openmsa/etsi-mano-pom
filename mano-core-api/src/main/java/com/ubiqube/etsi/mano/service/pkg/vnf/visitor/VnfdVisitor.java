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
package com.ubiqube.etsi.mano.service.pkg.vnf.visitor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.pkg.FileEntry;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardVisitor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfdVisitor implements OnboardVisitor {
	private final VnfPackageRepository vnfPackageRepository;

	public VnfdVisitor(final VnfPackageRepository vnfPackageRepository) {
		this.vnfPackageRepository = vnfPackageRepository;
	}

	@Override
	public void visit(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final Map<String, String> userData) {
		final List<String> files = vnfPackageReader.getVnfdFiles(true);
		final List<FileEntry> ret = convertToFileEntry(vnfPackageReader, files);
		final byte[] newZip = buildZip(ret);
		final ByteArrayInputStream stream = new ByteArrayInputStream(newZip);
		vnfPackageRepository.storeBinary(vnfPackage.getId(), Constants.REPOSITORY_FILENAME_VNFD, stream);
		vnfPackage.setVnfdContentType("application/zip");
	}

	private static byte[] buildZip(final List<FileEntry> ret) {
		try (final ByteArrayOutputStream fos = new ByteArrayOutputStream();
				final ZipOutputStream zipOut = new ZipOutputStream(fos)) {
			for (final FileEntry srcFile : ret) {
				try (final ByteArrayInputStream fis = new ByteArrayInputStream(srcFile.content())) {
					final ZipEntry zipEntry = new ZipEntry(srcFile.fileName());
					zipOut.putNextEntry(zipEntry);
					zipOut.write(srcFile.content());
				}
			}
			zipOut.finish();
			return fos.toByteArray();
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private static List<FileEntry> convertToFileEntry(final VnfPackageReader vnfPackageReader, final List<String> imports) {
		return imports.stream().map(x -> new FileEntry(x, vnfPackageReader.getFileContent(x))).toList();
	}

}
