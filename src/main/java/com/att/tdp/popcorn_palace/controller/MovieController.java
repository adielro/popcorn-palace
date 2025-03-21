package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.MovieDto;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.service.MovieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing movies.
 * Exposes endpoints for adding, updating, retrieving, and deleting movies.
 */
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    /**
     * Retrieves all movies in the system.
     * GET /movies/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    /**
     * Adds a new movie.
     * POST /movies
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieDto dto) {
        return ResponseEntity.ok(movieService.addMovie(dto));
    }

    /**
     * Updates a movie by title.
     * POST /movies/update/{movieTitle}
     */
    @PostMapping("/update/{movieTitle}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieTitle, @Valid @RequestBody MovieDto dto) {
        return ResponseEntity.ok(movieService.updateMovie(movieTitle, dto));
    }

    /**
     * Deletes a movie by title.
     * DELETE /movies/{movieTitle}
     */
    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieTitle) {
        movieService.deleteMovie(movieTitle);
        return ResponseEntity.ok().build();
    }
}