type: "security-group"
dependencies {
	me = SecurityGroup
	from me haveMany SecurityGroup
}

