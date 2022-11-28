package com.example.bilabonnementeksamen.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Subscription_type {

  @Id
  private int subscription_type_id;
  private String subscription_type_name;

  public Subscription_type() {  }

  public Subscription_type(int subscription_type_id, String subscription_type_name) {
    this.subscription_type_id = subscription_type_id;
    this.subscription_type_name = subscription_type_name;
  }

  public int getSubscription_type_id() {
    return subscription_type_id;
  }

  public void setSubscription_type_id(int subscription_type_id) {
    this.subscription_type_id = subscription_type_id;
  }

  public String getSubscription_type_name() {
    return subscription_type_name;
  }

  public void setSubscription_type_name(String subscription_type_name) {
    this.subscription_type_name = subscription_type_name;
  }
}
