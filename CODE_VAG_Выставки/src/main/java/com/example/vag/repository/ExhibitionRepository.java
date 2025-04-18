package com.example.vag.repository;

import com.example.vag.model.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    @EntityGraph(attributePaths = {"artworks", "user"})
    List<Exhibition> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"artworks", "user"})
    List<Exhibition> findByAuthorOnlyFalse();

    @EntityGraph(attributePaths = {"artworks", "user"})
    @Query("SELECT e FROM Exhibition e ORDER BY e.id DESC")
    Page<Exhibition> findPublicExhibitions(Pageable pageable);

    @Query("SELECT COUNT(e) FROM Exhibition e")
    long countPublicExhibitions();

    @EntityGraph(attributePaths = {"artworks", "user"})
    @Override
    Optional<Exhibition> findById(Long id);
}