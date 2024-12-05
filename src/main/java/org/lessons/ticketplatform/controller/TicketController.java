package org.lessons.ticketplatform.controller;

import java.util.List;

import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.repository.CategoryRepository;
import org.lessons.ticketplatform.repository.RoleRepository;
import org.lessons.ticketplatform.repository.TicketRepository;
import org.lessons.ticketplatform.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

  @Autowired
  TicketRepository ticketRepo;

  @Autowired
  RoleRepository roleRepo;

  @Autowired
  UserRepository userRepo;

  @Autowired
  CategoryRepository categoryRepo;

  @GetMapping()
  public String index(Model model, @RequestParam(required = false) String keyword) {

    List<Ticket> allTickets;

    if (keyword != null && !keyword.isBlank()) {
      allTickets = ticketRepo.findByTitleContaining(keyword);
      model.addAttribute("keyword", keyword);
    } else {
      allTickets = ticketRepo.findAll();
    }

    model.addAttribute("tickets", allTickets);

    return "tickets/index";
  }

  @GetMapping("/create")
  public String create(Model model) {

    model.addAttribute("ticket", new Ticket());
    model.addAttribute("users", userRepo.findByRoleNameAndStatus("USER", true).get());
    model.addAttribute("categories", categoryRepo.findAll());

    return "tickets/create";
  }

  @PostMapping("/store")
  public String store(@Valid @ModelAttribute("ticket") Ticket formTicket,
      BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      return "tickets/create";
    }

    ticketRepo.save(formTicket);

    redirectAttributes.addFlashAttribute("successMsg", "Ticket create");

    return "redirect:/tickets";

  }

}