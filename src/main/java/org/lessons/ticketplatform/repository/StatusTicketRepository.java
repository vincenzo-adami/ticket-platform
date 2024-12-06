package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.StatusTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTicketRepository extends JpaRepository<StatusTicket, Long> {

}