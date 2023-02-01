/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.20).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.ubiqube.etsi.mano.nfvem.v331.controller.nfvmanocim;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.CreatePeerEntityRequest;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.PeerEntity;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.PeerEntityConfigModificationRequest;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.PeerEntityConfigModifications;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ProblemDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Api(value = "peer_entities", description = "the peer_entities API")
public interface PeerEntitiesApi {

	@ApiOperation(value = "", nickname = "peerEntitiesGet", notes = "Queries information and configuration in the producer NFV-MANO  functional entity with regards to multiple peer entities. This method shall follow the provisions specified in the tables  5.5.13.3.2-1 and 5.5.13.3.2-2 for URI query parameters, request  and response data structures, and response codes. ", response = PeerEntity.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "200 OK Shall be returned when information about zero or more peer  entities has been queried successfully. The response body shall contain in an array the resource  representations of zero or more peer entities, as defined  in clause 5.6.2.15. If the “filter\" URI parameter or one of the \"all_fields\", \"fields\"  (if supported), \"exclude_fields\" (if supported) or \"exclude_default\"  URI parameters was supplied in the request, the data in the response  body shall have been transformed according to the rules specified in  clauses 5.2.2 and 5.3.2 of ETSI GS NFV-SOL 013, respectively. If the NFV-MANO functional entity supports alternative N°2 (paging)  according to clause 5.4.2.1 of ETSI GS NFV-SOL 013 for this resource,  inclusion of the Link HTTP header in this response shall follow the  provisions in clause 5.4.2.3 of ETSI GS NFV-SOL 013. ", response = PeerEntity.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "400 BAD REQUEST Shall be returned upon the following errors:    - Invalid attribute-based filtering expression.     The response body shall contain a ProblemDetails structure, in which      the \"detail\" attribute should convey more information about the error.   - Invalid attribute selector.     The response body shall contain a ProblemDetails structure, in which      the \"detail\" attribute should convey more information about the error.   - Response too big.     If the NFV-MANO functional entity supports alternative N°1 (error)      according to clause 5.4.2.1 of ETSI GS NFV-SOL 013 for this resource,      this error response shall follow the provisions in clause 5.4.2.2 of      ETSI GS NFV-SOL 013. ", response = ProblemDetails.class),
			@ApiResponse(code = 401, message = "401 UNAUTHORIZED If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "403 FORBIDDEN If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided. It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class),
			@ApiResponse(code = 404, message = "404 NOT FOUND If the API producer did not find a current representation for the resource addressed by the URI passed in the request or is not willing to disclose that one exists, it shall respond with this response code. The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. This response code is not appropriate in case the resource addressed by the URI is a container resource which is designed to contain child resources, but does not contain any child resource at the time the request is received. For a GET request to an existing empty container resource, a typical response contains a 200 OK response code and a payload body with an empty array. ", response = ProblemDetails.class),
			@ApiResponse(code = 405, message = "405 METHOD NOT ALLOWED If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "406 NOT ACCEPTABLE If the \"Accept\" HTTP header does not contain at least one name of a content type that is acceptable to the API producer, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 422, message = "422 UNPROCESSABLE ENTITY If the payload body of a request contains syntactically correct data (e.g. well-formed JSON) but the data cannot be processed (e.g. because it fails validation against a schema), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. This error response code is only applicable for methods that have a request body. ", response = ProblemDetails.class),
			@ApiResponse(code = 500, message = "500 INTERNAL SERVER ERROR If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class),
			@ApiResponse(code = 503, message = "503 SERVICE UNAVAILABLE If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 for the use of the \"Retry-After\" HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 504, message = "504 GATEWAY TIMEOUT If the API producer encounters a timeout while waiting for a response from an upstream server (i.e. a server that the API producer communicates with when fulfilling a request), it should respond with this response code. ", response = ProblemDetails.class) })
	@RequestMapping(value = "/peer_entities", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<PeerEntity>> peerEntitiesGet(
			@ApiParam(value = "Marker to obtain the next page of a paged response. Shall be supported by  the NFV-MANO functional entity if the entity supports alternative 2 (paging)  according to clause 5.4.2.1 of ETSI GS NFV-SOL 013 for this resource. ") @Valid @RequestParam(value = "nextpage_opaque_marker", required = false) final String nextpageOpaqueMarker);

	@ApiOperation(value = "", nickname = "peerEntitiesPeerEntityIdDelete", notes = "Ddeletes an individual peer entity resource. By deleting such  a resource in the producer NFV-MANO functional entity representing  a peer NFV-MANO entity, the configuration and information with  regards to such peer entity is deleted. Consequently, the peering  relationship between the producer NFV-MANO functional entity and  the peer entity is terminated, i.e., the producer NFV-MANO functional  entity does not have the necessary information to communicate/interact  with the peer entity. This method shall follow the provisions specified in the tables  5.5.14.3.5-1 and 5.5.14.3.5-2 for URI query parameters, request  and response data structures, and response codes. ", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "204 NO CONTENT Shall be returned when the \"Individual peer entity\" resource  has been deleted successfully. The response body shall be empty. "),
			@ApiResponse(code = 409, message = "409 CONFLICT. Shall be returned upon the following error: The operation cannot be  executed currently, due to a conflict with the state of the  \"Individual peer entity\" resource. Typically, this is due to the fact that another operation is ongoing. The response body shall contain a ProblemDetails structure, in which  the \"detail\" attribute should convey more information about the error. ", response = ProblemDetails.class),
			@ApiResponse(code = 412, message = "412 PRECONDITION FAILED Shall be returned upon the following error: A precondition given in  an HTTP request header is not fulfilled. Typically, this is due to an ETag mismatch, indicating that the  resource was modified by another entity. The response body should contain a ProblemDetails structure, in which  the \"detail\" attribute should convey more information about the error. ", response = ProblemDetails.class),
			@ApiResponse(code = 401, message = "401 UNAUTHORIZED If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "403 FORBIDDEN If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided. It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class),
			@ApiResponse(code = 404, message = "404 NOT FOUND If the API producer did not find a current representation for the resource addressed by the URI passed in the request or is not willing to disclose that one exists, it shall respond with this response code. The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. This response code is not appropriate in case the resource addressed by the URI is a container resource which is designed to contain child resources, but does not contain any child resource at the time the request is received. For a GET request to an existing empty container resource, a typical response contains a 200 OK response code and a payload body with an empty array. ", response = ProblemDetails.class),
			@ApiResponse(code = 405, message = "405 METHOD NOT ALLOWED If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "406 NOT ACCEPTABLE If the \"Accept\" HTTP header does not contain at least one name of a content type that is acceptable to the API producer, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 422, message = "422 UNPROCESSABLE ENTITY If the payload body of a request contains syntactically correct data (e.g. well-formed JSON) but the data cannot be processed (e.g. because it fails validation against a schema), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. This error response code is only applicable for methods that have a request body. ", response = ProblemDetails.class),
			@ApiResponse(code = 500, message = "500 INTERNAL SERVER ERROR If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class),
			@ApiResponse(code = 503, message = "503 SERVICE UNAVAILABLE If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 for the use of the \"Retry-After\" HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 504, message = "504 GATEWAY TIMEOUT If the API producer encounters a timeout while waiting for a response from an upstream server (i.e. a server that the API producer communicates with when fulfilling a request), it should respond with this response code. ", response = ProblemDetails.class) })
	@RequestMapping(value = "/peer_entities/{peerEntityId}", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> peerEntitiesPeerEntityIdDelete(@ApiParam(value = "Identifier of the peer entity", required = true) @PathVariable("peerEntityId") final String peerEntityId);

	@ApiOperation(value = "", nickname = "peerEntitiesPeerEntityIdGet", notes = "Retrieves information and configuration hold in the producer NFV-MANO  functional entity with regards to a peer entity by reading an individual  peer entity resource. This method shall follow the provisions specified in the tables  5.5.14.3.2-1 and 5.5.14.3.2-2 for URI query parameters, request  and response data structures, and response codes. ", response = PeerEntity.class, tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "200 OK Shall be returned when information about an individual peer  functional entity has been read successfully. The response body shall contain a resource representation of  the peer functional entity, as defined in clause 5.6.2.15. ", response = PeerEntity.class),
			@ApiResponse(code = 400, message = "400 BAD REQUEST 400 code can be returned in the following specified cases, the specific cause has to be proper specified in the \"ProblemDetails\" structure to be returned. If the request is malformed or syntactically incorrect (e.g. if the request URI contains incorrect query parameters or the payload body contains a syntactically incorrect data structure), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If the response to a GET request which queries a container resource would be so big that the performance of the API producer is adversely affected, and the API producer does not support paging for the affected resource, it shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If there is an application error related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. If the request contains a malformed access token, the API producer should respond with this response. The details of the error shall be returned in the WWW Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. The use of this HTTP error response code described above is applicable to the use of the OAuth 2.0 for the authorization of API requests and notifications, as defined in clauses 4.5.3.3 and 4.5.3.4. ", response = ProblemDetails.class),
			@ApiResponse(code = 401, message = "401 UNAUTHORIZED If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "403 FORBIDDEN If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided. It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class),
			@ApiResponse(code = 404, message = "404 NOT FOUND If the API producer did not find a current representation for the resource addressed by the URI passed in the request or is not willing to disclose that one exists, it shall respond with this response code. The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. This response code is not appropriate in case the resource addressed by the URI is a container resource which is designed to contain child resources, but does not contain any child resource at the time the request is received. For a GET request to an existing empty container resource, a typical response contains a 200 OK response code and a payload body with an empty array. ", response = ProblemDetails.class),
			@ApiResponse(code = 405, message = "405 METHOD NOT ALLOWED If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "406 NOT ACCEPTABLE If the \"Accept\" HTTP header does not contain at least one name of a content type that is acceptable to the API producer, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 422, message = "422 UNPROCESSABLE ENTITY If the payload body of a request contains syntactically correct data (e.g. well-formed JSON) but the data cannot be processed (e.g. because it fails validation against a schema), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. This error response code is only applicable for methods that have a request body. ", response = ProblemDetails.class),
			@ApiResponse(code = 500, message = "500 INTERNAL SERVER ERROR If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class),
			@ApiResponse(code = 503, message = "503 SERVICE UNAVAILABLE If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 for the use of the \"Retry-After\" HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 504, message = "504 GATEWAY TIMEOUT If the API producer encounters a timeout while waiting for a response from an upstream server (i.e. a server that the API producer communicates with when fulfilling a request), it should respond with this response code. ", response = ProblemDetails.class) })
	@RequestMapping(value = "/peer_entities/{peerEntityId}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<PeerEntity> peerEntitiesPeerEntityIdGet(@ApiParam(value = "Identifier of the peer entity", required = true) @PathVariable("peerEntityId") final String peerEntityId);

	@ApiOperation(value = "", nickname = "peerEntitiesPeerEntityIdPatch", notes = "Modifies configuration and information of the producer NFV-MANO  functional entity with regards to a peer functional entity by  updating the corresponding \"Individual peer entity\" resource. Changes to the configurable parameters of the corresponding peer  entity are applied to the information managed by the producer  NFV-MANO functional entity and reflected in the representation  of this resource. This method shall follow the provisions specified in the tables  5.5.14.3.4-1 and 5.5.14.3.4-2 for URI query parameters, request  and response data structures, and response codes. ", response = PeerEntityConfigModifications.class, tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "200 OK Shall be returned when the request has been accepted and completed. The response body shall contain a representation of the attribute  modifications for the \"Individual peer entity\" resource, as defined  in clause 5.6.2.17. ", response = PeerEntityConfigModifications.class),
			@ApiResponse(code = 409, message = "409 CONFLICT. Shall be returned upon the following error: The operation cannot be  executed currently, due to a conflict with the state of the  \"Individual peer entity\" resource. Typically, this is due to the fact that another operation is ongoing. The response body shall contain a ProblemDetails structure, in which  the \"detail\" attribute should convey more information about the error. ", response = ProblemDetails.class),
			@ApiResponse(code = 412, message = "412 PRECONDITION FAILED Shall be returned upon the following error: A precondition given in  an HTTP request header is not fulfilled. Typically, this is due to an ETag mismatch, indicating that the  resource was modified by another entity. The response body should contain a ProblemDetails structure, in which  the \"detail\" attribute should convey more information about the error. ", response = ProblemDetails.class),
			@ApiResponse(code = 401, message = "401 UNAUTHORIZED If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "403 FORBIDDEN If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided. It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class),
			@ApiResponse(code = 404, message = "404 NOT FOUND If the API producer did not find a current representation for the resource addressed by the URI passed in the request or is not willing to disclose that one exists, it shall respond with this response code. The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. This response code is not appropriate in case the resource addressed by the URI is a container resource which is designed to contain child resources, but does not contain any child resource at the time the request is received. For a GET request to an existing empty container resource, a typical response contains a 200 OK response code and a payload body with an empty array. ", response = ProblemDetails.class),
			@ApiResponse(code = 405, message = "405 METHOD NOT ALLOWED If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "406 NOT ACCEPTABLE If the \"Accept\" HTTP header does not contain at least one name of a content type that is acceptable to the API producer, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 422, message = "422 UNPROCESSABLE ENTITY If the payload body of a request contains syntactically correct data (e.g. well-formed JSON) but the data cannot be processed (e.g. because it fails validation against a schema), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. This error response code is only applicable for methods that have a request body. ", response = ProblemDetails.class),
			@ApiResponse(code = 500, message = "500 INTERNAL SERVER ERROR If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class),
			@ApiResponse(code = 503, message = "503 SERVICE UNAVAILABLE If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 for the use of the \"Retry-After\" HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 504, message = "504 GATEWAY TIMEOUT If the API producer encounters a timeout while waiting for a response from an upstream server (i.e. a server that the API producer communicates with when fulfilling a request), it should respond with this response code. ", response = ProblemDetails.class) })
	@RequestMapping(value = "/peer_entities/{peerEntityId}", produces = { "application/json" }, consumes = { "application/merge-patch+json" }, method = RequestMethod.PATCH)
	ResponseEntity<PeerEntityConfigModifications> peerEntitiesPeerEntityIdPatch(
			@ApiParam(value = "Parameters for the modification of configuration parameters of  the peer functional entity, as defined in clause 5.6.2.16The Content-Type header shall be set to 'application/merge-patch+json'  according to IETF RFC 7396.", required = true) @Valid @RequestBody final PeerEntityConfigModificationRequest body,
			@ApiParam(value = "Identifier of the peer entity", required = true) @PathVariable("peerEntityId") final String peerEntityId);

	@ApiOperation(value = "", nickname = "peerEntitiesPost", notes = "Creates in the producer NFV-MANO functional entity a new peer  entity resource which contains configuration and information  with regards to the peer functional entity. This method shall follow the provisions specified in the tables  5.5.13.3.1-1 and 5.5.13.3.1-2 for URI query parameters, request  and response data structures, and response codes. ", response = PeerEntity.class, tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "201 CREATED Shall be returned when a new \"Individual peer entity\" resource  has been created successfully. The response body shall contain a representation of the created  resource with regards to a peer entity, as defined in  clause 5.6.2.15. The HTTP response shall include a “Location” HTTP header that  points to the created \"Individual peer entity\" resource. ", response = PeerEntity.class),
			@ApiResponse(code = 400, message = "400 BAD REQUEST 400 code can be returned in the following specified cases, the specific cause has to be proper specified in the \"ProblemDetails\" structure to be returned. If the request is malformed or syntactically incorrect (e.g. if the request URI contains incorrect query parameters or the payload body contains a syntactically incorrect data structure), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If the response to a GET request which queries a container resource would be so big that the performance of the API producer is adversely affected, and the API producer does not support paging for the affected resource, it shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If there is an application error related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. If the request contains a malformed access token, the API producer should respond with this response. The details of the error shall be returned in the WWW Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. The use of this HTTP error response code described above is applicable to the use of the OAuth 2.0 for the authorization of API requests and notifications, as defined in clauses 4.5.3.3 and 4.5.3.4. ", response = ProblemDetails.class),
			@ApiResponse(code = 401, message = "401 UNAUTHORIZED If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", response = ProblemDetails.class),
			@ApiResponse(code = 403, message = "403 FORBIDDEN If the API consumer is not allowed to perform a particular request to a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided. It should include in the \"detail\" attribute information about the source of the problem, and may indicate how to solve it. ", response = ProblemDetails.class),
			@ApiResponse(code = 404, message = "404 NOT FOUND If the API producer did not find a current representation for the resource addressed by the URI passed in the request or is not willing to disclose that one exists, it shall respond with this response code. The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. This response code is not appropriate in case the resource addressed by the URI is a container resource which is designed to contain child resources, but does not contain any child resource at the time the request is received. For a GET request to an existing empty container resource, a typical response contains a 200 OK response code and a payload body with an empty array. ", response = ProblemDetails.class),
			@ApiResponse(code = 405, message = "405 METHOD NOT ALLOWED If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 406, message = "406 NOT ACCEPTABLE If the \"Accept\" HTTP header does not contain at least one name of a content type that is acceptable to the API producer, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 422, message = "422 UNPROCESSABLE ENTITY If the payload body of a request contains syntactically correct data (e.g. well-formed JSON) but the data cannot be processed (e.g. because it fails validation against a schema), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. This error response code is only applicable for methods that have a request body. ", response = ProblemDetails.class),
			@ApiResponse(code = 500, message = "500 INTERNAL SERVER ERROR If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", response = ProblemDetails.class),
			@ApiResponse(code = 503, message = "503 SERVICE UNAVAILABLE If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 for the use of the \"Retry-After\" HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", response = ProblemDetails.class),
			@ApiResponse(code = 504, message = "504 GATEWAY TIMEOUT If the API producer encounters a timeout while waiting for a response from an upstream server (i.e. a server that the API producer communicates with when fulfilling a request), it should respond with this response code. ", response = ProblemDetails.class) })
	@RequestMapping(value = "/peer_entities", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<PeerEntity> peerEntitiesPost(@ApiParam(value = "The peer entity creation parameters, as defined in clause 5.6.2.14.", required = true) @Valid @RequestBody CreatePeerEntityRequest body);

}
