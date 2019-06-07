package com.ubiqube.api.rs.endpoints.nfvo.patch;

import javax.ejb.Local;

/**
 *
 * @author ovi@ubiqube.com
 *
 */
@Local
public interface Patcher {

	void patch(String _patchDocument, Object _entity);
}
