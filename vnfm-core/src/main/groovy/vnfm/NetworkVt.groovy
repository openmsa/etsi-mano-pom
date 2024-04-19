type: "network"
dependsOn: "dns-zone"
scalable: false

dependencies {
	me = VnfVl
	from me dependsOn "dns-zone" oneToMany
	from me haveManyChildren "sub-network" as VlProtocolData oneToMany
}
