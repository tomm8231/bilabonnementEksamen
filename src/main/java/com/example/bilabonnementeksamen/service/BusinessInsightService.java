package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.repository.BusinessInsightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

@Service
public class BusinessInsightService {

  @Autowired
  BusinessInsightRepo businessInsightRepo;


  public double calculateIncome(double fullMonthReservationsTotalIncome, double startMonthReservationsTotalIncome, double endMonthReservationsTotalIncome) {
    //forsøgte at gøre metoden mere opdelt
    //Nuværende måneds start og slutdatoer, til brug i metoderne
/*
    LocalDate today = LocalDate.now();
    LocalDate startDayOfMonthDate = today.with(TemporalAdjusters.firstDayOfMonth());
    LocalDate endDayOfMonthDate = today.with(TemporalAdjusters.lastDayOfMonth());
    int currentMonthLength = endDayOfMonthDate.getDayOfMonth();


    // Del 1: finde alle reservationer som går over en hel måned
    double fullMonthReservationsTotalIncome = businessInsightRepo.fetchFullCurrentMonthReservationsIncome(startDayOfMonthDate, endDayOfMonthDate);


    //Del 2: finde summen, for alle reservationer som starter i denne måned
    ArrayList<Reservation> startMonthReservations = businessInsightRepo.fetchStartCurrentMonthReservations(startDayOfMonthDate, endDayOfMonthDate);
    double startMonthReservationsTotalIncome = calculatePickupMonthReservationsIncome(startMonthReservations, currentMonthLength);

    //Del 3: finde summen, for alle reservationer som starter i denne måned
    ArrayList<Reservation> endMonthReservations = businessInsightRepo.fetchEndCurrentMonthReservations(startDayOfMonthDate, endDayOfMonthDate);
    double endMonthReservationsTotalIncome = calculateReturnMonthReservationsIncome(endMonthReservations, currentMonthLength);
*/

    double sum = fullMonthReservationsTotalIncome + startMonthReservationsTotalIncome + endMonthReservationsTotalIncome;
    return sum = Math.round(sum*100.0)/100.0;
  }

  public double calculatePickupMonthReservationsIncome(ArrayList<Reservation> startMonthReservations, int lastDayOfCurrentMonth) {

    double totalSum = 0;

    for (Reservation reservation : startMonthReservations) {

      String startDateAsString = reservation.getPickup_date().toString();
      int pickupDate = Integer.parseInt(startDateAsString.substring(startDateAsString.length() - 2));

      double pricePrMonth = reservation.getCar_vehicle_number().getCar_price_month();
      double numberOfDaysToPayFor = lastDayOfCurrentMonth - pickupDate;

      totalSum += (pricePrMonth / lastDayOfCurrentMonth) * numberOfDaysToPayFor;

    }

    return totalSum = Math.round(totalSum*100.0)/100.0;
  }

  public double calculateReturnMonthReservationsIncome(ArrayList<Reservation> startMonthReservations, int lastDayOfCurrentMonth) {

    double totalSum = 0;

    for (Reservation reservation : startMonthReservations) {

      String startDateAsString = reservation.getReturn_date().toString();
      int returnDate = Integer.parseInt(startDateAsString.substring(startDateAsString.length() - 2));

      double pricePrMonth = reservation.getCar_vehicle_number().getCar_price_month();

      totalSum += (pricePrMonth / lastDayOfCurrentMonth) * returnDate;

    }

    return totalSum = Math.round(totalSum*100.0)/100.0;
  }


  public ArrayList<Reservation> fetchAllStartingContracts(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {
    return businessInsightRepo.fetchStartCurrentMonthReservations(startDayOfMonthDate,endDayOfMonthDate);
  }

  public ArrayList<Reservation> fetchAllEndingContracts(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {
    return businessInsightRepo.fetchEndCurrentMonthReservations(startDayOfMonthDate,endDayOfMonthDate);
  }

  public int fetchAllOngoingContractsIncome(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {
    return businessInsightRepo.fetchFullCurrentMonthReservationsIncome(startDayOfMonthDate,endDayOfMonthDate);
  }

  public ArrayList<Reservation> fetchAllOngingContracts(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {
    return businessInsightRepo.fetchAllOngingContracts(startDayOfMonthDate, endDayOfMonthDate);
  }
}
