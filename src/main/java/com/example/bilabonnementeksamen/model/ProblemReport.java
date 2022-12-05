package com.example.bilabonnementeksamen.model;

public class ProblemReport {

  private int report_id;
  private Car car;
  private double total_price;
  private Employee employee;
  private Customer customer;
  private Problem problem;

  public ProblemReport() {}

  public ProblemReport(int report_id, Car car, double total_price,
                       Employee employee, Customer customer, Problem problem) {
    this.report_id = report_id;
    this.car = car;
    this.total_price = total_price;
    this.employee = employee;
    this.customer = customer;
    this.problem = problem;
  }


  public int getReport_id() {
    return report_id;
  }

  public void setReport_id(int report_id) {
    this.report_id = report_id;
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

  public void setTotal_price(double total_price) {
    this.total_price = total_price;
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

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Problem getProblem() {
    return problem;
  }

  public void setProblem(Problem problem) {
    this.problem = problem;
  }
}
