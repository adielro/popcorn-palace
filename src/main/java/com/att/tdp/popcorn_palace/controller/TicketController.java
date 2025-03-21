package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.TicketDto;
import com.att.tdp.popcorn_palace.entity.Ticket;
import com.att.tdp.popcorn_palace.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing tickets.
 */
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    /**
     * Books a new ticket.
     * POST /tickets
     */
    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody TicketDto dto) {
        Ticket bookedTicket = ticketService.bookTicket(dto);
        return ResponseEntity.ok(bookedTicket);
    }
}