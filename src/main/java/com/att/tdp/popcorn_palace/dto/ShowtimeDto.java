package com.att.tdp.popcorn_palace.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for creating or updating a showtime.
 * Includes reference to movieId and showtime details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeDto {

    @NotNull(message = "Movie ID must not be null")
    private Long movieId;

    @NotBlank(message = "Theater must not be blank")
    private String theater;

    @NotNull(message = "Start time must not be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time must not be null")
    private LocalDateTime endTime;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    private BigDecimal price;
}