package newspapercrud.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import newspapercrud.domain.model.ArticleDTO;
import newspapercrud.domain.service.ArticleService;

import java.util.List;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    private ArticleService articleService;

    @GET
    public Response getAllArticles() {
        try {
            List<ArticleDTO> articles = articleService.getArticles();
            return Response.ok(articles).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching articles: " + e.getMessage()).build();
        }
    }

    @POST
    public Response addArticle(ArticleDTO article) {
        try {
            int id = articleService.addArticle(article);
            return Response.ok(id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding article: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response updateArticle(ArticleDTO article) {
        try {
            articleService.updateArticle(article);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating article: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") int id, @QueryParam("confirm") @DefaultValue("false") boolean confirm) {
        try {
            articleService.deleteArticle(id, confirm);
            return Response.noContent().build();
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("readers")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("This article has associated readers. Do you want to delete it anyway?").build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting article: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/readers")
    public Response getReadersByArticle(@PathParam("id") int articleId) {
        try {
            return Response.ok(articleService.getArticles()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching readers: " + e.getMessage()).build();
        }
    }
}

