package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.dao.DAOFactory;
import fr.btn.sdbmrestapi.metier.*;
import fr.btn.sdbmrestapi.wrapper.ArticleWrapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.List;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
public class ArticleResource {
    @GET
    public Response getLike(@QueryParam("name") String name,
                            @QueryParam("volume") int volume,
                            @QueryParam("titrageMin") float titrageMin,
                            @QueryParam("titrageMax") float titrageMax,
                            @QueryParam("couleurId") int idCouleur,
                            @QueryParam("typeId") int idType,
                            @QueryParam("marqueId") int idMarque,
                            @QueryParam("fabricantId") int idFabricant,
                            @QueryParam("paysId") int idPays,
                            @QueryParam("continentId") int idContinent,
                            @QueryParam("offset") int offset,
                            @QueryParam("pageSize") int pageSize) {

        name = name == null ? "" : name;

        ArticleSearch articleSearch = ArticleSearch.builder()
                                                    .name(name)
                                                    .volume(volume)
                                                    .titrageMin(titrageMin)
                                                    .titrageMax(titrageMax == 0 ? 30 : titrageMax)
                                                    .marque(new Marque(idMarque, "", new Pays(), new Fabricant()))
                                                    .couleur(new Couleur(idCouleur, ""))
                                                    .type(new Type(idType, ""))
                                                    .fabricant(new Fabricant(idFabricant, ""))
                                                    .pays(new Pays(idPays, "", new Continent()))
                                                    .continent(new Continent(idContinent, ""))
                                                    .offset(offset)
                                                    .pageSize(pageSize)
                                                    .build();


        List<Article> articles = DAOFactory.getArticleDAO().getLike(articleSearch);

        ArticleWrapper wrapper = ArticleWrapper.builder()
                .data(articles)
                .total(articleSearch.getRowCount())
                .offset(offset)
                .pageSize(pageSize)
                .build();

        return Response.ok(wrapper).build();
    }
}
