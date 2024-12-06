package org.lessons.ticketplatform.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.model.User;
import org.lessons.ticketplatform.repository.TicketRepository;
import org.lessons.ticketplatform.repository.UserRepository;
import org.lessons.ticketplatform.security.DatabaseUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserRepository userRepo;

  @Autowired
  TicketRepository ticketRepo;

  @GetMapping
  public String index(Model model, @AuthenticationPrincipal DatabaseUserDetails userDetails) {

    Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
    model.addAttribute("user", user.get());

    List<Ticket> allTickets = ticketRepo.findByUser(user.get());
    model.addAttribute("tickets", allTickets);

    return "users/index";
  }

  @PostMapping("/update")
  public String update(@Valid @ModelAttribute("user") User userForm, BindingResult bindingResult, Model model,
      RedirectAttributes redirectAttributes, @AuthenticationPrincipal DatabaseUserDetails userDetails) {

    Optional<User> user = userRepo.findById(userForm.getId());
    if (!user.isPresent()) {
      bindingResult.addError(new FieldError("userNull", "username", "User not found"));
    }

    if (bindingResult.hasErrors()) {
      Optional<User> userRedirect = userRepo.findByUsername(userDetails.getUsername());
      model.addAttribute("user", userRedirect.get());

      List<Ticket> allTickets = ticketRepo.findByUser(userRedirect.get());
      model.addAttribute("tickets", allTickets);
      redirectAttributes.addFlashAttribute("errorMsg", "Somethings go wrong");

      return "users/index";
    }

    user.get().setPassword("{noop}" + userForm.getPassword());
    user.get().setStatus(userForm.isStatus());
    userRepo.save(user.get());

    redirectAttributes.addFlashAttribute("successMsg", "All is done");

    return "/users";
  }

}