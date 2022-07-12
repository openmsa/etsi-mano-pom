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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ubiqube.parser.tosca.api.ContextResolver;
import com.ubiqube.parser.tosca.api.OrikaMapper;
import com.ubiqube.parser.tosca.api.ToscaApi;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.VNF;
import com.ubiqube.parser.tosca.scalar.Version;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 *
 * @author olivier
 *
 */
class MappingClassTest {
	private final static String JAR_PATH = "/tosca-class-%s-2.0.0-SNAPSHOT.jar";

	@Test
	void testName() throws Exception {
		System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
		System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES_TO_PATH, "/tmp/orika-tosca");
		final ToscaParser tp = new ToscaParser(new File("src/test/resources/ubi-tosca/Definitions/tosca_ubi.yaml"));
		final ToscaContext root = tp.getContext();
		assertNotNull(root);
		Version v = getVersion(root.getMetadata());
		v = new Version("3.6.1");
		final String jarPath = String.format(JAR_PATH, toJarVersions(v));
		System.out.println("" + jarPath);
		final URL cls = this.getClass().getResource(jarPath);
		if (null == cls) {
			throw new ParseException("Unable to find " + jarPath);
		}
		final URLClassLoader inst = URLClassLoader.newInstance(new URL[] { cls });
		final Class<?> clz = inst.loadClass("tosca.nodes.nfv.VNF");
		assertNotNull(clz);
		final ContextResolver ctx = new ContextResolver(root, new HashMap<String, String>());
		final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		configureMapper(inst, mapperFactory);
		mapperFactory.getConverterFactory().registerConverter(new OrikaTimeConverter());
		final ToscaApi toscaApi = new ToscaApi(this.getClass().getClassLoader(), mapperFactory.getMapperFacade());
		final List<VNF> objs = (List<VNF>) toscaApi.getObjects(root, Map.of(), clz);

		final List<VNF> fin = mapperFactory.getMapperFacade().mapAsList(objs, VNF.class);
		assertNotNull(fin);
		assertEquals(1, fin.size());
		final VNF vnf = fin.get(0);
		assertNotNull(vnf);
	}

	private void configureMapper(final URLClassLoader inst, final MapperFactory mapperFactory) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// com.ubiqube.etsi.mano.sol001.OrikaMapperImpl
		final Class<?> clz = inst.loadClass("com.ubiqube.etsi.mano.sol001.OrikaMapperImpl");
		final OrikaMapper ins = (OrikaMapper) clz.getDeclaredConstructor().newInstance();
		ins.configureMapper(mapperFactory);
	}

	private Object toJarVersions(final Version v) {
		return v.toString().replace(".", "");
	}

	private Version getVersion(final Map<String, String> metadata) {
		final String author = metadata.get("template_author");
		if (null == author) {
			return new Version("2.5.1");
		}
		return Optional.ofNullable(metadata.get("template_version"))
				.map(Version::new)
				.orElseGet(() -> new Version("2.5.1"));
	}
}
