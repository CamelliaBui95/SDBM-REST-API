package fr.btn.sdbmrestapi.metier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private int id;
    private String name;
    private int volume;
    private float titrage;
    private float prixAchat;
    private int stock;
    private Couleur couleur;
    private Type type;
    private Marque marque;
}
