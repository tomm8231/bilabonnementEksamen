package com.example.bilabonnementeksamen.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Reservation {

  @Id
  private int reservation_ID;
  private int car_number;
  private Date pickup_date;

  public Reservation() {
  }

  public Reservation(int reservation_ID, int car_number, Date pickup_date) {
    this.reservation_ID = reservation_ID;
    this.car_number = car_number;
    this.pickup_date = pickup_date;
  }
}
