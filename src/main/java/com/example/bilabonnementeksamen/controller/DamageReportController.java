package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.Employee;
import com.example.bilabonnementeksamen.model.Problem;
import com.example.bilabonnementeksamen.model.ProblemReport;
import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.service.DamageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class DamageReportController {

  @Autowired
  DamageReportService damageReportService;


  @GetMapping("/damage-home-page")
  public String showDamageReportHome(HttpSession session) {
    session.invalidate();

    return "problem/problem-home-page";
  }

  // Første siden som søger op en reservation
  @GetMapping("/search-reservation")
  public String showSearchReservation() {
    return "problem/problem-registration-start";
  }


  @PostMapping("/find-reservation-info")
  public String fetchReservationInfo(@RequestParam("reservation-id") int id, HttpSession session) {

    Reservation reservation = damageReportService.fetchReservationInfo(id);
    session.setAttribute("reservation", reservation);
    return "redirect:/problem-form";
  }


  @GetMapping("/problem-form")
  public String showFormRegistration(HttpSession session, Model model) {
    Reservation reservation = (Reservation) session.getAttribute("reservation");
    model.addAttribute("reservation", reservation);
    return "/problem/problem-form";
  }


  @GetMapping("/registrate-problem")
  public String registrateProblem() {
    return "/problem/problem-registration-input";
  }

  @PostMapping("/registrate-problem")
  public String registrateMoreProblem(HttpSession session, @ModelAttribute Problem problem) {
    ArrayList<Problem> listOfProblems = new ArrayList<>();

    if (session.getAttribute("problems") != null) {
      listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    }

    listOfProblems.add(problem);
    session.setAttribute("problems", listOfProblems);
    return "redirect:/registrate-problem";
  }

  @GetMapping("/problem-overview")
  public String showProblemOverview(HttpSession session, Model model) {
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    model.addAttribute("problems", listOfProblems);
    return "/problem/problem-input-overview";
  }

  @GetMapping("/deleteSpecificDamage")
  public String deleteSingleDamage(HttpSession session, Model model){
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    model.addAttribute("problems", listOfProblems);
    listOfProblems.remove(0);

    return "redirect:/problem-overview";
  }

/*
  @GetMapping("/problem-form")
  public String showFormRegistration(HttpSession session, Model model) {
    Reservation reservation = (Reservation) session.getAttribute("reservation");
    model.addAttribute("reservation", reservation);
    return "/problem/problem-form";
  }

 */

  @PostMapping("/problem-report-submit")
  public String createProblemReport(HttpSession session){
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    Reservation reservation = (Reservation) session.getAttribute("reservation");

    damageReportService.createProblemReport(listOfProblems, reservation);
    return "redirect:/result";
  }

  @GetMapping("/result")
  public String showProblemReport(HttpSession session, Model model){
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    Reservation reservation = (Reservation) session.getAttribute("reservation");
    int reportId = damageReportService.fetchReportId(listOfProblems, reservation);
    model.addAttribute("report_id", reportId);
    return "/problem/problem-form-result";
  }


  @PostMapping("/result")
  public String sendProblemReport(){
    //TODO: lave en metode for at oprette en pdf til kunden
    return "redirect:/damage-home-page";
  }

}
