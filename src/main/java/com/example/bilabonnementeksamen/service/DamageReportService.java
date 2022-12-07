package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Problem;
import com.example.bilabonnementeksamen.model.ProblemReport;
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


  public void createProblemReport(ArrayList<Problem> listOfProblems, Reservation reservation) {
    double totalPrice = calculateTotalPriceReport(listOfProblems);
    damageReportRepo.createProblemReport(reservation, totalPrice);
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
