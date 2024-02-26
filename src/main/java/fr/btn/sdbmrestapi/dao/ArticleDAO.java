package fr.btn.sdbmrestapi.dao;

import fr.btn.sdbmrestapi.metier.*;

import java.sql.*;
import java.util.ArrayList;

public class ArticleDAO extends DAO<Article, ArticleSearch>{
    @Override
    public ArrayList<Article> getAll() {
        return null;
    }

    @Override
    public ArrayList<Article> getLike(ArticleSearch articleSearch) {
        ArrayList<Article> articles = new ArrayList<>();
        String spReq = "{call ps_searchArticles(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

        try(CallableStatement stm = connection.prepareCall(spReq)) {
            stm.setString(1, articleSearch.getName());
            stm.setInt(2, articleSearch.getVolume());
            stm.setFloat(3, articleSearch.getTitrageMin());
            stm.setFloat(4, articleSearch.getTitrageMax());
            stm.setInt(5, articleSearch.getCouleur().getId());
            stm.setInt(6, articleSearch.getMarque().getId());
            stm.setInt(7, articleSearch.getType().getId());
            stm.setInt(8, articleSearch.getPays().getId());
            stm.setInt(9, articleSearch.getFabricant().getId());
            stm.setInt(10, articleSearch.getContinent().getId());
            stm.setInt(11, articleSearch.getOffset());
            stm.setInt(12, articleSearch.getPageSize());
            stm.registerOutParameter(13, Types.INTEGER);

            ResultSet rs = stm.executeQuery();
            rs.next();
            articleSearch.setRowCount(rs.getInt(1));

            stm.getMoreResults();
            rs = stm.getResultSet();

            while(rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("Référence"));
                article.setName(rs.getString("Désignation"));
                article.setVolume(rs.getInt("VOLUME"));
                article.setTitrage(rs.getFloat("TITRAGE"));
                article.setPrixAchat(rs.getFloat("PRIX_ACHAT"));
                article.setStock(rs.getInt("STOCK"));

                Type type = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE"));
                Couleur couleur = new Couleur(rs.getInt("ID_COULEUR"), rs.getString("COULEUR"));

                Continent continent = new Continent(rs.getInt("ID_CONTINENT"), rs.getString("CONTINENT"));
                Pays pays = new Pays(rs.getInt("ID_PAYS"), rs.getString("PAYS"), continent);
                Fabricant fabricant = new Fabricant(rs.getInt("ID_FABRICANT"), rs.getString("FABRICANT"));

                Marque marque = new Marque();
                marque.setId(rs.getInt("ID_MARQUE"));
                marque.setName(rs.getString("MARQUE"));
                marque.setPays(pays);
                marque.setFabricant(fabricant);

                article.setType(type);
                article.setCouleur(couleur);
                article.setMarque(marque);

                articles.add(article);
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    @Override
    public Article getById(int id) {
        return null;
    }

    @Override
    public boolean update(Article article) {
        String rq = "{call ps_modifierArticle(?,?,?,?,?,?,?,?,?)}";
        try ( PreparedStatement stmt = connection.prepareStatement(rq, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, article.getId());
            stmt.setString(2, article.getName());
            stmt.setFloat(3, article.getPrixAchat());
            stmt.setInt(4, article.getVolume());
            stmt.setFloat(5, article.getTitrage());
            stmt.setInt(6, article.getMarque().getId());
            stmt.setInt(7, article.getCouleur().getId());
            stmt.setInt(8, article.getType().getId());
            stmt.setInt(9, article.getStock());

            stmt.executeUpdate();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean post(Article article) {
        String ps = "{call ps_insertArticle(?,?,?,?,?,?,?,?)}";
        try {
            PreparedStatement stmt = connection.prepareStatement(ps, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, article.getName());
            stmt.setFloat(2, article.getPrixAchat());
            stmt.setInt(3, article.getVolume());
            stmt.setFloat(4, article.getTitrage());
            stmt.setInt(5, article.getMarque().getId());
            stmt.setInt(6, article.getCouleur().getId());
            stmt.setInt(7, article.getType().getId());
            stmt.setInt(8, article.getStock());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next())
                article.setId(rs.getInt(1));

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Article article) {
        String rq = "DELETE FROM ARTICLE WHERE ID_ARTICLE = ?";

        try(PreparedStatement stm = connection.prepareStatement(rq)) {
            stm.setInt(1, article.getId());
            stm.executeUpdate();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
