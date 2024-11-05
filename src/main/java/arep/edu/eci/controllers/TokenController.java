package arep.edu.eci.controllers;

import arep.edu.eci.dtos.TokenDto;
import arep.edu.eci.models.User;
import arep.edu.eci.security.TokenService;
import arep.edu.eci.services.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * TokenController class
 * This class is responsible for handling the token related operations
 * @author Andres Felipe
 */
@Path("/security")
public class TokenController {

    @Inject
    TokenService tokenService;

    @Inject
    UserService userService;

    /**
     * login method
     * This method logs in a user
     * @param user the user to be logged in
     * @return Response with the token
     */
    @POST
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Login(User user) {
        if(userService.verifyPassword(user.getUserName(),user.getHashedPassword())){
            TokenDto token = tokenService.generateToken(user);
            return Response.status(200).entity(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
