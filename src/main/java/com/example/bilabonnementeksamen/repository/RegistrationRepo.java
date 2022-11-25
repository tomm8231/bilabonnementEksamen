package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Car;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepo {

  public List<Car> fetchCarsByDate(Date date){

    ArrayList<Car> cars = new ArrayList<Car>();

    cars.add(new Car("Ford", "Focus", Date.valueOf("2022-11-25")));
    cars.add(new Car("Opel", "Astra", Date.valueOf("2022-11-23")));
    cars.add(new Car("Tesla", "X", null));
    cars.add(new Car("Mazda", "5", null));

    ArrayList<Car> availableCars = new ArrayList<Car>();

    for (Car car: cars) {

      if(car.getStartDato() == null || car.getStartDato().before(date)){
        availableCars.add(car);
      }
    }

    return availableCars;
  }

}
