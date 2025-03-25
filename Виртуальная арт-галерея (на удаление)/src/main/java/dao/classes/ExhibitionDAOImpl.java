package dao.classes;

import beans.Exhibition;
import dao.interfaces.ExhibitionDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionDAOImpl implements ExhibitionDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Exhibition exhibition) {
        String sql = "INSERT INTO exhibitions (title, description, artist_id, cover) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, exhibition.getTitle());
            stmt.setString(2, exhibition.getDescription());
            stmt.setInt(3, exhibition.getArtistId());
            stmt.setString(4, exhibition.getCover());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                exhibition.setId(rs.getInt(1));
                System.out.println("Сгенерированный ID: " + exhibition.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Exhibition exhibition) {
        String sql = "UPDATE exhibitions SET title = ?, description = ?, artist_id = ?, cover = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, exhibition.getTitle());
            stmt.setString(2, exhibition.getDescription());
            stmt.setInt(3, exhibition.getArtistId());
            stmt.setString(4, exhibition.getCover());
            stmt.setInt(5, exhibition.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM exhibitions WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Exhibition getById(int id) {
        Exhibition exhibition = null;
        String sql = "SELECT * FROM exhibitions WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exhibition = new Exhibition();
                exhibition.setId(rs.getInt("id"));
                exhibition.setTitle(rs.getString("title"));
                exhibition.setDescription(rs.getString("description"));
                exhibition.setArtistId(rs.getInt("artist_id"));
                exhibition.setCover(rs.getString("cover"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exhibition;
    }

    @Override
    public List<Exhibition> getAll() {
        List<Exhibition> list = new ArrayList<>();
        String sql = "SELECT * FROM exhibitions";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Exhibition exhibition = new Exhibition();
                exhibition.setId(rs.getInt("id"));
                exhibition.setTitle(rs.getString("title"));
                exhibition.setDescription(rs.getString("description"));
                exhibition.setArtistId(rs.getInt("artist_id"));
                exhibition.setCover(rs.getString("cover"));
                list.add(exhibition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
