/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.ubiqube.etsi.mano.dao.mano.OAuth2GrantType;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "mano.helmv3")
public class HelmWrapperProperty {

	private String url;

	private OAuth2 oauth2;

	@Data
	public static class OAuth2 {
		private String clientId;

		private String clientSecret;

		private OAuth2GrantType grantType = OAuth2GrantType.CLIENT_CREDENTIAL;

		private List<String> scope;

		private String tokenEndpoint;
	}
}
