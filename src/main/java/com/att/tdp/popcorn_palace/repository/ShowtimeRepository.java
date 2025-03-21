package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing showtime data.
 */
@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    /**
     * Checks if there is any overlapping showtime for the same theater.
     */
    List<Showtime> findByTheaterAndStartTimeLessThanAndEndTimeGreaterThan(
        String theater, LocalDateTime endTime, LocalDateTime startTime
    );

    List<Showtime> findByTheater(String theater);
}