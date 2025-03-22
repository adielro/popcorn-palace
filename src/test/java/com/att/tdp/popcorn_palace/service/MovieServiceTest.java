package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.MovieDto;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private MovieDto movieDto;
    private Movie savedMovie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the DTO and Movie entity
        movieDto = new MovieDto("Moana 2", "Family", 100, 7.0, 2024);
        savedMovie = new Movie(1L, "Moana 2", "Family", 100, 7.0, 2024);
    }

    // Test adding a movie successfully
    @Test
    void testAddMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);
        when(movieRepository.existsByTitle(movieDto.getTitle())).thenReturn(false);

        Movie addedMovie = movieService.addMovie(movieDto);

        assertNotNull(addedMovie);
        assertEquals("Moana 2", addedMovie.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    // Test adding a movie with a duplicate title
    @Test
    void testAddMovieWithDuplicateTitle() {
        when(movieRepository.existsByTitle(movieDto.getTitle())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movieService.addMovie(movieDto);
        });

        assertEquals("Movie with this title already exists.", exception.getMessage());
    }

    // Test retrieving all movies
    @Test
    void testGetAllMovies() {
        when(movieRepository.findAll()).thenReturn(List.of(savedMovie));

        List<Movie> movies = movieService.getAllMovies();

        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("Moana 2", movies.get(0).getTitle());
    }

    // Test updating a movie by title
    @Test
    void testUpdateMovie() {
        MovieDto updateDto = new MovieDto("Moana 3", "Family", 110, 7.5, 2025);
        when(movieRepository.findByTitle("Moana 2")).thenReturn(java.util.Optional.of(savedMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        Movie updatedMovie = movieService.updateMovie("Moana 2", updateDto);

        assertNotNull(updatedMovie);
        assertEquals("Moana 3", updatedMovie.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    // Test trying to update a non-existing movie
    @Test
    void testUpdateMovieNotFound() {
        MovieDto updateDto = new MovieDto("Moana 3", "Family", 110, 7.5, 2025);
        when(movieRepository.findByTitle("Moana 2")).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movieService.updateMovie("Moana 2", updateDto);
        });

        assertEquals("Movie not found", exception.getMessage());
    }

    // Test deleting a movie
    @Test
    void testDeleteMovie() {
        when(movieRepository.existsByTitle("Moana 2")).thenReturn(true);

        movieService.deleteMovie("Moana 2");

        verify(movieRepository, times(1)).deleteByTitle("Moana 2");
    }

    // Test trying to delete a non-existing movie
    @Test
    void testDeleteMovieNotFound() {
        when(movieRepository.existsByTitle("Moana 2")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movieService.deleteMovie("Moana 2");
        });

        assertEquals("Movie not found", exception.getMessage());
    }
}