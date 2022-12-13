package com.example.bilabonnementeksamen.model;

public class Problem {

  private int problem_id;
  private String problem_type;
  private String problem_description;
  private double problem_price;
  private int report_id;


  public Problem() {}


  public Problem(int problem_id, String problem_type, String problem_description, double problem_price, int report_id) {
    this.problem_id = problem_id;
    this.problem_type = problem_type;
    this.problem_description = problem_description;
    this.problem_price = problem_price;
    this.report_id = report_id;
  }

  public int getProblem_id() {
    return problem_id;
  }

  public void setProblem_id(int problem_id) {
    this.problem_id = problem_id;
  }

  public String getProblem_type() {
    return problem_type;
  }

  public void setProblem_type(String problem_type) {
    this.problem_type = problem_type;
  }

  public String getProblem_description() {
    return problem_description;
  }

  public void setProblem_description(String problem_description) {
    this.problem_description = problem_description;
  }

  public double getProblem_price() {
    return problem_price;
  }

  public void setProblem_price(double problem_price) {
    this.problem_price = problem_price;
  }

  public int getReport_id() {
    return report_id;
  }

  public void setReport_id(int report_id) {
    this.report_id = report_id;
  }
}
