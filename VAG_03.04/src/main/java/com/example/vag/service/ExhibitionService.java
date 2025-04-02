package com.example.vag.service;

import com.example.vag.model.Exhibition;
import com.example.vag.model.User;
import com.example.vag.dto.ExhibitionDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ExhibitionService {
    Exhibition save(Exhibition exhibition);
    Exhibition create(ExhibitionDTO exhibitionDTO, User user);
    List<Exhibition> findAll();
    List<Exhibition> findByUser(User user);
    List<Exhibition> findPublicExhibitions();
    Optional<Exhibition> findById(Long id);
    void delete(Exhibition exhibition);
    Exhibition update(ExhibitionDTO exhibitionDTO);
    Page<Exhibition> findPaginatedPublicExhibitions(int page, int size);
}