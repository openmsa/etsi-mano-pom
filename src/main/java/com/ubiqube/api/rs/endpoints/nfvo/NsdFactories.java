package com.ubiqube.api.rs.endpoints.nfvo;

import java.util.ArrayList;

import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfo;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfo.NsdOnboardingStateEnum;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfo.NsdOperationalStateEnum;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfo.NsdUsageStateEnum;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfoIdGetResponse;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfoLinks;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfoLinksSelf;

public class NsdFactories {

	private NsdFactories() {
		// Nothing.
	}

	public static NsDescriptorsNsdInfoIdGetResponse createNsDescriptorsNsdInfoIdGetResponse(String _id, String _self, String _nsdContent) {
		final NsDescriptorsNsdInfoIdGetResponse ret = new NsDescriptorsNsdInfoIdGetResponse();
		final NsDescriptorsNsdInfo nsdInfo = new NsDescriptorsNsdInfo();
		nsdInfo.setLinks(createNsDescriptorsNsdInfoLinks(_self, _nsdContent));
		nsdInfo.setNestedNsdInfoIds(new ArrayList<String>());
		nsdInfo.setNsdOnboardingState(NsdOnboardingStateEnum.CREATED);
		nsdInfo.setNsdOperationalState(NsdOperationalStateEnum.ENABLED);
		nsdInfo.setNsdUsageState(NsdUsageStateEnum.NOT_IN_USE);
		nsdInfo.setPnfdInfoIds(new ArrayList<String>());
		nsdInfo.setVnfPkgIds(new ArrayList<String>());
		nsdInfo.setId(_id);
		ret.setNsdInfo(nsdInfo);
		return ret;
	}

	public static NsDescriptorsNsdInfoLinks createNsDescriptorsNsdInfoLinks(String _self, String _nsdContent) {
		final NsDescriptorsNsdInfoLinks ret = new NsDescriptorsNsdInfoLinks();
		final NsDescriptorsNsdInfoLinksSelf nsdContent = new NsDescriptorsNsdInfoLinksSelf();
		nsdContent.setHref(_nsdContent);
		ret.setNsdContent(nsdContent);
		final NsDescriptorsNsdInfoLinksSelf self = new NsDescriptorsNsdInfoLinksSelf();
		self.setHref(_self);
		ret.setSelf(self);

		return ret;
	}

	public static NsDescriptorsNsdInfo createNsDescriptorsNsdInfo(String _id, String _self, String _nsdContent) {
		final NsDescriptorsNsdInfo nsdInfo = new NsDescriptorsNsdInfo();
		nsdInfo.setLinks(createNsDescriptorsNsdInfoLinks(_self, _nsdContent));
		nsdInfo.setNestedNsdInfoIds(new ArrayList<String>());
		nsdInfo.setNsdOnboardingState(NsdOnboardingStateEnum.CREATED);
		nsdInfo.setNsdOperationalState(NsdOperationalStateEnum.ENABLED);
		nsdInfo.setNsdUsageState(NsdUsageStateEnum.NOT_IN_USE);
		nsdInfo.setPnfdInfoIds(new ArrayList<String>());
		nsdInfo.setVnfPkgIds(new ArrayList<String>());
		nsdInfo.setId(_id);
		return nsdInfo;
	}

}
