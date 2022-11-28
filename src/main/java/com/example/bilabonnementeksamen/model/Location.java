package com.example.bilabonnementeksamen.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Location {

  @Id
  private String location_adress;
  private int location_phone;
  private String location_name;

  public Location() {  }

  public Location(String location_adress, int location_phone, String location_name) {
    this.location_adress = location_adress;
    this.location_phone = location_phone;
    this.location_name = location_name;
  }

  public String getLocation_adress() {
    return location_adress;
  }

  public void setLocation_adress(String location_adress) {
    this.location_adress = location_adress;
  }

  public int getLocation_phone() {
    return location_phone;
  }

  public void setLocation_phone(int location_phone) {
    this.location_phone = location_phone;
  }

  public String getLocation_name() {
    return location_name;
  }

  public void setLocation_name(String location_name) {
    this.location_name = location_name;
  }
}
