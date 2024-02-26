package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.metier.Continent;
import fr.btn.sdbmrestapi.metier.Fabricant;
import fr.btn.sdbmrestapi.metier.Marque;
import fr.btn.sdbmrestapi.metier.Pays;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MarqueDAO extends DAO<Marque, Marque> {
    @Override
    public ArrayList<Marque> getAll() {
        return null;
    }

    @Override
    public ArrayList<Marque> getLike(Marque marque) {
        ArrayList<Marque> marqueList = new ArrayList<>();
        String request = "{call ps_searchMarque(@NOM_MARQUE = ?,@ID_MARQUE = ?,@ID_FABRICANT = ?,@ID_PAYS= ?,@ID_CONTINENT = ?)}";
        try(PreparedStatement stmt = connection.prepareCall(request)) {
            stmt.setString(1, marque.getName());
            stmt.setInt(2, marque.getId());
            stmt.setInt(3, marque.getFabricant().getId());
            stmt.setInt(4, marque.getPays().getId());
            stmt.setInt(5, marque.getPays().getContinent().getId());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int idMarque = rs.getInt(1);
                String nomMarque = rs.getString(2);
                int idPays = rs.getInt(3);
                String nomPays = rs.getString(4);
                int idFabricant = rs.getInt(5);
                String nomFabricant = rs.getString(6);
                int idContinent = rs.getInt(7);
                String nomContinent = rs.getString(8);

                Fabricant fabricant = new Fabricant(idFabricant, nomFabricant);
                Pays pays = new Pays(idPays, nomPays, new Continent(idContinent, nomContinent));

                marqueList.add(new Marque(idMarque, nomMarque, pays, fabricant));
            }
            rs.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return marqueList;
    }

    @Override
    public Marque getById(int id) {
        return null;
    }

    @Override
    public boolean update(Marque marque) {
        String rq = "UPDATE MARQUE\n" +
                "SET NOM_MARQUE = ?, ID_FABRICANT = ?\n" +
                "WHERE ID_MARQUE = ?";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setString(1, marque.getName());
            stm.setInt(2, marque.getFabricant().getId());
            stm.setInt(3, marque.getId());

            stm.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean post(Marque marque) {
        String rq = "INSERT INTO MARQUE (NOM_MARQUE, ID_FABRICANT, ID_PAYS) VALUES (?,?,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(rq, Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, marque.getName());
            stm.setInt(2, marque.getFabricant().getId());
            stm.setInt(3, marque.getPays().getId());
            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();

            if(rs.next())
                marque.setId(rs.getInt(1));

            rs.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Marque marque) {
        String rq = "DELETE FROM MARQUE WHERE ID_MARQUE = ?";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, marque.getId());
            stm.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
