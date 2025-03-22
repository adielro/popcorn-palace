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
    @Pattern(regexp = "^[A-Z]{1,3}[0-9]{1,3}$", message = "Seat number must be in the format 'A1', 'B12', 'Z99', etc.")
    private String seatNumber;

    private UUID userId; // User who booked the ticket
}