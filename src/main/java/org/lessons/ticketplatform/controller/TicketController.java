package org.lessons.ticketplatform.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.ticketplatform.model.Category;
import org.lessons.ticketplatform.model.StatusTicket;
import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.repository.StatusTicketRepository;
import org.lessons.ticketplatform.repository.CategoryRepository;
import org.lessons.ticketplatform.repository.NoteRepository;
import org.lessons.ticketplatform.repository.TicketRepository;
import org.lessons.ticketplatform.repository.UserRepository;
import org.lessons.ticketplatform.security.DatabaseUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

  @Autowired
  private TicketRepository ticketRepo;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private StatusTicketRepository statusTicketRepository;

  @Autowired
  private NoteRepository noteRepo;

  @Autowired
  private CategoryRepository categoryRepo;

  // get all ticket
  @GetMapping()
  public String index(Model model, @RequestParam(required = false) String keyword,
      @AuthenticationPrincipal DatabaseUserDetails userDetails) {

    // get all tickets if logged user has ADMIN authority
    List<Ticket> allTickets = null;
    if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {

      // filter ticket by keyword
      if (keyword != null && !keyword.isBlank()) {
        allTickets = ticketRepo.findByTitleContaining(keyword);
        model.addAttribute("keyword", keyword);
      } else {
        allTickets = ticketRepo.findAll();
      }
    } else {

      // get all ticket of the current logged user if don't have ADMIN authority

      // filter ticket by keyword
      if (keyword != null && !keyword.isBlank()) {
        allTickets = ticketRepo.findTicketsByUserUsernameAndTitle(userDetails.getUsername(), keyword);
        model.addAttribute("keyword", keyword);
      } else {
        allTickets = ticketRepo.findByUserUsername(userDetails.getUsername());
      }
    }

    model.addAttribute("tickets", allTickets);

    return "tickets/index";
  }

  // get ticket details
  @GetMapping("/show/{id}")
  public String show(@PathVariable Long id, @RequestParam(required = false) String keyword, Model model) {
    Optional<Ticket> ticketById = ticketRepo.findById(id);

    if (ticketById.isPresent()) {
      model.addAttribute("ticket", ticketById.get());
    }
    model.addAttribute("keyword", keyword);
    if (keyword == null || keyword.isBlank() || keyword.equals("null")) {
      model.addAttribute("ticketUrl", "/tickets");
    } else {
      model.addAttribute("ticketUrl", "/?keyword=" + keyword);
    }

    return "tickets/show";
  }

  // get ticket create page
  @GetMapping("/create")
  public String create(Model model) {

    model.addAttribute("ticket", new Ticket());
    model.addAttribute("users", userRepo.findByRolesNameAndStatus("USER", true));
    model.addAttribute("statusTicket", statusTicketRepository.findAll());
    model.addAttribute("categories", categoryRepo.findAll());

    return "tickets/create";
  }

  // save ticket
  @PostMapping("/store")
  public String store(@Valid @ModelAttribute("ticket") Ticket formTicket,
      BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

    // add error if user selected doesn't exist
    if (!formTicket.getUser().isStatus()) {
      bindingResult.addError(new FieldError("notAvaible", "user", "User not avaible, chose another one"));
    }

    // add error if statuTicket selected doesn't exist
    List<StatusTicket> findAllStatusTicket = statusTicketRepository.findAll();
    if (!findAllStatusTicket.contains(formTicket.getStatusTicket())) {
      bindingResult.addError(new FieldError("statusNotFound", "statusTicket", "Chose a valid Status for Ticket"));
      model.addAttribute("statusTicket", findAllStatusTicket);
    }

    // add error if category selected doesn't exist
    List<Category> findAllCategory = categoryRepo.findAll();
    if (!findAllCategory.contains(formTicket.getCategory())) {
      bindingResult.addError(new FieldError("categoryNotFound", "category", "Chose a valid Category for Ticket"));
      model.addAttribute("category", findAllCategory);
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("users", userRepo.findByRolesNameAndStatus("USER", true));
      model.addAttribute("statusTicket", statusTicketRepository.findAll());
      model.addAttribute("categories", categoryRepo.findAll());
      return "tickets/create";
    }

    ticketRepo.save(formTicket);

    redirectAttributes.addFlashAttribute("successMsg", "Ticket create");

    return "redirect:/tickets";

  }

  // get ticket edit page
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Long id, Model model, @AuthenticationPrincipal DatabaseUserDetails userDetails) {

    Optional<Ticket> byId = ticketRepo.findById(id);

    if (byId.isPresent()) {
      model.addAttribute("ticket", byId.get());
      if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
        model.addAttribute("users", userRepo.findByRolesNameAndStatus("USER", true));
      } else {
        model.addAttribute("users", userRepo.findByUsername(userDetails.getUsername()).get());
      }
      model.addAttribute("statusTicket", statusTicketRepository.findAll());
      model.addAttribute("categories", categoryRepo.findAll());
    }

    return "tickets/edit";

  }

  // update ticket from edit page
  @PostMapping("edit/{id}")
  public String update(@Valid @ModelAttribute("ticket") Ticket formEditTicket, BindingResult bindingResult, Model model,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("users", userRepo.findByRolesNameAndStatus("USER", true));
      model.addAttribute("statusTicket", statusTicketRepository.findAll());
      model.addAttribute("categories", categoryRepo.findAll());
      return "tickets/edit";
    }

    ticketRepo.save(formEditTicket);

    redirectAttributes.addFlashAttribute("updateMsg", "Ticket update");

    return "redirect:/tickets";
  }

  // delete ticket and note linked to it
  @PostMapping("delete/{id}")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {

    noteRepo.deleteByTicketId(id);
    ticketRepo.deleteById(id);

    redirectAttributes.addFlashAttribute("deleteMsg", "Ticket deleted");

    return "redirect:/tickets";
  }
}