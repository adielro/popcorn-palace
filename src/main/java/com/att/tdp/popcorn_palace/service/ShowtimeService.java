package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDto;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service class for handling showtime business logic.
 */
@Service
@RequiredArgsConstructor
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    
    /**
     * Adds a new showtime for a movie if it doesn't overlap in the same theater.
     *
     * @param dto showtime input data
     * @return saved showtime
     */
    public Showtime addShowtime(ShowtimeDto dto) {
        if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
            throw new RuntimeException("Start time must be before end time.");
        }

        // Validate showtime fits movie duration
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        long availableMinutes = Duration.between(dto.getStartTime(), dto.getEndTime()).toMinutes();
        if (availableMinutes < movie.getDuration()) {
            throw new RuntimeException("Showtime must be at least " + movie.getDuration() + " minutes long to fit the movie.");
        }

        // Check for overlaps (after time validation)
        List<Showtime> overlapping = showtimeRepository
                .findByTheaterAndStartTimeLessThanAndEndTimeGreaterThan(
                        dto.getTheater(), dto.getEndTime(), dto.getStartTime());

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Showtime overlaps with an existing one in this theater.");
        }

        Showtime showtime = Showtime.builder()
                .movieId(dto.getMovieId())
                .theater(dto.getTheater())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .price(dto.getPrice())
                .build();

        return showtimeRepository.save(showtime);
    }

    /**
     * Retrieves a showtime by its ID.
     */
    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
    }

    /**
     * Updates an existing showtime if no overlap occurs.
     */
    public Showtime updateShowtime(Long id, ShowtimeDto dto) {
        Showtime existing = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
    
        if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
            throw new RuntimeException("Start time must be before end time.");
        }
    
        // Validate against movie duration
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    
        long availableMinutes = Duration.between(dto.getStartTime(), dto.getEndTime()).toMinutes();
        if (availableMinutes < movie.getDuration()) {
            throw new RuntimeException("Showtime must be at least " + movie.getDuration() + " minutes long to fit the movie.");
        }
    
        // Check for overlaps
        List<Showtime> overlapping = showtimeRepository
                .findByTheaterAndStartTimeLessThanAndEndTimeGreaterThan(
                        dto.getTheater(), dto.getEndTime(), dto.getStartTime());
    
        boolean overlaps = overlapping.stream().anyMatch(s -> !s.getId().equals(id));
        if (overlaps) {
            throw new RuntimeException("Updated showtime would overlap with an existing one.");
        }
    
        existing.setMovieId(dto.getMovieId());
        existing.setTheater(dto.getTheater());
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setPrice(dto.getPrice());
    
        return showtimeRepository.save(existing);
    }

    /**
     * Deletes a showtime by its ID.
     */
    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new RuntimeException("Showtime not found");
        }
        showtimeRepository.deleteById(id);
    }
}