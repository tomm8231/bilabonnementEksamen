package com.example.bilabonnementeksamen.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;


public class Car {

  private String brand;
  private String model;


  public Car(){}

  public Car(String brand, String model){
    this.brand = brand;
    this.model = model;
  }


}
