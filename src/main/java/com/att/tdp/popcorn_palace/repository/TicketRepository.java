package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing ticket data.
 * Provides basic CRUD operations for Ticket entity.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}