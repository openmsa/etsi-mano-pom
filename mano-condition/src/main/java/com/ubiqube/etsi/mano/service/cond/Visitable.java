package com.ubiqube.etsi.mano.service.cond;

public interface Visitable {

	<R, A> R accept(Visitor<R, A> v, A arg);
}
