package com.example.bilabonnementeksamen.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;
import java.util.Date;

@Entity
public class Reservation {

  @Id
  private int reservation_id;
  private int car_vehicle_number;
  private int customer_id;
  private String location_address;
  private Date pickup_date;
  private Date return_date;
  private Time pickup_time;
  private Time return_time;
  private double reservation_payment;
  private String reservation_comment;
  private int employee_id;

  public Reservation() {}

  public Reservation(int reservation_id, int car_vehicle_number, int customer_id, String location_address,
                     Date pickup_date, Date return_date, Time pickup_time, Time return_time, double reservation_payment,
                     String reservation_comment, int employee_id) {
    this.reservation_id = reservation_id;
    this.car_vehicle_number = car_vehicle_number;
    this.customer_id = customer_id;
    this.location_address = location_address;
    this.pickup_date = pickup_date;
    this.return_date = return_date;
    this.pickup_time = pickup_time;
    this.return_time = return_time;
    this.reservation_payment = reservation_payment;
    this.reservation_comment = reservation_comment;
    this.employee_id = employee_id;
  }

  public int getReservation_id() {
    return reservation_id;
  }

  public void setReservation_id(int reservation_id) {
    this.reservation_id = reservation_id;
  }

  public int getCar_vehicle_number() {
    return car_vehicle_number;
  }

  public void setCar_vehicle_number(int car_vehicle_number) {
    this.car_vehicle_number = car_vehicle_number;
  }

  public int getCustomer_id() {
    return customer_id;
  }

  public void setCustomer_id(int customer_id) {
    this.customer_id = customer_id;
  }

  public String getLocation_address() {
    return location_address;
  }

  public void setLocation_address(String location_address) {
    this.location_address = location_address;
  }

  public Date getPickup_date() {
    return pickup_date;
  }

  public void setPickup_date(Date pickup_date) {
    this.pickup_date = pickup_date;
  }

  public Date getReturn_date() {
    return return_date;
  }

  public void setReturn_date(Date return_date) {
    this.return_date = return_date;
  }

  public Time getPickup_time() {
    return pickup_time;
  }

  public void setPickup_time(Time pickup_time) {
    this.pickup_time = pickup_time;
  }

  public Time getReturn_time() {
    return return_time;
  }

  public void setReturn_time(Time return_time) {
    this.return_time = return_time;
  }

  public double getReservation_payment() {
    return reservation_payment;
  }

  public void setReservation_payment(double reservation_payment) {
    this.reservation_payment = reservation_payment;
  }

  public String getReservation_comment() {
    return reservation_comment;
  }

  public void setReservation_comment(String reservation_comment) {
    this.reservation_comment = reservation_comment;
  }

  public int getEmployee_id() {
    return employee_id;
  }

  public void setEmployee_id(int employee_id) {
    this.employee_id = employee_id;
  }
}
