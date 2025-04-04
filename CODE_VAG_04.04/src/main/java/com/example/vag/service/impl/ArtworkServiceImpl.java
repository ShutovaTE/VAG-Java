package com.example.vag.service.impl;

import com.example.vag.model.*;
import com.example.vag.repository.ArtworkRepository;
import com.example.vag.repository.CategoryRepository;
import com.example.vag.repository.CommentRepository;
import com.example.vag.repository.LikeRepository;
import com.example.vag.service.ArtworkService;
import com.example.vag.util.FileUploadUtil;
import com.example.vag.controller.dto.ArtworkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;
    private final FileUploadUtil fileUploadUtil;
    private CommentRepository commentRepository;

    public ArtworkServiceImpl(ArtworkRepository artworkRepository,
                              CategoryRepository categoryRepository,
                              CommentRepository commentRepository,
                              LikeRepository likeRepository,
                              FileUploadUtil fileUploadUtil) {
        this.artworkRepository = artworkRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public Artwork save(Artwork artwork) {
        return artworkRepository.save(artwork);
    }

    @Override
    public Artwork create(ArtworkDTO artworkDTO, MultipartFile imageFile, User user) throws IOException {
        Category category = categoryRepository.findById(artworkDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be null or empty");
        }

        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String uploadDir = "artwork-images/" + user.getId();
        fileUploadUtil.saveFile(uploadDir, fileName, imageFile);
        String imagePath = uploadDir + "/" + fileName;
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be null or empty");
        }
        Artwork artwork = new Artwork();
        artwork.setTitle(artworkDTO.getTitle());
        artwork.setDescription(artworkDTO.getDescription());
        artwork.setCategory(category);
        artwork.setImagePath(imagePath);
        artwork.setDateCreation(LocalDate.now());
        artwork.setUser(user);
        artwork.setStatus(Artwork.ArtworkStatus.PENDING.name());
        artwork.setLikes(0);
        artwork.setViews(0);

        return artworkRepository.save(artwork);
    }

    @Override
    public List<Artwork> findAll() {
        return artworkRepository.findAll();
    }

    @Override
    public List<Artwork> findByStatus(String status) {
        return artworkRepository.findByStatus(status);
    }

    @Override
    public List<Artwork> findByUser(User user) {
        return artworkRepository.findByUserId(user.getId());
    }

    @Override
    public List<Artwork> findByCategoryId(Long categoryId) {
        return artworkRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Artwork> findByExhibitionId(Long exhibitionId) {
        return artworkRepository.findByExhibitionId(exhibitionId);
    }

    @Override
    public Optional<Artwork> findById(Long id) {
        return artworkRepository.findById(id);
    }

    @Override
    public void delete(Artwork artwork) {
        artworkRepository.delete(artwork);
    }

    @Override
    public Artwork update(ArtworkDTO artworkDTO, MultipartFile imageFile) throws IOException {
        Artwork artwork = artworkRepository.findById(artworkDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid artwork ID"));

        Category category = categoryRepository.findById(artworkDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            String uploadDir = "artwork-images/" + artwork.getUser().getId();
            fileUploadUtil.saveFile(uploadDir, fileName, imageFile);
            artwork.setImagePath(uploadDir + "/" + fileName);
        }

        artwork.setTitle(artworkDTO.getTitle());
        artwork.setDescription(artworkDTO.getDescription());
        artwork.setCategory(category);

        return artworkRepository.save(artwork);
    }

    @Override
    public void approveArtwork(Long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid artwork ID"));
        artwork.setStatus(Artwork.ArtworkStatus.APPROVED.name());
        artworkRepository.save(artwork);
    }

    @Override
    public void rejectArtwork(Long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid artwork ID"));
        artwork.setStatus(Artwork.ArtworkStatus.REJECTED.name());
        artworkRepository.save(artwork);
    }

    @Override
    public List<Artwork> findLikedArtworks(User user) {
        return likeRepository.findByUser(user).stream()
                .map(Like::getArtwork)
                .toList();
    }

    @Override
    public void likeArtwork(Long artworkId, User user) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid artwork ID"));

        if (likeRepository.findByArtworkAndUser(artwork, user).isEmpty()) {
            Like like = new Like();
            like.setArtwork(artwork);
            like.setUser(user);
            likeRepository.save(like);
            artwork.setLikes(artwork.getLikes() + 1);
            artworkRepository.save(artwork);
        }
    }

    @Override
    public void unlikeArtwork(Long artworkId, User user) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid artwork ID"));

        likeRepository.findByArtworkAndUser(artwork, user).ifPresent(like -> {
            likeRepository.delete(like);
            artwork.setLikes(artwork.getLikes() - 1);
            artworkRepository.save(artwork);
        });
    }


    @Override
    public Page<Artwork> findPaginatedApprovedArtworks(int page, int size) {
        List<Artwork> artworks = artworkRepository.findApprovedArtworks(page, size);
        long total = artworkRepository.countApprovedArtworks();
        return new PageImpl<>(artworks, PageRequest.of(page, size), total);
    }

    public void addComment(Long artworkId, User user, String content) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new EntityNotFoundException("Artwork not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setArtwork(artwork);

        // Важно добавить комментарий в коллекцию artwork
        artwork.getComments().add(comment);

        commentRepository.save(comment);
        artworkRepository.save(artwork); // Сохраняем изменения
    }
    public boolean isLikedByUser(Artwork artwork, User user) {
        return likeRepository.existsByArtworkAndUser(artwork, user);
    }
    public Artwork findByIdWithComments(Long id) {
        return artworkRepository.findByIdWithComments(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found"));
    }
}