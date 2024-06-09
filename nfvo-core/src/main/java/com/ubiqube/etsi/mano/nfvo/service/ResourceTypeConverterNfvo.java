package com.ubiqube.etsi.mano.nfvo.service;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgLoadbalancerTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPostTask;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NetworkPolicyVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsCreateVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsExtratorVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsInstantiateVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVirtualLinkVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfCreateVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfExtractorVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfInstantiateVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnffgPortPairVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnffgPostVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.PortTupleVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.PtLinkVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.ServiceInstanceVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.ServiceTemplateVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.VnffgLoadbalancerVt;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PortTupleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PtLinkNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceTemplateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.NsdExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NetworkPolicyNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.PortPairNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgLoadbalancerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPostNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.ResourceTypeConverter;
import com.ubiqube.etsi.mano.tf.entities.NetworkPolicyTask;
import com.ubiqube.etsi.mano.tf.entities.PortTupleTask;
import com.ubiqube.etsi.mano.tf.entities.PtLinkTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceInstanceTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceTemplateTask;
import com.ubiqube.etsi.mano.vnfm.service.graph.ResourceHolder;

@Service
public class ResourceTypeConverterNfvo implements ResourceTypeConverter<NsTask> {
	private final Map<ResourceTypeEnum, Function<NsTask, VirtualTaskV3<? extends NsTask>>> vts;
	private final Set<ResourceHolder<NsTask>> set = new LinkedHashSet<>();
	private final Map<Class<? extends Node>, ResourceHolder<NsTask>> classToHolder;
	private final Map<ResourceTypeEnum, ResourceHolder<NsTask>> resourceToHolder;

	public ResourceTypeConverterNfvo() {
		set.add(new ResourceHolder(ResourceTypeEnum.VL, x -> new NsVirtualLinkVt((NsVirtualLinkTask) x), Network.class));
		set.add(new ResourceHolder(ResourceTypeEnum.VNFFG_LOADBALANCER, x -> new VnffgLoadbalancerVt((VnffgLoadbalancerTask) x), VnffgLoadbalancerNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.VNFFG_POST, x -> new NsVnffgPostVt((VnffgPostTask) x), VnffgPostNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.VNFFG_PORT_PAIR, x -> new NsVnffgPortPairVt((VnffgPortPairTask) x), PortPairNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.NSD_CREATE, x -> new NsCreateVt((NsdTask) x), NsdCreateNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.NSD_INSTANTIATE, x -> new NsInstantiateVt((NsdInstantiateTask) x), NsdInstantiateNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.NSD_EXTRACTOR, x -> new NsExtratorVt((NsdExtractorTask) x), NsdExtractorNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.VNF_CREATE, x -> new NsVnfCreateVt((NsVnfTask) x), VnfCreateNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.VNF_INSTANTIATE, x -> new NsVnfInstantiateVt((NsVnfInstantiateTask) x), VnfInstantiateNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.VNF_EXTRACTOR, x -> new NsVnfExtractorVt((NsVnfExtractorTask) x), VnfExtractorNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.TF_NETWORK_POLICY, x -> new NetworkPolicyVt((NetworkPolicyTask) x), NetworkPolicyNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.TF_PORT_TUPLE, x -> new PortTupleVt((PortTupleTask) x), PortTupleNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.TF_PT_LINK, x -> new PtLinkVt((PtLinkTask) x), PtLinkNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.TF_SERVICE_INSTANCE, x -> new ServiceInstanceVt((ServiceInstanceTask) x), ServiceInstanceNode.class));
		set.add(new ResourceHolder(ResourceTypeEnum.TF_SERVICE_TEMPLATE, x -> new ServiceTemplateVt((ServiceTemplateTask) x), ServiceTemplateNode.class));
		vts = set.stream()
				.collect(Collectors.toMap(ResourceHolder::res, ResourceHolder::createVt));
		classToHolder = set.stream()
				.collect(Collectors.toMap(ResourceHolder::node, x -> x));
		resourceToHolder = set.stream()
				.collect(Collectors.toMap(ResourceHolder::res, x -> x));
	}

	@Override
	public Function<NsTask, VirtualTaskV3<? extends NsTask>> toVt(final ResourceTypeEnum resource) {
		return vts.get(resource);
	}

	@Override
	public Optional<ResourceHolder<NsTask>> toResourceHolder(final ResourceTypeEnum resource) {
		return Optional.ofNullable(resourceToHolder.get(resource));
	}

}
