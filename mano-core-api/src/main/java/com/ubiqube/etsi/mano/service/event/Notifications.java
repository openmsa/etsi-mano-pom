/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.event;

import java.net.URI;

import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

/**
 * This class handle the notification callback.
 * <ul>
 * <li>Building the HTTP client.
 * <li>Crafting the request.
 * <li>Sending the request.
 * <li>Interpreting the result.
 * </ul>
 * This class should be compatible with Basic, OAuth2, TLS CERT
 * authentification. One the possiblities for OAuth authentification is group:
 * 'net.oauth.core', name: 'oauth-httpclient4', version: '20090913' you may also
 * need this: http://www.codedq.net/blog/articles/146.html
 *
 * @author ovi@ubiqube.com
 *
 */
public interface Notifications {

	/**
	 * Send a notification Object to the _uri
	 *
	 * @param obj     The JSON Onject.
	 * @param uri     The complete URL.
	 * @param server  A Servers object.
	 * @param version Server HTTP header version.
	 */
	void doNotification(final Object obj, final URI uri, final ServerAdapter server, String version);

	/**
	 *
	 * @param uri     The complete URL.
	 * @param server  A Servers object.
	 * @param version Server HTTP header version.
	 */
	void check(ServerAdapter server, final URI uri, String version);

	/**
	 *
	 * @param authentication Authentication informations.
	 * @param callbackUri    Callback URI.
	 * @param version        Server HTTP header version.
	 */
	void check(AuthentificationInformations authentication, URI callbackUri, String version);

}
