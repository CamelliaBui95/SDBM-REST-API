package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.metier.Type;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/types")
@Produces(MediaType.APPLICATION_JSON)
public class TypeResource {
    @GET
    public Response getAll() {
      List<Type> types = DAOFactory.getTypeDAO().getAll();
      return Response.ok(new GenericEntity<List<Type>>(types){}).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
        if(id == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        Type type = DAOFactory.getTypeDAO().getById(id);

        if(type == null || type.getId() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(type).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Type type) {
        if(type == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getTypeDAO().post(type))
            return Response.ok(type).status(Response.Status.CREATED).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response put(@PathParam("id") int id, Type type) {
        if(id == 0 || type == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(id != type.getId())
            return Response.status(Response.Status.CONFLICT).build();

        if(DAOFactory.getTypeDAO().update(type))
            return Response.ok(type).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") int id) {
        if(id == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(DAOFactory.getTypeDAO().delete(new Type(id, "")))
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    }
}
