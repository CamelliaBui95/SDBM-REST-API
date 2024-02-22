package fr.btn.sdbmrestapi.metier;

import java.util.Objects;

public class Couleur {
    private int id;
    private String nomCouleur;
    public Couleur(int id, String nomCouleur) {
        this.id = id;
        this.nomCouleur = nomCouleur;
    }

    public Couleur() {
        this.id = 0;
        this.nomCouleur = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCouleur() {
        return nomCouleur;
    }

    public void setNomCouleur(String nomCouleur) {
        this.nomCouleur = nomCouleur;
    }

    @Override
    public String toString() {
        return nomCouleur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couleur couleur = (Couleur) o;
        return id == couleur.id && Objects.equals(nomCouleur, couleur.nomCouleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomCouleur);
    }
}
