package com.example.bilabonnementeksamen.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

@Controller
public class HomeController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/registration")
  public String showRegistrationPage() {
    return "registration-home-page";
  }



  @GetMapping("/lease-contract")
  public String showNewContract() {
    return "lease-start-new-contract";
  }

  //Find biler ud fra dato og lejetype og send videre
  @PostMapping("/lease-contract")
  public String findCarsByDate() {
    return "redirect:/lease-available-cars";
  }

  @GetMapping("/lease-available-cars")
  public String showAvailableCars() {
    return "lease-available-cars";
  }

  @PostMapping("/lease-available-cars")
  public String chooseCar() {
    //If-statement der tjekker om bilen er reserveret eller ej
    return "redirect:/lease-find-or-create-customer";
  }

}
