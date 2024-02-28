package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.dto.CouleurDto;
import fr.btn.sdbmrestapi.metier.Couleur;
import fr.btn.sdbmrestapi.security.Tokened;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/couleurs")
@Produces(MediaType.APPLICATION_JSON)
@Tag( name = "Couleur", description = "CRUD operations for Color table")
public class CouleurResource {
    @Context
    UriInfo request;

    @GET
    public Response getAll() {
        List<CouleurDto> couleurs = CouleurDto.getDtoList(DAOFactory.getCouleurDAO().getAll(), UriBuilder.fromUri(request.getBaseUri()));
        return Response.ok(new GenericEntity<List<CouleurDto>>(couleurs) {}).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Integer id) {
        Couleur couleur = DAOFactory.getCouleurDAO().getById(id);
        if(couleur != null && couleur.getId() != 0)
            return Response.ok(new CouleurDto(couleur, UriBuilder.fromUri(request.getBaseUri()))).build();
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Tokened
    public Response insert(Couleur couleur) {
        if(couleur == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getCouleurDAO().post(couleur))
            return Response.ok(new CouleurDto(couleur, UriBuilder.fromUri(request.getBaseUri()))).status(Response.Status.CREATED).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Tokened
    public Response update(@PathParam("id") Integer id, Couleur couleur) {
        if(couleur == null || id == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(id != couleur.getId())
            return Response.status(Response.Status.CONFLICT).build();

        if(DAOFactory.getCouleurDAO().update(couleur))
            return Response.ok(new CouleurDto(couleur, UriBuilder.fromUri(request.getBaseUri()))).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Tokened
    public Response deleteById(@PathParam("id") Integer id) {
        if(id == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getCouleurDAO().delete(new Couleur(id, "")))
            return Response.status(204).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}

//extract().as(Something.Class)