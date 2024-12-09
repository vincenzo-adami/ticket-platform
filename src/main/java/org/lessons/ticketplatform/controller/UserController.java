package org.lessons.ticketplatform.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.ticketplatform.model.PasswordChanger;
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

  // Gett all users
  @GetMapping
  public String index(Model model, @AuthenticationPrincipal DatabaseUserDetails userDetails) {

    Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
    model.addAttribute("user", user.get());

    List<Ticket> allTickets = ticketRepo.findByUser(user.get());
    model.addAttribute("tickets", allTickets);

    model.addAttribute("passwordChanger", new PasswordChanger());

    return "users/index";
  }

  // update user info
  @PostMapping("/update")
  public String update(@Valid @ModelAttribute("user") User userForm, BindingResult bindingResult, Model model,
      RedirectAttributes redirectAttributes, @AuthenticationPrincipal DatabaseUserDetails userDetails) {

    // verify if user already exist
    Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
    if (!user.isPresent()) {
      bindingResult.addError(new FieldError("user.id", "id", "User not found"));
    }

    // veirfy if user have some ticket not completed and block update id true
    List<Ticket> userTicketByStatus = ticketRepo.findByStatusTicketNameNotAndUserUsername("complete",
        userDetails.getUsername());
    if (!(userTicketByStatus.isEmpty()) && !(userForm.isStatus())) {
      bindingResult.addError(new FieldError("user.status", "status",
          "Cannot change status because some ticket are not complete"));
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("tickets", ticketRepo.findByUser(user.get()));
      model.addAttribute("passwordChanger", new PasswordChanger());
      return "users/index";
    }

    // add current encrypt to password
    user.get().setPassword("{noop}" + userForm.getPassword());
    user.get().setStatus(userForm.isStatus());
    userRepo.save(user.get());

    redirectAttributes.addFlashAttribute("successMsg", "All is done");

    return "redirect:/users";
  }
}