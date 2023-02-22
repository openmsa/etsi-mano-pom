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
package com.ubiqube.etsi.mano.em.controller.vnfind;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.vnfm.fc.vnfind.Sol002VnfIndSubscriptionsFrontController;

@Service
public class Sol002VnfIndSubscriptionsFrontControllerImpl implements Sol002VnfIndSubscriptionsFrontController {

	@Override
	public <U> ResponseEntity<List<U>> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final Consumer<U> makeLink) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> create(final Object vnfIndicatorSubscriptionRequest, final Class<U> clazz, final Class<?> versionController, final Consumer<U> makeLink, final Function<U, String> getSelfLink) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Class<U> clazz, final Consumer<U> makeLink) {
		// TODO Auto-generated method stub
		return null;
	}

}
