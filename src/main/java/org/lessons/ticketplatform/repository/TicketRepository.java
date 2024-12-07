package org.lessons.ticketplatform.repository;

import java.util.List;

import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByTitleContaining(String keyword);

    List<Ticket> findTicketsByUserUsernameAndTitle(String username, String keyword);

    List<Ticket> findByUserUsername(String username);

    List<Ticket> findByStatusTicketNameNotAndUserUsername(String statusTicket, String username);

    List<Ticket> findByUser(User user);
}