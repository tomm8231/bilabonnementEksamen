package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

  private String months;
  private Car car;
  private RegistrationService registrationService;

  @BeforeEach
  void setUp() {
    // arrange
    months = "3";
    car = new Car();
    car.setCar_price_month(3000);
    registrationService = new RegistrationService();
  }

  @Test
  void calculatePaymentTotal() {

    // act
    double result = registrationService.calculatePaymentTotal(months, car);

    // assert
    double expected = 9000;
    assertEquals(expected, result);

  }

  // Daniel og Marcus
  @Test
  void addNegativeFail(){
   // assertEquals(expected, result);
    car.setCar_price_month(3000);
    // act og assert i samme, giver ikke så meget forretningsværdi, teknisk demo med lambda
    assertThrows(NullPointerException.class, () -> registrationService.calculatePaymentTotal(months, null),
        "Exception on wrong exchange input");

  }

}