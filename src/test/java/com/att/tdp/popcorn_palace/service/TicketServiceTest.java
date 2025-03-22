package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.TicketDto;
import com.att.tdp.popcorn_palace.entity.Ticket;
import com.att.tdp.popcorn_palace.repository.TicketRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private TicketService ticketService;

    private TicketDto ticketDto;
    private Ticket savedTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the TicketDto
        ticketDto = TicketDto.builder()
                .showtimeId(1L)
                .seatNumber("A1")
                .userId(UUID.randomUUID())  // Optional userId
                .build();

        // Mock saved Ticket entity
        savedTicket = Ticket.builder()
                .id(UUID.randomUUID())
                .showtimeId(1L)
                .seatNumber("A1")
                .userId(UUID.randomUUID())
                .build();
    }

    // Test: Book a new ticket successfully
    @Test
    void testBookTicket() {
        when(showtimeRepository.existsById(anyLong())).thenReturn(true);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        Ticket bookedTicket = ticketService.bookTicket(ticketDto);

        assertNotNull(bookedTicket);
        assertEquals("A1", bookedTicket.getSeatNumber());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    // Test: Seat is already taken
    @Test
    void testBookTicketWithTakenSeat() {
        // Mock the repository to return valid data
        when(showtimeRepository.existsById(anyLong())).thenReturn(true);

        // Mock existing booked ticket
        when(ticketRepository.findAll()).thenReturn(List.of(savedTicket));  // Simulate that seat "A1" is already taken

        // Mock saving the ticket
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        // Expect a RuntimeException to be thrown due to seat being taken
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.bookTicket(ticketDto);  // Seat is already taken
        });

        assertEquals("Seat is already taken", exception.getMessage());
    }

    // Test: Booking a ticket for a non-existent showtime
    @Test
    void testBookTicketForNonExistentShowtime() {
        when(showtimeRepository.existsById(anyLong())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.bookTicket(ticketDto);  // Showtime doesn't exist
        });

        assertEquals("Showtime not found", exception.getMessage());
    }

    // Test: Check if seat availability works correctly
    @Test
    void testCheckSeatAvailability() {
        when(ticketRepository.findAll()).thenReturn(List.of(savedTicket));

        boolean seatAvailable = ticketService.checkSeatAvailability(1L, "A1");  // Seat is already booked

        assertTrue(seatAvailable);
    }
}