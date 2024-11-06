package arep.edu.eci.controllers;


import arep.edu.eci.models.User;
import arep.edu.eci.services.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * UserController class
 * This class is responsible for handling the requests related to the users
 * @author Andres Felipe
 */
@Path("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    /**
     * getUsers method
     * This method returns a list of users
     * @return Response with the list of users
     */
    @GET
    @RolesAllowed({"Admin", "User"})
    public Response getUsers() {
        return Response.status(200).entity(userService.getUsers()).build();
    }


    /**
     * createUser method
     * This method creates a new user
     * @param user the user to be created
     * @return Response with the created user
     */
    @POST
    @PermitAll
    public Response createUser(User user) {
        userService.createUser(user);
        return Response.status(201).entity(user).build();
    }

    /**
     * getUser method
     * This method returns a user with the given id
     * @param id the id of the user to be returned
     * @return Response with the user with the given id
     */
    @GET
    @RolesAllowed("Admin")
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id) {
        User user = userService.getUser(id);
        if (user == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(user).build();
    }

    /**
     * updateUser method
     * This method updates a user with the given id
     * @param id the id of the user to be updated
     * @param user the user to be updated
     * @return Response with the updated user
     */

    @PUT
    @RolesAllowed({"Admin", "User"})
    @Path("/{id}")
    public Response updateUser(@PathParam("id") String id, User user) {
        User existingUser = User.findById(id);
        if (existingUser == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(userService.updateUser(existingUser, user)).build();
    }

    /**
     * deleteUser method
     * This method deletes a user with the given id
     * @param id the id of the user to be deleted
     * @return Response with the deleted user
     */
    @DELETE
    @RolesAllowed("Admin")
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        User existingUser = userService.getUser(id);
        if (existingUser == null) {
            return Response.status(404).build();
        }
        userService.deleteUser(id);
        return Response.status(204).build();
    }
}

