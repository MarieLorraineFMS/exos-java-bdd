package fr.fms.dao;

import fr.fms.config.DbConfig;
import fr.fms.entity.Article;
import fr.fms.exceptions.DaoException;

import static fr.fms.utils.Helpers.isNullOrEmpty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {

    public List<Article> readAll() throws SQLException {
        String sql = "SELECT IdArticle, Description, Brand, UnitaryPrice FROM T_Articles";
        List<Article> articles = new ArrayList<>();
        try (Connection cnx = DbConfig.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("IdArticle"),
                        rs.getString("Description"),
                        rs.getString("Brand"),
                        rs.getDouble("UnitaryPrice")));
            }
            return articles;

        } catch (SQLException e) {
            throw new DaoException("Failed to read articles : ", e);
        }
    }

    public int create(Article a) {
        if (a == null) {
            throw new DaoException("Article cannot be null");
        }
        if (isNullOrEmpty(a.description)) {
            throw new DaoException("Article description is required");
        }
        if (isNullOrEmpty(a.brand)) {
            throw new DaoException("Article brand is required");
        }
        if (a.unitaryPrice < 0) {
            throw new DaoException("Article price cannot be negative");
        }

        String sql = "INSERT INTO T_Articles (Description, Brand, UnitaryPrice) VALUES (?, ?, ?)";

        try (Connection cnx = DbConfig.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.description);
            ps.setString(2, a.brand);
            ps.setDouble(3, a.unitaryPrice);

            ps.executeUpdate();

            // AUTO-INCREMENT
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next())
                    return keys.getInt(1);
                throw new DaoException("Insert succeeded but no generated id was returned");
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to create article: " + a.description, e);
        }
    }

    public Article readById(int id) throws SQLException {
        String sql = "SELECT IdArticle, Description, Brand, UnitaryPrice FROM T_Articles WHERE IdArticle = ?";

        try (Connection cnx = DbConfig.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return new Article(
                        rs.getInt("IdArticle"),
                        rs.getString("Description"),
                        rs.getString("Brand"),
                        rs.getDouble("UnitaryPrice"));
            } catch (SQLException e) {
                throw new DaoException("Failed to read article with id : " + id + ". ", e);
            }
        }
    }

    public boolean update(Article a) throws SQLException {
        String sql = "UPDATE T_Articles SET Description = ?, Brand = ?, UnitaryPrice = ? WHERE IdArticle = ?";

        try (Connection cnx = DbConfig.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, a.description);
            ps.setString(2, a.brand);
            ps.setDouble(3, a.unitaryPrice);
            ps.setInt(4, a.id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to update article with id : " + a.id + ". ", e);
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM T_Articles WHERE IdArticle = ?";

        try (Connection cnx = DbConfig.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete article with id : " + id + ". ", e);
        }
    }

}
