package org.lessons.ticketplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  @GetMapping()
  public String index() {
    return "redirect:/tickets";
  }

}