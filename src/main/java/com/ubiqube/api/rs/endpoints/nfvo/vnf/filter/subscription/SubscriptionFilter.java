package com.ubiqube.api.rs.endpoints.nfvo.vnf.filter.subscription;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.beanutils.BeanUtils;

import com.ubiqube.api.ejb.nfvo.vnf.SubscriptionObject;
import com.ubiqube.api.ejb.nfvo.vnf.SubscriptionsPkgmSubscription;
import com.ubiqube.api.rs.endpoints.nfvo.GenericException;
import com.ubiqube.etsi.grammar.EtsiLexer;
import com.ubiqube.etsi.grammar.Etsifilter;

/**
 * Handle filter parsing and filering on objects for VNFPackage Subscription.
 *
 * @author ovi@ubiqube.com
 *
 */
public class SubscriptionFilter {

	public List<Filter> getFilters(String query) {
		final FilterListner filterListner = new FilterListner();
		final EtsiLexer el = new EtsiLexer(new ANTLRInputStream(query));
		final CommonTokenStream tokens = new CommonTokenStream(el);
		final Etsifilter parser = new Etsifilter(tokens);
		parser.addParseListener(filterListner);
		parser.filterExpr();

		return filterListner.getFilters();
	}

	/**
	 * In fact this method could be more generic by taking an Object.
	 *
	 * @param subscriptionRepository
	 * @param filters
	 * @return
	 */
	public boolean apply(SubscriptionObject subscriptionRepository, List<Filter> filters) {
		final SubscriptionsPkgmSubscription pkgSub = subscriptionRepository.getSubscriptionsPkgmSubscription();
		for (final Filter filter : filters) {
			if (!apply(pkgSub, filter)) {
				return false;
			}
		}

		return true;
	}

	private boolean apply(SubscriptionsPkgmSubscription _pkgSub, Filter filter) {
		try {
			final String objectValue = BeanUtils.getNestedProperty(_pkgSub, filter.getAttr());
			return decide(objectValue, filter.getValue(), filter.getOp());
		} catch (final Exception e) {
			throw new GenericException(e);
		}
	}

	private boolean decide(String _objectValue, String _value, String _op) {
		if ("eq".equals(_value)) {
			return _value.equals(_objectValue);
		}
		if ("neq".equals(_value)) {
			return !_value.equals(_objectValue);
		}
		// GT LT GTE LTE are numerical so cast everything in integer
		final int left = Integer.parseInt(_objectValue);
		final int right = Integer.parseInt(_value);
		if ("gt".equals(_value)) {
			return left > right;
		}
		if ("lt".equals(_value)) {
			return left < right;
		}
		if ("gte".equals(_value)) {
			return left >= right;
		}
		if ("lte".equals(_value)) {
			return left <= right;
		}
		return false;
	}
}
