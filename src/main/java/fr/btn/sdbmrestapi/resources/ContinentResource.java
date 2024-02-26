package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.metier.Continent;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/continents")
@Produces(MediaType.APPLICATION_JSON)
public class ContinentResource {
    @GET
    public Response getAll() {
        List<Continent> continents = DAOFactory.getContinentDAO().getAll();
        return Response.ok(new GenericEntity<List<Continent>>(continents) {}).build();
    }


}
