package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.MovieDto;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling Movie-related business logic.
 */
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * Fetches all movies from the repository.
     *
     * @return list of all movies
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Adds a new movie if the title is not already taken.
     *
     * @param dto the movie data to add
     * @return the saved movie entity
     * @throws RuntimeException if the movie title already exists
     */
    public Movie addMovie(MovieDto dto) {
        if (movieRepository.existsByTitle(dto.getTitle())) {
            throw new RuntimeException("Movie with this title already exists.");
        }

        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .duration(dto.getDuration())
                .rating(dto.getRating())
                .releaseYear(dto.getReleaseYear())
                .build();

        return movieRepository.save(movie);
    }

    /**
     * Updates an existing movie by its title.
     *
     * @param title the current title of the movie
     * @param dto   the updated movie details
     * @return the updated movie entity
     * @throws RuntimeException if the movie is not found
     */
    public Movie updateMovie(String title, MovieDto dto) {
        Movie existing = movieRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        existing.setTitle(dto.getTitle());
        existing.setGenre(dto.getGenre());
        existing.setDuration(dto.getDuration());
        existing.setRating(dto.getRating());
        existing.setReleaseYear(dto.getReleaseYear());

        return movieRepository.save(existing);
    }

    /**
     * Deletes a movie by its title.
     *
     * @param title the title of the movie to delete
     * @throws RuntimeException if the movie is not found
     */
    public void deleteMovie(String title) {
        if (!movieRepository.existsByTitle(title)) {
            throw new RuntimeException("Movie not found");
        }
        movieRepository.deleteByTitle(title);
    }
}