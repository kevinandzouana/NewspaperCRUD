package newspapercrud.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import newspapercrud.domain.model.NewspaperDTO;
import newspapercrud.domain.service.NewspaperService;

import java.util.List;

@Path("/newspapers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewspaperResource {

    @Inject
    private NewspaperService newspaperService;

    @GET
    public Response getAllNewspapers() {
        try {
            List<NewspaperDTO> newspapers = newspaperService.getAll();
            return Response.ok(newspapers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching newspapers: " + e.getMessage()).build();
        }
    }
}

