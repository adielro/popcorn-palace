package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a Movie in the system.
 * Maps to the "movies" table in the database.
 * Includes basic movie metadata: title, genre, duration, rating, and release year.
 */
@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private int duration; // in minutes
    private double rating;
    private int releaseYear;
}