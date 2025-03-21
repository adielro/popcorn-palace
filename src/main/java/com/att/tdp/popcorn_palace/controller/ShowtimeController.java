package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.ShowtimeDto;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.service.ShowtimeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing showtimes.
 */
@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    /**
     * Adds a new showtime.
     * POST /showtimes
     */
    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@Valid @RequestBody ShowtimeDto dto) {
        return ResponseEntity.ok(showtimeService.addShowtime(dto));
    }

    /**
     * Gets a showtime by ID.
     * GET /showtimes/{showtimeId}
     */
    @GetMapping("/{showtimeId}")
    public ResponseEntity<Showtime> getShowtime(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(showtimeService.getShowtimeById(showtimeId));
    }

    /**
     * Updates a showtime by ID.
     * POST /showtimes/update/{showtimeId}
     */
    @PostMapping("/update/{showtimeId}")
    public ResponseEntity<Showtime> updateShowtime(
            @PathVariable Long showtimeId,
            @Valid @RequestBody ShowtimeDto dto) {
        return ResponseEntity.ok(showtimeService.updateShowtime(showtimeId, dto));
    }

    /**
     * Deletes a showtime by ID.
     * DELETE /showtimes/{showtimeId}
     */
    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
        return ResponseEntity.ok().build();
    }
}