package dao.classes;

import beans.User;
import dao.interfaces.ArtistDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAOImpl implements ArtistDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(User artist) {
        String sql = "INSERT INTO artists (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, artist.getUsername());
            stmt.setString(2, artist.getPassword());
            stmt.setString(3, artist.getEmail());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                artist.setId(rs.getInt(1));
                System.out.println("Сгенерированный ID: " + artist.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User artist) {
        String sql = "UPDATE artists SET username = ?, password = ?, email = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, artist.getUsername());
            stmt.setString(2, artist.getPassword());
            stmt.setString(3, artist.getEmail());
            stmt.setInt(4, artist.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM artists WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(int id) {
        User artist = null;
        String sql = "SELECT * FROM artists WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                artist = new User();
                artist.setId(rs.getInt("id"));
                artist.setUsername(rs.getString("username"));
                artist.setPassword(rs.getString("password"));
                artist.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artist;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM artists";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User artist = new User();
                artist.setId(rs.getInt("id"));
                artist.setUsername(rs.getString("username"));
                artist.setPassword(rs.getString("password"));
                artist.setEmail(rs.getString("email"));
                list.add(artist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

