package com.example.bilabonnementeksamen.model;


public class Car {


  private int car_vehicle_number;
  private int car_chassis_number;
  private CarModel car_model_id;
  private double car_price_month;
  private Subscription_type subscription_type_id;
  private int car_is_reserved;


  public Car(){}

  public Car(int car_vehicle_number, int car_chassis_number, CarModel car_model_id,
             double car_price_month, Subscription_type subscription_type_id, int car_is_reserved) {
    this.car_vehicle_number = car_vehicle_number;
    this.car_chassis_number = car_chassis_number;
    this.car_model_id = car_model_id;
    this.car_price_month = car_price_month;
    this.subscription_type_id = subscription_type_id;
    this.car_is_reserved = car_is_reserved;
  }

  // get på Fueltype + distanceUnit
  // car er information expert på alt som hører ind under car

  public String getFuelType(){
    return car_model_id.getCar_fuel_type().getFuel_type();
  }

  public String getDistanceUnit(){
    return car_model_id.getCar_fuel_type().getDistance_unit();
  }

  public double getDistanceAmount(){
    return car_model_id.getCar_distance_amount();
  }


  public String getSubscriptionTypeName(){
    return subscription_type_id.getSubscription_type_name();
  }

  public String getCarDescription(){
    return car_model_id.getCar_description();
  }

  public String getCarBrand(){
    return car_model_id.getCar_brand();
  }

  public String getCarModel(){
    return car_model_id.getCar_model();
  }

  public int getCarHp(){
    return car_model_id.getCar_hp();
  }

  public String getGearBoxType(){
    return car_model_id.getCar_gearbox_type();
  }


  public double getCarCo2Km(){
    return car_model_id.getCar_co2_km();
  }

  public String getEnergyLabel(){
    return car_model_id.getCar_energy_label();
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

  public CarModel getCar_model_id() {
    return car_model_id;
  }

  public void setCar_model_id(CarModel car_model_id) {
    this.car_model_id = car_model_id;
  }

  public double getCar_price_month() {
    return car_price_month;
  }

  public void setCar_price_month(double car_price_month) {
    this.car_price_month = car_price_month;
  }

  public Subscription_type getSubscription_type_id() {
    return subscription_type_id;
  }

  public void setSubscription_type_id(Subscription_type subscription_type_id) {
    this.subscription_type_id = subscription_type_id;
  }

  public int getCar_is_reserved() {
    return car_is_reserved;
  }

  public void setCar_is_reserved(int car_is_reserved) {
    this.car_is_reserved = car_is_reserved;
  }
}
