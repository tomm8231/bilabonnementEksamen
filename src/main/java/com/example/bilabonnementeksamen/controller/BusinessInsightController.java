package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.service.BusinessInsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class BusinessInsightController {

  @Autowired
  BusinessInsightService businessInsightService;

  @GetMapping("/business-home-page")
  public String showBusinessHome() {
    return "/business/business-home-page";
  }

  //sebastian
  @GetMapping("/show-reserved-cars")
  public String showReservedCars(Model model) {
    model.addAttribute("reservations", businessInsightService.fetchAllReservations());
    return "/business/business-show-rented-out-cars";
  }

// Sebastian
//viser regnskab ud fra år
@GetMapping("/show-income-chosen-year/{chosenYear}")
public String showIncomeChosenYear(@PathVariable("chosenYear") String chosenYear, Model model){

  int year = Integer.parseInt(chosenYear);

  //slutdatoer til brug i metoderne, baseret på året valgt
  ArrayList<LocalDate> everyMonthEndDate = businessInsightService.fetchEveryMonthEndDate(year);

  //Alt om startende kontrakter
  ArrayList <Integer> startingContracts = businessInsightService.fetchAllStartingContractsYearAmount(everyMonthEndDate);
  ArrayList <Double> startingContractsIncome = businessInsightService.fetchAllStartingContractsYearIncome(everyMonthEndDate);
  model.addAttribute("startingContracts",startingContracts);
  model.addAttribute("startingContractsIncome",startingContractsIncome);

  //Alt om endende kontrakter
  ArrayList <Integer> endingContracts;
  endingContracts = businessInsightService.fetchAllEndingContractsYearAmount(everyMonthEndDate);
  ArrayList <Double> endingContractsIncome = businessInsightService.fetchAllEndingContractsYearIncome(everyMonthEndDate);
  model.addAttribute("endingContracts",endingContracts);
  model.addAttribute("endingContractsIncome",endingContractsIncome);

  //Alt om fuldemåneds kontrakter
  ArrayList <Integer> ongoingContracts;
  ongoingContracts = businessInsightService.fetchAllongoingContractsYearAmount(everyMonthEndDate);
  ArrayList <Double> ongoingContractsIncome = businessInsightService.fetchAllOngoingContractsYearIncome(everyMonthEndDate);
  model.addAttribute("ongoingContracts",ongoingContracts);
  model.addAttribute("ongoingContractsIncome",ongoingContractsIncome);

  //total indkomst sammenlagt
  ArrayList <Double> totalIncomeMonths =  businessInsightService.calculateIncomeMonths(startingContractsIncome,endingContractsIncome,ongoingContractsIncome);
  model.addAttribute("totalContractsIncome",totalIncomeMonths);

  //Total indkomst
  model.addAttribute("totalStartingContracts", businessInsightService.calculateAmountFromList(startingContracts)); // int
  model.addAttribute("totalEndingContracts", businessInsightService.calculateAmountFromList(endingContracts)); // int
  model.addAttribute("totalOngoingContracts", businessInsightService.calculateAmountFromList(ongoingContracts));
  model.addAttribute("totalStartingContractsIncome", businessInsightService.calculateIncomeFromList(startingContractsIncome)); //double
  model.addAttribute("totalEndingContractsIncome", businessInsightService.calculateIncomeFromList(endingContractsIncome)); //double
  model.addAttribute("totalOngoingContractsIncome", businessInsightService.calculateIncomeFromList(ongoingContractsIncome)); //double
  model.addAttribute("totalYearIncome", businessInsightService.calculateIncomeFromList(totalIncomeMonths)); //double

  //tilføj året
  model.addAttribute("year",year);

  return "/business/showIncomeChoosenYear";
}

  //sebastian
  @PostMapping("/show-income-choosen-year")
  public String showIncomeChoosenYear(@RequestParam("year") String year) {
    return "redirect:/show-income-chosen-year/"+year;
  }


}
