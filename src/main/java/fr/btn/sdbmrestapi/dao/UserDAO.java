package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.security.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO extends DAO<User, User>{
    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public ArrayList<User> getLike(User user) {
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public boolean update(User object) {
        return false;
    }

    @Override
    public boolean post(User user) {
        String rq = "INSERT INTO UTILISATEUR (NOM_LOGIN, EMAIL, PASSWORD, ROLE) VALUES (?,?,?,?)";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getEmail());
            stm.setString(3, user.getPassword());
            stm.setString(4, "USER");

            user.setRole("USER");

            stm.executeUpdate();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User object) {
        return false;
    }

    public User getUser(User user) {
        String rq = "SELECT * FROM UTILISATEUR WHERE NOM_LOGIN = ?";

        try (PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setString(1, user.getLogin());

            ResultSet rs = stm.executeQuery();

            User queriedUser = new User();

            while (rs.next()) {
                queriedUser.setLogin(rs.getString("NOM_LOGIN"));
                queriedUser.setPassword(rs.getString("PASSWORD"));
                queriedUser.setRole(rs.getString("ROLE"));
                queriedUser.setEmail(rs.getString("EMAIL"));
            }
            return queriedUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
