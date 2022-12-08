package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.service.BusinessInsightService;
import com.example.bilabonnementeksamen.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BusinessInsightController {

  @Autowired
  BusinessInsightService businessInsightService;

  @Autowired
  RegistrationService registrationService;

  @GetMapping("/business-home-page")
  public String showBusinessHome() {
    return "/business/business-home-page";
  }
/*
  @GetMapping("/business-economy")
  public String showEconomy(Model model){
    //TODO: skal ikke være alle reservationer, kun de i indeværende måned
    List<Reservation> reservations = registrationService.fetchAllReservations();
    model.addAttribute(reservations);

    double totalLeaseSum = businessInsightService.calculateIncome();
    model.addAttribute("totalSum", totalLeaseSum);
    return "/business/business-income";
  }

 */


  @GetMapping("/show-reserved-cars")
  public String showReservedCars(Model model){
    model.addAttribute("reservations",registrationService.fetchAllReservations());
    return "/business/business-show-rented-out-cars";
  }


    @GetMapping("/business-economy")
  public String showEconomy(Model model){
    List<Reservation> reservations = registrationService.fetchAllReservations();
    model.addAttribute(reservations);

    double totalLeaseSum = businessInsightService.calculateIncome();
    model.addAttribute("totalSum", totalLeaseSum);
      return "/business/business-income";
  }


}
