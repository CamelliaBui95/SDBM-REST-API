package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.metier.Continent;
import fr.btn.sdbmrestapi.metier.Fabricant;
import fr.btn.sdbmrestapi.metier.Marque;
import fr.btn.sdbmrestapi.metier.Pays;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/marques")
@Produces(MediaType.APPLICATION_JSON)
public class MarqueResource {
    @GET
    public Response getAll(@QueryParam("name") String name,
                           @QueryParam("marqueId") int idMarque,
                           @QueryParam("fabricantId") int idFabricant,
                           @QueryParam("paysId") int idPays,
                           @QueryParam("continentId") int idContinent) {

        name = name == null ? "" : name;
        Marque queryMarque = new Marque(idMarque, name, new Pays(idPays, "", new Continent(idContinent, "")), new Fabricant(idFabricant, ""));

        List<Marque> marques = DAOFactory.getMarqueDAO().getLike(queryMarque);

        return Response.ok(new GenericEntity<List<Marque>>(marques){}).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Marque marque) {
        if(marque == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getMarqueDAO().post(marque))
            return Response.ok(marque).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response put(@PathParam("id") Integer id, Marque marque) {
        if(id == null || id == 0 || marque == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(marque.getId() != id)
            return Response.status(Response.Status.CONFLICT).build();

        if(DAOFactory.getMarqueDAO().update(marque))
            return Response.ok(marque).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") Integer id) {
        if(id == null || id == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getMarqueDAO().delete(new Marque(id, "", new Pays(), new Fabricant())))
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
