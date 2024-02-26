package fr.btn.sdbmrestapi.metier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleSearch {
    private String name;
    private int volume;
    private float titrageMin;
    private float titrageMax;
    private Couleur couleur;
    private Type type;
    private Marque marque;
    private Fabricant fabricant;
    private Pays pays;
    private  Continent continent;
    private int offset;
    private int pageSize;
    private int rowCount;
}
