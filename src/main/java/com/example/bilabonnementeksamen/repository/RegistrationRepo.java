package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Car;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepo {

  public List<Car> fetchCarsByDate(Date startDate, Date endDate) {

    ArrayList<Car> cars = new ArrayList<Car>();

    cars.add(new Car("Ford", "Focus", Date.valueOf("2022-11-24"), Date.valueOf("2022-11-30")));
    cars.add(new Car("Opel", "Astra", Date.valueOf("2022-11-24"), Date.valueOf("2022-11-30")));
    // Bug: 1.12 - 3.12 viser Tesla og Mazda
    cars.add(new Car("Tesla", "X", Date.valueOf("2022-12-30"), Date.valueOf("2023-01-30")));
    cars.add(new Car("Mazda", "5", Date.valueOf("2022-12-30"), Date.valueOf("2023-01-30")));
    cars.add(new Car("TestBil", "null", null, null));

    ArrayList<Car> availableCars = new ArrayList<Car>();

    for (Car car : cars) {

      // OBS not working! Adds 1 to to check the car after the return of the car
     /*Instant endDateInstant = endDate.toInstant();
      Instant plus1day = endDateInstant.plus(1, ChronoUnit.DAYS);*/


      // create a timezone
      ZoneId zoneId = ZoneId.of("Europe/Paris");

      // create an LocalDate object using now(zoneId)
     //  LocalDate today = LocalDate.now(zoneId);

      //default time zone
      //ZoneId defaultZoneId = ZoneId.systemDefault();

      // convert from LocalDate to Date
      //Date date = (Date) Date.from(today.atStartOfDay(defaultZoneId).toInstant());

      // Findes det en sperre i Bootstrap som gør at man ikke kan vælge dato før dagens dato?

      // Logik: getStartDate er bilerne, mens startDate er datoen man selv har valg
      // car.getStartDate().after(date)
      if (car.getStartDate() == null && car.getEndDate() == null || (car.getStartDate().before(startDate) &&
      if (/*car.getStartDate().after(date) &&  */ car.getStartDate() == null && car.getEndDate() == null || (car.getStartDate().before(startDate) &&
          car.getEndDate().before(startDate)) || (car.getStartDate().after(endDate))){
        availableCars.add(car);
      }


    }
    return availableCars;
  }
}