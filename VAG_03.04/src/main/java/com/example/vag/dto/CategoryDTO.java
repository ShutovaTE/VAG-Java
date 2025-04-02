package com.example.vag.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDTO {

    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    private String description;
}