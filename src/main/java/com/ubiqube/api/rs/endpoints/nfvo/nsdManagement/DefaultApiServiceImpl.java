package com.ubiqube.api.rs.endpoints.nfvo.nsdManagement;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfoIdGetResponse;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsNsdInfoIdPatchQuery;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsDescriptorsPostQuery;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsdChangeNotification;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsdDeletionNotification;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsdOnBoardingFailureNotification;
import com.ubiqube.api.ejb.nfvo.nsdManagement.NsdOnBoardingNotification;
import com.ubiqube.api.ejb.nfvo.nsdManagement.PnfDescriptorsPnfdInfoIdGetResponse;
import com.ubiqube.api.ejb.nfvo.nsdManagement.PnfDescriptorsPnfdInfoIdPatchQuery;
import com.ubiqube.api.ejb.nfvo.nsdManagement.PnfDescriptorsPnfdInfoIdPatchResponse;
import com.ubiqube.api.ejb.nfvo.nsdManagement.PnfDescriptorsPostQuery;
import com.ubiqube.api.ejb.nfvo.nsdManagement.PnfdDeletionNotification;
import com.ubiqube.api.ejb.nfvo.nsdManagement.PnfdOnBoardingFailureNotification;
import com.ubiqube.api.ejb.nfvo.nsdManagement.PnfdOnBoardingNotification;
import com.ubiqube.api.ejb.nfvo.nsdManagement.SubscriptionsPostQuery;
import com.ubiqube.api.ejb.nfvo.nsdManagement.SubscriptionsPostResponse;

import io.swagger.annotations.Api;

/**
 * SOL005 - NSD Management Interface
 *
 * <p>
 * SOL005 - NSD Management Interface IMPORTANT: Please note that this file might
 * be not aligned to the current version of the ETSI Group Specification it
 * refers to and has not been approved by the ETSI NFV ISG. In case of
 * discrepancies the published ETSI Group Specification takes precedence. Please
 * report bugs to
 * https://forge.etsi.org/bugzilla/buglist.cgi?component=Nfv-Openapis
 *
 */
@Path("/nsd/v1")
@Api(value = "/", description = "")
public class DefaultApiServiceImpl implements DefaultApi {
	/**
	 * Query information about multiple NS descriptor resources.
	 *
	 * \&quot;The GET method queries information about multiple NS descriptor
	 * resources. This method shall follow the provisions specified in the Tables
	 * 5.4.2.3.2-1 and 5.4.2.3.2-2 for URI query parameters, request and response
	 * data structures, and response codes.\&quot;
	 *
	 */
	@Override
	public List<Object> nsDescriptorsGet(String accept, String filter, String allFields, String fields, String excludeFields, String excludeDefault, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Delete an individual NS descriptor resource.
	 *
	 * The DELETE method deletes an individual NS descriptor resource. An individual
	 * NS descriptor resource can only be deleted when there is no NS instance using
	 * it (i.e. usageState &#x3D; NOT_IN_USE) and has been disabled already (i.e.
	 * operationalState &#x3D; DISABLED). Otherwise, the DELETE method shall fail.
	 *
	 */
	@Override
	public void nsDescriptorsNsdInfoIdDelete(String nsdInfoId, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Read information about an individual NS descriptor resource.
	 *
	 * \&quot;The GET method reads information about an individual NS descriptor.
	 * This method shall follow the provisions specified in GS NFV-SOL 005 Tables
	 * 5.4.3.3.2-1 and 5.4.3.3.2-2 of GS NFV-SOL 005 for URI query parameters,
	 * request and response data structures, and response codes.\&quot;
	 *
	 */
	@Override
	public NsDescriptorsNsdInfoIdGetResponse nsDescriptorsNsdInfoIdGet(String nsdInfoId, String accept, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Fetch the content of a NSD.
	 *
	 * The GET method fetches the content of the NSD. The NSD can be implemented as
	 * a single file or as a collection of multiple files. If the NSD is implemented
	 * in the form of multiple files, a ZIP file embedding these files shall be
	 * returned. If the NSD is implemented as a single file, either that file or a
	 * ZIP file embedding that file shall be returned. The selection of the format
	 * is controlled by the \&quot;Accept\&quot; HTTP header passed in the GET
	 * request:• If the \&quot;Accept\&quot; header contains only
	 * \&quot;text/plain\&quot; and the NSD is implemented as a single file, the
	 * file shall be returned; otherwise, an error message shall be returned.• If
	 * the \&quot;Accept\&quot; header contains only \&quot;application/zip\&quot;,
	 * the single file or the multiple files that make up the NSD shall be returned
	 * embedded in a ZIP file.• If the \&quot;Accept\&quot; header contains both
	 * \&quot;text/plain\&quot; and \&quot;application/zip\&quot;, it is up to the
	 * NFVO to choose the format to return for a single-file NSD; for a multi-file
	 * NSD, a ZIP file shall be returned.NOTE: The structure of the NSD zip file is
	 * outside the scope of the present document.
	 *
	 */
	@Override
	public void nsDescriptorsNsdInfoIdNsdContentGet(String nsdInfoId, String accept, @Context SecurityContext securityContext, String range) {
		// TODO: Implement...

	}

	/**
	 * Upload the content of a NSD.
	 *
	 * \&quot;The PUT method is used to upload the content of a NSD. The NSD to be
	 * uploaded can be implemented as a single file or as a collection of multiple
	 * files, as defined in clause 5.4.4.3.2 of GS NFV-SOL 005. If the NSD is
	 * implemented in the form of multiple files, a ZIP file embedding these files
	 * shall be uploaded. If the NSD is implemented as a single file, either that
	 * file or a ZIP file embedding that file shall be uploaded. The
	 * \&quot;Content-Type\&quot; HTTP header in the PUT request shall be set
	 * accordingly based on the format selection of the NSD. If the NSD to be
	 * uploaded is a text file, the \&quot;Content-Type\&quot; header is set to
	 * \&quot;text/plain\&quot;. If the NSD to be uploaded is a zip file, the
	 * \&quot;Content-Type\&quot; header is set to \&quot;application/zip\&quot;.
	 * This method shall follow the provisions specified in the Tables 5.4.4.3.3-1
	 * and 5.4.4.3.3-2 of GS-NFV-SOL 005 for URI query parameters, request and
	 * response data structures, and response codes.\&quot;
	 *
	 */
	@Override
	public void nsDescriptorsNsdInfoIdNsdContentPut(String nsdInfoId, String accept, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Modify the operational state and/or the user defined data of an individual NS
	 * descriptor resource.
	 *
	 * The PATCH method modifies the operational state and/or user defined data of
	 * an individual NS descriptor resource. This method can be used to: 1) Enable a
	 * previously disabled individual NS descriptor resource, allowing again its use
	 * for instantiation of new network service with this descriptor. The usage
	 * state (i.e. \&quot;IN_USE/NOT_IN_USE\&quot;) shall not change as result. 2)
	 * Disable a previously enabled individual NS descriptor resource, preventing
	 * any further use for instantiation of new network service(s) with this
	 * descriptor. The usage state (i.e. \&quot;IN_USE/NOT_IN_USE\&quot;) shall not
	 * changes a result. 3) Modify the user defined data of an individual NS
	 * descriptor resource.
	 *
	 */
	@Override
	public List<Object> nsDescriptorsNsdInfoIdPatch(String nsdInfoId, NsDescriptorsNsdInfoIdPatchQuery body, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Create a new NS descriptor resource.
	 *
	 * The POST method is used to create a new NS descriptor resource or a new
	 * version of an on-boarded NS descriptor.
	 *
	 */
	@Override
	public NsDescriptorsNsdInfoIdGetResponse nsDescriptorsPost(String accept, String contentType, NsDescriptorsPostQuery body, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Query information about multiple PNF descriptor resources.
	 *
	 * \&quot;The GET method queries information about multiple PNF descriptor
	 * resources.\&quot;
	 *
	 */
	@Override
	public List<Object> pnfDescriptorsGet(String filter, String allFields, String fields, String excludeFields, String excludeDefault) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Delete an individual PNF descriptor resource.
	 *
	 * The DELETE method deletes an individual PNF descriptor resource. An
	 * individual PNF descriptor resource can only be deleted when there is no NS
	 * instance using it or there is NSD referencing it. To delete all PNFD versions
	 * identified by a particular value of the \&quot;pnfdInvariantId\&quot;
	 * attribute, the procedure is to first use the GET method with filter
	 * \&quot;pnfdInvariantId\&quot; towards the PNF descriptors resource to find
	 * all versions of the PNFD. Then, the client uses the DELETE method described
	 * in this clause to delete each PNFD version individually.
	 *
	 */
	@Override
	public void pnfDescriptorsPnfdInfoIdDelete(String pnfdInfoId) {
		// TODO: Implement...

	}

	/**
	 * Read an individual PNFD resource.
	 *
	 * The GET method reads information about an individual PNF descriptor. This
	 * method shall follow the provisions specified in the Tables 5.4.6.3.2-1 and
	 * 5.4.6.3.2-2 of GS NFV-SOL 005 for URI query parameters, request and response
	 * data structures, and response codes.
	 *
	 */
	@Override
	public PnfDescriptorsPnfdInfoIdGetResponse pnfDescriptorsPnfdInfoIdGet(String pnfdInfoId, String accept, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Modify the user defined data of an individual PNF descriptor resource.
	 *
	 * The PATCH method modifies the user defined data of an individual PNF
	 * descriptor resource.
	 *
	 */
	@Override
	public PnfDescriptorsPnfdInfoIdPatchResponse pnfDescriptorsPnfdInfoIdPatch(String pnfdInfoId, String accept, String contentType, PnfDescriptorsPnfdInfoIdPatchQuery body, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Fetch the content of a PNFD.
	 *
	 * The GET method fetches the content of the PNFD. This method shall follow the
	 * provisions specified in the Table 5.4.7.3.2-2 for URI query parameters,
	 * request and response data structures, and response codes.
	 *
	 */
	@Override
	public void pnfDescriptorsPnfdInfoIdPnfdContentGet(String pnfdInfoId, String accept, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Upload the content of a PNFD.
	 *
	 * The PUT method is used to upload the content of a PNFD. This resource
	 * represents the content of the individual PNF descriptor, i.e. PNFD content.
	 * The client can use this resource to upload and download the content of the
	 * PNFD.
	 *
	 */
	@Override
	public void pnfDescriptorsPnfdInfoIdPnfdContentPut(String pnfdInfoId, String accept, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Create a new PNF descriptor resource.
	 *
	 * The POST method is used to create a new PNF descriptor resource
	 *
	 */
	@Override
	public PnfDescriptorsPnfdInfoIdGetResponse pnfDescriptorsPost(String accept, String contentType, PnfDescriptorsPostQuery body, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Query multiple subscriptions.
	 *
	 * The GET method queries the list of active subscriptions of the functional
	 * block that invokes the method. It can be used e.g. for resynchronization
	 * after error situations. This method shall support the URI query parameters,
	 * request and response data structures, and response codes. This resource
	 * represents subscriptions. The client can use this resource to subscribe to
	 * notifications related to NSD management and to query its subscriptions.
	 *
	 */
	@Override
	public List<Object> subscriptionsGet(String accept, String filter, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Subscribe to NSD and PNFD change notifications.
	 *
	 * The POST method creates a new subscription. This method shall support the URI
	 * query parameters, request and response data structures, and response codes,
	 * as specified in the Tables 5.4.8.3.1-1 and 5.4.8.3.1-2 of GS-NFV SOL 005.
	 * Creation of two subscription resources with the same callbackURI and the same
	 * filter can result in performance degradation and will provide duplicates of
	 * notifications to the OSS, and might make sense only in very rare use cases.
	 * Consequently, the NFVO may either allow creating a subscription resource if
	 * another subscription resource with the same filter and callbackUri already
	 * exists (in which case it shall return the \&quot;201 Created\&quot; response
	 * code), or may decide to not create a duplicate subscription resource (in
	 * which case it shall return a \&quot;303 See Other\&quot; response code
	 * referencing the existing subscription resource with the same filter and
	 * callbackUri). This resource represents subscriptions. The client can use this
	 * resource to subscribe to notifications related to NSD management and to query
	 * its subscriptions.
	 *
	 */
	@Override
	public SubscriptionsPostResponse subscriptionsPost(String accept, String contentType, SubscriptionsPostQuery body, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Terminate Subscription
	 *
	 * This resource represents an individual subscription. It can be used by the
	 * client to read and to terminate a subscription to notifications related to
	 * NSD management. The DELETE method terminates an individual subscription. This
	 * method shall support the URI query parameters, request and response data
	 * structures, and response codes, as specified in the Table 5.4.9.3.3-2.
	 *
	 */
	@Override
	public void subscriptionsSubscriptionIdDelete(String subscriptionId, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Read an individual subscription resource.
	 *
	 * This resource represents an individual subscription. It can be used by the
	 * client to read and to terminate a subscription to notifications related to
	 * NSD management. The GET method retrieves information about a subscription by
	 * reading an individual subscription resource. This resource represents an
	 * individual subscription. It can be used by the client to read and to
	 * terminate a subscription to notifications related to NSD management.
	 *
	 */
	@Override
	public SubscriptionsPostResponse subscriptionsSubscriptionIdGet(String subscriptionId, String accept, @Context SecurityContext securityContext) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Notify about NSD and PNFD changes
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The POST method
	 * delivers a notification from the server to the client. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Table 5.4.10.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsdChangeNotificationPost(NsdChangeNotification nsdChangeNotification, String accept, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Notify about NSD and PNFD changes
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The POST method
	 * delivers a notification from the server to the client. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Table 5.4.10.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsdDeletionNotificationPost(NsdDeletionNotification nsdDeletionNotification, String accept, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Notify about NSD and PNFD changes
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The POST method
	 * delivers a notification from the server to the client. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Table 5.4.10.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsdOnBoardingFailureNotificationPost(NsdOnBoardingFailureNotification nsdOnBoardingFailureNotification, String accept, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Notify about NSD and PNFD changes
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The POST method
	 * delivers a notification from the server to the client. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Table 5.4.10.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsdOnBoardingNotificationPost(NsdOnBoardingNotification nsdOnBoardingNotification, String accept, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Test the notification endpoint
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The GET method allows
	 * the server to test the notification endpoint that is provided by the client,
	 * e.g. during subscription. This method shall follow the provisions specified
	 * in the Table 5.4.10.3.2-2 for URI query parameters, request and response data
	 * structures, and response codes.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionPnfdDeletionNotificationGet(String accept, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Notify about NSD and PNFD changes
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The POST method
	 * delivers a notification from the server to the client. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Table 5.4.10.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionPnfdDeletionNotificationPost(PnfdDeletionNotification pnfdDeletionNotification, String accept, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Notify about NSD and PNFD changes
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The POST method
	 * delivers a notification from the server to the client. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Table 5.4.10.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionPnfdOnBoardingFailureNotificationPost(PnfdOnBoardingFailureNotification pnfdOnBoardingFailureNotification, String accept, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

	/**
	 * Notify about NSD and PNFD changes
	 *
	 * This resource represents a notification endpoint. The server can use this
	 * resource to send notifications to a subscribed client, which has provided the
	 * URI of this resource during the subscription process. The POST method
	 * delivers a notification from the server to the client. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Table 5.4.10.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionPnfdOnBoardingNotificationPost(PnfdOnBoardingNotification pnfdOnBoardingNotification, String accept, String contentType, @Context SecurityContext securityContext) {
		// TODO: Implement...

	}

}
