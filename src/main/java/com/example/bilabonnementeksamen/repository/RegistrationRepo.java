package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepo {

  public List<Car> fetchAllCars(){

    ArrayList<Car> cars = new ArrayList<Car>();

    cars.add(new Car("Ford", "Focus"));
    cars.add(new Car("Opel", "Astra"));
    cars.add(new Car("Tesla", "X"));
    cars.add(new Car("Mazda", "5"));

    return cars;
  }

}
