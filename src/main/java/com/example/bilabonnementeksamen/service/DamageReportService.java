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

  private double calculateTotalPriceReport(ArrayList<Problem> listOfProblems) {
    double totalPrice = 0;

    for (Problem problem : listOfProblems) {
      totalPrice += problem.getProblem_price();
    }
    return totalPrice;
  }


  public int fetchReportId(ArrayList<Problem> listOfProblems, Reservation reservation) {
    double totalPrice = calculateTotalPriceReport(listOfProblems);
    int report_id = damageReportRepo.fetchReportId(reservation, totalPrice);
    return report_id;
  }

  //sebastian, marcus, daniel
  public ArrayList<Problem> removeProblemFromList(ArrayList<Problem> listOfProblems, Problem deleteProblem) {

    ArrayList<Problem> problems = listOfProblems;

    for (int i = 0; i < problems.size(); i++) {

      Problem tempProblem = problems.get(i);

      //sammenligner alle parametre, som findes på vores deleteproblem med den givne liste og fjerner problem
      if (deleteProblem.getProblem_description().equals(tempProblem.getProblem_description())
          && deleteProblem.getProblem_price() == tempProblem.getProblem_price()
          && deleteProblem.getProblem_type().equals(tempProblem.getProblem_type())) {

        problems.remove(tempProblem);
      }

    }
    return problems;
  }
}
