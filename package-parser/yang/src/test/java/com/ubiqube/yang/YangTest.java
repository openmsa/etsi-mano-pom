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
package com.ubiqube.yang;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamReader;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.jupiter.api.Test;
import org.opendaylight.mdsal.binding.api.DataBroker;
import org.opendaylight.mdsal.binding.api.WriteTransaction;
import org.opendaylight.mdsal.binding.dom.adapter.BindingAdapterFactory;
import org.opendaylight.mdsal.binding.dom.adapter.ConstantAdapterContext;
import org.opendaylight.mdsal.binding.dom.adapter.CurrentAdapterSerializer;
import org.opendaylight.mdsal.binding.dom.codec.api.BindingIdentityCodec;
import org.opendaylight.mdsal.binding.dom.codec.api.BindingInstanceIdentifierCodec;
import org.opendaylight.mdsal.binding.dom.codec.impl.BindingCodecContext;
import org.opendaylight.mdsal.binding.generator.impl.DefaultBindingGenerator;
import org.opendaylight.mdsal.binding.java.api.generator.JavaFileGeneratorFactory;
import org.opendaylight.mdsal.binding.java.api.generator.YangModuleInfoTemplate;
import org.opendaylight.mdsal.binding.model.api.GeneratedType;
import org.opendaylight.mdsal.binding.model.api.TypeComment;
import org.opendaylight.mdsal.binding.runtime.api.BindingRuntimeContext;
import org.opendaylight.mdsal.binding.runtime.spi.BindingRuntimeHelpers;
import org.opendaylight.mdsal.binding.spec.naming.BindingMapping;
import org.opendaylight.mdsal.common.api.LogicalDatastoreType;
import org.opendaylight.mdsal.dom.api.DOMDataBroker;
import org.opendaylight.mdsal.dom.api.DOMDataTreeReadTransaction;
import org.opendaylight.mdsal.dom.broker.SerializedDOMDataBroker;
import org.opendaylight.mdsal.dom.spi.store.DOMStore;
import org.opendaylight.mdsal.dom.store.inmemory.InMemoryDOMDataStore;
import org.opendaylight.yangtools.plugin.generator.api.FileGenerator;
import org.opendaylight.yangtools.plugin.generator.api.FileGeneratorException;
import org.opendaylight.yangtools.plugin.generator.api.GeneratedFile;
import org.opendaylight.yangtools.plugin.generator.api.GeneratedFilePath;
import org.opendaylight.yangtools.util.xml.UntrustedXML;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.common.Revision;
import org.opendaylight.yangtools.yang.common.YangConstants;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier;
import org.opendaylight.yangtools.yang.data.api.schema.ContainerNode;
import org.opendaylight.yangtools.yang.data.api.schema.NormalizedNode;
import org.opendaylight.yangtools.yang.data.api.schema.stream.NormalizedNodeStreamWriter;
import org.opendaylight.yangtools.yang.data.codec.xml.XmlParserStream;
import org.opendaylight.yangtools.yang.data.impl.schema.ImmutableNormalizedNodeStreamWriter;
import org.opendaylight.yangtools.yang.data.impl.schema.NormalizedNodeResult;
import org.opendaylight.yangtools.yang.model.api.EffectiveModelContext;
import org.opendaylight.yangtools.yang.model.api.Module;
import org.opendaylight.yangtools.yang.model.repo.api.SchemaSourceRepresentation;
import org.opendaylight.yangtools.yang.model.repo.api.YangTextSchemaSource;
import org.opendaylight.yangtools.yang.model.util.SchemaInferenceStack.Inference;
import org.opendaylight.yangtools.yang.parser.api.YangParser;
import org.opendaylight.yangtools.yang.parser.api.YangParserConfiguration;
import org.opendaylight.yangtools.yang.parser.api.YangParserException;
import org.opendaylight.yangtools.yang.parser.api.YangParserFactory;
import org.opendaylight.yangtools.yang.parser.api.YangSyntaxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.ubiqube.parser.tosca.generator.JavaWalker;
import com.ubiqube.parser.tosca.generator.YangListener;

public class YangTest {

	private static final Logger LOG = LoggerFactory.getLogger(YangTest.class);

	private static final @NonNull YangParserFactory PARSER_FACTORY;

	static {
		final Iterator<@NonNull YangParserFactory> it = ServiceLoader.load(YangParserFactory.class).iterator();
		if (!it.hasNext()) {
			throw new IllegalStateException("No YangParserFactory found");
		}
		PARSER_FACTORY = it.next();
	}

	public static EffectiveModelContext parseSources(final YangParserConfiguration config,
			final Set<QName> supportedFeatures, final Collection<? extends SchemaSourceRepresentation> sources) {
		final YangParser parser = PARSER_FACTORY.createParser(config);
		if (supportedFeatures != null) {
			parser.setSupportedFeatures(supportedFeatures);
		}

		try {
			parser.addSources(sources);
		} catch (final YangSyntaxErrorException e) {
			throw new IllegalArgumentException("Malformed source", e);
		} catch (final IOException e) {
			throw new IllegalArgumentException("Failed to read a source", e);
		}

		try {
			return parser.buildEffectiveModel();
		} catch (final YangParserException e) {
			throw new IllegalStateException("Failed to assemble SchemaContext", e);
		}
	}

	protected static final void generateTestSources(final EffectiveModelContext context, final List<GeneratedType> types, final File sourcesOutputDir)
			throws IOException, FileGeneratorException {
		types.sort(Comparator.comparing(GeneratedType::getName).reversed());
		final JavaFileGeneratorFactory javaGeneratorFactory = new JavaFileGeneratorFactory();
		final FileGenerator java = javaGeneratorFactory.newFileGenerator(new HashMap<>());
		final Table<?, GeneratedFilePath, GeneratedFile> generatedFiles = java.generateFiles(context, Set.copyOf(context.getModules()),
				(module, representation) -> Optional.of("src/main/yang" + File.separator + module.getName()
						+ YangConstants.RFC6020_YANG_FILE_EXTENSION));
		for (final Cell<?, GeneratedFilePath, GeneratedFile> cell : generatedFiles.cellSet()) {
			final File target = new File(sourcesOutputDir, cell.getColumnKey().getPath());
			Files.createParentDirs(target);

			try (OutputStream os = new FileOutputStream(target)) {
				cell.getValue().writeBody(os);
			}
		}
	}

	protected static final List<GeneratedType> generateTestSources(final String resourceDirPath,
			final File sourcesOutputDir) throws IOException, FileGeneratorException {
		System.out.println("rd=" + resourceDirPath + "  " + new File(resourceDirPath).list());
		final List<File> sourceFiles = Arrays.asList(new File(resourceDirPath).list()).stream().map(x -> new File(new File(resourceDirPath), x)).collect(Collectors.toList());
		final EffectiveModelContext context = parseSources(YangParserConfiguration.DEFAULT, null, sourceFiles.stream().map(YangTextSchemaSource::forFile).collect(Collectors.toList()));
		final List<GeneratedType> types = new DefaultBindingGenerator().generateTypes(context);
		generateTestSources(context, types, sourcesOutputDir);
		buildSources(context, types, sourcesOutputDir, new JavaWalker(sourcesOutputDir.toString()));

		// Also generate YangModuleInfo
		for (final Module module : context.getModules()) {
			final YangModuleInfoTemplate template = new YangModuleInfoTemplate(module, context,
					mod -> Optional.of("/" + mod.getName() + ".yang"));

			final File file = new File(new File(sourcesOutputDir,
					BindingMapping.getRootPackageName(module.getQNameModule()).replace('.', File.separatorChar)),
					BindingMapping.MODULE_INFO_CLASS_NAME + ".java");
			Files.createParentDirs(file);
			Files.asCharSink(file, StandardCharsets.UTF_8).write(template.generate());
		}

		return types;
	}

	private static void buildSources(final EffectiveModelContext context, final List<GeneratedType> types, final File sourcesOutputDir, final YangListener listener) {
		types.sort(Comparator.comparing(GeneratedType::getName).reversed());
		listener.startDocument();
		for (final GeneratedType generatedType : types) {
			if (generatedType.isAbstract()) {
				continue;
			}
			System.out.println(generatedType.getIdentifier() + " => " + generatedType.getClass());
			if (generatedType.getEnumerations().isEmpty()) {
				handleType(generatedType, listener);
			} else {
				handleEnum(generatedType, listener);
			}
			// System.out.println("" + generatedType.getIdentifier() + " => " +
			// generatedType.isAbstract() + " (" + generatedType.getClass() + ")");
			// generatedType.getProperties().forEach(x -> {
			// System.out.println(" " + x.getName() + "=" + x.getValue());
			// });
			// generatedType.getMethodDefinitions().forEach(x -> {
			// System.out.println(" m: " + x.getName() + "(" + x.getParameters() + ") ");
			// });
		}
		listener.terminateDocument();
	}

	private static void handleEnum(final GeneratedType generatedType, final YangListener listener) {
		listener.startEnum(generatedType.getIdentifier().toString());
		listener.addEnum(generatedType.getEnumerations());
		listener.endEnum();
	}

	private static void handleType(final GeneratedType generatedType, final YangListener listener) {
		LOG.debug("Starting : " + generatedType.getIdentifier() + " => " + generatedType.getClass());
		final String comment = Optional.ofNullable(generatedType.getComment()).map(TypeComment::toString).orElse(null);
		listener.startClass(generatedType.getIdentifier().toString(), comment);
		generatedType.getMethodDefinitions().forEach(x -> {
			listener.startField(x.getName(), x.getReturnType());
			listener.onFieldTerminate();
		});
	}

	@Test
	void testGenerator() throws Exception {
		generateTestSources("src/main/yang/", new File("src/main/gen2"));
	}

	@Test
	void testgetResource() throws Exception {
		final InputStream res = getClass().getResourceAsStream("/etsi-nfv-common.yang");
		assertNotNull(res);
	}

	@Test
	void testBinding() throws Exception {
		final YangInstanceIdentifier id = null;// YangInstanceIdentifier.builder().node(Vnfd.QNAME).build();

		final CurrentAdapterSerializer codec = new CurrentAdapterSerializer(new BindingCodecContext(
				BindingRuntimeHelpers.createRuntimeContext()));
		final BindingAdapterFactory binding = new BindingAdapterFactory(new ConstantAdapterContext(new BindingCodecContext(codec.getRuntimeContext())));
		final ListeningExecutorService executor = MoreExecutors.newDirectExecutorService();
		final InMemoryDOMDataStore dataStore = new InMemoryDOMDataStore("OPER", MoreExecutors.newDirectExecutorService());
		dataStore.onModelContextUpdated(codec.getRuntimeContext().getEffectiveModelContext());
		final InMemoryDOMDataStore dataStoreCfg = new InMemoryDOMDataStore("CFG", MoreExecutors.newDirectExecutorService());
		dataStoreCfg.onModelContextUpdated(codec.getRuntimeContext().getEffectiveModelContext());
		final Map<LogicalDatastoreType, DOMStore> datastores = new HashMap<>();// Map.of(LogicalDatastoreType.OPERATIONAL, dataStore,
																				// LogicalDatastoreType.CONFIGURATION, dataStoreCfg);
		final DOMDataBroker domService = new SerializedDOMDataBroker(datastores, executor);
		final DataBroker broker = binding.createDataBroker(domService);
		final WriteTransaction wx2 = broker.newWriteOnlyTransaction();
		// wx2.put(LogicalDatastoreType.CONFIGURATION, id, data);
		final DOMDataTreeReadTransaction rx = domService.newReadOnlyTransaction();

		final FluentFuture<Optional<NormalizedNode>> result = rx.read(LogicalDatastoreType.CONFIGURATION, id);
		final NormalizedNode n = result.get().get();
	}

	@Test
	void testCodec() throws Exception {
		final CurrentAdapterSerializer codec = new CurrentAdapterSerializer(new BindingCodecContext(
				BindingRuntimeHelpers.createRuntimeContext()));
		final InputStream resourceAsStream = getClass().getResourceAsStream("/complex-vnfd.xml");
		final XMLStreamReader reader = UntrustedXML.createXMLStreamReader(resourceAsStream);
		final NormalizedNodeResult result = new NormalizedNodeResult();
		final NormalizedNodeStreamWriter streamWriter = ImmutableNormalizedNodeStreamWriter.from(result);
		final XmlParserStream xmlParser = XmlParserStream.create(streamWriter,
				Inference.ofDataTreePath(codec.getRuntimeContext().getEffectiveModelContext(), QName.create("urn:etsi:nfv:yang:etsi-nfv-descriptors", "nfv", Revision.of("2020-06-10"))), false);
		xmlParser.parse(reader);
		final NormalizedNode r = result.getResult();
		assertNotNull(r);
		final ContainerNode cn = (ContainerNode) r;
		final BindingIdentityCodec idc = codec.getIdentityCodec();
		final BindingRuntimeContext rc = codec.getRuntimeContext();
		final BindingInstanceIdentifierCodec iic = codec.getInstanceIdentifierCodec();
		System.out.println("" + cn.body().getClass() + " " + idc + rc + iic);
	}
}
