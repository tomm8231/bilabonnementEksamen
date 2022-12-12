package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessInsightService {

  @Autowired
  ReservationRepo reservationRepo;


  public double calculatePickupMonthReservationsIncome(ArrayList<Reservation> startMonthReservations, int lastDayOfChosenMonth) {

    double totalSum = 0;

    for (Reservation reservation : startMonthReservations) {

      String startDateAsString = reservation.getPickup_date().toString();
      int pickupDate = Integer.parseInt(startDateAsString.substring(startDateAsString.length() - 2));

      double pricePrMonth = reservation.getCar_vehicle_number().getCar_price_month();
      double numberOfDaysToPayFor = lastDayOfChosenMonth - pickupDate;

      totalSum += (pricePrMonth / lastDayOfChosenMonth) * numberOfDaysToPayFor;

    }

    totalSum = Math.round(totalSum * 100.0) / 100.0; //til to decimaler
    return totalSum;
  }

  public double calculateReturnMonthReservationsIncome(ArrayList<Reservation> startMonthReservations, int lastDayOfChosenMonth) {

    double totalSum = 0;

    for (Reservation reservation : startMonthReservations) {

      String startDateAsString = reservation.getReturn_date().toString();
      int returnDate = Integer.parseInt(startDateAsString.substring(startDateAsString.length() - 2));

      double pricePrMonth = reservation.getCar_vehicle_number().getCar_price_month();

      totalSum += (pricePrMonth / lastDayOfChosenMonth) * returnDate;

    }

    totalSum = Math.round(totalSum * 100.0) / 100.0; //til to decimaler
    return totalSum;
  }

  public ArrayList<Integer> fetchAllStartingContractsYearAmount(ArrayList<LocalDate> endDaysOfMonthDate) {

    ArrayList<Integer> allStartingContractsYear = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      LocalDate startDayOfMonthDate = endDaysOfMonthDate.get(i).with(TemporalAdjusters.firstDayOfMonth());
      ArrayList<Reservation> chosenMonthStartingContracts = reservationRepo.fetchStartCurrentMonthReservations(startDayOfMonthDate, endDaysOfMonthDate.get(i));

      allStartingContractsYear.add(chosenMonthStartingContracts.size());
    }
    return allStartingContractsYear;
  }

  public ArrayList<Integer> fetchAllEndingContractsYearAmount(ArrayList<LocalDate> endDaysOfMonthDate) {

    ArrayList<Integer> allEndingContractsYear = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      LocalDate startDayOfMonthDate = endDaysOfMonthDate.get(i).with(TemporalAdjusters.firstDayOfMonth());
      ArrayList<Reservation> chosenMonthStartingContracts = reservationRepo.fetchEndCurrentMonthReservations(startDayOfMonthDate, endDaysOfMonthDate.get(i));

      allEndingContractsYear.add(chosenMonthStartingContracts.size());
    }
    return allEndingContractsYear;
  }

  public ArrayList<Integer> fetchAllongoingContractsYearAmount(ArrayList<LocalDate> endDaysOfMonthDate) {

    ArrayList<Integer> allongoingContractsYear = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      LocalDate startDayOfMonthDate = endDaysOfMonthDate.get(i).with(TemporalAdjusters.firstDayOfMonth());
      ArrayList<Reservation> chosenMonthongoingContracts = reservationRepo.fetchAllOngingContracts(startDayOfMonthDate, endDaysOfMonthDate.get(i));
      allongoingContractsYear.add(chosenMonthongoingContracts.size());
    }
    return allongoingContractsYear;
  }

  public int calculateAmountFromList(ArrayList<Integer> contracts) {
    int sum = 0;

    for (Integer amout: contracts){
      sum += amout;
    }
    return sum;
  }

  public double calculateIncomeFromList(ArrayList<Double> contracts) {
    double totalSum = 0;

    for (Double sum: contracts){
      totalSum += sum;
    }

    totalSum = Math.round(totalSum * 100.0) / 100.0; //til to decimaler
    return totalSum;
  }

  public ArrayList<LocalDate> fetchEveryMonthEndDate(int year){
    ArrayList<LocalDate> endDaysOfMonthDate = new ArrayList<>();
    LocalDate chosenMonth;
    for (int i = 1; i <= 12; i++) {
      chosenMonth = LocalDate.of(year, i, 1);
      LocalDate end = chosenMonth.withDayOfMonth(chosenMonth.getMonth().length(chosenMonth.isLeapYear()));
      endDaysOfMonthDate.add(end);
    }
    return  endDaysOfMonthDate;
  }

  public ArrayList<Double> fetchAllStartingContractsYearIncome(ArrayList<LocalDate> endDaysOfMonthDate) {


    ArrayList<Double> allStartingContractsYearIncome = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      //find første dag på måneden, ud fra listen af endDaysOfMonthDate
      LocalDate startDayOfMonthDate = endDaysOfMonthDate.get(i).with(TemporalAdjusters.firstDayOfMonth());
      //find listen af reservation, ud fra startdatoen ovenfor og endDaysOfMonthDate
      ArrayList<Reservation> chosenMonthStartingContracts = reservationRepo.fetchStartCurrentMonthReservations(startDayOfMonthDate, endDaysOfMonthDate.get(i));
      //Omregn månedens reservations til en samlet sum, og tilføj dem til listen
      int lastDayValue = endDaysOfMonthDate.get(i).getDayOfMonth();
      allStartingContractsYearIncome.add(calculatePickupMonthReservationsIncome(chosenMonthStartingContracts,lastDayValue));
    }

    return allStartingContractsYearIncome;
  }

  public ArrayList<Double> fetchAllEndingContractsYearIncome(ArrayList<LocalDate> endDaysOfMonthDate) {

    ArrayList<Double> allEndingContractsYearIncome = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      //find første dag på måneden, ud fra listen af endDaysOfMonthDate
      LocalDate startDayOfMonthDate = endDaysOfMonthDate.get(i).with(TemporalAdjusters.firstDayOfMonth());
      //find listen af reservation, ud fra startdatoen ovenfor og endDaysOfMonthDate
      ArrayList<Reservation> chosenMonthEndingContracts = reservationRepo.fetchEndCurrentMonthReservations(startDayOfMonthDate, endDaysOfMonthDate.get(i));
      //Omregn månedens reservations til en samlet sum, og tilføj dem til listen
      int lastDayValue = endDaysOfMonthDate.get(i).getDayOfMonth();
      double incomeFromMonth = calculateReturnMonthReservationsIncome(chosenMonthEndingContracts,lastDayValue);
      allEndingContractsYearIncome.add(incomeFromMonth);
    }

    return allEndingContractsYearIncome;
  }

  public ArrayList<Double> fetchAllOngoingContractsYearIncome(ArrayList<LocalDate> endDaysOfMonthDate) {

    ArrayList<Double> allOngoingContractsYearIncome = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      //find første dag på måneden, ud fra listen af endDaysOfMonthDate
      LocalDate startDayOfMonthDate = endDaysOfMonthDate.get(i).with(TemporalAdjusters.firstDayOfMonth());
      //Hent månedens reservationer som en samlet sum, og tilføj dem til listen
      double incomeFromMonth = reservationRepo.fetchFullCurrentMonthReservationsIncome(startDayOfMonthDate,endDaysOfMonthDate.get(i));
      allOngoingContractsYearIncome.add(incomeFromMonth);
    }

    return allOngoingContractsYearIncome;
  }

  public ArrayList<Double> calculateIncomeMonths(ArrayList<Double> startingContractsIncome, ArrayList<Double> endingContractsIncome, ArrayList<Double> ongoingContractsIncome) {

    ArrayList<Double> calculateIncomeMonths = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      double monthTotalIncome = 0;
      monthTotalIncome += startingContractsIncome.get(i);
      monthTotalIncome += endingContractsIncome.get(i);
      monthTotalIncome += ongoingContractsIncome.get(i);
      monthTotalIncome = Math.round(monthTotalIncome * 100.0) / 100.0; //til to decimaler
      calculateIncomeMonths.add(monthTotalIncome);
    }

    return  calculateIncomeMonths;
  }

  public List<Reservation> fetchAllReservations() {
    return reservationRepo.fetchAllReservations();
  }

}
