package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.*;
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


  public ProblemReport fetchProblemReportById(int id) {
    ProblemReport problemReport = new ProblemReport();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
            SELECT * FROM car_leasing.problem_report
             INNER JOIN car
             USING (car_vehicle_number)
             INNER JOIN car_model
             USING (car_model_id)
             INNER JOIN fuel
             USING (car_fuel_type)
             INNER JOIN subscription_type
             USING (subscription_type_id)
             INNER JOIN employee
             USING (employee_id)
             INNER JOIN customer
             USING (customer_id)
             WHERE report_id = ?;          
             """;

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        int customer_id = rs.getInt(1);
        int employee_id = rs.getInt(2);
        int subscription_type_id = rs.getInt(3);
        String car_fuel_type = rs.getString(4);
        int car_model_id = rs.getInt(5);
        int car_vehicle_number = rs.getInt(6);
        int report_id = rs.getInt(7);
        double total_price = rs.getDouble(8);
        int car_chassis_number = rs.getInt(9);
        double car_price_month = rs.getDouble(10);
        int car_is_reserved = rs.getInt(11);
        String car_model = rs.getString(12);
        int car_hp = rs.getInt(13);
        String car_brand = rs.getString(14);
        String car_gearbox_type = rs.getString(15);
        int car_co2_km = rs.getInt(16);
        String car_energy_label = rs.getString(17);
        int car_distance_amount = rs.getInt(18);
        String car_description = rs.getString(19);
        String distance_unit = rs.getString(20);
        String subscription_type_name = rs.getString(21);
        String employee_initials = rs.getString(22);
        String employee_name = rs.getString(23);
        String customer_name = rs.getString(24);
        String customer_mail = rs.getString(25);
        String customer_address = rs.getString(26);
        String customer_phone_number = rs.getString(27);

        ArrayList<Problem> listOfProblems = new ArrayList<>();

        // Opretter et fuel objekt
        Fuel fuel = new Fuel(car_fuel_type, distance_unit);

        // Opretter et objekt af sub.type
        Subscription_type subscription_type = new Subscription_type(subscription_type_id, subscription_type_name);

        // Et carModel objekt skal have et fuel-objekt
        CarModel carModel = new CarModel(car_model_id, car_brand, car_model,
            car_hp, fuel, car_gearbox_type,
            car_co2_km, car_energy_label, car_distance_amount,
            car_description);

        //problemReport kræver customer
        Customer customer = new Customer(customer_id,customer_name,customer_mail,customer_address,customer_phone_number);

        //problemReport kræver car
        Car car = new Car(car_vehicle_number, car_chassis_number, carModel, car_price_month,
            subscription_type, car_is_reserved);

        //problemReport kræver employee
        Employee employee = new Employee(employee_id,employee_initials,employee_name);

        problemReport = new ProblemReport(report_id, car, total_price, employee, customer, listOfProblems);
      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
    return problemReport;
  }


  public ArrayList<Problem> fetchListOfProblemsById(int id) {
    ArrayList<Problem> problems = new ArrayList<>();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
             SELECT * FROM car_leasing.problem
             WHERE report_id = ?;
          """;
      PreparedStatement pst = conn.prepareStatement(sql);

      pst.setInt(1, id);

      ResultSet rs = pst.executeQuery();

      while (rs.next()){
        int problem_id = rs.getInt(1);
        String problem_type = rs.getString(2);
        String problem_description = rs.getString(3);
        double problem_price = rs.getDouble(4);
        int report_id = rs.getInt(5);

        Problem problem = new Problem(problem_id,problem_type,problem_description,problem_price,report_id);
        problems.add(problem);
      }

    } catch (SQLException e) {
      System.err.println("Cannot find report_id");
      e.printStackTrace();
    }
    return problems;
  }


  // Tommy
  public boolean checkIfProblemReportExist(int id) {

    boolean doesExist = true;

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM problem_report where report_id = ?";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, id);

      ResultSet rs = pst.executeQuery();

      if (!rs.next()) {
        doesExist = false;
      }

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
    return doesExist;
  }
}


