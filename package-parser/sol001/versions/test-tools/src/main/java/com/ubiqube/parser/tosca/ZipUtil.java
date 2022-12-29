/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.parser.tosca;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	private ZipUtil() {
		// Nothing.
	}

	public static void makeToscaZip(final String dest, final Entry... toscaFile) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(dest)) {
			try (ZipOutputStream zipOut = new ZipOutputStream(fos)) {
				for (final Entry srcFile : toscaFile) {
					try (InputStream is = ZipUtil.class.getClassLoader().getResourceAsStream(srcFile.classPath)) {
						if (null == is) {
							throw new IllegalArgumentException("Could not open " + srcFile.classPath);
						}
						final ZipEntry zipEntry = new ZipEntry(srcFile.zipName);
						zipOut.putNextEntry(zipEntry);

						final byte[] bytes = new byte[1024];
						int length;
						while ((length = is.read(bytes)) >= 0) {
							zipOut.write(bytes, 0, length);
						}
					}
				}
			}
		}
	}

	public static class Entry {
		public static Entry of(final String path, final String zip) {
			final Entry e = new Entry();
			e.classPath = path;
			e.zipName = zip;
			return e;
		}

		public String classPath;
		public String zipName;
	}
}
