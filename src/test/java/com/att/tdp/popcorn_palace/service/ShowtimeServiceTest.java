package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDto;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.exception.ShowtimeOverlapException;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ShowtimeServiceTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ShowtimeService showtimeService;

    private ShowtimeDto showtimeDto;
    private Showtime savedShowtime;
    private Movie movie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create ShowtimeDto
        showtimeDto = ShowtimeDto.builder()
                .movieId(1L)
                .theater("Theater A")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .price(50.0)
                .build();

        // Mock saved Showtime entity
        savedShowtime = Showtime.builder()
                .id(1L)
                .movieId(1L)
                .theater("Theater A")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .price(50.0)
                .build();

        // Create a Movie entity
        movie = new Movie(1L, "Test Movie", "Adventure", 75, 9.5, 2025);

        // Mock movieRepository.findById to return the movie
        when(movieRepository.findById(anyLong())).thenReturn(java.util.Optional.of(movie));
    }

    // Test: Add a new showtime
    @Test
    void testAddShowtime() {
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(savedShowtime);
        when(movieRepository.existsById(anyLong())).thenReturn(true);

        Showtime addedShowtime = showtimeService.addShowtime(showtimeDto);

        assertNotNull(addedShowtime);
        assertEquals("Theater A", addedShowtime.getTheater());
        verify(showtimeRepository, times(1)).save(any(Showtime.class));
    }

    // Test: Showtime overlap when adding a new showtime
    @Test
    void testAddShowtimeWithOverlap() {
        // Mock the repository to return a non-empty list of showtimes (simulating overlap)
        when(showtimeRepository.findByTheaterAndStartTimeLessThanAndEndTimeGreaterThan(
                anyString(), any(), any()
        )).thenReturn(List.of(savedShowtime));  // Simulate an existing showtime that causes overlap

        // Expect the ShowtimeOverlapException to be thrown
        assertThrows(ShowtimeOverlapException.class, () -> {
            showtimeService.addShowtime(showtimeDto);
        });
    }

    // Test: Update an existing showtime successfully
    @Test
    void testUpdateShowtime() {
        ShowtimeDto updateDto = new ShowtimeDto(1L, "Theater A", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), 60.0);
        when(showtimeRepository.findById(1L)).thenReturn(java.util.Optional.of(savedShowtime));
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(savedShowtime);

        Showtime updatedShowtime = showtimeService.updateShowtime(1L, updateDto);

        assertNotNull(updatedShowtime);
        assertEquals("Theater A", updatedShowtime.getTheater());
        verify(showtimeRepository, times(1)).save(any(Showtime.class));
    }

    // Test: Delete a showtime by ID
    @Test
    void testDeleteShowtime() {
        when(showtimeRepository.existsById(1L)).thenReturn(true);

        showtimeService.deleteShowtime(1L);

        verify(showtimeRepository, times(1)).deleteById(1L);
    }

    // Test: Try deleting a non-existing showtime
    @Test
    void testDeleteShowtimeNotFound() {
        when(showtimeRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            showtimeService.deleteShowtime(1L);
        });

        assertEquals("Showtime not found", exception.getMessage());
    }
}