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
    if (leaseType.equals("LIMITED")){

      LocalDate limitedEndDate = startDateBooking.plusDays(157);
      return registrationRepo.fetchCarsByDate(Date.valueOf(startDateBooking), Date.valueOf(limitedEndDate), leaseType);

    } else {

      return registrationRepo.fetchCarsByDate(Date.valueOf(startDateBooking),Date.valueOf(endDate),leaseType);
    }


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

  public void createLocation(Location location) {
    registrationRepo.createLocation(location);
  }

  public List<Location> fetchAllLocations() {
    return registrationRepo.fetchAllLocations();
  }
}
