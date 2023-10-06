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
package com.ubiqube.etsi.mano.service.sol009.pkg;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.sol009.entity.SupportedPackageFormats;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.VnfdFormatEnum;
import com.ubiqube.etsi.mano.exception.GenericException;

/**
 * This class have to be moved to package parser, it talks about package
 * discovery.
 *
 * @author Olivier Vignaud
 */
@Service
public class ToscaVersionManager {
	public List<SupportedPackageFormats> extractAndSetPackageFormat() {
		final List<SupportedPackageFormats> ret = new ArrayList<>();
		final PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
		try {
			final Resource[] res = pmrpr.getResources("classpath:tosca-class-*");
			for (final Resource resource : res) {
				try (final URLClassLoader urlLoader = URLClassLoader.newInstance(new URL[] { resource.getURL() }, this.getClass().getClassLoader());
						final InputStream stream = urlLoader.getResourceAsStream("META-INF/tosca-resources.properties")) {
					final Properties props = new Properties();
					props.load(stream);
					final SupportedPackageFormats spf = new SupportedPackageFormats();
					spf.setStandardVersion(props.getProperty("version"));
					spf.setVnfdFormat(VnfdFormatEnum.TOSCA);
					ret.add(spf);
				}
			}
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ret;
	}

}
