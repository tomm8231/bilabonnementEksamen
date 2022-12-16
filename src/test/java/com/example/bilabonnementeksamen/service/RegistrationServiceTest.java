package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Car;
import com.example.bilabonnementeksamen.model.Employee;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

  private String months;
  private Car car;
  private RegistrationService registrationService;
  private Employee employee;



  @BeforeEach
  void setUp() {
    // arrange
    months = "5";
    car = new Car();
    car.setCar_price_month(3000);
    registrationService = new RegistrationService();
  }


  // Daniel og Marcus
  @Test
  void addNegativeFail() {
    // assertEquals(expected, result);
    car.setCar_price_month(3000);
    // act og assert i samme, giver ikke så meget forretningsværdi, teknisk demo med lambda
    assertThrows(NullPointerException.class, () -> registrationService.calculatePaymentTotal(months, null),
        "Exception on wrong exchange input");
  }


  @Test
  void modifyStartDate() {
    String startDate;

    //Arrange
    startDate = "2022-12-11";

    //Act
    LocalDate result = registrationService.modifyStartDate(startDate);

    //Assert
    LocalDate expected = LocalDate.now();
    assertEquals(expected, result);

  }


  @Test
  void checkForDuplicateInitialsEmployeeNoSpaceInName() {

    // Arrange
    employee = new Employee(1, "mh", "MarcusHolje");

    // Act
    int result = registrationService.checkForDuplicateInitialsEmployee(employee);

    // Assert
    int expected = 0;

    assertEquals(result, expected);


  }

  @Test
  void checkForDuplicateInitialsEmployeeWithSpaceInInitials() {

    // Arrange
    employee = new Employee(1, "m h", "Marcus Holje");

    // Act
    int result = registrationService.checkForDuplicateInitialsEmployee(employee);

    // Assert
    int expected = 0;
    assertEquals(result, expected);


  }

}