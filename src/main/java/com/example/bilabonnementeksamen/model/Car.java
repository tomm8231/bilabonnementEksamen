package com.example.bilabonnementeksamen.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;


public class Car {

  private String brand;
  private String model;
  private Date startDato;


  public Car(){}

  public Car(String brand, String model, Date startDato){
    this.brand = brand;
    this.model = model;
    this.startDato = startDato;
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


  public Date getStartDato() {
    return startDato;
  }

  public void setStartDato(Date startDato) {
    this.startDato = startDato;
  }

}
