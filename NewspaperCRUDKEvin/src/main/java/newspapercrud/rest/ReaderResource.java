package newspapercrud.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import newspapercrud.domain.model.ReaderArticleDTO;
import newspapercrud.domain.model.ReaderDTO;
import newspapercrud.domain.service.ReaderService;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReaderResource {

    @Inject
    private ReaderService readerService;

    @GET
    @Path("/readers")
    public Response getAllReaders() {
        try {
            List<ReaderDTO> readers = readerService.getAll();
            return Response.ok(readers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching readers: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/articles/{articleId}/readers")
    public Response getReadersByArticle(@PathParam("articleId") int articleId) {
        try {
            List<ReaderArticleDTO> readers = readerService.getAllReadersByArticle(articleId);
            return Response.ok(readers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching readers for article: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/articles/{readerId}/reader")
    public Response getReaderDetails(@PathParam("readerId") int readerId) {
        try {
            ReaderArticleDTO reader = readerService.get(readerId);
            return Response.ok(reader).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching reader details: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/readers")
    public Response addReader(ReaderDTO reader) {
        try {
            int id = readerService.addReader(reader);
            return Response.ok(id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding reader: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/readers/{id}")
    public Response deleteReader(@PathParam("id") int id) {
        try {
            readerService.deleteReader(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting reader: " + e.getMessage()).build();
        }
    }
}

