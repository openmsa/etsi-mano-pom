package com.ubiqube.api.rs.endpoints.nfvo.vnf;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.ubiqube.api.ejb.nfvo.vnf.InlineResponse2001;
import com.ubiqube.api.ejb.nfvo.vnf.SubscriptionsPkgmSubscription;
import com.ubiqube.api.exception.ServiceException;

/**
 * SOL005 - VNF Package Management Interface
 *
 * <p>
 * SOL005 - VNF Package Management Interface IMPORTANT: Please note that this
 * file might be not aligned to the current version of the ETSI Group
 * Specification it refers to and has not been approved by the ETSI NFV ISG. In
 * case of discrepancies the published ETSI Group Specification takes
 * precedence. Please report bugs to
 * https://forge.etsi.org/bugzilla/buglist.cgi?component=Nfv-Openapis
 *
 */

public interface DefaultApi {

	/**
	 * Query multiple subscriptions.
	 *
	 * The GET method queries the list of active subscriptions of the functional
	 * block that invokes the method. It can be used e.g. for resynchronization
	 * after error situations. This method shall follow the provisions specified in
	 * the Tables 9.4.7.8.2-1 and 9.4.8.3.2-2 for URI query parameters, request and
	 * response data structures, and response codes.
	 *
	 */
	public List<SubscriptionsPkgmSubscription> subscriptionsGet(@HeaderParam("Accept") String accept, @QueryParam("filter") String filter, @Context SecurityContext securityContext);

	/**
	 * Subscribe to notifications related to on-boarding and/or changes of VNF
	 * packages.
	 *
	 * The POST method creates a new subscription. This method shall follow the
	 * provisions specified in the Tables 9.4.8.3.1-1 and 9.4.8.3.1-2 for URI query
	 * parameters, request and response data structures, and response codes.
	 * Creation of two subscription resources with the same callbackURI and the same
	 * filter can result in performance degradation and will provide duplicates of
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
	public List<InlineResponse2001> subscriptionsPost(@HeaderParam("Accept") String accept, @HeaderParam("Content-Type") String contentType, String body, @Context SecurityContext securityContext, @Context UriInfo uriInfo);

	/**
	 * Terminate a subscription.
	 *
	 * The DELETE method terminates an individual subscription.
	 *
	 */
	public void subscriptionsSubscriptionIdDelete(@PathParam("subscriptionId") String subscriptionId, @Context SecurityContext securityContext);

	/**
	 * Read an individual subscription resource.
	 *
	 * Query Subscription Information The GET method reads an individual
	 * subscription.
	 *
	 */
	public String subscriptionsSubscriptionIdGet(@PathParam("subscriptionId") String subscriptionId, @HeaderParam("Accept") String accept, @Context SecurityContext securityContext);

	/**
	 * Test the notification endpoint
	 *
	 * The GET method allows the server to test the notification endpoint that is
	 * provided by the client, e.g. during subscription. This method shall follow
	 * the provisions specified in the Tables 9.4.10.3.2-1 and 9.4.10.3.2-2 for URI
	 * query parameters, request and response data structures, and response codes.
	 *
	 */
	public void uRIIsProvidedByTheClientWhenCreatingTheSubscriptionVnfPackageChangeNotificationGet(@HeaderParam("Accept") String accept, @Context SecurityContext securityContext);

	/**
	 * Notify about VNF package onboarding or change
	 *
	 * The POST method delivers a notification from the server to the client. This
	 * method shall follow the provisions specified in the Tables 9.4.10.3.1-1 and
	 * 9.4.10.3.1-2 for URI query parameters, request and response data structures,
	 * and response codes.
	 *
	 */
	public void vnfPackageChangeNotificationPost(String body, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

	/**
	 * Notify about VNF package onboarding or change
	 *
	 * The POST method delivers a notification from the server to the client. This
	 * method shall follow the provisions specified in the Tables 9.4.10.3.1-1 and
	 * 9.4.10.3.1-2 for URI query parameters, request and response data structures,
	 * and response codes.
	 *
	 */
	public void vnfPackageOnboardingNotificationPost(String body, @Context UriInfo uriInfo, @Context SecurityContext securityContext);

	/**
	 * Query VNF packages information.
	 *
	 * The GET method queries the information of the VNF packages matching the
	 * filter. This method shall follow the provisions specified in the Tables
	 * 9.4.2.3.2-1 and 9.4.2.3.2-2 for URI query parameters, request and response
	 * data structures, and response codes.
	 *
	 *
	 * @param accept          the accepted content type of the HTTP request
	 * @param uriInfo         the UriInfo object extracted from the context of the
	 *                        HTTP request
	 * @param securityContext the security context of the HTTP request
	 * @return the HTTP response
	 * @throws ServiceException this exception is raised if some problem occurs
	 *                          during the execution of the method
	 */
	public Response vnfPackagesGet(@HeaderParam("Accept") String accept, @Context UriInfo uriInfo, @Context SecurityContext securityContext) throws ServiceException;

	/**
	 * Create a new individual VNF package resource.
	 *
	 * The POST method creates a new individual VNF package resource.
	 *
	 */
	public Response vnfPackagesPost(@HeaderParam("Accept") String accept, @HeaderParam("Content-Type") String contentType, String body, @Context SecurityContext securityContext, @Context UriInfo uriInfo);

	/**
	 * Fetch individual VNF package artifact.
	 *
	 * The GET method fetches the content of an artifact within a VNF package. This
	 * method shall follow the provisions specified in the Tables 9.4.7.3.2-1 and
	 * 9.4.7.3.2-2 for URI query parameters, request and response data structures,
	 * and response codes.
	 *
	 *
	 */
	/**
	 *
	 * @param vnfPkgId        the VNF Package Identification (Id)
	 * @param artifactPath    the VNF Package artifact target path
	 * @param accept          the accepted content type of the HTTP request
	 * @param securityContext the security context of the HTTP request
	 * @param range           the ZIP bytes array range to download
	 * @return the HTTP response
	 * @throws ServiceException this exception is raised if some problem occurs
	 *                          during the execution of the method
	 */
	public Response vnfPackagesVnfPkgIdArtifactsArtifactPathGet(@PathParam("vnfPkgId") String vnfPkgId, @PathParam("artifactPath") String artifactPath, @HeaderParam("Accept") String accept, @Context SecurityContext securityContext, @HeaderParam("Range") String range) throws ServiceException;

	/**
	 * Delete an individual VNF package.
	 *
	 * The DELETE method deletes an individual VNF Package resource.
	 *
	 */
	public Response vnfPackagesVnfPkgIdDelete(@PathParam("vnfPkgId") String vnfPkgId, @Context SecurityContext securityContext);

	/**
	 *
	 * Read information about an individual VNF package.
	 *
	 * The GET method reads the information of a VNF package.
	 *
	 * @param vnfPkgId the VNF Package Identification (Id)
	 * @param accept   the accepted content type of the HTTP request
	 * @return the HTTP response
	 * @throws ServiceException this exception is raised if some problem occurs
	 *                          during the execution of the method
	 */
	public Response vnfPackagesVnfPkgIdGet(@PathParam("vnfPkgId") String vnfPkgId, @HeaderParam("Accept") String accept) throws ServiceException;

	/**
	 * Fetch an on-boarded VNF package.
	 *
	 * The GET method fetches the content of a VNF package identified by the VNF
	 * package identifier allocated by the NFVO. This method shall follow the
	 * provisions specified in the Tables 9.4.5.3.2-1 and 9.4.5.3.2-2 for URI query
	 * parameters, request and response data structures, and response codes.
	 *
	 * @throws IOException
	 * @throws ServiceException
	 *
	 */
	public Response vnfPackagesVnfPkgIdPackageContentGet(@PathParam("vnfPkgId") String vnfPkgId, @HeaderParam("Accept") String accept, @Context SecurityContext securityContext, @HeaderParam("Range") String range) throws IOException, ServiceException;

	/**
	 * Upload a VNF package by providing the content of the VNF package.
	 *
	 * The PUT method uploads the content of a VNF package. This method shall follow
	 * the provisions specified in the Tables 9.4.5.3.3-1 and 9.4.5.3.3-2 for URI
	 * query parameters, request and response data structures, and response codes.
	 *
	 */
	public Response vnfPackagesVnfPkgIdPackageContentPut(@PathParam("vnfPkgId") String vnfPkgId, @HeaderParam("Accept") String accept, @Context SecurityContext securityContext,
			/* @Multipart(value = "file", required = false) FileInputStream fileDetail, */
			// OK: FormDataMultiPart multipart
			@FormDataParam("file") InputStream fileDetail, @FormDataParam("file") FormDataBodyPart part);

	/**
	 * Upload a VNF package by providing the address information of the VNF package.
	 *
	 * The POST method provides the information for the NFVO to get the content of a
	 * VNF package. This method shall follow the provisions specified in the Tables
	 * 9.4.6.3.1-1 and 9.4.6.3.1-2 for URI query parameters, request and response
	 * data structures, and response codes.
	 *
	 * @return TODO
	 *
	 */
	public Response vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(@HeaderParam("Accept") String accept, @HeaderParam("Content-Type") String contentType, @PathParam("vnfPkgId") String vnfPkgId, String body, @Context SecurityContext securityContext);

	/**
	 * Update information about an individual VNF package.
	 *
	 * \&quot;The PATCH method updates the information of a VNF package.\&quot;
	 * \&quot;This method shall follow the provisions specified in the Tables
	 * 9.4.3.3.4-1 and 9.4.3.3.4-2 for URI query parameters, request and response
	 * data structures, and response codes.\&quot;
	 *
	 */
	public Object vnfPackagesVnfPkgIdPatch(@PathParam("vnfPkgId") String vnfPkgId, String body, @HeaderParam("Content-Type") String contentType, @Context SecurityContext securityContext);

	/**
	 * Read VNFD of an on-boarded VNF package.
	 *
	 * The GET method reads the content of the VNFD within a VNF package. The VNFD
	 * can be implemented as a single file or as a collection of multiple files. If
	 * the VNFD is implemented in the form of multiple files, a ZIP file embedding
	 * these files shall be returned. If the VNFD is implemented as a single file,
	 * either that file or a ZIP file embedding that file shall be returned. The
	 * selection of the format is controlled by the \&quot;Accept\&quot; HTTP header
	 * passed in the GET request. • If the \&quot;Accept\&quot; header contains only
	 * \&quot;text/plain\&quot; and the VNFD is implemented as a single file, the
	 * file shall be returned; otherwise, an error message shall be returned. • If
	 * the \&quot;Accept\&quot; header contains only \&quot;application/zip\&quot;,
	 * the single file or the multiple files that make up the VNFD shall be returned
	 * embedded in a ZIP file. • If the \&quot;Accept\&quot; header contains both
	 * \&quot;text/plain\&quot; and \&quot;application/zip\&quot;, it is up to the
	 * NFVO to choose the format to return for a single-file VNFD; for a multi-file
	 * VNFD, a ZIP file shall be returned. The default format of the ZIP file shall
	 * be the one specified in ETSI GS NFV-SOL 004 [5] where only the YAML files
	 * representing the VNFD, and information necessary to navigate the ZIP file and
	 * to identify the file that is the entry point for parsing the VNFD (such as
	 * TOSCA-meta or manifest files or naming conventions) are included. This method
	 * shall follow the provisions specified in the Tables 9.4.4.3.2-1 and
	 * 9.4.4.3.2-2 for URI query parameters, request and response data structures,
	 * and response codes.
	 *
	 * @param vnfPkgId        the VNF Package Identification (Id)
	 * @param accept          the accepted content type of the HTTP request
	 * @param securityContext the security context of the HTTP request
	 * @return the HTTP response
	 * @throws ServiceException this exception is raised if some problem occurs
	 *                          during the execution of the method
	 *
	 */
	public Response vnfPackagesVnfPkgIdVnfdGet(@PathParam("vnfPkgId") String vnfPkgId, @HeaderParam("Accept") String accept, @Context SecurityContext securityContext) throws ServiceException;
}
