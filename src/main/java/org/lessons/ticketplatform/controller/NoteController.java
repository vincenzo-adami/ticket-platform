package org.lessons.ticketplatform.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.lessons.ticketplatform.model.Note;
import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.model.User;
import org.lessons.ticketplatform.repository.NoteRepository;
import org.lessons.ticketplatform.repository.TicketRepository;
import org.lessons.ticketplatform.repository.UserRepository;
import org.lessons.ticketplatform.security.DatabaseUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/notes")
public class NoteController {

  @Autowired
  UserRepository userRepo;

  @Autowired
  NoteRepository noteRepo;

  @Autowired
  TicketRepository ticketRepo;

  @GetMapping("/create/{id}")
  public String create(Model model, @AuthenticationPrincipal DatabaseUserDetails userDetails, @PathVariable Long id) {

    Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
    Optional<Ticket> ticketById = ticketRepo.findById(id);
    Note note = new Note();

    note.setCreationDate(LocalDate.now());
    note.setUser(user.get());
    note.setTicket(ticketById.get());

    model.addAttribute("note", note);

    return "notes/create";
  }

  @PostMapping("/store")
  public String store(@Valid @ModelAttribute("note") Note formNote, BindingResult bindingResult,
      @AuthenticationPrincipal DatabaseUserDetails userDetails, Model model,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
      Optional<Ticket> ticketById = ticketRepo.findById(formNote.getTicket().getId());
      formNote.setCreationDate(LocalDate.now());
      formNote.setUser(user.get());
      formNote.setTicket(ticketById.get());
      return "notes/create";
    }

    noteRepo.save(formNote);

    redirectAttributes.addFlashAttribute("successMsg", "Note create");

    return "redirect:/tickets/show/" + formNote.getTicket().getId();
  }

}
