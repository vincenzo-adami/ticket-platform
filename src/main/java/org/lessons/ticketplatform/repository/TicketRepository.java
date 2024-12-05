package org.lessons.ticketplatform.repository;

import java.util.List;

import org.lessons.ticketplatform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findByTitleContaining(String keyword);
}
