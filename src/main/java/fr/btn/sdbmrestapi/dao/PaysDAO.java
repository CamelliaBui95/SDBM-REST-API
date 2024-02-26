package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.metier.Continent;
import fr.btn.sdbmrestapi.metier.Pays;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PaysDAO extends DAO<Pays, Pays>{
    @Override
    public ArrayList<Pays> getAll() {
        ArrayList<Pays> pays = new ArrayList<>();
        String request = "SELECT ID_PAYS,\n" +
                "\t\tNOM_PAYS,\n" +
                "\t\tC.ID_CONTINENT,\n" +
                "\t\tC.NOM_CONTINENT\n" +
                "FROM PAYS P\n" +
                "JOIN CONTINENT C\n" +
                "ON P.ID_CONTINENT = C.ID_CONTINENT;";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(request);
            while(rs.next()) {
                int idPays = rs.getInt(1);
                String nomPays = rs.getString(2);

                Continent continent = new Continent(rs.getInt("ID_CONTINENT"), rs.getString("NOM_CONTINENT"));

                pays.add(new Pays(idPays, nomPays, continent));
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return pays;
    }

    @Override
    public ArrayList<Pays> getLike(Pays pays) {
        ArrayList<Pays> paysList = new ArrayList<>();
        String request = "{call ps_searchPays(@NOM_PAYS=?,@ID_PAYS=?,@ID_CONTINENT=?)}";
        try {
            PreparedStatement stmt = connection.prepareCall(request);
            stmt.setString(1, pays.getName());
            stmt.setInt(2, pays.getId());
            stmt.setInt(3, pays.getContinent().getId());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int idPays = rs.getInt(1);
                String nomPays = rs.getString(2);
                Continent rsContinent = new Continent(rs.getInt("ID_CONTINENT"), rs.getString("NOM_CONTINENT"));
                paysList.add(new Pays(idPays, nomPays, rsContinent));
            }

            rs.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return paysList;
    }

    @Override
    public Pays getById(int id) {
        return null;
    }

    @Override
    public boolean update(Pays pays) {
        String rq = "UPDATE PAYS\n" +
                    "SET NOM_PAYS = ?, ID_CONTINENT = ?\n" +
                    "WHERE ID_PAYS = ?;";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setString(1, pays.getName());
            stm.setInt(2, pays.getContinent().getId());
            stm.setInt(3, pays.getId());

            stm.executeUpdate();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean post(Pays pays) {
        String rq = "INSERT INTO PAYS (NOM_PAYS, ID_CONTINENT) VALUES (?,?);";

        try(PreparedStatement stm = connection.prepareStatement(rq, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, pays.getName());
            stm.setInt(2, pays.getContinent().getId());

            ResultSet rs = stm.executeQuery();

            while(rs.next())
                pays.setId(rs.getInt(1));

            rs.close();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Pays pays) {
        String rq = "DELETE FROM PAYS\n" +
                "WHERE ID_PAYS = ?;";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, pays.getId());
            stm.executeUpdate();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
