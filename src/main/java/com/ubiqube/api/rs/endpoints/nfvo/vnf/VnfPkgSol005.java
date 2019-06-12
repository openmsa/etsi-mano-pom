package com.ubiqube.api.rs.endpoints.nfvo.vnf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ubiqube.api.ejb.nfvo.vnf.ProblemDetails;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPackagePostQuery;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPackagesVnfPkgIdGetResponse;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPackagesVnfPkgIdPackageContentUploadFromUriPostRequest;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPackagesVnfPkgInfoLinks;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPackagesVnfPkgInfoLinksSelf;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPkgInfo;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPkgInfo.OnboardingStateEnum;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPkgInfo.OperationalStateEnum;
import com.ubiqube.api.ejb.nfvo.vnf.VnfPkgInfo.UsageStateEnum;
import com.ubiqube.api.entities.repository.RepositoryElement;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.rs.endpoints.nfvo.GenericException;
import com.ubiqube.api.rs.exception.etsi.ConflictException;
import com.ubiqube.api.rs.exception.etsi.NotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;

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
 * NOTE: Normaly we should receive object in methods but Genson seems to be on
 * the classpath and is unable to unserialize objects. So we use a string2Object
 * to do So. Note same problems occurred when returning object some times genson
 * could be here and not Jackson, in this case you can use object2String.
 *
 */
@Path("/sol005/vnfpkgm/v1")
@Api(value = "/sol005/vnfpkgm/v1", description = "")
public class VnfPkgSol005 extends BaseApi {

	/**
	 * Create a new individual VNF package resource. 9.5.2.4
	 *
	 * The POST method creates a new individual VNF package resource.
	 *
	 */
	@POST
	@Path("/vnf_packages")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "Create a new individual VNF package resource.", tags = {})
	@ApiResponses(value = { @ApiResponse(code = 201, message = "201 Created             An individual VNF package resource has been created successfully. The response body shall contain a representation of the new individual VNF package resource, as defined in clause 9.5.2.4. The HTTP response shall include a \"Location\" HTTP header that contains the resource URI of the individual VNF package resource. ", response = VnfPackagesVnfPkgIdGetResponse.class) })
	public Response vnfPackagesPost(@HeaderParam("Accept") String accept, @HeaderParam("Content-Type") String contentType, String body, @Context SecurityContext securityContext, @Context UriInfo uriInfo) {
		final VnfPackagePostQuery vnfPackagePostQuery = string2Object(body, VnfPackagePostQuery.class);

		final String vnfPkgId = UUID.randomUUID().toString();
		final Object jsonString = vnfPackagePostQuery.getCreateVnfPkgInfoRequest().getUserDefinedData();
		final VnfPkgInfo vnfPkgInfo = new VnfPkgInfo();
		vnfPkgInfo.setId(vnfPkgId);
		vnfPkgInfo.setOnboardingState(OnboardingStateEnum.CREATED);

		try {
			final StringBuilder sb = new StringBuilder().append(REPOSITORY_NVFO_DATAFILE_BASE_PATH).append("/").append(vnfPkgId);
			String uri = sb.toString();
			if (!repositoryService.exists(uri.toString())) {
				repositoryService.addDirectory(uri, "", "SOL005", "ncroot");
			}

			uri = sb.append("/").append("Metadata.yaml").toString();
			try {
				repositoryService.addFile(uri, "", "Added: SOL005", mapper.writeValueAsString(jsonString).toString(), "ncroot");
			} catch (final JsonProcessingException e) {
				throw new GenericException(e);
			}
		} catch (final ServiceException e) {
			throw new GenericException(e);
		}

		vnfPkgInfo.setUserDefinedData(jsonString);

		/*
		 * VnfPackagesVnfPkgInfoChecksum checksum = new VnfPackagesVnfPkgInfoChecksum();
		 * checksum.algorithm("SHA-256"); String hash =
		 * Hashing.sha256().hashString(jsonString, StandardCharsets.UTF_8).toString();
		 * checksum.setHash(hash); gud.setChecksum(checksum);
		 */

		final VnfPackagesVnfPkgIdGetResponse vnfPackagesVnfPkgIdGetResponse = new VnfPackagesVnfPkgIdGetResponse();
		vnfPackagesVnfPkgIdGetResponse.setVnfPkgInfo(vnfPkgInfo);
		final VnfPackagesVnfPkgInfoLinks links = new VnfPackagesVnfPkgInfoLinks();
		final VnfPackagesVnfPkgInfoLinksSelf self = new VnfPackagesVnfPkgInfoLinksSelf();
		self.setHref(Link.fromUriBuilder(uriInfo.getBaseUriBuilder().path(this.getClass(), "vnfPackagesVnfPkgIdGet")).build(vnfPkgId).getUri().toString());
		links.self(self);
		vnfPkgInfo.setLinks(links);
		vnfPkgInfo.setOperationalState(OperationalStateEnum.DISABLED);
		vnfPkgInfo.setUsageState(UsageStateEnum.NOT_IN_USE);
		vnfPackageRepository.save(vnfPkgInfo);
		// Return.
		URI uri;
		try {
			uri = new URI(self.getHref());
		} catch (final URISyntaxException e) {
			throw new GenericException(e);
		}
		return Response.status(201).contentLocation(uri).entity(vnfPackagesVnfPkgIdGetResponse).build();

	}

	/**
	 * Delete an individual VNF package.
	 *
	 * The DELETE method deletes an individual VNF Package resource.
	 *
	 */
	@DELETE
	@Path("/vnf_packages/{vnfPkgId}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "Delete an individual VNF package.", tags = {})
	@ApiResponses(value = { @ApiResponse(code = 204, message = "204 No Content The VNF package was deleted successfully. The response body shall be empty. "), @ApiResponse(code = 400, message = "Bad Request. Error: Invalid attribute-based filtering parameters. The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute should convey more information about the error.        ", response = ProblemDetails.class), @ApiResponse(code = 401, message = "Unauthorized If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "Forbidden If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided.  It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class), @ApiResponse(code = 404, message = "Not Found If the API producer did not find a current representation for the resource addressed by the URI passed in the request, or is not willing to disclose that one exists, it shall respond with this response code.  The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. ", response = ProblemDetails.class), @ApiResponse(code = 405, message = "Method Not Allowed If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted in that case. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "If the \"Accept\" header does not contain at least one name of a content type for which the NFVO can provide a representation of the VNFD, the NFVO shall respond with this response code.         ", response = ProblemDetails.class), @ApiResponse(code = 409, message = "Conflict. Error: The operation cannot be executed currently, due to a conflict with the state of the resource. Typically, this is due to any of the following scenarios: - Disable a VNF package resource of hich the operational state is not ENABLED - Enable a VNF package resource of which the operational state is not DISABLED The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute shall convey more information about the error. ", response = ProblemDetails.class), @ApiResponse(code = 416, message = "Requested Range Not Satisfiable The byte range passed in the \"Range\" header did not match any available byte range in the VNF package file (e.g. \"access after end of file\"). The response body may contain a ProblemDetails structure. ", response = ProblemDetails.class),
			@ApiResponse(code = 500, message = "Internal Server Error If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond withthis response code. The ProblemDetails structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class), @ApiResponse(code = 503, message = "Service Unavailable If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 [13] for the use of the Retry-After HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class) })
	public Response vnfPackagesVnfPkgIdDelete(@PathParam("vnfPkgId") String vnfPkgId, @Context SecurityContext securityContext) {
		final String uri = new StringBuilder().append(REPOSITORY_NVFO_DATAFILE_BASE_PATH).append("/").append(sanitize(vnfPkgId)).toString();
		final VnfPkgInfo vnfPkgInfo = vnfPackageRepository.get(vnfPkgId);
		if (!"DISABLED".equals(vnfPkgInfo.getOperationalState())) {
			throw new ConflictException("Packaged is enabled.");
		}
		boolean isVnfdMetafile = false;
		try {
			isVnfdMetafile = repositoryService.exists(uri);
		} catch (final ServiceException e) {
			throw new NotFoundException("No such object: " + vnfPkgId);
		}
		if (isVnfdMetafile) {
			final RepositoryElement repositoryElement = repositoryService.getElement(uri);
			repositoryService.deleteRepositoryElement(repositoryElement, "ncroot");
			return null;
		}
		throw new NotFoundException("No such object: " + vnfPkgId);
	}

	/**
	 * Update information about an individual VNF package.
	 *
	 * \&quot;The PATCH method updates the information of a VNF package.\&quot;
	 * \&quot;This method shall follow the provisions specified in the Tables
	 * 9.4.3.3.4-1 and 9.4.3.3.4-2 for URI query parameters, request and response
	 * data structures, and response codes.\&quot;
	 *
	 */
	@PATCH
	@Path("/vnf_packages/{vnfPkgId}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "Update information about an individual VNF package.", tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "200 OK The operation was completed successfully. The response body shall contain attribute modifications for an \"Individual VNF package\" resource ", response = Object.class), @ApiResponse(code = 400, message = "Bad Request. Error: Invalid attribute-based filtering parameters. The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute should convey more information about the error.        ", response = ProblemDetails.class), @ApiResponse(code = 401, message = "Unauthorized If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "Forbidden If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided.  It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class), @ApiResponse(code = 404, message = "Not Found If the API producer did not find a current representation for the resource addressed by the URI passed in the request, or is not willing to disclose that one exists, it shall respond with this response code.  The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. ", response = ProblemDetails.class), @ApiResponse(code = 405, message = "Method Not Allowed If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted in that case. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "If the \"Accept\" header does not contain at least one name of a content type for which the NFVO can provide a representation of the VNFD, the NFVO shall respond with this response code.         ", response = ProblemDetails.class), @ApiResponse(code = 409, message = "Conflict. Error: The operation cannot be executed currently, due to a conflict with the state of the resource. Typically, this is due to any of the following scenarios: - Disable a VNF package resource of hich the operational state is not ENABLED - Enable a VNF package resource of which the operational state is not DISABLED The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute shall convey more information about the error. ", response = ProblemDetails.class), @ApiResponse(code = 416, message = "Requested Range Not Satisfiable The byte range passed in the \"Range\" header did not match any available byte range in the VNF package file (e.g. \"access after end of file\"). The response body may contain a ProblemDetails structure. ", response = ProblemDetails.class),
			@ApiResponse(code = 500, message = "Internal Server Error If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond withthis response code. The ProblemDetails structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class), @ApiResponse(code = 503, message = "Service Unavailable If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 [13] for the use of the Retry-After HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class) })
	public Object vnfPackagesVnfPkgIdPatch(@PathParam("vnfPkgId") String vnfPkgId, String body, @HeaderParam("Content-Type") String contentType, @Context SecurityContext securityContext) {
		final VnfPkgInfo vnfPkgInfo = vnfPackageRepository.get(vnfPkgId);

		patcher.patch(body, vnfPkgInfo);
		vnfPackageRepository.save(vnfPkgInfo);
		/*
		 * VnfPackagesVnfPkgInfoChecksum checksum = new VnfPackagesVnfPkgInfoChecksum();
		 * checksum.algorithm("SHA-256"); String hash =
		 * Hashing.sha256().hashString(jsonString, StandardCharsets.UTF_8).toString();
		 * checksum.setHash(hash); gud.setChecksum(checksum);
		 */

		final VnfPackagesVnfPkgIdGetResponse vnfPackagesVnfPkgIdGetResponse = new VnfPackagesVnfPkgIdGetResponse();
		vnfPackagesVnfPkgIdGetResponse.setVnfPkgInfo(vnfPkgInfo);
		return vnfPackagesVnfPkgIdGetResponse;
	}

	/**
	 * Upload a VNF package by providing the content of the VNF package.
	 *
	 * The PUT method uploads the content of a VNF package. This method shall follow
	 * the provisions specified in the Tables 9.4.5.3.3-1 and 9.4.5.3.3-2 for URI
	 * query parameters, request and response data structures, and response codes.
	 *
	 */
	@PUT
	@Path("/vnf_packages/{vnfPkgId}/package_content")
	@Consumes({ "multipart/form-data" })
	@Produces({ "application/json" })
	@ApiOperation(value = "Upload a VNF package by providing the content of the VNF package.", tags = {})
	@ApiResponses(value = { @ApiResponse(code = 202, message = "202 Accepted The VNF package was accepted for uploading, but the processing has not been completed. It is expected to take some time for processing. The response body shall be empty. "), @ApiResponse(code = 400, message = "Bad Request. Error: Invalid attribute-based filtering parameters. The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute should convey more information about the error.        ", response = ProblemDetails.class), @ApiResponse(code = 401, message = "Unauthorized If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "Forbidden If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided.  It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class), @ApiResponse(code = 404, message = "Not Found If the API producer did not find a current representation for the resource addressed by the URI passed in the request, or is not willing to disclose that one exists, it shall respond with this response code.  The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. ", response = ProblemDetails.class), @ApiResponse(code = 405, message = "Method Not Allowed If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted in that case. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "If the \"Accept\" header does not contain at least one name of a content type for which the NFVO can provide a representation of the VNFD, the NFVO shall respond with this response code.         ", response = ProblemDetails.class), @ApiResponse(code = 409, message = "Conflict. Error: The operation cannot be executed currently, due to a conflict with the state of the resource. Typically, this is due to the fact that the on boarding state of the VNF package resource is not CREATED . The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute shall convey more information about the error. ", response = ProblemDetails.class), @ApiResponse(code = 500, message = "Internal Server Error If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond withthis response code. The ProblemDetails structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class),
			@ApiResponse(code = 503, message = "Service Unavailable If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 [13] for the use of the Retry-After HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class) })
	public Response vnfPackagesVnfPkgIdPackageContentPut(@PathParam("vnfPkgId") String vnfPkgId, @HeaderParam("Accept") String accept, @Context SecurityContext securityContext,
			/* @Multipart(value = "file", required = false) FileInputStream fileDetail, */
			// OK:FormDataMultiPart multipart
			@FormDataParam("file") InputStream fileDetail, @FormDataParam("file") FormDataBodyPart part) {
		final String uri = new StringBuilder().append(REPOSITORY_NVFO_DATAFILE_BASE_PATH).append("/").append(vnfPkgId).toString();
		try {
			if (!repositoryService.exists(uri)) {
				throw new NotFoundException("No such object: " + vnfPkgId);
			}
		} catch (final ServiceException e) {
			throw new GenericException(e);
		}
		if (isZip(part.getMediaType().toString())) {
			// Normally we should do an asynchrone call
			try {
				unzip(vnfPkgId, fileDetail);
			} catch (final Exception e) {
				throw new GenericException(e);
			}
		}
		return Response.ok().build();
	}

	/**
	 * Upload a VNF package by providing the address information of the VNF package.
	 *
	 * The POST method provides the information for the NFVO to get the content of a
	 * VNF package. This method shall follow the provisions specified in the Tables
	 * 9.4.6.3.1-1 and 9.4.6.3.1-2 for URI query parameters, request and response
	 * data structures, and response codes.
	 *
	 */
	@POST
	@Path("/vnf_packages/{vnfPkgId}/package_content/upload_from_uri")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "Upload a VNF package by providing the address information of the VNF package.", tags = {})
	@ApiResponses(value = { @ApiResponse(code = 202, message = "202 Accepted The information about the VNF package was received successfully, but the on-boarding has not been completed. It is expected to take some time for processing. The response body shall be empty. "), @ApiResponse(code = 400, message = "Bad Request. Error: Invalid attribute-based filtering parameters. The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute should convey more information about the error.        ", response = ProblemDetails.class), @ApiResponse(code = 401, message = "Unauthorized If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "Forbidden If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided.  It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class), @ApiResponse(code = 404, message = "Not Found If the API producer did not find a current representation for the resource addressed by the URI passed in the request, or is not willing to disclose that one exists, it shall respond with this response code.  The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. ", response = ProblemDetails.class), @ApiResponse(code = 405, message = "Method Not Allowed If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted in that case. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "If the \"Accept\" header does not contain at least one name of a content type for which the NFVO can provide a representation of the VNFD, the NFVO shall respond with this response code.         ", response = ProblemDetails.class), @ApiResponse(code = 409, message = "Conflict. Error: The operation cannot be executed currently, due to a conflict with the state of the resource. Typically, this is due to the fact that the on boarding state of the VNF package resource is not CREATED . The response body shall contain a ProblemDetails structure, in which the \"detail\" attribute shall convey more information about the error. ", response = ProblemDetails.class), @ApiResponse(code = 500, message = "Internal Server Error If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond withthis response code. The ProblemDetails structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class),
			@ApiResponse(code = 503, message = "Service Unavailable If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 [13] for the use of the Retry-After HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class) })
	public Response vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(@HeaderParam("Accept") String accept, @HeaderParam("Content-Type") String contentType, @PathParam("vnfPkgId") String vnfPkgId, String body, @Context SecurityContext securityContext) {
		final VnfPkgInfo vnfPkgInfo = vnfPackageRepository.get(vnfPkgId);
		if (!"CREATED".equals(vnfPkgInfo.getOnboardingState())) {
			throw new ConflictException("Onboarding state is not correct.");
		}
		final VnfPackagesVnfPkgIdPackageContentUploadFromUriPostRequest vnfPackagesVnfPkgIdPackageContentUploadFromUriPostRequest = string2Object(body, VnfPackagesVnfPkgIdPackageContentUploadFromUriPostRequest.class);

		final LinkedHashMap<String, String> uddList = (LinkedHashMap<String, String>) vnfPackagesVnfPkgIdPackageContentUploadFromUriPostRequest.getUploadVnfPkgFromUriRequest().getUserDefinedData();
		final String uri = uddList.get("url");
		final InputStream content = getUrlContent(uri);
		try {
			unzip(vnfPkgId, content);
		} catch (final Exception e) {
			throw new GenericException(e);
		}

		return Response.status(202).build();
	}

	private InputStream getUrlContent(String uri) {
		URL url;
		try {
			url = new URL(uri);
			return (InputStream) url.getContent();
		} catch (final Exception e) {
			throw new GenericException(e);
		}
	}

	private void unzip(String vnfPkgId, InputStream fileDetail) throws IOException, ServiceException {
		final ZipInputStream zis = new ZipInputStream(fileDetail);
		ZipEntry ze;
		while ((ze = zis.getNextEntry()) != null) {
			String fileName = ze.getName();
			fileName = sanitize(fileName);
			if (ze.isDirectory()) {
				// XXX/ Fix Path ending by '/'
				if (fileName.endsWith("/")) {
					fileName = fileName.substring(0, fileName.length() - 1);
				}
				final String uri = new StringBuilder().append(REPOSITORY_NVFO_DATAFILE_BASE_PATH).append("/").append(vnfPkgId).append("/").append(fileName).toString();
				repositoryService.addDirectory(uri, "", "SOL005", "ncroot");
				continue;
			}

			final String uri = new StringBuilder().append(REPOSITORY_NVFO_DATAFILE_BASE_PATH).append("/").append(vnfPkgId).append("/").append(fileName).toString();
			final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			final byte buffer[] = new byte[1024];
			int read;
			while ((read = zis.read(buffer)) != -1) {
				baos.write(buffer, 0, read);
			}
			repositoryService.addFile(uri, "SOL005", "", baos.toByteArray(), "ncroot");
		}
	}

	private boolean isZip(String httpAccept) {
		if ("application/zip".equals(httpAccept)) {
			return true;
		} else if ("application/x-zip-compressed".equals(httpAccept)) {
			return true;
		}
		return false;
	}

}
