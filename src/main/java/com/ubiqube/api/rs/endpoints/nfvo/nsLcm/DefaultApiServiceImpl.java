package com.ubiqube.api.rs.endpoints.nfvo.nsLcm;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import com.ubiqube.api.commons.id.DeviceId;
import com.ubiqube.api.ejb.nfvo.nsLcm.InlineResponse200;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsIdentifierCreationNotification;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsIdentifierDeletionNotification;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsInstancesNsInstance;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsInstancesNsInstanceIdHealPostQuery;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsInstancesNsInstanceIdInstantiatePostQuery;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsInstancesNsInstanceIdScalePostQuery;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsInstancesNsInstanceIdTerminatePostQuery;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsInstancesNsInstanceIdUpdatePostQuery;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsInstancesPostQuery;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsLcmOpOccsNsLcmOpOccIdGetResponse;
import com.ubiqube.api.ejb.nfvo.nsLcm.NsLcmOperationOccurrenceNotification;
import com.ubiqube.api.ejb.nfvo.nsLcm.NslcmV1NsLcmOpOccsNsLcmOpOccIdCancelPostQuery;
import com.ubiqube.api.ejb.nfvo.nsLcm.NslcmV1NsLcmOpOccsNsLcmOpOccIdFailPostResponse;
import com.ubiqube.api.ejb.nfvo.nsLcm.SubscriptionsPost;
import com.ubiqube.api.ejb.nfvo.nsLcm.SubscriptionsPostQuery;
import com.ubiqube.api.entities.device.SimpleDevice;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.device.DeviceService;

import io.swagger.annotations.Api;

/**
 * SOL005 - NS Lifecycle Management Interface
 *
 * <p>
 * SOL005 - NS Lifecycle Management Interface IMPORTANT: Please note that this
 * file might be not aligned to the current version of the ETSI Group
 * Specification it refers to and has not been approved by the ETSI NFV ISG. In
 * case of discrepancies the published ETSI Group Specification takes
 * precedence. Please report bugs to
 * https://forge.etsi.org/bugzilla/buglist.cgi?component=Nfv-Openapis
 *
 */
@Path("/nslcm/v1")
@Api(value = "/", description = "")
public class DefaultApiServiceImpl implements DefaultApi {
	private static final Logger logger = Logger.getLogger(DefaultApiServiceImpl.class);

	private DeviceService deviceService;

	public DefaultApiServiceImpl() {
		try {
			final InitialContext jndiContext = new InitialContext();
			deviceService = (DeviceService) jndiContext.lookup(DeviceService.RemoteJNDIName);
		} catch (final NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Query multiple NS instances.
	 *
	 * Query NS Instances. The GET method queries information about multiple NS
	 * instances. This method shall support the URI query parameters, request and
	 * response data structures, and response codes, as specified in the Tables
	 * 6.4.2.3.2-1 and 6.4.2.3.2-2.
	 *
	 */
	@Override
	public List<Object> nsInstancesGet(String accept, String filter, String allFields, String fields, String excludeFields, String excludeDefault, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Delete NS instance resource.
	 *
	 * Delete NS Identifier This method deletes an individual NS instance resource.
	 *
	 */
	@Override
	public void nsInstancesNsInstanceIdDelete(String nsInstanceId, @Context SecurityContext securityContext) {
		try {
			deviceService.deleteDevice(deviceService.getDeviceId(nsInstanceId), securityContext.getUserPrincipal().getName());
		} catch (final ServiceException e) {
			logger.error("", e);
		}

	}

	/**
	 * Read an individual NS instance resource.
	 *
	 * The GET method retrieves information about a NS instance by reading an
	 * individual NS instance resource.
	 *
	 */
	@Override
	public InlineResponse200 nsInstancesNsInstanceIdGet(String nsInstanceId, String accept, String contentType, @Context SecurityContext securityContextn) {
		try {
			final DeviceId deviceId = deviceService.getDeviceId(nsInstanceId);
			final SimpleDevice deviceModel = deviceService.getDeviceModeleAndManId(deviceId);
			final InlineResponse200 resp = new InlineResponse200();
			final NsInstancesNsInstance nsInstance = new NsInstancesNsInstance();
			resp.setNsInstance(nsInstance);
			/*
			 * sb.append("    id: ").append(toIndentedString(id)).append("\n");
			 * sb.append("    nsInstanceName: ").append(toIndentedString(nsInstanceName)).
			 * append("\n");
			 * sb.append("    nsInstanceDescription: ").append(toIndentedString(
			 * nsInstanceDescription)).append("\n");
			 * sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
			 * sb.append("    nsdInfoId: ").append(toIndentedString(nsdInfoId)).append("\n")
			 * ;
			 * sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n")
			 * ;
			 * sb.append("    vnfInstance: ").append(toIndentedString(vnfInstance)).append(
			 * "\n");
			 * sb.append("    pnfInfo: ").append(toIndentedString(pnfInfo)).append("\n");
			 * sb.append("    virtualLinkInfo: ").append(toIndentedString(virtualLinkInfo)).
			 * append("\n");
			 * sb.append("    vnffgInfo: ").append(toIndentedString(vnffgInfo)).append("\n")
			 * ; sb.append("    sapInfo: ").append(toIndentedString(sapInfo)).append("\n");
			 * sb.append("    nestedNsInstanceId: ").append(toIndentedString(
			 * nestedNsInstanceId)).append("\n");
			 * sb.append("    nsState: ").append(toIndentedString(nsState)).append("\n");
			 * sb.append("    nsScaleStatus: ").append(toIndentedString(nsScaleStatus)).
			 * append("\n"); sb.append("    additionalAffinityOrAntiAffinityRule: ").append(
			 * toIndentedString(additionalAffinityOrAntiAffinityRule)).append("\n");
			 * sb.append("    links: ").append(toIndentedString(links)).append("\n");
			 */
			nsInstance.setId(deviceModel.getUbiqubeId().getUbiId());
			nsInstance.setNsInstanceName(deviceModel.getName());
			nsInstance.setNsInstanceDescription("");
			nsInstance.setNsdId("???");
			nsInstance.setNsdInfoId("???");
			nsInstance.setFlavourId("???");
			nsInstance.setVnfInstance(null); // ???
			nsInstance.setPnfInfo(null); // ??
			nsInstance.setVirtualLinkInfo(null);
			nsInstance.setVnffgInfo(null);
			nsInstance.setSapInfo(null);
			nsInstance.setNestedNsInstanceId(null);
			nsInstance.setNsState(NsInstancesNsInstance.NsStateEnum.INSTANTIATED);
			nsInstance.setNsScaleStatus(null);
			nsInstance.setAdditionalAffinityOrAntiAffinityRule(null);
			nsInstance.setLinks(null);
		} catch (final ServiceException e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * Heal a NS instance.
	 *
	 * The POST method requests to heal a NS instance resource. This method shall
	 * follow the provisions specified in the Tables 6.4.7.3.1-1 and 6.4.7.3.1-2 for
	 * URI query parameters, request and response data structures, and response
	 * codes.
	 *
	 */
	@Override
	public NsInstancesNsInstance nsInstancesNsInstanceIdHealPost(String nsInstanceId, String accept, String contentType, NsInstancesNsInstanceIdHealPostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Instantiate a NS.
	 *
	 * The POST method requests to instantiate a NS instance resource.
	 *
	 */
	@Override
	public NsInstancesNsInstance nsInstancesNsInstanceIdInstantiatePost(String nsInstanceId, String accept, String contentType, NsInstancesNsInstanceIdInstantiatePostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Scale a NS instance.
	 *
	 * The POST method requests to scale a NS instance resource.
	 *
	 */
	@Override
	public NsInstancesNsInstance nsInstancesNsInstanceIdScalePost(String nsInstanceId, String accept, String contentType, NsInstancesNsInstanceIdScalePostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Terminate a NS instance.
	 *
	 * Terminate NS task. The POST method terminates a NS instance. This method can
	 * only be used with a NS instance in the INSTANTIATED state. Terminating a NS
	 * instance does not delete the NS instance identifier, but rather transitions
	 * the NS into the NOT_INSTANTIATED state. This method shall support the URI
	 * query parameters, request and response data structures, and response codes,
	 * as specified in the Tables 6.4.8.3.1-1 and 6.8.8.3.1-2.
	 *
	 */
	@Override
	public NsInstancesNsInstance nsInstancesNsInstanceIdTerminatePost(String nsInstanceId, String accept, String contentType, NsInstancesNsInstanceIdTerminatePostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Updates a NS instance.
	 *
	 * Scale NS instance. The POST method requests to scale a NS instance resource.
	 *
	 */
	@Override
	public NsInstancesNsInstance nsInstancesNsInstanceIdUpdatePost(String nsInstanceId, String accept, String contentType, NsInstancesNsInstanceIdUpdatePostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Create a NS instance resource.
	 *
	 * The POST method creates a new NS instance resource.
	 *
	 */
	@Override
	public InlineResponse200 nsInstancesPost(String accept, String contentType, NsInstancesPostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Query multiple NS LCM operation occurrences.
	 *
	 * Get Operation Status. The client can use this method to query status
	 * information about multiple NS lifecycle management operation occurrences.
	 * This method shall follow the provisions specified in the Tables 6.4.9.3.2-1
	 * and 6.4.9.3.2-2 for URI query parameters, request and response data
	 * structures, and response codes.
	 *
	 */
	@Override
	public List<Object> nsLcmOpOccsGet(String accept, String filter, String fields, String excludeFields, String excludeDefault, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Continue a NS lifecycle management operation occurrence.
	 *
	 * The POST method initiates continuing an NS lifecycle operation if that
	 * operation has experienced a temporary failure, i.e. the related \&quot;NS LCM
	 * operation occurrence\&quot; is in \&quot;FAILED_TEMP\&quot; state. This
	 * method shall follow the provisions specified in the Tables 6.4.13.3.1-1 and
	 * 6.4.13.3.1-2 for URI query parameters, request and response data structures,
	 * and response codes.
	 *
	 */
	@Override
	public void nsLcmOpOccsNsLcmOpOccIdContinuePost(String nsLcmOpOccId, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Read an individual NS LCM operation occurrence resource.
	 *
	 * The client can use this method to retrieve status information about a NS
	 * lifecycle management operation occurrence by reading an individual \&quot;NS
	 * LCM operation occurrence\&quot; resource. This method shall follow the
	 * provisions specified in the Tables 6.4.10.3.2-1 and 6.4.10.3.2-2 for URI
	 * query parameters, request and response data structures, and response codes.
	 *
	 */
	@Override
	public NsLcmOpOccsNsLcmOpOccIdGetResponse nsLcmOpOccsNsLcmOpOccIdGet(String nsLcmOpOccId, String accept, String contentType, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Retry a NS lifecycle management operation occurrence.
	 *
	 * The POST method initiates retrying a NS lifecycle management operation if
	 * that operation has experienced a temporary failure, i.e. the related
	 * \&quot;NS LCM operation occurrence\&quot; is in \&quot;FAILED_TEMP\&quot;
	 * state. This method shall follow the provisions specified in the Tables
	 * 6.4.11.3.1-1 and 6.4.11.3.1-2 for URI query parameters, request and response
	 * data structures, and response codes.
	 *
	 */
	@Override
	public void nsLcmOpOccsNsLcmOpOccIdRetryPost(String nsLcmOpOccId, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Rollback a NS lifecycle management operation occurrence.
	 *
	 * The POST method initiates rolling back a NS lifecycle operation if that
	 * operation has experienced a temporary failure, i.e. the related \&quot;NS LCM
	 * operation occurrence\&quot; is in \&quot;FAILED_TEMP\&quot; state. This
	 * method shall follow the provisions specified in the Tables 6.4.12.3.1-1 and
	 * 6.4.12.3.1-2 for URI query parameters, request and response data structures,
	 * and response codes.
	 *
	 */
	@Override
	public void nsLcmOpOccsNsLcmOpOccIdRollbackPost(String nsLcmOpOccId, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Cancel a NS lifecycle management operation occurrence.
	 *
	 * The POST method initiates canceling an ongoing NS lifecycle management
	 * operation while it is being executed or rolled back, i.e. the related
	 * \&quot;NS LCM operation occurrence\&quot; is either in
	 * \&quot;PROCESSING\&quot; or \&quot;ROLLING_BACK\&quot; state. This method
	 * shall follow the provisions specified in the Tables 6.4.15.3.1-1 and
	 * 6.4.15.3.1-2 for URI query parameters, request and response data structures,
	 * and response codes.
	 *
	 */
	@Override
	public void nslcmV1NsLcmOpOccsNsLcmOpOccIdCancelPost(String nsLcmOpOccId, String accept, String contentType, NslcmV1NsLcmOpOccsNsLcmOpOccIdCancelPostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Mark a NS lifecycle management operation occurrence as failed.
	 *
	 * The POST method marks a NS lifecycle management operation occurrence as
	 * \&quot;finally failed\&quot; if that operation occurrence is in
	 * \&quot;FAILED_TEMP\&quot; state.
	 *
	 */
	@Override
	public NslcmV1NsLcmOpOccsNsLcmOpOccIdFailPostResponse nslcmV1NsLcmOpOccsNsLcmOpOccIdFailPost(String nsLcmOpOccId, String accept, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Query multiple subscriptions.
	 *
	 * Query Subscription Information. The GET method queries the list of active
	 * subscriptions of the functional block that invokes the method. It can be used
	 * e.g. for resynchronization after error situations.
	 *
	 */
	@Override
	public List<Object> subscriptionsGet(String accept, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Subscribe to NS lifecycle change notifications.
	 *
	 * The POST method creates a new subscription. This method shall support the URI
	 * query parameters, request and response data structures, and response codes,
	 * as specified in the Tables 6.4.16.3.1-1 and 6.4.16.3.1-2. Creation of two
	 * subscription resources with the same callbackURI and the same filter can
	 * result in performance degradation and will provide duplicates of
	 * notifications to the OSS, and might make sense only in very rare use cases.
	 * Consequently, the NFVO may either allow creating a subscription resource if
	 * another subscription resource with the same filter and callbackUri already
	 * exists (in which case it shall return the \&quot;201 Created\&quot; response
	 * code), or may decide to not create a duplicate subscription resource (in
	 * which case it shall return a \&quot;303 See Other\&quot; response code
	 * referencing the existing subscription resource with the same filter and
	 * callbackUri).
	 *
	 */
	@Override
	public SubscriptionsPost subscriptionsPost(String accept, String contentType, SubscriptionsPostQuery body, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Terminate a subscription.
	 *
	 * The DELETE method terminates an individual subscription. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Tables 6.4.17.3.5-1 and 6.4.17.3.5-2.
	 *
	 */
	@Override
	public void subscriptionsSubscriptionIdDelete(String subscriptionId, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Read an individual subscription resource.
	 *
	 * The GET method retrieves information about a subscription by reading an
	 * individual subscription resource. This method shall support the URI query
	 * parameters, request and response data structures, and response codes, as
	 * specified in the Tables 6.4.17.3.2-1 and 6.4.17.3.2-2
	 *
	 */
	@Override
	public SubscriptionsPost subscriptionsSubscriptionIdGet(String subscriptionId, String accept, @Context SecurityContext securityContextn) {
		// TODO: Implement...

		return null;
	}

	/**
	 * Notify about NS lifecycle change
	 *
	 * The POST method delivers a notification from the server to the client. This
	 * method shall support the URI query parameters, request and response data
	 * structures, and response codes, as specified in the Tables 6.4.18.3.1-1 and
	 * 6.4.18.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsIdentifierCreationNotificationPost(NsIdentifierCreationNotification nsIdentifierCreationNotification, String accept, String contentType, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Test the notification endpoint.
	 *
	 * Query NS Instances. The GET method queries information about multiple NS
	 * instances. This method shall support the URI query parameters, request and
	 * response data structures, and response codes, as specified in the Tables
	 * 6.4.2.3.2-1 and 6.4.2.3.2-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsIdentifierDeletionNotificationGet(String accept, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Notify about NS lifecycle change
	 *
	 * The POST method delivers a notification from the server to the client. This
	 * method shall support the URI query parameters, request and response data
	 * structures, and response codes, as specified in the Tables 6.4.18.3.1-1 and
	 * 6.4.18.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsIdentifierDeletionNotificationPost(NsIdentifierDeletionNotification nsIdentifierDeletionNotification, String accept, String contentType, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

	/**
	 * Notify about NS lifecycle change
	 *
	 * The POST method delivers a notification from the server to the client. This
	 * method shall support the URI query parameters, request and response data
	 * structures, and response codes, as specified in the Tables 6.4.18.3.1-1 and
	 * 6.4.18.3.1-2.
	 *
	 */
	@Override
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionNsLcmOperationOccurrenceNotificationPost(NsLcmOperationOccurrenceNotification nsLcmOperationOccurrenceNotification, String accept, String contentType, @Context SecurityContext securityContextn) {
		// TODO: Implement...

	}

}
