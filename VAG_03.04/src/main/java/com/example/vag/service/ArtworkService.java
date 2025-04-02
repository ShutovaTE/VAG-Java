package com.example.vag.service;

import com.example.vag.model.Artwork;
import com.example.vag.model.User;
import com.example.vag.dto.ArtworkDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ArtworkService {
    Artwork save(Artwork artwork);
    Artwork create(ArtworkDTO artworkDTO, MultipartFile imageFile, User user) throws IOException;
    Page<Artwork> findPaginatedApprovedArtworks(int page, int size);
    List<Artwork> findAll();
    List<Artwork> findByStatus(String status);
    List<Artwork> findByUser(User user);
    List<Artwork> findByCategoryId(Long categoryId);
    List<Artwork> findByExhibitionId(Long exhibitionId);
    Optional<Artwork> findById(Long id);
    void delete(Artwork artwork);
    Artwork update(ArtworkDTO artworkDTO, MultipartFile imageFile) throws IOException;
    void approveArtwork(Long artworkId);
    void rejectArtwork(Long artworkId);
    List<Artwork> findLikedArtworks(User user);
    void likeArtwork(Long artworkId, User user);
    void unlikeArtwork(Long artworkId, User user);
    boolean isLikedByUser(Artwork artwork, User user);
}