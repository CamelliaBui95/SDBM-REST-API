package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.metier.Couleur;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/couleurs")
@Produces(MediaType.APPLICATION_JSON)
public class CouleurResource {

    @GET
    public Response getAll() {
        List<Couleur> couleurs = DAOFactory.getCouleurDAO().getAll();
        return Response.ok(new GenericEntity<List<Couleur>>(couleurs) {}).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Integer id) {
        Couleur couleur = DAOFactory.getCouleurDAO().getById(id);
        if(couleur != null && couleur.getId() != 0)
            return Response.ok(couleur).build();
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Couleur couleur) {
        if(couleur == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getCouleurDAO().post(couleur))
            return Response.ok(couleur).status(Response.Status.CREATED).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, Couleur couleur) {

        if(couleur == null || id == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(id != couleur.getId())
            return Response.status(Response.Status.CONFLICT).build();

        if(DAOFactory.getCouleurDAO().update(couleur))
            return Response.ok(couleur).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Integer id) {
        if(id == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getCouleurDAO().delete(new Couleur(id, "")))
            return Response.status(204).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}

//extract().as(Something.Class)