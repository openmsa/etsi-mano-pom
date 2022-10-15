package com.ubiqube.etsi.mano.dao.mano.v2;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;

import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditListener.class)
@Getter
@Setter
public class VnfIndicatorTask extends VnfTask {

	private String name;

	@ManyToOne
	private VnfIndicator vnfIndicator;

	@Override
	public VnfTask copy() {
		final VnfIndicatorTask t = new VnfIndicatorTask();
		super.copy(t);
		t.setName(name);
		t.setVnfIndicator(vnfIndicator);
		return t;
	}

}
