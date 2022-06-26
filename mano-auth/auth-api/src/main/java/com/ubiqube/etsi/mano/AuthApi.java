package com.ubiqube.etsi.mano;

/**
 *
 * @author olivier
 *
 */
public interface AuthApi {

	ServerCredentials createServerCredential(String serverName);

	void revokeServer(String serverName);
}
