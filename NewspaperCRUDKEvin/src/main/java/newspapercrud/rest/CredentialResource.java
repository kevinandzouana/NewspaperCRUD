package newspapercrud.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import newspapercrud.domain.model.TypeDTO;
import newspapercrud.domain.service.TypeService;

import java.util.List;

@Path("/types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TypeResource {

    @Inject
    private TypeService typeService;

    @GET
    public Response getAllTypes() {
        try {
            List<TypeDTO> types = typeService.getAll();
            return Response.ok(types).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching types: " + e.getMessage()).build();
        }
