package com.example.vag.repository;

import com.example.vag.model.Artwork;
import com.example.vag.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
      @Override
      @EntityGraph(attributePaths = {"artworks"})
      List<Category> findAll();

 }