package com.example.vag.repository;

import com.example.vag.model.Artwork;
import com.example.vag.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.id IN :ids")
    List<Category> findAllByIds(@Param("ids") List<Long> ids);

    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.artworks")
    List<Category> findAllWithArtworks();
}