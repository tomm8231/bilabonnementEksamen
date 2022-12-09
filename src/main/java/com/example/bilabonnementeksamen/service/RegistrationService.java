package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.*;
import com.example.bilabonnementeksamen.repository.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class RegistrationService {

  @Autowired
  RegistrationRepo registrationRepo;


  public Customer fetchCustomerByMail(String mail) {
    return registrationRepo.fetchCustomerByMail(mail);
  }

  public void createCustomer(Customer customer) {
    registrationRepo.createCustomer(customer);
  }

  public Car fetchCarById(int id) {
    return registrationRepo.fetchCarById(id);
  }

  public boolean fetchCarReservedStatus(int id) {
    // 0 er false (er ikke reserveret) og 1 er true (er reserveret)
    return registrationRepo.fetchCarReservedStatus(id) == 1;
  }

  public void reserveCarById(int id) {
    registrationRepo.reserveCarById(id);
  }

  public List<Car> fetchCarsByDate(String startDate, String endDate, String leaseType) {

    //start dato tjekkes
    LocalDate startDateBooking = LocalDate.parse(startDate);

    if (startDateBooking.isBefore(LocalDate.now())) {
      startDateBooking = LocalDate.now();
    }

    //opdeler i limited eller unlimited

    if (leaseType.equalsIgnoreCase("LIMITED")) {

      LocalDate limitedEndDate = startDateBooking.plusDays(157);

      return registrationRepo.fetchCarsByDate(Date.valueOf(startDateBooking), Date.valueOf(limitedEndDate), leaseType.toUpperCase());

    } else {
      // Lægge til 5 dage til returdatoen, så værkstedet kan nå at checke og frigive bilen.
      // TODO: OBS kunden skal ikke betale for disse ekstra 5 dage... Virker hellere ikke!
      LocalDate endDatePlus7days = Date.valueOf(endDate).toLocalDate().plusDays(5);

      return registrationRepo.fetchCarsByDate(Date.valueOf(startDate), Date.valueOf(endDatePlus7days), leaseType.toUpperCase());
    }
  }

  public LocalDate modifyStartDate(String startDate) {

    //start dato tjekkes
    LocalDate startDateBooking = LocalDate.parse(startDate);

    if (startDateBooking.isBefore(LocalDate.now())) {
      startDateBooking = LocalDate.now();
    }

    return startDateBooking;
  }

  public LocalDate modifyEndDateLimited(String startDate, String endDate) {

    LocalDate startBooking = LocalDate.parse(startDate);
    LocalDate bookingEndDate;

    if (endDate == null) {
      bookingEndDate = startBooking.plusDays(157);
      return bookingEndDate;
    }
    bookingEndDate = LocalDate.parse(endDate);
    return bookingEndDate;
  }


  public void unreserveCarById(int car_vehicle_number) {
    registrationRepo.unreserveCarById(car_vehicle_number);
  }


  public void unreserveAllCarsFromSession() {
    registrationRepo.unreserveAllCarsFromSession();
  }

  public List<Reservation> fetchAllReservations() {
    return registrationRepo.fetchAllReservations();
  }

  public void createEmployee(Employee employee) {
    registrationRepo.createEmployee(employee);
  }

  public void createLocation(Location location) {
    registrationRepo.createLocation(location);
  }

  public List<Location> fetchAllLocations() {
    return registrationRepo.fetchAllLocations();
  }

  public Location fetchLocationByAddress(String locationAddress) {
    return registrationRepo.fetchLocationByAddress(locationAddress);
  }

  public Employee fetchEmployeeById(int id) {
    return registrationRepo.fetchEmployeeById(id);
  }

  public void createReservation(Car car_vehicle_number, Customer customer_id, Location location_address,
                                LocalDate pickup_date, LocalDate return_date, String pickup_time, String return_time,
                                double reservation_payment, String reservation_comment, Employee employee_id) {

    //String der modtages er i "hr:min", men Time format i SQL kræver "hr:min:sec", derfor en tilføjelse til string
    String fixedPickupTime = pickup_time + ":00";
    String fixedReturnTime = return_time + ":00";

    registrationRepo.createReservation(car_vehicle_number, customer_id, location_address, pickup_date,
        return_date, fixedPickupTime, fixedReturnTime, reservation_payment, reservation_comment, employee_id);
  }


  // Marcus
  public boolean checkIfLocationExist(Location location) {

    List<Location> locations = registrationRepo.fetchAllLocations();
    Location newLocation;
    boolean locationAlreadyExist = false;

    // Hente en liste over alle locations
    for (int i = 0; i < locations.size(); i++) {

      newLocation = locations.get(i);

      // Location findes allerede med samme adresse
      if (location.getLocation_address().equals(newLocation.getLocation_address())) {
        locationAlreadyExist = true;
      }

    }
    return locationAlreadyExist;
  }

  // Tommy
  public int checkForDuplicateInitialsEmployee(Employee employee) {
    int number = 0;

    if (employee.getEmployee_name().contains(" ") && (!employee.getEmployee_initials().contains(" "))) {

      String name = registrationRepo.checkForDuplicateInitialsEmployee(employee);
      //Hvis initialer allerede findes
      if (name != null) {
        number = 1;
      } else { //Hvis initialer ikke allerede findes
        //registrationRepo.createEmployee(employee);
        number = 2;
      }
    }

    return number;
  }


  public Employee fetchEmployeeByInitials(String initials) {
    return registrationRepo.fetchEmployeeByInitials(initials);
  }


  // Fælles
  public double calculatePaymentTotal(String months, Car car) {

    // Pris per måned
    double pricePerMonth = car.getCar_price_month();

    double paymentTotal = (pricePerMonth * Integer.valueOf(months));

    return paymentTotal;
  }

  //sebastian
  public ArrayList<Employee> fetchAllEployees() {
    return registrationRepo.fetchAllEmplyees();
  }

  public Reservation fetchReservationById(int reservationId) {
    return registrationRepo.fetchReservationById(reservationId);
  }


  public String addMonthsToStartReservationDate(String startDate, String months) {

    LocalDate endDateUnlimited = LocalDate.parse(startDate);
    endDateUnlimited = endDateUnlimited.plusMonths(Long.parseLong(months));

    return endDateUnlimited.toString();
  }
}