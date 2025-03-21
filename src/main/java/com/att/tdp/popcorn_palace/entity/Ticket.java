package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entity class representing a booked movie ticket.
 * Each ticket is linked to a showtime and a seat.
 */
@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue
    private UUID id; // Unique ticket ID

    @Column(name = "showtime_id", nullable = false)
    private Long showtimeId; // Foreign key to the showtime this ticket is for

    @Column(name = "seat_number", nullable = false)
    private String seatNumber; // Seat assigned for the ticket (e.g., "A12")

    @Column(name = "user_id")
    private UUID userId; // User who booked the ticket (optional)
}