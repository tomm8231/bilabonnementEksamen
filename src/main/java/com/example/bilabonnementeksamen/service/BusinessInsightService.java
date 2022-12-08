package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.repository.BusinessInsightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BusinessInsightService {

  @Autowired
  BusinessInsightRepo businessInsightRepo;


  public double calculateIncome() {

    // Del 1: finde alle reservationer som går over en hel måned
   // int fullMonthReservationsTotalIncome =

    double sum = businessInsightRepo.fetchFullCurrentMonthReservations();

    // + scenario 2

    // + scenario 3

/*
    for (Reservation reservation : reservations) {
    //TODO: Hvad hvis bilerne er lejet ud kun halve måneden?
      // Dato check = aktive reservationer
      // Checke datoer og beregne en månedspris ud i fra den måneden

      sum += reservation.getCar_vehicle_number().getCar_price_month();
    }


 */
    return sum;
  }


}
