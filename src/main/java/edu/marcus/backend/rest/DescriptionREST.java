package edu.marcus.backend.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.marcus.backend.business.DescriptionBC;
import edu.marcus.backend.business.model.Description;
import edu.marcus.backend.rest.model.DescriptionCover;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("description")
public class DescriptionREST {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DescriptionREST.class);
	
	@Inject
	private DescriptionBC descriptionBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Retrieve API description",
			tags = {"Description"},
			responses = {
					@ApiResponse(
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = DescriptionCover.class)
					)
			),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "405", description = "Method not allowed") }
	)
	public Response getDescriptio() {
		try {
			Description description = descriptionBC.retrieve();
			return Response.ok(new DescriptionCover(description), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return Response.serverError().build();
		}
	}

}
