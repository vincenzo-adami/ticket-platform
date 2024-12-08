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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/password")
public class PasswordController {

  @Autowired
  UserRepository userRepo;

  @Autowired
  TicketRepository ticketRepo;

  @PostMapping("/change")
  public String changePassword(@Valid @ModelAttribute("passwordChanger") PasswordChanger changePasswordForm,
      BindingResult bindingResult, Model model,
      @AuthenticationPrincipal DatabaseUserDetails userDetails, RedirectAttributes redirectAttributes) {

    System.out.println(userDetails.getPassword().substring(5));
    if (!(changePasswordForm.getActualPassword().equals(userDetails.getPassword().substring(6)))) {
      bindingResult.addError(new FieldError("changePasswordForm", "actualPassword", "Actual Password is different"));
    }

    if (!changePasswordForm.getConfirmNewPassword().equals(changePasswordForm.getNewPassword())) {
      bindingResult
          .addError(new FieldError("changePasswordForm", "confirmNewPassword", "New Password are not matching"));
    }

    if (bindingResult.hasErrors()) {
      Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
      model.addAttribute("user", user.get());

      List<Ticket> allTickets = ticketRepo.findByUser(user.get());
      model.addAttribute("tickets", allTickets);
      return "users/index";
    }

    User userByUsername = userRepo.findByUsername(userDetails.getUsername()).get();

    userByUsername.setPassword("{noop}" + changePasswordForm.getConfirmNewPassword());

    userRepo.save(userByUsername);
    redirectAttributes.addFlashAttribute("successPwdMsg", "Password updated");

    return "redirect:/users";

  }

}
