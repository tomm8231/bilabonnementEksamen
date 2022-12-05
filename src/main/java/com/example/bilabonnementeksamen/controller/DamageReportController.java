package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.service.DamageReportService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DamageReportController {

  @Autowired
  DamageReportService damageReportService;


  @GetMapping("/damage-home-page")
  public String showDamageReportHome() {
    return "problem/problem-home-page";
  }

  // Første siden som søger op en reservation
  @GetMapping("/search-reservation")
  public String showSearchReservation(){
    return "problem/problem-registration-start";
  }



  @PostMapping ("/find-reservation-info")
  public String fetchReservationInfo(@RequestParam("reservation-id") int id, RedirectAttributes redirectAttributes){
    Reservation reservation = (Reservation) damageReportService.fetchReservationInfo(id);
    redirectAttributes.addAttribute("reservation", reservation);
    return "redirect:/problem-form";
  }


  @GetMapping ("/problem-form")
  public String showFormRegistration(@RequestParam("reservation") Reservation reservation){
    return "/problem/problem-form";
  }


}
