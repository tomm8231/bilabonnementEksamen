package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.*;
import com.example.bilabonnementeksamen.repository.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

    if(startDateBooking.isBefore(LocalDate.now())){
      startDateBooking = LocalDate.now();
    }

    //opdeler i limited eller unlimited

    if (leaseType.equalsIgnoreCase("LIMITED")){

      LocalDate limitedEndDate = startDateBooking.plusDays(157);

      return registrationRepo.fetchCarsByDate(Date.valueOf(startDateBooking), Date.valueOf(limitedEndDate), leaseType.toUpperCase());

    } else  {

      return registrationRepo.fetchCarsByDate(Date.valueOf(startDate),Date.valueOf(endDate),leaseType.toUpperCase());
    }
  }

  public LocalDate modifyStartDate(String startDate) {

    //start dato tjekkes
    LocalDate startDateBooking = LocalDate.parse(startDate);

    if(startDateBooking.isBefore(LocalDate.now())){
      startDateBooking = LocalDate.now();
    }

    return startDateBooking;
  }

  public LocalDate modifyEndDate(String startDate, String endDate) {

    LocalDate startBooking = LocalDate.parse(startDate);
    LocalDate bookingEndDate;

    if(endDate == null){
      bookingEndDate = startBooking.plusDays(157);
      return bookingEndDate;
    }
    bookingEndDate= LocalDate.parse(endDate);
    return bookingEndDate;
  }


  public void unreserveCarById(int car_vehicle_number) {
    registrationRepo.unreserveCarById(car_vehicle_number);
  }


  public Subscription_type fetchSubscriptionById(int id) {
    return registrationRepo.fetchCarById(id).getSubscription_type_id();
  }

  public Fuel fetchFuelTypeById(int id) {
    return registrationRepo.fetchCarById(id).getCar_model_id().getCar_fuel_type();
  }

  public CarModel fetchCarModelById(int id) {
    return registrationRepo.fetchCarById(id).getCar_model_id();
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
                             int reservation_payment, String reservation_comment, Employee employee_id){

    //String der modtages er i "hr:min", men Time format i SQL kræver "hr:min:sec", derfor en tilføjelse til string
    String fixedPickupTime = pickup_time + ":00";
    String fixedReturnTime = return_time + ":00";

    registrationRepo.createReservation(car_vehicle_number, customer_id, location_address, pickup_date,
        return_date, fixedPickupTime, fixedReturnTime, reservation_payment, reservation_comment, employee_id);
  }

}
