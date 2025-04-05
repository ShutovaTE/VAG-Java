package com.example.vag.repository;

import com.example.vag.model.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    List<Exhibition> findByUserId(Long userId);

    List<Exhibition> findByIsPrivateFalse();

    @Query("SELECT e FROM Exhibition e WHERE e.isPrivate = false ORDER BY e.id DESC")
    Page<Exhibition> findPublicExhibitions(Pageable pageable);

    @Query("SELECT COUNT(e) FROM Exhibition e WHERE e.isPrivate = false")
    long countPublicExhibitions();
}