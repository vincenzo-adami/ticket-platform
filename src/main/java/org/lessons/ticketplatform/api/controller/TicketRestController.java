package org.lessons.ticketplatform.api.controller;

import java.util.List;

import org.lessons.ticketplatform.api.model.Payload;
import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

  @Autowired
  TicketRepository ticketRepo;

  @GetMapping()
  public ResponseEntity<Payload<List<Ticket>>> all() {
    return ResponseEntity.ok(new Payload<List<Ticket>>("OK", "200", ticketRepo.findAll()));
  }

  @GetMapping("/status/{statusTicket}")
  public ResponseEntity<Payload<List<Ticket>>> findByStatus(@PathVariable(required = true) String statusTicket) {

    try {
      List<Ticket> byStatus = ticketRepo.findByStatusTicketName(statusTicket);

      if (!byStatus.isEmpty()) {
        return ResponseEntity.ok(new Payload<>("OK", "200", byStatus));
      }

      throw new Exception("Status ticket not found");

    } catch (Exception e) {
      return new ResponseEntity<Payload<List<Ticket>>>(
          new Payload<List<Ticket>>(e.getMessage(), "404", null),
          HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<Payload<List<Ticket>>> findByCategory(@PathVariable(required = true) String category) {

    try {
      List<Ticket> byCategory = ticketRepo.findByCategoryName(category);

      if (!byCategory.isEmpty()) {
        return ResponseEntity.ok(new Payload<>("OK", "200", byCategory));

      }

      throw new Exception("Category not found");

    } catch (Exception e) {
      return new ResponseEntity<Payload<List<Ticket>>>(
          new Payload<List<Ticket>>(e.getMessage(), "404", null),
          HttpStatus.NOT_FOUND);
    }
  }

}
