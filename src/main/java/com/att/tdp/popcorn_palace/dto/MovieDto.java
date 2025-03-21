package com.att.tdp.popcorn_palace.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO class used for creating or updating movie records via the API.
 * Mirrors the Movie entity fields, excluding the database ID.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Genre must not be blank")
    private String genre;

    @Min(value = 1, message = "Duration must be greater than 0")
    private int duration;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be >= 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be <= 10")
    private double rating;

    @Min(value = 1888, message = "Release year must be >= 1888")
    private int releaseYear;
}