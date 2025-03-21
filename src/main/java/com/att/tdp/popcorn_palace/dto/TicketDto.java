package com.att.tdp.popcorn_palace.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

/**
 * DTO class for creating a new movie ticket.
 * Includes showtime ID, seat number, and user information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {

    @NotNull(message = "Showtime ID must not be null")
    private Long showtimeId;

    @NotBlank(message = "Seat number must not be blank")
    private String seatNumber;

    private UUID userId; // User who booked the ticket
}