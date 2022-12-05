package com.example.bilabonnementeksamen.model;

public class Problem {

  private int problem_id;
  private String problem_description;
  private double problem_price;


  public Problem() {}

  public Problem(int problem_id, String problem_description, double problem_price) {
    this.problem_id = problem_id;
    this.problem_description = problem_description;
    this.problem_price = problem_price;
  }


  public int getProblem_id() {
    return problem_id;
  }

  public void setProblem_id(int problem_id) {
    this.problem_id = problem_id;
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
}
