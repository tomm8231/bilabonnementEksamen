package com.example.bilabonnementeksamen.model;

import java.util.ArrayList;

public class ProblemReport {

  private int report_id;
  private Car car;
  private double total_price;
  private Employee employee;
  private Customer customer;
  private ArrayList<Problem> listOfProblems;

  public ProblemReport() {}

  public ProblemReport(int report_id, Car car, double total_price,
                       Employee employee, Customer customer, ArrayList<Problem> listOfProblems) {
    this.report_id = report_id;
    this.car = car;
    this.total_price = total_price;
    this.employee = employee;
    this.customer = customer;
    this.listOfProblems = listOfProblems;
  }


  public int getReport_id() {
    return report_id;
  }


  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public double getTotal_price() {
    return total_price;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setListOfProblems(ArrayList<Problem> listOfProblems) {
    this.listOfProblems = listOfProblems;
  }

  public ArrayList<Problem> getListOfProblems() {
    return listOfProblems;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }


}
