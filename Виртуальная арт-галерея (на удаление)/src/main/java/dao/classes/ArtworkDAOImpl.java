package dao.classes;

import beans.Artwork;
import beans.Category;
import dao.interfaces.ArtworkDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtworkDAOImpl implements ArtworkDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Artwork artwork) {
        String sql = "INSERT INTO artworks (title, description, image, artist_id, rating, date_creation, published, draft, exhibition_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setString(3, artwork.getImage());
            stmt.setInt(4, artwork.getArtistId());
            stmt.setInt(5, artwork.getViews());
            stmt.setDate(6, artwork.getDateCreation());
            stmt.setBoolean(7, artwork.isPublished());
            stmt.setBoolean(8, artwork.isDraft());
            stmt.setInt(9, artwork.getExhibitionId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                artwork.setId(rs.getInt(1));
                System.out.println("Сгенерированный ID: " + artwork.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Artwork artwork) {
        String sql = "UPDATE artworks SET title = ?, description = ?, image = ?, artist_id = ?, rating = ?, date_creation = ?, published = ?, draft = ?, exhibition_id = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setString(3, artwork.getImage());
            stmt.setInt(4, artwork.getArtistId());
            stmt.setInt(5, artwork.getViews());
            stmt.setDate(6, artwork.getDateCreation());
            stmt.setBoolean(7, artwork.isPublished());
            stmt.setBoolean(8, artwork.isDraft());
            stmt.setInt(9, artwork.getExhibitionId());
            stmt.setInt(10, artwork.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM artworks WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Artwork getById(int id) {
        Artwork artwork = null;
        String sql = "SELECT * FROM artworks WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                artwork = new Artwork();
                artwork.setId(rs.getInt("id"));
                artwork.setTitle(rs.getString("title"));
                artwork.setDescription(rs.getString("description"));
                artwork.setImage(rs.getString("image"));
                artwork.setArtistId(rs.getInt("artist_id"));
                artwork.setViews(rs.getInt("views"));
                artwork.setDateCreation(rs.getDate("date_creation"));
                artwork.setPublished(rs.getBoolean("published"));
                artwork.setDraft(rs.getBoolean("draft"));
                artwork.setExhibitionId(rs.getInt("exhibition_id"));
                artwork.setCategory(getCategoryForArtwork(id));  // Получаем только одну категорию
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artwork;
    }



    @Override
    public List<Artwork> getAll() {
        List<Artwork> list = new ArrayList<>();
        String sql = "SELECT * FROM artworks";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Artwork artwork = new Artwork();
                artwork.setId(rs.getInt("id"));
                artwork.setTitle(rs.getString("title"));
                artwork.setDescription(rs.getString("description"));
                artwork.setImage(rs.getString("image"));
                artwork.setArtistId(rs.getInt("artist_id"));
                artwork.setViews(rs.getInt("views"));
                artwork.setDateCreation(rs.getDate("date_creation"));
                artwork.setPublished(rs.getBoolean("published"));
                artwork.setDraft(rs.getBoolean("draft"));
                artwork.setExhibitionId(rs.getInt("exhibition_id"));
                list.add(artwork);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    @Override
    public Category getCategoryForArtwork(int artworkId) throws SQLException {
        String sql = "SELECT c.id, c.name, c.description FROM categories c " +
                "JOIN artwork_category ac ON c.id = ac.category_id " +
                "WHERE ac.artwork_id = ? LIMIT 1";
        Category category = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, artworkId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
            }
        }
        return category;
    }

}


