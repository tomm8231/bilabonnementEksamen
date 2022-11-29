package com.example.bilabonnementeksamen.model;


public class Customer {


  private int customer_id;
  private String customer_name;
  private String customer_mail;
  private String customer_address;
  private String customer_phone_number;

  public Customer() {}

  public Customer(int customer_id, String customer_name, String customer_mail, String customer_address, String customer_phone_number) {
    this.customer_id = customer_id;
    this.customer_name = customer_name;
    this.customer_mail = customer_mail;
    this.customer_address = customer_address;
    this.customer_phone_number = customer_phone_number;
  }


  public int getCustomer_id() {
    return customer_id;
  }

  public void setCustomer_id(int customer_id) {
    this.customer_id = customer_id;
  }

  public String getCustomer_name() {
    return customer_name;
  }

  public void setCustomer_name(String customer_name) {
    this.customer_name = customer_name;
  }

  public String getCustomer_mail() {
    return customer_mail;
  }

  public void setCustomer_mail(String customer_mail) {
    this.customer_mail = customer_mail;
  }

  public String getCustomer_address() {
    return customer_address;
  }

  public void setCustomer_address(String customer_address) {
    this.customer_address = customer_address;
  }

  public String getCustomer_phone_number() {
    return customer_phone_number;
  }

  public void setCustomer_phone_number(String customer_phone_number) {
    this.customer_phone_number = customer_phone_number;
  }
}
