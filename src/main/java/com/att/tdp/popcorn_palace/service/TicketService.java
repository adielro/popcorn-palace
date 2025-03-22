package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.TicketDto;
import com.att.tdp.popcorn_palace.entity.Ticket;
import com.att.tdp.popcorn_palace.exception.SeatAlreadyTakenException;
import com.att.tdp.popcorn_palace.exception.ShowtimeNotFoundException;
import com.att.tdp.popcorn_palace.repository.TicketRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for handling ticket-related business logic.
 * Includes seat availability checks and ticket booking.
 */
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ShowtimeRepository showtimeRepository;

    /**
     * Books a ticket if the seat is available.
     *
     * @param dto The ticket data to be booked
     * @return The saved ticket entity
     */
    public Ticket bookTicket(TicketDto dto) {
        // Check if the showtime exists
        if (!showtimeRepository.existsById(dto.getShowtimeId())) {
            throw new ShowtimeNotFoundException("Showtime not found");
        }

        // Check if the seat is already booked
        if (checkSeatAvailability(dto.getShowtimeId(), dto.getSeatNumber())) {
            throw new SeatAlreadyTakenException("Seat is already taken");
        }

        // Create and save the ticket
        Ticket ticket = Ticket.builder()
                .showtimeId(dto.getShowtimeId())
                .seatNumber(dto.getSeatNumber())
                .userId(dto.getUserId()) // Optional, for now we leave it null
                .build();

        return ticketRepository.save(ticket);
    }

    /**
     * Checks if a seat is already booked for the given showtime.
     *
     * @param showtimeId The showtime ID
     * @param seatNumber The seat number
     * @return true if the seat is already booked, false otherwise
     */
    public boolean checkSeatAvailability(Long showtimeId, String seatNumber) {
        return ticketRepository.findAll().stream()
                .anyMatch(ticket -> ticket.getShowtimeId().equals(showtimeId) && ticket.getSeatNumber().equals(seatNumber));
    }
}