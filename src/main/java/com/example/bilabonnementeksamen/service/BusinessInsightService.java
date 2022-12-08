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
    //Nuværende måneds start og slutdatoer, til brug i metoderne
    int firstDayOfCurrentMonth = 1;
    int lastDayOfCurrentMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH); //TODO check for 0
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    Calendar startDayOfMonth = Calendar.getInstance();
    startDayOfMonth.set(currentYear, currentMonth, firstDayOfCurrentMonth);

    Date startDayOfMonthDate = (Date) startDayOfMonth.getTime();
    Date endDayOfMonthDate = (Date) startDayOfMonth.getTime();
    // Del 1: finde alle reservationer som går over en hel måned
   // int fullMonthReservationsTotalIncome =

    Calendar endDayOfMonth = Calendar.getInstance();
    endDayOfMonth.set(currentMonth, currentMonth, lastDayOfCurrentMonth);
    double sum = businessInsightRepo.fetchFullCurrentMonthReservations();


    double sum = 0;


    //Del 2: finde summen, for alle reservationer som starter i denne måned
    ArrayList <Reservation> startMonthReservations = businessInsightRepo.fetchStartCurrentMonthReservations(startDayOfMonthDate, endDayOfMonthDate);
    double startMonthReservationsTotalIncome = calculatePickupMonthReservationsIncome(startMonthReservations, lastDayOfCurrentMonth);

    //Del 3: finde summen, for alle reservationer som starter i denne måned
    ArrayList <Reservation> endMonthReservations = businessInsightRepo.fetchEndCurrentMonthReservations(startDayOfMonthDate, endDayOfMonthDate);
    double endMonthReservationsTotalIncome = calculatePickupMonthReservationsIncome(endMonthReservations, lastDayOfCurrentMonth);



    double sum = fullMonthReservationsTotalIncome + startMonthReservationsTotalIncome + endMonthReservationsTotalIncome;

    return sum;
  }

  private double calculatePickupMonthReservationsIncome(ArrayList<Reservation> startMonthReservations, int lastDayOfCurrentMonth) {

    double totalSum = 0;

    for (Reservation reservation: startMonthReservations) {

      String startDateAsString = reservation.getPickup_date().toString();
      int pickupDate = Integer.parseInt(startDateAsString.substring(startDateAsString.length()-2));

      double pricePrMonth = reservation.getCar_vehicle_number().getCar_price_month();
      double numberOfDaysToPayFor = lastDayOfCurrentMonth - pickupDate;

      totalSum += (pricePrMonth / lastDayOfCurrentMonth) * numberOfDaysToPayFor;

    }

    return totalSum;
  }

  private double calculateReturnMonthReservationsIncome(ArrayList<Reservation> startMonthReservations, int lastDayOfCurrentMonth) {

    double totalSum = 0;

    for (Reservation reservation: startMonthReservations) {

      String startDateAsString = reservation.getReturn_date().toString();
      int returnDate = Integer.parseInt(startDateAsString.substring(startDateAsString.length()-2));

      double pricePrMonth = reservation.getCar_vehicle_number().getCar_price_month();

      totalSum += (pricePrMonth / lastDayOfCurrentMonth) * returnDate;

    }

    return totalSum;
  }


}
