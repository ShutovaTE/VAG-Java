package com.example.vag.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ArtworkDTO {

    private Long id;

    @NotEmpty(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Category is required")
    private Long categoryId;

    private MultipartFile imageFile;
}