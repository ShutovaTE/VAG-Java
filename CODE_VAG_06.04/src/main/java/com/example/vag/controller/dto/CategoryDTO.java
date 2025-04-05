package com.example.vag.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDTO {

    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    private String description;
}

