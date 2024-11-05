package arep.edu.eci.controllers;
import arep.edu.eci.models.Post;
import arep.edu.eci.services.PostService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * PostController class
 * This class is responsible for handling the requests related to the posts
 * @author Andres Felipe
 */
@Path("/api/v1/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    @Inject
    PostService postService;

    /**
     * getPosts method
     * This method returns a list of posts
     * @return Response with the list of posts
     */
    @GET
    @RolesAllowed({"Admin", "User"})
    public Response getPosts() {
        List<Post> posts = postService.getPosts();
        return Response.status(200).entity(posts).build();
    }

    /**
     * createPost method
     * This method creates a new post
     * @param post the post to be created
     * @return Response with the created post
     */
    @POST
    @RolesAllowed({"Admin", "User"})
    public Response createPost(Post post) {
        postService.createPost(post);
        return Response.status(201).entity(post).build();
    }

    /**
     * getPost method
     * This method returns a post with the given id
     * @param id the id of the post to be returned
     * @return Response with the post with the given id
     */
    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response getPost(@PathParam("id") String id) {
        Post post = postService.getPost(id);
        if (post == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(post).build();
    }

    /**
     * updatePost method
     * This method updates a post with the given id
     * @param id the id of the post to be updated
     * @param post the post to be updated
     * @return Response with the updated post
     */
    @PUT
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response updatePost(@PathParam("id") String id, Post post) {
        Post existingPost = Post.findById(id);
        if (existingPost == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(postService.updatePost(existingPost, post)).build();
    }

    /**
     * deletePost method
     * This method deletes a post with the given id
     * @param id the id of the post to be deleted
     * @return Response with the deleted post
     */
    @DELETE
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response deletePost(@PathParam("id") String id) {
        Post existingPost = postService.getPost(id);
        if (existingPost == null) {
            return Response.status(404).build();
        }
        postService.deletePost(id);
        return Response.status(204).build();
    }
}
