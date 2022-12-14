package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.Problem;
import com.example.bilabonnementeksamen.model.ProblemReport;
import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.service.ProblemReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProblemReportController {

  @Autowired
  ProblemReportService problemReportService;


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

  // Tommy
  @PostMapping("/find-reservation-info")
  public String fetchReservationInfo(@RequestParam("reservation-id") int id, HttpSession session) {

    int numberOption = problemReportService.checkIfReservationExist(id);
    switch (numberOption) {
      case 1 -> {
        Reservation reservation = problemReportService.fetchReservationInfo(id);
        session.setAttribute("reservation", reservation);
        return "redirect:/problem-form";
      }
      case 2 -> {
        return "redirect:/search-reservation";
      }
      default -> {
        return "redirect:/search-reservation";
      }
    }
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

// Sebastian og Daniel
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

  // Viser alle problem som er tilføjet
  @GetMapping("/problem-overview")
  public String showProblemOverview(HttpSession session, Model model) {
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    model.addAttribute("problems", listOfProblems);
    return "/problem/problem-input-overview";
  }

  //Marcus, daniel, sebastian
  @PostMapping("/deleteSpecificDamage")
  public String deleteSingleDamage(HttpSession session, @ModelAttribute Problem deleteProblem){
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");

    listOfProblems = problemReportService.removeProblemFromList(listOfProblems, deleteProblem);

    session.setAttribute("problems",listOfProblems);

    return "redirect:/problem-overview";
  }

  @PostMapping("/problem-report-submit")
  public String createProblemReport(HttpSession session){
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    Reservation reservation = (Reservation) session.getAttribute("reservation");

    //laver en rapport og returnere id
    Integer reportID = problemReportService.createProblemReport(listOfProblems, reservation);
    //tilføjer problmer
    problemReportService.createProblemsInReport(listOfProblems, reportID);
    return "redirect:/result";
  }

  // Bekræftelse til ansat på at man har oprettet en skaderapport
  @GetMapping("/result")
  public String showProblemReport(HttpSession session, Model model){
    ArrayList<Problem> listOfProblems = (ArrayList<Problem>) session.getAttribute("problems");
    // Finde reservationen
    Reservation reservation = (Reservation) session.getAttribute("reservation");
    // Finde report_id
    int reportId = problemReportService.fetchReportId(listOfProblems, reservation);
    model.addAttribute("report_id", reportId);
    return "/problem/problem-form-result";
  }


  @PostMapping("/result")
  public String sendProblemReport(){
    //TODO: lave en metode for at oprette en pdf til kunden
    return "redirect:/damage-home-page";
  }

  // Tommy
  //Slå skade op

  @GetMapping("/search-problem-report-by-id")
  public String showSearchFormProblem(@RequestParam (value = "message", required = false) String message, Model model) {
    model.addAttribute("message", message);
    return "problem/search-problem-report";
  }

  // Tommy
  @PostMapping("search-problem-report-by-id")
  public String sendProblemReportId(@RequestParam("problem-report-id") int report_id,
                                    RedirectAttributes redirectAttributes, HttpSession session) {
    int numberOption = problemReportService.checkIfProblemReportExist(report_id);

    switch (numberOption) {
      case 1 -> {
        ProblemReport problemReport = problemReportService.fetchProblemReportById(report_id);
        ArrayList<Problem> listOfProblems = problemReportService.fetchListOfProblemsById(report_id);
        problemReport.setListOfProblems(listOfProblems);
        session.setAttribute("problem-report", problemReport);
        return "redirect:/show-problem-report";

      } case 2 -> {
        redirectAttributes.addAttribute("message", "Reservationen findes ikke");
        return "redirect:/search-problem-report-by-id";
      }

      default -> {
        redirectAttributes.addAttribute("message", "Indtast 1 eller højere");
        return "redirect:/search-problem-report-by-id";
      }
    }

  }

  // Tommy
  @GetMapping("/show-problem-report")
  public String showProblemReportById(HttpSession session, Model model) {
    ProblemReport problemReport = (ProblemReport) session.getAttribute("problem-report");
    model.addAttribute("problemReport", problemReport);
    return "problem/problem-show-report";
  }

}
