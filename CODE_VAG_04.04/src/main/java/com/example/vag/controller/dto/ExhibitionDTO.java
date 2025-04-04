package com.example.vag.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ExhibitionDTO {

    private Long id;

    @NotEmpty(message = "Title is required")
    private String title;

    private String description;

    private boolean isPrivate;
}