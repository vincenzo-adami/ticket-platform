package org.lessons.ticketplatform.repository;

import java.util.List;

import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findByTitleContaining(String keyword);

  @Query("SELECT t FROM Ticket t " +
      "JOIN t.user u " +
      "JOIN u.roles r " +
      "WHERE r.name LIKE :roleName AND t.title LIKE %:titleSubstring%")
  List<Ticket> findTicketsByUserRoleAndTitle(@Param("roleName") String roleName,
      @Param("titleSubstring") String titleSubstring);

  @Query("SELECT t FROM Ticket t " +
      "JOIN t.user u " +
      "JOIN u.roles r " +
      "WHERE r.name = :roleName")
  List<Ticket> findByUserRoleName(@Param("roleName") String role);

  List<Ticket> findByUser(User user);
}