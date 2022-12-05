package com.example.bilabonnementeksamen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Før man har en database
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

@Controller
public class HomeController {


  @GetMapping("/")
  public String index() {
    return "index";
  }
}
