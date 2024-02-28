package fr.btn.sdbmrestapi.metier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class Couleur {
    private int id;
    private String name;
    @JsonIgnore
    private int nbArticles;
    public Couleur(int id, String nomCouleur) {
        this.id = id;
        this.name = nomCouleur;
    }

    public Couleur() {
        this.id = 0;
        this.name = "";
    }
}
