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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.opp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingPostProcessor;

@SuppressWarnings("static-method")
class VerifyClassifierTest {

	@Test
	void test_Minimal() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		nsPkg.setVnffgs(Set.of());
		proc.visit(nsPkg);
		assertTrue(true);
	}

	@Test
	void test_NullPass() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final Classifier cls = new Classifier();
		vnffg.setClassifier(cls);
		nsPkg.setVnffgs(Set.of(vnffg));
		proc.visit(nsPkg);
		assertTrue(true);
	}

	@Test
	void test_BadClassifier001() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final Classifier cls = new Classifier();
		cls.setDestinationPortRangeMax(123L);
		cls.setDestinationPortRangeMin(123L);
		cls.setSourcePortRangeMax(123L);
		cls.setSourcePortRangeMin(123L);
		vnffg.setClassifier(cls);
		nsPkg.setVnffgs(Set.of(vnffg));
		assertThrows(GenericException.class, () -> proc.visit(nsPkg));
	}

	@Test
	void test_BadClassifier002() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final Classifier cls = new Classifier();
		cls.setDestinationPortRangeMin(123L);
		cls.setSourcePortRangeMax(123L);
		cls.setSourcePortRangeMin(123L);
		vnffg.setClassifier(cls);
		nsPkg.setVnffgs(Set.of(vnffg));
		assertThrows(GenericException.class, () -> proc.visit(nsPkg));
	}

	@Test
	void test_BadClassifier003() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final Classifier cls = new Classifier();
		cls.setSourcePortRangeMax(123L);
		cls.setSourcePortRangeMin(123L);
		vnffg.setClassifier(cls);
		nsPkg.setVnffgs(Set.of(vnffg));
		assertThrows(GenericException.class, () -> proc.visit(nsPkg));
	}

	@Test
	void test_BadClassifier004() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final Classifier cls = new Classifier();
		cls.setSourcePortRangeMax(123L);
		cls.setSourcePortRangeMin(123L);
		vnffg.setClassifier(cls);
		nsPkg.setVnffgs(Set.of(vnffg));
		assertThrows(GenericException.class, () -> proc.visit(nsPkg));
	}

	@Test
	void test_BadClassifier005() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final Classifier cls = new Classifier();
		cls.setSourcePortRangeMin(123L);
		vnffg.setClassifier(cls);
		nsPkg.setVnffgs(Set.of(vnffg));
		assertThrows(GenericException.class, () -> proc.visit(nsPkg));
	}

	@Test
	void test_GoodClassifier() {
		final NsOnboardingPostProcessor proc = new VerifyClassifier();
		final NsdPackage nsPkg = new NsdPackage();
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final Classifier cls = new Classifier();
		cls.setSourcePortRangeMin(123L);
		cls.setProtocol("");
		vnffg.setClassifier(cls);
		nsPkg.setVnffgs(Set.of(vnffg));
		proc.visit(nsPkg);
		assertTrue(true);
	}

}
