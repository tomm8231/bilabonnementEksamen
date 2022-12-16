package com.example.bilabonnementeksamen.model;

public class Fuel {

  private String fuel_type;
  private String distance_unit;


  public Fuel() {}

  public Fuel(String fuel_type, String distance_unit) {
    this.fuel_type = fuel_type;
    this.distance_unit = distance_unit;
  }

  public String getFuel_type() {
    return fuel_type;
  }

  public String getDistance_unit() {
    return distance_unit;
  }

  public void setFuel_type(String fuel_type) {
    this.fuel_type = fuel_type;
  }

  public void setDistance_unit(String distance_unit) {
    this.distance_unit = distance_unit;
  }
}