package com.ubiqube.etsi.mano.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ubiqube.etsi.mano.json.ViewHolder;
import com.ubiqube.etsi.mano.json.WantedSerializer;
import com.ubiqube.etsi.mano.model.vnf.sol005.VnfPkgInfo;

public class WantedSerializerTest {

	@Test
	void testName() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final VnfPkgInfo vnfPkgInfo = mapper.readValue(new FileInputStream("src/test/resources/VnfPkgInfo.json"), VnfPkgInfo.class);

		final List<String> list = new ArrayList<>();
		list.add("id");
		list.add("_links.self.href");
		list.add("invalid.expr");
		mapper.registerModule(new SimpleModule() {

			@Override
			public void setupModule(SetupContext context) {
				super.setupModule(context);
				context.addBeanSerializerModifier(new WantedSerializer(list));
			}
		});

		final String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(vnfPkgInfo);
		// Check by re-reading the produced json.
		JsonNode node = mapper.readTree(content);
		assertNull(node.findValue("nsdOnboardingState"), "nsdOnboardingState should be null.");
		assertNotNull(node.findValue("id"), "Id should not be null.");
		node = node.findValue("_links");
		assertNotNull(node, "_links is null.");
		node = node.findValue("self");
		assertNotNull(node, "self is null.");
		node = node.findValue("href");
		assertNotNull(node, "href is null.");
	}

	private static List<ViewHolder> explodeExpression(List<String> list) {
		final List<ViewHolder> ret = new ArrayList<>();
		for (final String expression : list) {
			final ViewHolder viewHolder = new ViewHolder(expression);
			ret.add(viewHolder);
		}
		return ret;
	}
}
