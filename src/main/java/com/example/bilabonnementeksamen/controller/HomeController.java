package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.repository.RegistrationRepo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

@Controller
public class HomeController {


  RegistrationRepo registrationRepo = new RegistrationRepo();


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

  /*@RequestMapping(value="/pickupDate", method= RequestMethod.POST)*/
  //Find biler ud fra dato og lejetype og send videre
  @PostMapping("/lease-contract")
  public String findCarsByDate() {

    return "redirect:/lease-available-cars";
  }

  @GetMapping("/lease-available-cars")
  public String showAvailableCars(Model model) {
    model.addAttribute("cars", registrationRepo.fetchCarsByDate(Date.valueOf("2022-11-24")));
    return "lease-available-cars";
  }
/*
  @PostMapping("/lease-available-cars")
  public String chooseCar() {
    //If-statement der tjekker om bilen er reserveret eller ej
    return "redirect:/lease-find-or-create-customer";
  }
*/
}
