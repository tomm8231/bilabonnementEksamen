package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.*;
import com.example.bilabonnementeksamen.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class RegistrationService {

  @Autowired
  private LocationRepo locationRepo;

  @Autowired
  private ReservationRepo reservationRepo;

  @Autowired
  private CustomerRepo customerRepo;

  @Autowired
  private CarRepo carRepo;

  @Autowired
  private EmployeeRepo employeeRepo;


  public Customer fetchCustomerByMail(String mail) {
    return customerRepo.fetchCustomerByMail(mail);
  }

  public void createCustomer(Customer customer) {
    customerRepo.createCustomer(customer);
  }

  public Car fetchCarById(int id) {
    return carRepo.fetchCarById(id);
  }

  public boolean fetchCarReservedStatus(int id) {
    // 0 er false (er ikke reserveret) og 1 er true (er reserveret)
    return carRepo.fetchCarReservedStatus(id) == 1;
  }

  public void reserveCarById(int id) {
    carRepo.reserveCarById(id);
  }

  public List<Car> fetchCarsByDate(String startDate, String endDate, String leaseType) {
    return carRepo.fetchCarsByDate(Date.valueOf(startDate), Date.valueOf(endDate), leaseType.toUpperCase());
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
      // Lægger til 150 + 3 dage fordi værkstedet skal have tid til at klargøre bilen. Kunden betaler for 150 dage
      bookingEndDate = startBooking.plusDays(153);
      return bookingEndDate;
    }
    bookingEndDate = LocalDate.parse(endDate);
    return bookingEndDate;
  }

  public void unreserveCarById(int car_vehicle_number) {
    carRepo.unreserveCarById(car_vehicle_number);
  }

  public void unreserveAllCarsFromSession() {
    carRepo.unreserveAllCarsFromSession();
  }

  public void createEmployee(Employee employee) {
    employeeRepo.createEmployee(employee);
  }

  public void createLocation(Location location) {
    locationRepo.createLocation(location);
  }

  public List<Location> fetchAllLocations() {
    return locationRepo.fetchAllLocations();
  }

  public Location fetchLocationByAddress(String locationAddress) {
    return locationRepo.fetchLocationByAddress(locationAddress);
  }

  public Employee fetchEmployeeById(int id) {
    return employeeRepo.fetchEmployeeById(id);
  }

  public void createReservation(Car car_vehicle_number, Customer customer_id, Location location_address,
                                LocalDate pickup_date, LocalDate return_date, String pickup_time, String return_time,
                                double reservation_payment, String reservation_comment, Employee employee_id) {

    //String der modtages er i "hr:min", men Time format i SQL kræver "hr:min:sec", derfor en tilføjelse til string
    String fixedPickupTime = pickup_time + ":00";
    String fixedReturnTime = return_time + ":00";

    reservationRepo.createReservation(car_vehicle_number, customer_id, location_address, pickup_date,
        return_date, fixedPickupTime, fixedReturnTime, reservation_payment, reservation_comment, employee_id);
  }


  // Marcus
  public boolean checkIfLocationExist(Location location) {

    List<Location> locations = locationRepo.fetchAllLocations();
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

      String name = employeeRepo.checkForDuplicateInitialsEmployee(employee);
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
    return employeeRepo.fetchEmployeeByInitials(initials);
  }


  public double calculatePaymentTotal(String months, Car car) {

    double paymentTotal = 0;
    int limitedMonths = 5; // fordi limited altid er 5 måneder

    try {
      // Pris per måned
      double pricePerMonth = car.getCar_price_month();

      if (car.getSubscription_type_id().getSubscription_type_name().equals("LIMITED")){
        paymentTotal = (pricePerMonth * limitedMonths);

      } else {
        paymentTotal = (pricePerMonth * Integer.parseInt(months));
      }

      return paymentTotal;

    } catch (IllegalArgumentException illegalArgumentException){
      System.err.println(illegalArgumentException.getMessage());
      System.out.println("Fejl input");
    }
    return paymentTotal;
  }

  //sebastian
  public ArrayList<Employee> fetchAllEployees() {
    return employeeRepo.fetchAllEmplyees();
  }

  public Reservation fetchReservationById(int reservationId) {
    return reservationRepo.fetchReservationById(reservationId);
  }


  public String addMonthsToStartReservationDate(String startDate, String months) {

    LocalDate endDateUnlimited = LocalDate.parse(startDate);
    endDateUnlimited = endDateUnlimited.plusMonths(Long.parseLong(months));

    return endDateUnlimited.toString();
  }


  public int checkReservationIdInUse(int reservationId) {
    String check = reservationRepo.checkIfReservationExist(reservationId);

    if (check != null) {
      return 1;
    } else {
      return 2;
    }
  }

}