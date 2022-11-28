package com.example.bilabonnementeksamen.model;

import java.util.Date;

public class Car {

  private String brand;
  private String model;
  private Date startDate;
  private Date endDate;


  public Car(){}

  public Car(String brand, String model, Date startDate, Date endDate){
    this.brand = brand;
    this.model = model;
    this.startDate = startDate;
    this.endDate = endDate;
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
}
