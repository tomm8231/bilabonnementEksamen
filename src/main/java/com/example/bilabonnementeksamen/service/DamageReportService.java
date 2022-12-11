package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Problem;
import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.repository.ProblemReportRepo;
import com.example.bilabonnementeksamen.repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DamageReportService {

  @Autowired
  ProblemReportRepo problemReportRepo;

  @Autowired
  ReservationRepo reservationRepo;


  public Reservation fetchReservationInfo(int id) {
    return reservationRepo.fetchReservationInfoById(id);
  }

  // Tommy
  public int checkIfReservationExist(int reservationId) {
    String check = reservationRepo.checkIfReservationExist(reservationId);

    if (check != null) {
      return 1;
    } else {
      return 2;
    }
  }

  // Sebastian og lidt Marcus
  // TODO: Kan returnere enten objektet eller en int. Dele op til 2 metoder.
  public void createProblemReport(ArrayList<Problem> listOfProblems, Reservation reservation) {
    double totalPrice = calculateTotalPriceReport(listOfProblems);
    // Opretter en rapport
    problemReportRepo.createProblemReport(reservation, totalPrice);

    // Opretter problemer til rapporten (id bliver først oprettet når den er lavet i databasen)
    int report_id = problemReportRepo.fetchReportId(reservation, totalPrice);
    problemReportRepo.createProblems(listOfProblems, report_id);
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
    int report_id = problemReportRepo.fetchReportId(reservation, totalPrice);
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
