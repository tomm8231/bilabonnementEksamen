package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Problem;
import com.example.bilabonnementeksamen.model.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class ProblemReportRepo {

  @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;

  public int createProblemReport(Reservation reservation, double totalPrice) {
  int number = 0;
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "INSERT INTO problem_report (car_vehicle_number, total_price, employee_id, customer_id) VALUES (?,?,?,?)";
      PreparedStatement pst = conn.prepareStatement(sql);

      pst.setInt(1, reservation.getCar_vehicle_number().getCar_vehicle_number());
      pst.setDouble(2, totalPrice);
      pst.setInt(3, reservation.getEmployee_id().getEmployee_id());
      pst.setInt(4, reservation.getCustomer_id().getCustomer_id());
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot make problem report");
      e.printStackTrace();
    }

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT MAX(report_id) FROM problem_report";
      PreparedStatement pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery(sql);

      while (rs.next()) {
        number = rs.getInt(1);
      }
    } catch (SQLException e) {
      System.err.println("Cannot find report id");
      e.printStackTrace();
    }

    return number;
  }

  public void createProblems(ArrayList<Problem> listOfProblems, int report_id){


    for (int i = 0; i < listOfProblems.size(); i++) {
      Problem problem = listOfProblems.get(i);


      try {
        Connection conn = DriverManager.getConnection(databaseURL, user, password);
        String sql = "INSERT INTO problem (problem_type, problem_description, problem_price, report_id) VALUES (?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, problem.getProblem_type());
        pst.setString(2, problem.getProblem_description());
        pst.setDouble(3, problem.getProblem_price());
        pst.setInt(4, report_id);
        pst.executeUpdate();

      } catch (SQLException e) {
        System.err.println("Cannot add problem");
        e.printStackTrace();
      }

    }
  }

  public int fetchReportId(Reservation reservation, double totalPrice) {

    int reportId = 0;


    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
          
             SELECT report_id FROM problem_report
             WHERE car_vehicle_number = ?
             AND total_price = ? 
             AND employee_id = ? 
             AND customer_id = ?
          """;
      PreparedStatement pst = conn.prepareStatement(sql);


      pst.setInt(1, reservation.getCar_vehicle_number().getCar_vehicle_number());
      pst.setDouble(2, totalPrice);
      pst.setInt(3, reservation.getEmployee_id().getEmployee_id());
      pst.setInt(4, reservation.getCustomer_id().getCustomer_id());
      ResultSet resultSet = pst.executeQuery();

      while (resultSet.next()){
        // 1 = Første kolonne i resultatset
        reportId = resultSet.getInt(1);
      }

    } catch (SQLException e) {
      System.err.println("Cannot find report_id");
      e.printStackTrace();
    }

    return reportId;
  }
}


