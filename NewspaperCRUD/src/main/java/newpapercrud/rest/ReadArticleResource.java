package newspapercrud.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import newspapercrud.domain.model.ReaderArticleDTO;
import newspapercrud.domain.service.ReadArticleService;

@Path("/articles/readers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReadArticleResource {

    @Inject
    private ReadArticleService readArticleService;

    @POST
    public Response addRating(ReaderArticleDTO readerArticle) {
        try {
            int result = readArticleService.addReadArticle(readerArticle);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding rating: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response updateRating(ReaderArticleDTO readerArticle) {
        try {
            readArticleService.updateReadArticle(readerArticle);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating rating: " + e.getMessage()).build();
        }
    }

    @DELETE
    public Response deleteRating(ReaderArticleDTO readerArticle) {
        try {
            boolean deleted = readArticleService.delete(readerArticle);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Rating not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting rating: " + e.getMessage()).build();
        }
    }
}
package newspapercrud.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/")
public class RestApplication extends Application {
}

