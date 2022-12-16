package com.example.bilabonnementeksamen.model;

import java.sql.Time;
import java.util.Date;


public class Reservation {

  private int reservation_id;
  private Car car_vehicle_number;
  private Customer customer_id;
  private Location location_address;
  private Date pickup_date;
  private Date return_date;
  private Time pickup_time;
  private Time return_time;
  private double reservation_payment;
  private String reservation_comment;
  private Employee employee_id;


  public Reservation() {
  }

  public Reservation(int reservation_id, Car car_vehicle_number, Customer customer_id, Location location_address,
                     Date pickup_date, Date return_date, Time pickup_time, Time return_time,
                     double reservation_payment, String reservation_comment, Employee employee_id) {
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


  public Car getCar_vehicle_number() {
    return car_vehicle_number;
  }

  public void setCar_vehicle_number(Car car_vehicle_number) {
    this.car_vehicle_number = car_vehicle_number;
  }

  public Customer getCustomer_id() {
    return customer_id;
  }

  public void setCustomer_id(Customer customer_id) {
    this.customer_id = customer_id;
  }

  public Location getLocation_address() {
    return location_address;
  }

  public void setLocation_address(Location location_address) {
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

  public Time getReturn_time() {
    return return_time;
  }


  public double getReservation_payment() {
    return reservation_payment;
  }


  public String getReservation_comment() {
    return reservation_comment;
  }


  public Employee getEmployee_id() {
    return employee_id;
  }

  public void setEmployee_id(Employee employee_id) {
    this.employee_id = employee_id;
  }

  @Override
  public String toString() {
    return "Reservation{" +
        "reservation_id=" + reservation_id +
        ", car_vehicle_number=" + car_vehicle_number +
        ", customer_id=" + customer_id +
        ", location_address=" + location_address +
        ", pickup_date=" + pickup_date +
        ", return_date=" + return_date +
        ", pickup_time=" + pickup_time +
        ", return_time=" + return_time +
        ", reservation_payment=" + reservation_payment +
        ", reservation_comment='" + reservation_comment + '\'' +
        ", employee_id=" + employee_id +
        '}';
  }
}
