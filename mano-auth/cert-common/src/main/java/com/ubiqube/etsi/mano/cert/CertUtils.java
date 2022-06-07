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
package com.ubiqube.etsi.mano.cert;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.ubiqube.etsi.mano.AuthException;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public final class CertUtils {

	private CertUtils() {
		// Nothing.
	}

	public static UserDetails createUserDetails(final X500Name name) {
		final RDN[] oid = name.getRDNs(BCStyle.O);
		final Collection<? extends GrantedAuthority> roles = toRoles(oid);
		final RDN[] cns = name.getRDNs(BCStyle.CN);
		final String cn = getCn(cns);
		return new User(cn, "********", roles);
	}

	private static String getCn(final RDN[] cns) {
		if (cns.length > 1) {
			throw new AuthException("Multiple CN detected.");
		}
		return IETFUtils.valueToString(cns[0].getFirst().getValue());
	}

	private static List<SimpleGrantedAuthority> toRoles(final RDN[] oid) {
		return Arrays.stream(oid)
				.map(x -> IETFUtils.valueToString(x.getFirst().getValue()))
				.flatMap(x -> Arrays.stream(x.split("\\\\,")))
				.map(x -> "ROLE_" + x)
				.map(SimpleGrantedAuthority::new)
				.toList();
	}

}
