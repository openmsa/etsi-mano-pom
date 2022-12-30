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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import com.ubiqube.etsi.mano.service.pkg.tosca.Frequency2Converter;
import com.ubiqube.etsi.mano.service.pkg.tosca.Range2Converter;
import com.ubiqube.etsi.mano.service.pkg.tosca.Size2Converter;
import com.ubiqube.etsi.mano.service.pkg.tosca.Time2Converter;
import com.ubiqube.parser.tosca.api.OrikaMapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;

public class Utils {

	private Utils() {
		// Nothing.
	}

	public static MapperFactory createMapperFactory() {
		System.setProperty(OrikaSystemProperties.COMPILER_STRATEGY, EclipseJdtCompilerStrategy.class.getName());
		System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
		System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES_TO_PATH, "/tmp/orika-tests");
		final DefaultMapperFactory mapper = new DefaultMapperFactory.Builder().build();
		final ConverterFactory conv = mapper.getConverterFactory();
		conv.registerConverter(new Size2Converter());
		conv.registerConverter(new Frequency2Converter());
		conv.registerConverter(new Time2Converter());
		conv.registerConverter(new Range2Converter());
		registerMapper(mapper);
		return mapper;
	}

	private static void registerMapper(final MapperFactory mapper) {
		final ClassLoader urlLoader = Utils.class.getClassLoader();
		try (InputStream is = urlLoader.getResourceAsStream("META-INF/tosca-resources.properties")) {
			final Properties props = new Properties();
			props.load(is);
			final Class<?> clz = urlLoader.loadClass(props.getProperty("mapper"));
			final OrikaMapper m = (OrikaMapper) clz.getDeclaredConstructor().newInstance();
			m.configureMapper(mapper);
		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IOException e) {
			throw new ParseException(e);
		}
	}

}
