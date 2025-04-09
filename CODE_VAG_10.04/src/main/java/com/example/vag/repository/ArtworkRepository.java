package com.example.vag.repository;

import com.example.vag.model.Artwork;
import com.example.vag.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    @EntityGraph(attributePaths = {"user", "categories", "comments"})
    @Override
    List<Artwork> findAll();

    @EntityGraph(attributePaths = {"user", "categories"})
    @Query("SELECT a FROM Artwork a WHERE a.status = :status")
    List<Artwork> findByStatus(@Param("status") String status);

    List<Artwork> findByUser_Id(Long userId);

    @Query(
            value = "SELECT DISTINCT a FROM Artwork a " +
                    "JOIN FETCH a.user " +
                    "JOIN a.categories c " +
                    "WHERE c.id = :categoryId AND a.status = 'APPROVED'",
            countQuery = "SELECT COUNT(DISTINCT a) FROM Artwork a " +
                    "JOIN a.categories c " +
                    "WHERE c.id = :categoryId AND a.status = 'APPROVED'"
    )
    Page<Artwork> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT a FROM Artwork a WHERE a.exhibition.id = :exhibitionId")
    List<Artwork> findByExhibitionId(@Param("exhibitionId") Long exhibitionId);

    @Query("SELECT DISTINCT a FROM Artwork a " +
            "LEFT JOIN FETCH a.user " +
            "LEFT JOIN FETCH a.comments c " +
            "LEFT JOIN FETCH c.user " +
            "WHERE a.id = :id")
    Optional<Artwork> findByIdWithComments(@Param("id") Long id);

    @Query("SELECT DISTINCT a FROM Artwork a " +
            "LEFT JOIN FETCH a.categories " +
            "WHERE a.id = :id")
    Optional<Artwork> findByIdWithCategories(@Param("id") Long id);

    @Query(
            value = "SELECT DISTINCT a FROM Artwork a " +
                    "LEFT JOIN FETCH a.user " +
                    "LEFT JOIN FETCH a.categories " +
                    "WHERE a.status = 'APPROVED'",
            countQuery = "SELECT COUNT(a) FROM Artwork a WHERE a.status = 'APPROVED'"
    )
    Page<Artwork> findApprovedArtworks(Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Like l WHERE l.artwork = :artwork AND l.user = :user")
    boolean existsByArtworkAndUser(
            @Param("artwork") Artwork artwork,
            @Param("user") User user
    );
    @Query("SELECT COUNT(DISTINCT a) FROM Artwork a " +
            "JOIN a.categories c " +
            "WHERE c.id = :categoryId AND a.status = 'APPROVED'")
    long countApprovedArtworksByCategoryId(@Param("categoryId") Long categoryId);
}