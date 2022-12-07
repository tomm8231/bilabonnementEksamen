package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Problem;
import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DamageReportService {

  @Autowired
  DamageReportRepo damageReportRepo;


  public Reservation fetchReservationInfo(int id) {
    return damageReportRepo.fetchReservationInfoById(id);
  }

// Sebastian og lidt Marcus
  // TODO: Kan metoden også returnere en int og overholde GRASP?
  public void createProblemReport(ArrayList<Problem> listOfProblems, Reservation reservation) {
    double totalPrice = calculateTotalPriceReport(listOfProblems);
    // Opretter en rapport
    damageReportRepo.createProblemReport(reservation, totalPrice);
    // Opretter problemer til rapporten (id bliver først oprettet når den er lavet i databasen)
    int report_id = damageReportRepo.fetchReportId(reservation, totalPrice);
    damageReportRepo.createProblems(listOfProblems, report_id);
  }

  private double calculateTotalPriceReport(ArrayList<Problem> listOfProblems){
    double totalPrice = 0;

    for (Problem problem : listOfProblems) {
      totalPrice += problem.getProblem_price();
    }
    return totalPrice;
  }


  public int fetchReportId(ArrayList<Problem> listOfProblems, Reservation reservation){
    double totalPrice = calculateTotalPriceReport(listOfProblems);
    int report_id = damageReportRepo.fetchReportId(reservation, totalPrice);
    return report_id;
  }


}
