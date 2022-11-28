package com.example.bilabonnementeksamen.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Car {

@Id
  private int car_vehicle_number;
  private int car_chassis_number;
  private int car_model_id;
  private int car_price_month;
  private int subscription_type_id;
  private int car_is_reserved;
  private String brand;
  private String model;
  private Date startDate;
  private Date endDate;

  public Car(){}

  public Car(int car_vehicle_number, int car_chassis_number, int car_model_id, int car_price_month, int subscription_type_id, int car_is_reserved, String brand, String model, Date startDate, Date endDate) {
    this.car_vehicle_number = car_vehicle_number;
    this.car_chassis_number = car_chassis_number;
    this.car_model_id = car_model_id;
    this.car_price_month = car_price_month;
    this.subscription_type_id = subscription_type_id;
    this.car_is_reserved = car_is_reserved;
    this.brand = brand;
    this.model = model;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public int getCar_vehicle_number() {
    return car_vehicle_number;
  }

  public void setCar_vehicle_number(int car_vehicle_number) {
    this.car_vehicle_number = car_vehicle_number;
  }

  public int getCar_chassis_number() {
    return car_chassis_number;
  }

  public void setCar_chassis_number(int car_chassis_number) {
    this.car_chassis_number = car_chassis_number;
  }

  public int getCar_model_id() {
    return car_model_id;
  }

  public void setCar_model_id(int car_model_id) {
    this.car_model_id = car_model_id;
  }

  public int getCar_price_month() {
    return car_price_month;
  }

  public void setCar_price_month(int car_price_month) {
    this.car_price_month = car_price_month;
  }

  public int getSubscription_type_id() {
    return subscription_type_id;
  }

  public void setSubscription_type_id(int subscription_type_id) {
    this.subscription_type_id = subscription_type_id;
  }

  public int getCar_is_reserved() {
    return car_is_reserved;
  }

  public void setCar_is_reserved(int car_is_reserved) {
    this.car_is_reserved = car_is_reserved;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
