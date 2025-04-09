package com.example.vag.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "artworks")
@Data
@NoArgsConstructor
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;


    @ManyToMany
    @JoinTable(
            name = "artwork_category",
            joinColumns = @JoinColumn(name = "artwork_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @Transient
    private List<Long> categoryIds;
    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED

    @Column(nullable = false)
    private int likes = 0;

    @Column(nullable = false)
    private int views = 0;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> artworkLikes;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public enum ArtworkStatus {
        PENDING, APPROVED, REJECTED
    }
    @Transient
    private MultipartFile imageFile;


    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Exhibition getExhibition() {
        return exhibition;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public int getLikes() {
        return likes;
    }

    public int getViews() {
        return views;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setArtworkLikes(List<Like> artworkLikes) {
        this.artworkLikes = artworkLikes;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}