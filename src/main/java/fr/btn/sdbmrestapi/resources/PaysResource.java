package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.metier.Continent;
import fr.btn.sdbmrestapi.metier.Pays;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pays")
@Produces(MediaType.APPLICATION_JSON)
public class PaysResource {
    @GET
    public Response getPaysList(@QueryParam("name") String name,
                                @QueryParam("paysId") int idPays,
                                @QueryParam("continentId") int idContinent) {

        Pays queryPays = new Pays(idPays, name, new Continent(idContinent, ""));

        List<Pays> pays = DAOFactory.getPaysDAO().getLike(queryPays);
        return Response.ok(new GenericEntity<List<Pays>>(pays){}).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postPays(Pays pays) {
        if(pays == null || pays.getContinent() == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getPaysDAO().post(pays))
            return Response.ok(pays).status(Response.Status.CREATED).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updatePays(@PathParam("id") int id, Pays pays) {
        if(id == 0 || pays == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(pays.getId() != id)
            return Response.status(Response.Status.CONFLICT).build();

        if(DAOFactory.getPaysDAO().update(pays))
            return Response.ok(pays).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") int id){
        if(id == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getPaysDAO().delete(new Pays(id, "", null)))
            return Response.noContent().build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

}
