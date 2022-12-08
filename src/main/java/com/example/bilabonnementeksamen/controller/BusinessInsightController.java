package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.service.BusinessInsightService;
import com.example.bilabonnementeksamen.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BusinessInsightController {

  @Autowired
  BusinessInsightService businessInsightService;

  //TODO: er denne nødvendig?
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
  public String showReservedCars(Model model) {
    model.addAttribute("reservations", registrationService.fetchAllReservations());
    return "/business/business-show-rented-out-cars";
  }


  @GetMapping("/business-economy")
  public String showEconomy(Model model) {

    LocalDate today = LocalDate.now();
    LocalDate startDayOfMonthDate = today.with(TemporalAdjusters.firstDayOfMonth());
    LocalDate endDayOfMonthDate = today.with(TemporalAdjusters.lastDayOfMonth());
    int currentMonthLength = endDayOfMonthDate.getDayOfMonth();

    //antal startende reservationer og indkomst deraf
    ArrayList<Reservation> allStartingContracts = businessInsightService.fetchAllStartingContracts(startDayOfMonthDate, endDayOfMonthDate);
    model.addAttribute("startingLeasesAmount", allStartingContracts.size());
    double startingContractsIncome = businessInsightService.calculatePickupMonthReservationsIncome(allStartingContracts,currentMonthLength);
    model.addAttribute("startingLeasesIncome", startingContractsIncome);

    //antal sluttende reservationer og indkomst deraf
    ArrayList<Reservation> allEndingContracts = businessInsightService.fetchAllEndingContracts(startDayOfMonthDate, endDayOfMonthDate);
    model.addAttribute("endingLeasesAmount", allEndingContracts.size());
    double endingContractsIncome = businessInsightService.calculateReturnMonthReservationsIncome(allEndingContracts,currentMonthLength);
    model.addAttribute("endingLeasesIncome", endingContractsIncome);

    //antal fuld måned reservationer og indkomst deraf
    ArrayList<Reservation> allOngoingContracts = businessInsightService.fetchAllOngingContracts(startDayOfMonthDate, endDayOfMonthDate);
    model.addAttribute("ongoingLeasesAmount", allOngoingContracts.size());
    double fullMonthContractsIncome = businessInsightService.fetchAllOngoingContractsIncome(startDayOfMonthDate, endDayOfMonthDate);
    model.addAttribute("ongoingLeasesIncome", fullMonthContractsIncome);

    //samlet indtægt
    double totalLeaseSum = businessInsightService.calculateIncome(fullMonthContractsIncome, startingContractsIncome, endingContractsIncome);
    model.addAttribute("totalSum", totalLeaseSum);


    return "/business/business-income";
  }


}
