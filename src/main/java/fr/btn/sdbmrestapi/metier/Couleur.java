package fr.btn.sdbmrestapi.metier;

import java.util.Objects;

public class Couleur {
    private int id;
    private String name;
    public Couleur(int id, String nomCouleur) {
        this.id = id;
        this.name = nomCouleur;
    }

    public Couleur() {
        this.id = 0;
        this.name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couleur couleur = (Couleur) o;
        return id == couleur.id && Objects.equals(name, couleur.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
