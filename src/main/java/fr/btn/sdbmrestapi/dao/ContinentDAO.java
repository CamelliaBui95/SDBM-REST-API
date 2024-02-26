package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.metier.Continent;
import fr.btn.sdbmrestapi.metier.Pays;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ContinentDAO extends DAO<Continent, Continent>{
    @Override
    public ArrayList<Continent> getAll() {
        ArrayList<Continent> continents = new ArrayList<>();
        String request = "SELECT * FROM CONTINENT";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(request);
            while(rs.next())
                continents.add(new Continent(rs.getInt("ID_CONTINENT"), rs.getString("NOM_CONTINENT")));
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return continents;
    }

    @Override
    public ArrayList<Continent> getLike(Continent objet) {
        return null;
    }

    @Override
    public Continent getById(int id) {
        return null;
    }

    @Override
    public boolean update(Continent objet) {
        return false;
    }

    @Override
    public boolean post(Continent object) {
        return false;
    }

    @Override
    public boolean delete(Continent object) {
        return false;
    }
}
