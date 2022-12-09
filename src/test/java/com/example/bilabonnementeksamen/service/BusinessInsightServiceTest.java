package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Car;
import com.example.bilabonnementeksamen.model.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusinessInsightServiceTest {

  // arrange, act, Assert

  private Reservation reservationOne;
  private Reservation reservationTwo;
  private ArrayList<Reservation> reservationList;
  private ArrayList<Reservation>  reservationListIsEmpty;

  private Car car;
  private BusinessInsightService businessInsightService;

  private int currentMonthLength;

  @BeforeEach
  void setUp() {

    //arrange
    businessInsightService = new BusinessInsightService();
    currentMonthLength = 30;
    car = new Car();
    car.setCar_price_month(2000);

    reservationOne = new Reservation();
    reservationOne.setPickup_date(Date.valueOf("2022-11-15"));
    reservationOne.setCar_vehicle_number(car);
    reservationOne.setReturn_date(Date.valueOf("2023-11-15"));

    reservationTwo = new Reservation();
    reservationTwo.setPickup_date(Date.valueOf("2022-11-10"));
    reservationTwo.setReturn_date(Date.valueOf("2023-11-10"));
    reservationTwo.setCar_vehicle_number(car);

    reservationList = new ArrayList<>();
    reservationList.add(reservationOne);
    reservationList.add(reservationTwo);

    reservationListIsEmpty = new ArrayList<>();

  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void calculatePickupMonthReservationsIncome() {
    //act
    double result = businessInsightService.calculatePickupMonthReservationsIncome(reservationList, currentMonthLength);

    //assert
    double expected = 2333.33;
    assertEquals(expected, result, 0.01);
  }

  @Test
  void calculateReturnMonthReservationsIncome() {
    //act
    double result = businessInsightService.calculateReturnMonthReservationsIncome(reservationList, currentMonthLength);

    //assert
    double expected = 1666.66;
    assertEquals(expected, result, 0.01);

  }

  @Test
  void calculatePickupMonthReservationsIncomeIfEmpty() {
    //act
    double result = businessInsightService.calculatePickupMonthReservationsIncome(reservationListIsEmpty, currentMonthLength);

    //assert
    double expected = 0;
    assertEquals(expected, result, 0.01);
  }

  @Test
  void calculateReturnMonthReservationsIncomeIfEmpty() {
    //act
    double result = businessInsightService.calculateReturnMonthReservationsIncome(reservationListIsEmpty, currentMonthLength);

    //assert
    double expected = 0;
    assertEquals(expected, result, 0.01);

  }

}