package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.hateos.HateOs;
import fr.btn.sdbmrestapi.hateos.Link;
import fr.btn.sdbmrestapi.security.Argon2;
import fr.btn.sdbmrestapi.security.MyToken;
import fr.btn.sdbmrestapi.security.User;
import fr.btn.sdbmrestapi.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name="Identification")
public class IdentificationResource {
    @Context
    UriInfo requestInfo;
    @POST
    @Path("login")
    public Response login(User user) {
        if(user == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(isUserValid(user))
            return Response.ok().header(HttpHeaders.AUTHORIZATION, MyToken.generate(user)).build();

        HateOs hateOs = new HateOs();
        UriBuilder uriBuilder = UriBuilder
                .fromUri(requestInfo.getBaseUri());

        URI forgotPasswordUri = uriBuilder.clone().path("auth").path(user.getLogin()).path("forgotPassword").build();
        URI forgotLoginUri = uriBuilder.clone().path("auth").path("forgotLogin").queryParam("email", "yourEmail").build();

        hateOs.addLink(new Link("Forgot Login", HttpMethod.GET, forgotLoginUri));
        hateOs.addLink(new Link("Forgot Password", HttpMethod.GET, forgotPasswordUri));

        return Response.ok(hateOs).status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("register")
    public Response register(User user) {
        if(!validateSubmittedUser(user))
            return Response.status(Response.Status.BAD_REQUEST).build();

        String rawPassword = user.getPassword();
        user.setPassword(Argon2.getHashedPassword(rawPassword));

        if(DAOFactory.getUserDAO().post(user))
            return Response.ok(new UserDto(user.getLogin(), user.getEmail())).header(HttpHeaders.AUTHORIZATION, MyToken.generate(user)).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    private boolean isUserValid(User user) {
        User savedUser = DAOFactory.getUserDAO().getUser(user);

        if(savedUser == null || savedUser.getPassword() == null)
            return false;

        String rawPassword = user.getPassword();
        String hashedPassword = savedUser.getPassword();

        return Argon2.validate(rawPassword, hashedPassword);
    }

    private boolean validateSubmittedUser(User user) {
        if(user == null)
            return false;

        boolean case1 = user.getLogin() != null && !user.getLogin().isEmpty();
        boolean case2 = user.getPassword() != null && !user.getPassword().isEmpty();
        boolean case3 = user.getEmail() != null && !user.getEmail().isEmpty();

        return case1 && case2 && case3;
    }
}
