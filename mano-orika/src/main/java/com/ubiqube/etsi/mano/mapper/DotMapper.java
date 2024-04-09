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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.mapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;

import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;

public class DotMapper {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(DotMapper.class);

	private final BeanWalker beanWalker;

	public DotMapper() {
		beanWalker = new BeanWalker();
	}

	public List<FilterAttributes> objectToAttr(final Object source) {
		LOG.info("A to B");
		final CollectNonNullListener beanListener = new CollectNonNullListener();
		beanWalker.walk(source, beanListener);
		final List<AttrHolder> attrs = beanListener.getAttrs();
		final SpelWriter sw = new SpelWriter();
		return sw.getFilterAttrs(attrs);
	}

	public Object AttrToObject(final List<FilterAttributes> source, final Object ret) {
		LOG.info("B to A => ");
		// Auto create objects if null.
		final SpelParserConfiguration config = new SpelParserConfiguration(true, true);
		final ExpressionParser parser = new SpelExpressionParser(config);
		final StandardEvaluationContext modelContext = getModelContext(ret);
		LOG.debug("Setting on entity type: {}", ret.getClass());
		source.forEach(x -> {
			LOG.debug(" - Setting: {}", x.getAttribute());
			parser.parseExpression(x.getAttribute()).setValue(modelContext, x.getValue());
		});
		return ret;
	}

	private static StandardEvaluationContext getModelContext(final Object ret) {
		final StandardEvaluationContext modelContext = new StandardEvaluationContext(ret);
		final DefaultConversionService conversionService = new DefaultConversionService();
		conversionService.addConverterFactory(new ManoLenientStringToEnum());
		final TypeConverter typeConverter = new StandardTypeConverter(conversionService);
		modelContext.setTypeConverter(typeConverter);
		return modelContext;
	}

}
