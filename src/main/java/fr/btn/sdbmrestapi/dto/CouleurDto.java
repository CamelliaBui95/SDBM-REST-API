package fr.btn.sdbmrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.btn.sdbmrestapi.hateos.HateOs;
import fr.btn.sdbmrestapi.metier.Couleur;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CouleurDto extends HateOs {
    @JsonIgnore
    private UriBuilder uriBuilder;
    @JsonProperty(index = 1)
    private int id;
    @JsonProperty(index = 2)
    private String name;
    @JsonIgnore
    private static String RESOURCE_PATH = "couleurs/";

    public CouleurDto() {
        this.id = 0;
        this.name = "";
    }

    public CouleurDto(Couleur couleur, UriBuilder uriBuilder) {
        this.id = couleur.getId();
        this.name = couleur.getName();
        this.uriBuilder = uriBuilder;

        addLink("SELF", HttpMethod.GET, uriBuilder.clone().path(RESOURCE_PATH).build(), couleur);
        if(couleur.getNbArticles() == 0)
            addLink("SELF", HttpMethod.DELETE, uriBuilder.clone().path(RESOURCE_PATH).build(), couleur);

        addLink("ALL", HttpMethod.GET, uriBuilder.clone().path(RESOURCE_PATH).build());
        addLink("NEW", HttpMethod.POST, uriBuilder.clone().path(RESOURCE_PATH).build(), new Couleur(0, "New Color"));
    }

    public static List<CouleurDto> getDtoList(List<Couleur> couleurs, UriBuilder uriBuilder) {
        List<CouleurDto> dtoList = new ArrayList<>();

        for(Couleur couleur : couleurs)
            dtoList.add(new CouleurDto(couleur, uriBuilder));

        return dtoList;
    }

}

// "all"
    // GET
// "self"
    // GET, DELETE