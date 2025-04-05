package com.example.vag.service.impl;

import com.example.vag.model.Exhibition;
import com.example.vag.model.User;
import com.example.vag.repository.ExhibitionRepository;
import com.example.vag.service.ExhibitionService;
import com.example.vag.controller.dto.ExhibitionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    @Override
    public Exhibition save(Exhibition exhibition) {
        return exhibitionRepository.save(exhibition);
    }

    @Override
    public Exhibition create(ExhibitionDTO exhibitionDTO, User user) {
        Exhibition exhibition = new Exhibition();
        exhibition.setTitle(exhibitionDTO.getTitle());
        exhibition.setDescription(exhibitionDTO.getDescription());
        exhibition.setUser(user);
        exhibition.setPrivate(exhibitionDTO.isPrivate());
        return exhibitionRepository.save(exhibition);
    }

    @Override
    public List<Exhibition> findAll() {
        return exhibitionRepository.findAll();
    }

    @Override
    public List<Exhibition> findByUser(User user) {
        return exhibitionRepository.findByUserId(user.getId());
    }

    @Override
    public List<Exhibition> findPublicExhibitions() {
        return exhibitionRepository.findByIsPrivateFalse();
    }

    @Override
    public Optional<Exhibition> findById(Long id) {
        return exhibitionRepository.findById(id);
    }

    @Override
    public void delete(Exhibition exhibition) {
        exhibitionRepository.delete(exhibition);
    }

    @Override
    public Exhibition update(ExhibitionDTO exhibitionDTO) {
        Exhibition exhibition = exhibitionRepository.findById(exhibitionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid exhibition ID"));
        exhibition.setTitle(exhibitionDTO.getTitle());
        exhibition.setDescription(exhibitionDTO.getDescription());
        exhibition.setPrivate(exhibitionDTO.isPrivate());
        return exhibitionRepository.save(exhibition);
    }

    @Override
    public Page<Exhibition> findPaginatedPublicExhibitions(int page, int size) {
        return exhibitionRepository.findPublicExhibitions(PageRequest.of(page, size));
    }
}