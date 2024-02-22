package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.metier.Couleur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CouleurDAO extends DAO<Couleur, Couleur>{
    @Override
    public ArrayList<Couleur> getAll() {
        ArrayList<Couleur> couleurs = new ArrayList<>();
        String request = "SELECT * FROM COULEUR";
        try {
            Statement stm = this.connection.createStatement();
            ResultSet rs = stm.executeQuery(request);
            while(rs.next())
                couleurs.add(new Couleur(rs.getInt(1), rs.getString(2)));
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return couleurs;
    }

    @Override
    public ArrayList<Couleur> getLike(Couleur couleur) {
        return null;
    }

    @Override
    public Couleur getById(int id) {
        String rq = "SELECT * FROM COULEUR WHERE ID_COULEUR = ?";
        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            Couleur couleur = new Couleur();

            while(rs.next()) {
                couleur.setId(rs.getInt("ID_COULEUR"));
                couleur.setNomCouleur(rs.getString("NOM_COULEUR"));
            }
            rs.close();
            return couleur;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Couleur couleur) {
        return false;
    }

    @Override
    public boolean post(Couleur couleur) {
        String rq = "INSERT INTO COULEUR (NOM_COULEUR) VALUES (?)";
        try {
            PreparedStatement stm = connection.prepareStatement(rq, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, couleur.getNomCouleur());

            ResultSet rs = stm.executeQuery();
            while(rs.next())
                couleur.setId(rs.getInt(1));

            rs.close();
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean delete(Couleur couleur) {
        return false;
    }
}
