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
package com.ubiqube.etsi.mano.config;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.mapper.BeanWalker;
import com.ubiqube.etsi.mano.mapper.JsonWalker;
import com.ubiqube.etsi.mano.mapper.SpelWriter;
import com.ubiqube.etsi.mano.service.cond.ConditionService;

/**
 *
 * @author olivier
 *
 */
@SuppressWarnings("static-method")
@Configuration
public class GlobalConfiguraton {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalConfiguraton.class);

	@Bean
	HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
		LOG.debug("Registering BufferedImage converter");
		return new BufferedImageHttpMessageConverter();
	}

	@Bean
	ConditionService conditionService() {
		return new ConditionService();
	}

	@Bean
	JsonWalker jsonWalker(final ObjectMapper mapper) {
		return new JsonWalker(mapper);
	}

	@Bean
	BeanWalker beanWalker() {
		return new BeanWalker();
	}

	@Bean
	SpelWriter spelWriter() {
		return new SpelWriter();
	}
}
