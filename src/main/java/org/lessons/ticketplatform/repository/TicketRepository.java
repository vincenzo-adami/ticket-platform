package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
