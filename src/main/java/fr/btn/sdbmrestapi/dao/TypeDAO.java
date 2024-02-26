package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.metier.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TypeDAO extends DAO<Type, Type>{
    @Override
    public ArrayList<Type> getAll() {
        ArrayList<Type> types = new ArrayList<>();
        String sqlRequest = "SELECT * FROM TYPEBIERE";
        try(Statement stm = connection.createStatement()) {
            ResultSet rs = stm.executeQuery(sqlRequest);
            while(rs.next())
                types.add(new Type(rs.getInt(1), rs.getString(2)));
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return types;
    }

    @Override
    public ArrayList<Type> getLike(Type object) {
        return null;
    }

    @Override
    public Type getById(int id) {
        String rq = "SELECT * FROM TYPEBIERE WHERE ID_TYPE = ?";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, id);

            Type type = new Type();

            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                type.setId(rs.getInt(1));
                type.setName(rs.getString(2));
            }
            rs.close();

            return type;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean update(Type type) {
        String rq = "UPDATE TYPEBIERE SET NOM_TYPE = ? WHERE ID_TYPE=?";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setString(1, type.getName());
            stm.setInt(2, type.getId());

            stm.executeUpdate();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean post(Type type) {
        String rq = "INSERT INTO TYPEBIERE (NOM_TYPE) VALUES (?)";

        try {
            PreparedStatement stm = connection.prepareStatement(rq, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, type.getName());

            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();

            if(rs.next())
                type.setId(rs.getInt(1));

            rs.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Type type) {
        String rq = "DELETE FROM TYPEBIERE WHERE ID_TYPE = ?";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, type.getId());

            stm.executeUpdate();

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
