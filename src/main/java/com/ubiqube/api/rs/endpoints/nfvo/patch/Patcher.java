package com.ubiqube.api.rs.endpoints.nfvo.patch;

/**
 *
 * @author ovi@ubiqube.com
 *
 */
public interface Patcher {

	void patch(String _patchDocument, Object _entity);
}
