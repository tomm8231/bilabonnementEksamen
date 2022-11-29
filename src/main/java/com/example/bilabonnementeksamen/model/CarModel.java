package com.example.bilabonnementeksamen.model;


public class CarModel {


  private int car_model_id;
  private String car_brand;
  private String car_model;
  private int car_hp;
  private Fuel car_fuel_type;
  private String car_gearbox_type;
  private double car_co2_km;
  private String car_energy_label;
  private int car_distance_amount;
  private String car_description;



  public CarModel() {
  }

  public CarModel(int car_model_id, String car_brand, String car_model,
                  int car_hp, Fuel car_fuel_type, String car_gearbox_type,
                  double car_co2_km, String car_energy_label,
                  int car_distance_amount, String car_description) {
    this.car_model_id = car_model_id;
    this.car_brand = car_brand;
    this.car_model = car_model;
    this.car_hp = car_hp;
    this.car_fuel_type = car_fuel_type;
    this.car_gearbox_type = car_gearbox_type;
    this.car_co2_km = car_co2_km;
    this.car_energy_label = car_energy_label;
    this.car_distance_amount = car_distance_amount;
    this.car_description = car_description;
  }

  public int getCar_model_id() {
    return car_model_id;
  }

  public void setCar_model_id(int car_model_id) {
    this.car_model_id = car_model_id;
  }

  public String getCar_brand() {
    return car_brand;
  }

  public void setCar_brand(String car_brand) {
    this.car_brand = car_brand;
  }

  public String getCar_model() {
    return car_model;
  }

  public void setCar_model(String car_model) {
    this.car_model = car_model;
  }

  public int getCar_hp() {
    return car_hp;
  }

  public void setCar_hp(int car_hp) {
    this.car_hp = car_hp;
  }

  public Fuel getCar_fuel_type() {
    return car_fuel_type;
  }

  public void setCar_fuel_type(Fuel car_fuel_type) {
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

  public int getCar_distance_amount() {
    return car_distance_amount;
  }

  public void setCar_distance_amount(int car_distance_amount) {
    this.car_distance_amount = car_distance_amount;
  }

  public String getCar_description() {
    return car_description;
  }

  public void setCar_description(String car_description) {
    this.car_description = car_description;
  }
}
