package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.repository.RegistrationRepo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

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

  //Find biler ud fra dato og lejetype og send videre
  @PostMapping("/lease-contract")
  public String findCarsByDate(@RequestParam("start-date") String startDate, @RequestParam("end-date") String endDate,
                               RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("rd-start-date", startDate);
    redirectAttributes.addAttribute("rd-end-date", endDate);

    return "redirect:/lease-available-cars";
  }
/*
  @GetMapping("/lease-available-cars")
  public String showAvailableCars(Model model, @RequestParam ("rd-start-date") String startDate,
                                  @RequestParam("rd-end-date") String endDate) {
    model.addAttribute("cars", registrationRepo.fetchCarsByDate(Date.valueOf(startDate), Date.valueOf(endDate)));
    return "lease-available-cars";
  }
*/
  @GetMapping("/lease-available-cars")
  public String showAvailableCars(Model model) {
    model.addAttribute("car_model", registrationRepo.fetchCarsByDate());
    return "lease-available-cars";
  }

/*
  @PostMapping("/lease-available-cars")
  public String chooseCar() {
    //If-statement der tjekker om bilen er reserveret eller ej
    return "redirect:/lease-find-or-create-customer";
  }
*/

  @GetMapping("/lease-find-or-create-customer")
  public String showFindOrdCreateCustomer(){
    return "lease-find-or-create-customer";
  }

  @PostMapping("/lease-find-or-create-customer")
  public String leaseAddYouser(){

    return "lease-find-or-create-customer";
  }
}
