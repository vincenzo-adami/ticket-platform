package org.lessons.ticketplatform.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

  @GetMapping
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        return "errors/404";
      }
      if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        return "errors/500";
      }
      if (statusCode == HttpStatus.FORBIDDEN.value()) {
        return "errors/403";
      }
    }
    return "errors/error";
  }
}