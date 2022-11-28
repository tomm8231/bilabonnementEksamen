package com.example.bilabonnementeksamen.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CarModel {

  @Id
  private int car_model_id;
  private String car_model;
  private String car_brand;
  private String car_fuel_type;
  private String car_gearbox_type;
  private double car_co2_km;
  private String car_energy_label;
  private double car_km_per_litre;
  private String car_description;


  public CarModel() {
  }

  public CarModel(int car_model_id, String car_model, String car_brand,
                  String car_fuel_type, String car_gearbox_type,
                  double car_co2_km, String car_energy_label,
                  double car_km_per_litre, String car_description) {
    this.car_model_id = car_model_id;
    this.car_model = car_model;
    this.car_brand = car_brand;
    this.car_fuel_type = car_fuel_type;
    this.car_gearbox_type = car_gearbox_type;
    this.car_co2_km = car_co2_km;
    this.car_energy_label = car_energy_label;
    this.car_km_per_litre = car_km_per_litre;
    this.car_description = car_description;
  }

  public int getCar_model_id() {
    return car_model_id;
  }

  public void setCar_model_id(int car_model_id) {
    this.car_model_id = car_model_id;
  }

  public String getCar_model() {
    return car_model;
  }

  public void setCar_model(String car_model) {
    this.car_model = car_model;
  }

  public String getCar_brand() {
    return car_brand;
  }

  public void setCar_brand(String car_brand) {
    this.car_brand = car_brand;
  }

  public String getCar_fuel_type() {
    return car_fuel_type;
  }

  public void setCar_fuel_type(String car_fuel_type) {
    this.car_fuel_type = car_fuel_type;
  }

  public String getCar_gearbox_type() {
    return car_gearbox_type;
  }

  public void setCar_gearbox_type(String car_gearbox_type) {
    this.car_gearbox_type = car_gearbox_type;
  }

  public double getCar_co2_km() {
    return car_co2_km;
  }

  public void setCar_co2_km(double car_co2_km) {
    this.car_co2_km = car_co2_km;
  }

  public String getCar_energy_label() {
    return car_energy_label;
  }

  public void setCar_energy_label(String car_energy_label) {
    this.car_energy_label = car_energy_label;
  }

  public double getCar_km_per_litre() {
    return car_km_per_litre;
  }

  public void setCar_km_per_litre(double car_km_per_litre) {
    this.car_km_per_litre = car_km_per_litre;
  }

  public String getCar_description() {
    return car_description;
  }

  public void setCar_description(String car_description) {
    this.car_description = car_description;
  }
}
