package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Car;
import com.example.bilabonnementeksamen.model.Customer;
import com.example.bilabonnementeksamen.repository.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public List<Car> fetchCarsByDate() {
    // Tilføj dage til slutdato
    return registrationRepo.fetchCarsByDate();
  }

  public void unreserveCarById(int car_vehicle_number) {
    registrationRepo.unreserveCarById(car_vehicle_number);
  }

  public void unreserveAllCarsFromSession() {
    registrationRepo.unreserveAllCarsFromSession();
  }
}