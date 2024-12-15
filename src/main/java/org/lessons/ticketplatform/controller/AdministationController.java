package org.lessons.ticketplatform.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.ticketplatform.model.Category;
import org.lessons.ticketplatform.model.Ticket;
import org.lessons.ticketplatform.model.User;
import org.lessons.ticketplatform.repository.CategoryRepository;
import org.lessons.ticketplatform.repository.RoleRepository;
import org.lessons.ticketplatform.repository.TicketRepository;
import org.lessons.ticketplatform.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/administration")
public class AdministationController {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private RoleRepository roleRepo;

  @Autowired
  private CategoryRepository categoryRepo;

  @Autowired
  private TicketRepository ticketRepo;

  // get all users and all tickets categories
  @GetMapping()
  public String manageIndex(Model model) {

    model.addAttribute("users", userRepo.findAll());
    model.addAttribute("categories", categoryRepo.findAll());

    return "administration/index";
  }

  @GetMapping("/createUser")
  public String createUser(Model model) {

    model.addAttribute("user", new User());
    model.addAttribute("allRoles", roleRepo.findAll());

    return "/administration/createUser";
  }

  // ADMIN create a new User
  @PostMapping("/storeUser")
  public String storeUser(@Valid @ModelAttribute("user") User userForm, BindingResult bindingResult, Model model,
      RedirectAttributes redirectAttributes) {

    // added error if username already exist
    List<User> findAll = userRepo.findAll();
    for (User user : findAll) {
      if (user.getUsername().equals(userForm.getUsername())) {
        bindingResult.addError(new FieldError("user", "username", "This Username already exist"));
      }
    }

    // added error if don't chose a role
    if (userForm.getRoles().isEmpty()) {
      bindingResult.addError(new FieldError("role", "roles", "Roles cannnot be blank. Chose one at least"));
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("allRoles", roleRepo.findAll());
      return "administration/createUser";
    }

    userForm.setPassword("{noop}" + userForm.getPassword());
    userRepo.save(userForm);
    redirectAttributes.addAttribute("userCreate", "User created");

    return "redirect:/administration";
  }

  @GetMapping("/editUser/{id}")
  public String editUser(@PathVariable Long id, Model model) {

    Optional<User> userById = userRepo.findById(id);
    if (userById.isPresent()) {
      // remove encryot algorithm from password before pass to model
      userById.get().setPassword(userById.get().getPassword().substring(6));
      model.addAttribute("user", userById.get());
      model.addAttribute("allRoles", roleRepo.findAll());
    }
    return "administration/editUser";
  }

  @PostMapping("/updateUser/{id}")
  public String updateUser(@Valid @ModelAttribute("user") User userForm, BindingResult bindingResult, Model model,
      RedirectAttributes redirectAttributes, @PathVariable Long id) {

    // added error if username is different from the chosen one or don't exist
    Optional<User> findById = userRepo.findById(id);
    if (findById.isPresent()) {
      if (!(userForm.getId() == findById.get().getId())) {
        bindingResult.addError(new FieldError("user", "username", "Username is diffent from selected one"));
      }
    } else {
      bindingResult.addError(new FieldError("user", "username", "This username dosen't exist"));
    }

    // added error if don't chose a role
    if (userForm.getRoles().isEmpty()) {
      bindingResult.addError(new FieldError("role", "roles", "Roles cannnot be blank. Chose one at least"));
    }

    /*
     * added error if try to change user statu to Not Avaible if they have some
     * ticket with status different to complete
     */
    List<Ticket> userTicketByStatus = ticketRepo.findByStatusTicketNameNotAndUserUsername("complete",
        userForm.getUsername());
    if (!(userTicketByStatus.isEmpty()) && !(userForm.isStatus())) {
      bindingResult.addError(new FieldError("user.status", "status",
          "Cannot change status because some ticket are not complete"));
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("allRoles", roleRepo.findAll());
      return "administration/editUser";
    }

    findById.get().setUsername(userForm.getUsername());
    findById.get().setPassword("{noop}" + userForm.getPassword());
    findById.get().setRoles(userForm.getRoles());
    findById.get().setStatus(userForm.isStatus());
    userRepo.save(findById.get());
    redirectAttributes.addFlashAttribute("updateUser", "User updated");

    return "redirect:/administration";
  }

  @GetMapping("/createCategory")
  public String createCategory(Model model) {

    model.addAttribute("category", new Category());

    return "administration/createCategory";
  }

  @PostMapping("/storeCategory")
  public String storeCategory(@Valid @ModelAttribute("category") Category categoryForm, BindingResult bindingResult,
      Model model, RedirectAttributes redirectAttributes) {

    // added error if new category already exist
    List<Category> findAll = categoryRepo.findAll();
    for (Category category : findAll) {
      if (category.getName().equals(categoryForm.getName())) {
        bindingResult.addError(new FieldError("category", "name", "This category already exist"));
      }
    }

    if (bindingResult.hasErrors()) {
      return "administration/createCategory";
    }

    categoryRepo.save(categoryForm);
    redirectAttributes.addFlashAttribute("categoryCreate", "Category created");

    return "redirect:/administration";
  }

  @GetMapping("/editCategory/{id}")
  public String editCategory(Model model, @PathVariable Long id) {
    Optional<Category> findById = categoryRepo.findById(id);
    if (findById.isPresent()) {
      model.addAttribute("category", findById.get());
    }
    return "/administration/editCategory";
  }

  @PostMapping("/updateCategory/{id}")
  public String updateCategory(@Valid @ModelAttribute("category") Category categoryForm, BindingResult bindingResult,
      Model model, RedirectAttributes redirectAttributes, @PathVariable Long id) {

    // added an error if the category chosen to update already exists
    List<Category> findAll = categoryRepo.findAll();
    for (Category category : findAll) {
      if (category.getName().equals(categoryForm.getName())) {
        bindingResult.addError(new FieldError("category", "name", "This category already exist"));
      }
    }
    if (bindingResult.hasErrors()) {
      return "administration/editCategory";
    }

    categoryRepo.save(categoryForm);
    redirectAttributes.addFlashAttribute("categoryUpdate", "Category updated");

    return "redirect:/administration";
  }

  @PostMapping("/deleteCategory/{id}")
  public String postMethodName(@PathVariable Long id, RedirectAttributes redirectAttributes) {

    categoryRepo.deleteById(id);

    redirectAttributes.addFlashAttribute("deleteCategory", "Category deleted");

    return "redirect:/administration";
  }
}