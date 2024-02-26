package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.metier.Fabricant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FabricantDAO extends DAO<Fabricant, Fabricant>{
    @Override
    public ArrayList<Fabricant> getAll() {
        ArrayList<Fabricant> fabricants = new ArrayList<>();
        String request = "SELECT * FROM FABRICANT";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(request);
            while(rs.next())
                fabricants.add(new Fabricant(rs.getInt("ID_FABRICANT"), rs.getString("NOM_FABRICANT")));
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return fabricants;
    }

    @Override
    public ArrayList<Fabricant> getLike(Fabricant object) {
        return null;
    }

    @Override
    public Fabricant getById(int id) {
        String rq = "SELECT * FROM FABRICANT WHERE ID_FABRICANT = ?";
        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, id);
            Fabricant fabricant = new Fabricant();

            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                fabricant.setId(rs.getInt("ID_FABRICANT"));
                fabricant.setName(rs.getString("NOM_FABRICANT"));
            }
            rs.close();
            return fabricant;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Fabricant fabricant) {
        String rq = "UPDATE FABRICANT\n" +
                "SET NOM_FABRICANT = ?\n" +
                "WHERE ID_FABRICANT = ?;";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setString(1, fabricant.getName());
            stm.setInt(2, fabricant.getId());
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean post(Fabricant fabricant) {
        String rq = "INSERT INTO FABRICANT (NOM_FABRICANT) VALUES (?)";

        try {
            PreparedStatement stm = connection.prepareStatement(rq, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, fabricant.getName());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            while(rs.next())
                fabricant.setId(rs.getInt(1));

            rs.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Fabricant fabricant) {
        String rq = "DELETE FROM FABRICANT WHERE ID_FABRICANT = ?";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, fabricant.getId());
            stm.executeUpdate();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
