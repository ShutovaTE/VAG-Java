package com.example.vag.repository;

import com.example.vag.model.Artwork;
import com.example.vag.model.Like;
import com.example.vag.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l " +
            "JOIN FETCH l.artwork a " +
            "JOIN FETCH a.user " +
            "JOIN FETCH a.category " +
            "WHERE l.user = :user")
    List<Like> findByUserWithArtworkDetails(@Param("user") User user);
    @Query("SELECT l FROM Like l WHERE l.artwork = :artwork AND l.user = :user")
    Optional<Like> findByArtworkAndUser(
            @Param("artwork") Artwork artwork,
            @Param("user") User user
    );

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Like l WHERE l.artwork = :artwork AND l.user = :user")
    boolean existsByArtworkAndUser(
            @Param("artwork") Artwork artwork,
            @Param("user") User user
    );

    List<Like> findByUser(User user);
}