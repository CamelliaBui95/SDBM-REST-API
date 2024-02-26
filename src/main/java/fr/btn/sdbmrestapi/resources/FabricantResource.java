package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.metier.Fabricant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/fabricants")
@Produces(MediaType.APPLICATION_JSON)
public class FabricantResource {
    @GET
    public Response getAll() {
        List<Fabricant> fabricants = DAOFactory.getFabricantDAO().getAll();
        return Response.ok(new GenericEntity<List<Fabricant>>(fabricants){}).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Integer id) {
        if(id == null || id == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        Fabricant fabricant = DAOFactory.getFabricantDAO().getById(id);
        if(fabricant == null || fabricant.getId() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(fabricant).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Fabricant fabricant) {
        if(fabricant == null || fabricant.getName().isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getFabricantDAO().post(fabricant))
            return Response.ok(fabricant).status(Response.Status.CREATED).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response put(@PathParam("id") Integer id, Fabricant fabricant) {
        if(id == null || id == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(id != fabricant.getId())
            return Response.status(Response.Status.CONFLICT).build();

        if(DAOFactory.getFabricantDAO().update(fabricant))
            return Response.ok(fabricant).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        if(id == null || id == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getFabricantDAO().delete(new Fabricant(id, "")))
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
