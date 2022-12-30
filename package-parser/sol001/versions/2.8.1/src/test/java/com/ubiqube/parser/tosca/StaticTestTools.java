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
package com.ubiqube.parser.tosca;

import java.io.IOException;

import com.ubiqube.parser.tosca.ZipUtil.Entry;

public class StaticTestTools {
	public static void createVnfPackage() {
		try {
			ZipUtil.makeToscaZip("/tmp/ubi-tosca.csar", Entry.of("ubi-tosca/Definitions/tosca_ubi.yaml", "Definitions/tosca_ubi.yaml"),
					Entry.of("etsi_nfv_sol001_vnfd_types.yaml", "Definitions/etsi_nfv_sol001_vnfd_types.yaml"),
					Entry.of("etsi_nfv_sol001_common_types.yaml", "Definitions/etsi_nfv_sol001_common_types.yaml"),
					Entry.of("ubi-tosca/TOSCA-Metadata/TOSCA.meta", "TOSCA-Metadata/TOSCA.meta"));
		} catch (final IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static void createNsdPackage() {
		try {
			ZipUtil.makeToscaZip("/tmp/ubi-tosca.csar",
					Entry.of("ubi-nsd/Definitions/nsd_ubi.yaml", "Definitions/nsd_ubi.yaml"),
					Entry.of("etsi_nfv_sol001_nsd_types.yaml", "Definitions/etsi_nfv_sol001_nsd_types.yaml"),
					Entry.of("etsi_nfv_sol001_vnfd_types.yaml", "Definitions/etsi_nfv_sol001_vnfd_types.yaml"),
					Entry.of("etsi_nfv_sol001_pnfd_types.yaml", "Definitions/etsi_nfv_sol001_pnfd_types.yaml"),
					Entry.of("etsi_nfv_sol001_common_types.yaml", "Definitions/etsi_nfv_sol001_common_types.yaml"),
					Entry.of("ubi-nsd/TOSCA-Metadata/TOSCA.meta", "TOSCA-Metadata/TOSCA.meta"));
		} catch (final IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
